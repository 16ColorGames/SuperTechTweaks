package com.sixteencolorgames.supertechtweaks.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * This Network Message is sent from the client to the server, to tell it to
 * spawn projectiles at a particular location. Typical usage: PREQUISITES: have
 * previously setup SimpleNetworkWrapper, registered the message class and the
 * handler
 *
 * 1) User creates an AirStrikeMessageToServer(projectile, targetCoordinates) 2)
 * simpleNetworkWrapper.sendToServer(airstrikeMessageToServer); 3) network code
 * calls airstrikeMessageToServer.toBytes() to copy the message member variables
 * to a ByteBuffer, ready for sending ... bytes are sent over the network and
 * arrive at the server.... 4) network code creates AirStrikeMessageToServer()
 * 5) network code calls airstrikeMessageToServer.fromBytes() to read from the
 * ByteBuffer into the member variables 6) the
 * handler.onMessage(airStrikeMessageToServer) is called to process the message
 *
 * User: The Grey Ghost Date: 15/01/2015
 */
public class ResearchUpdatePacket implements IMessage {
	public static final int SELECTION_UPDATE = 0;
	private BlockPos blockPos;
	private ResourceLocation selected;
	private int messageType;

	private boolean messageIsValid;
	private boolean unlock = false;

	// for use by the message handler only.
	public ResearchUpdatePacket() {
		messageIsValid = false;
	}

	// for use by the message handler only.
	public ResearchUpdatePacket(int messageType, ResourceLocation selected, BlockPos blockPos) {
		this.messageType = messageType;
		this.selected = selected;
		this.blockPos = blockPos;
		messageIsValid = true;
	}

	/**
	 * Called by the network code once it has received the message bytes over
	 * the network. Used to read the ByteBuf contents into your member variables
	 *
	 * @param buf
	 */
	@Override
	public void fromBytes(ByteBuf buf) {
		try {

			int x = buf.readInt();
			int y = buf.readInt();
			int z = buf.readInt();
			blockPos = new BlockPos(x, y, z);
			messageType = buf.readInt();
			switch (messageType) {
			case SELECTION_UPDATE:
				selected = new ResourceLocation(PacketHandler.readStringFromBuffer(buf),
						PacketHandler.readStringFromBuffer(buf));
				unlock = buf.readBoolean();
				break;
			}

			// these methods may also be of use for your code:
			// for Itemstacks - ByteBufUtils.readItemStack()
			// for NBT tags ByteBufUtils.readTag();
			// for Strings: ByteBufUtils.readUTF8String();

		} catch (IndexOutOfBoundsException ioe) {
			System.err.println("Exception while reading ResearchUpdatePacket: " + ioe);
			return;
		}
		messageIsValid = true;
	}

	public BlockPos getBlockPos() {
		return blockPos;
	}

	public int getMessageType() {
		return messageType;
	}

	public ResourceLocation getSelected() {
		return selected;
	}

	public boolean getUnlocked() {
		return unlock;
	}

	public boolean isMessageValid() {
		return messageIsValid;
	}

	public void setUnlock(boolean b) {
		unlock = b;

	}

	/**
	 * Called by the network code. Used to write the contents of your message
	 * member variables into the ByteBuf, ready for transmission over the
	 * network.
	 *
	 * @param buf
	 */
	@Override
	public void toBytes(ByteBuf buf) {
		if (!messageIsValid) {
			return;
		}
		buf.writeInt(blockPos.getX());
		buf.writeInt(blockPos.getY());
		buf.writeInt(blockPos.getZ());
		buf.writeInt(messageType);
		switch (messageType) {
		case SELECTION_UPDATE:
			PacketHandler.writeStringToBuffer(buf, selected.getResourceDomain());
			PacketHandler.writeStringToBuffer(buf, selected.getResourcePath());
			buf.writeBoolean(unlock);
			break;
		}

		// these methods may also be of use for your code:
		// for Itemstacks - ByteBufUtils.writeItemStack()
		// for NBT tags ByteBufUtils.writeTag();
		// for Strings: ByteBufUtils.writeUTF8String();
	}

	@Override
	public String toString() {
		return "ResearchUpdatePacket[location=" + blockPos.toString() + ", type=" + messageType + ", value="
				+ selected.toString() + "]";
	}

}