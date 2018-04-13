package com.sixteencolorgames.supertechtweaks.multiplayer;

import com.google.common.collect.Maps;
import com.sixteencolorgames.supertechtweaks.enums.Research;
import com.sixteencolorgames.supertechtweaks.gui.ResearchProgress;
import com.sixteencolorgames.supertechtweaks.gui.ResearchToast;

import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SideOnly(Side.CLIENT)
public class ClientResearchManager {
	private static final Logger LOGGER = LogManager.getLogger();
	private final Minecraft mc;
	private final ResearchList researchList = new ResearchList();
	private final Map<Research, ResearchProgress> researchToProgress = Maps.<Research, ResearchProgress> newHashMap();
	@Nullable
	private ClientResearchManager.IListener listener;
	@Nullable
	private Research selectedTab;

	public ClientResearchManager(Minecraft p_i47380_1_) {
		this.mc = p_i47380_1_;
	}

	public void read(SPacketResearchInfo p_192799_1_) {
		if (p_192799_1_.isFirstSync()) {
			this.researchList.clear();
			this.researchToProgress.clear();
		}

		this.researchList.removeAll(p_192799_1_.getResearchsToRemove());
		this.researchList.loadResearchs(p_192799_1_.getResearchsToAdd());

		for (Entry<ResourceLocation, ResearchProgress> entry : p_192799_1_.getProgressUpdates().entrySet()) {
			Research research = this.researchList.getResearch(entry.getKey());

			if (research != null) {
				ResearchProgress researchprogress = entry.getValue();
				researchprogress.update(research.getCriteria(), research.getRequirements());
				this.researchToProgress.put(research, researchprogress);

				if (this.listener != null) {
					this.listener.onUpdateResearchProgress(research, researchprogress);
				}

				if (!p_192799_1_.isFirstSync() && researchprogress.isDone() && research.getDisplay() != null
						&& research.getDisplay().shouldShowToast()) {
					this.mc.getToastGui().add(new ResearchToast(research));
				}
			} else {
				LOGGER.warn("Server informed client about progress for unknown research " + entry.getKey());
			}
		}
	}

	public ResearchList getResearchList() {
		return this.researchList;
	}

	public void setSelectedTab(@Nullable Research p_194230_1_, boolean tellServer) {
		NetHandlerPlayClient nethandlerplayclient = this.mc.getConnection();

		if (nethandlerplayclient != null && p_194230_1_ != null && tellServer) {
			nethandlerplayclient.sendPacket(CPacketSeenResearch.openedTab(p_194230_1_));
		}

		if (this.selectedTab != p_194230_1_) {
			this.selectedTab = p_194230_1_;

			if (this.listener != null) {
				this.listener.setSelectedTab(p_194230_1_);
			}
		}
	}

	public void setListener(@Nullable ClientResearchManager.IListener p_192798_1_) {
		this.listener = p_192798_1_;
		this.researchList.setListener(p_192798_1_);

		if (p_192798_1_ != null) {
			for (Entry<Research, ResearchProgress> entry : this.researchToProgress.entrySet()) {
				p_192798_1_.onUpdateResearchProgress(entry.getKey(), entry.getValue());
			}

			p_192798_1_.setSelectedTab(this.selectedTab);
		}
	}

	@SideOnly(Side.CLIENT)
	public interface IListener extends ResearchList.Listener {
		void onUpdateResearchProgress(Research p_191933_1_, ResearchProgress p_191933_2_);

		void setSelectedTab(@Nullable Research p_193982_1_);
	}
}