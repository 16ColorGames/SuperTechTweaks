/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 *
 * @author oa10712
 */
public class PacketHandler {

	private static int packetId = 0;

	public static SimpleNetworkWrapper INSTANCE = null;

	public static int nextID() {
		return packetId++;
	}

	public static String readStringFromBuffer(ByteBuf buf) {
		int len = buf.readInt();
		String ret = "";
		for (int i = 0; i < len; i++) {
			ret += buf.readChar();
		}
		return ret;
	}

	public static void registerMessages() {
		INSTANCE.registerMessage(UpdateOresPacket.Handler.class, UpdateOresPacket.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(UpdateResearchUnlocksPacket.Handler.class, UpdateResearchUnlocksPacket.class, nextID(),
				Side.CLIENT);

	}

	public static void registerMessages(String channelName) {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		registerMessages();
	}

	public static void writeStringToBuffer(ByteBuf buf, String str) {
		buf.writeInt(str.length());
		for (int i = 0; i < str.length(); i++) {
			buf.writeChar(str.charAt(i));
		}
	}

	public PacketHandler() {
	}
}
