package com.sixteencolorgames.supertechtweaks.multiplayer;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SPacketResearchInfo implements Packet<INetHandlerPlayClient> {
	private boolean firstSync;
	private Map<ResourceLocation, Research.Builder> researchsToAdd;
	private Set<ResourceLocation> researchsToRemove;
	private Map<ResourceLocation, ResearchProgress> progressUpdates;

	public SPacketResearchInfo() {
	}

	public SPacketResearchInfo(boolean p_i47519_1_, Collection<Research> p_i47519_2_, Set<ResourceLocation> p_i47519_3_,
			Map<ResourceLocation, ResearchProgress> p_i47519_4_) {
		this.firstSync = p_i47519_1_;
		this.researchsToAdd = Maps.<ResourceLocation, Research.Builder> newHashMap();

		for (Research research : p_i47519_2_) {
			this.researchsToAdd.put(research.getId(), research.copy());
		}

		this.researchsToRemove = p_i47519_3_;
		this.progressUpdates = Maps.<ResourceLocation, ResearchProgress> newHashMap(p_i47519_4_);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient handler) {
		handler.handleResearchInfo(this);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer buf) throws IOException {
		this.firstSync = buf.readBoolean();
		this.researchsToAdd = Maps.<ResourceLocation, Research.Builder> newHashMap();
		this.researchsToRemove = Sets.<ResourceLocation> newLinkedHashSet();
		this.progressUpdates = Maps.<ResourceLocation, ResearchProgress> newHashMap();
		int i = buf.readVarInt();

		for (int j = 0; j < i; ++j) {
			ResourceLocation resourcelocation = buf.readResourceLocation();
			Research.Builder research$builder = Research.Builder.readFrom(buf);
			this.researchsToAdd.put(resourcelocation, research$builder);
		}

		i = buf.readVarInt();

		for (int k = 0; k < i; ++k) {
			ResourceLocation resourcelocation1 = buf.readResourceLocation();
			this.researchsToRemove.add(resourcelocation1);
		}

		i = buf.readVarInt();

		for (int l = 0; l < i; ++l) {
			ResourceLocation resourcelocation2 = buf.readResourceLocation();
			this.progressUpdates.put(resourcelocation2, ResearchProgress.fromNetwork(buf));
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer buf) throws IOException {
		buf.writeBoolean(this.firstSync);
		buf.writeVarInt(this.researchsToAdd.size());

		for (Entry<ResourceLocation, Research.Builder> entry : this.researchsToAdd.entrySet()) {
			ResourceLocation resourcelocation = entry.getKey();
			Research.Builder research$builder = entry.getValue();
			buf.writeResourceLocation(resourcelocation);
			research$builder.writeTo(buf);
		}

		buf.writeVarInt(this.researchsToRemove.size());

		for (ResourceLocation resourcelocation1 : this.researchsToRemove) {
			buf.writeResourceLocation(resourcelocation1);
		}

		buf.writeVarInt(this.progressUpdates.size());

		for (Entry<ResourceLocation, ResearchProgress> entry1 : this.progressUpdates.entrySet()) {
			buf.writeResourceLocation(entry1.getKey());
			((ResearchProgress) entry1.getValue()).serializeToNetwork(buf);
		}
	}

	@SideOnly(Side.CLIENT)
	public Map<ResourceLocation, Research.Builder> getResearchsToAdd() {
		return this.researchsToAdd;
	}

	@SideOnly(Side.CLIENT)
	public Set<ResourceLocation> getResearchsToRemove() {
		return this.researchsToRemove;
	}

	@SideOnly(Side.CLIENT)
	public Map<ResourceLocation, ResearchProgress> getProgressUpdates() {
		return this.progressUpdates;
	}

	@SideOnly(Side.CLIENT)
	public boolean isFirstSync() {
		return this.firstSync;
	}
}