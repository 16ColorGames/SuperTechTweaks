package com.sixteencolorgames.supertechtweaks.multiplayer;

import java.io.IOException;
import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.enums.Research;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CPacketSeenResearch implements Packet<INetHandlerPlayServer> {
	private CPacketSeenResearch.Action action;
	private ResourceLocation tab;

	public CPacketSeenResearch() {
	}

	@SideOnly(Side.CLIENT)
	public CPacketSeenResearch(CPacketSeenResearch.Action p_i47595_1_, @Nullable ResourceLocation p_i47595_2_) {
		this.action = p_i47595_1_;
		this.tab = p_i47595_2_;
	}

	@SideOnly(Side.CLIENT)
	public static CPacketSeenResearch openedTab(Research p_194163_0_) {
		return new CPacketSeenResearch(CPacketSeenResearch.Action.OPENED_TAB, p_194163_0_.getId());
	}

	@SideOnly(Side.CLIENT)
	public static CPacketSeenResearch closedScreen() {
		return new CPacketSeenResearch(CPacketSeenResearch.Action.CLOSED_SCREEN, (ResourceLocation) null);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer buf) throws IOException {
		this.action = (CPacketSeenResearch.Action) buf.readEnumValue(CPacketSeenResearch.Action.class);

		if (this.action == CPacketSeenResearch.Action.OPENED_TAB) {
			this.tab = buf.readResourceLocation();
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer buf) throws IOException {
		buf.writeEnumValue(this.action);

		if (this.action == CPacketSeenResearch.Action.OPENED_TAB) {
			buf.writeResourceLocation(this.tab);
		}
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayServer handler) {
		handler.handleSeenResearchs(this);
	}

	public CPacketSeenResearch.Action getAction() {
		return this.action;
	}

	public ResourceLocation getTab() {
		return this.tab;
	}

	public static enum Action {
		OPENED_TAB, CLOSED_SCREEN;
	}
}