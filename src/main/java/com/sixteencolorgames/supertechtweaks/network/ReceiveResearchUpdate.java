package com.sixteencolorgames.supertechtweaks.network;

import com.sixteencolorgames.supertechtweaks.tileentities.researchselector.ResearchSelectorTileEntity;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * The MessageHandlerOnServer is used to process the network message once it has
 * arrived on the Server side. WARNING! In 1.8 onwards the MessageHandler now
 * runs in its own thread. This means that if your onMessage code calls any
 * vanilla objects, it may cause crashes or subtle problems that are hard to
 * reproduce. Your onMessage handler should create a task which is later
 * executed by the client or server thread as appropriate - see below. User: The
 * Grey Ghost Date: 15/01/2015
 */
public class ReceiveResearchUpdate implements IMessageHandler<ResearchUpdatePacket, IMessage> {
	/**
	 * Called when a message is received of the appropriate type. CALLED BY THE
	 * NETWORK THREAD
	 * 
	 * @param message
	 *            The message
	 */
	@Override
	public IMessage onMessage(final ResearchUpdatePacket message, MessageContext ctx) {
		if (ctx.side != Side.SERVER) {
			System.err.println("ResearchUpdatePacket received on wrong side:" + ctx.side);
			return null;
		}
		if (!message.isMessageValid()) {
			System.err.println("ResearchUpdatePacket was invalid" + message.toString());
			return null;
		}

		// we know for sure that this handler is only used on the server side,
		// so it is ok to assume
		// that the ctx handler is a serverhandler, and that WorldServer exists.
		// Packets received on the client side must be handled differently! See
		// MessageHandlerOnClient

		final EntityPlayerMP sendingPlayer = ctx.getServerHandler().player;
		if (sendingPlayer == null) {
			System.err.println("EntityPlayerMP was null when ResearchUpdatePacket was received");
			return null;
		}

		// This code creates a new task which will be executed by the server
		// during the next tick,
		// for example see MinecraftServer.updateTimeLightAndEntities(), just
		// under section
		// this.theProfiler.startSection("jobs");
		// In this case, the task is to call
		// messageHandlerOnServer.processMessage(message, sendingPlayer)
		final WorldServer playerWorldServer = sendingPlayer.getServerWorld();
		playerWorldServer.addScheduledTask(new Runnable() {
			@Override
			public void run() {
				processMessage(message, sendingPlayer);
			}
		});

		return null;
	}

	// This message is called from the Server thread.
	void processMessage(ResearchUpdatePacket message, EntityPlayerMP sendingPlayer) {
		System.out.println("Processing research packet");
		switch (message.getMessageType()) {
		case ResearchUpdatePacket.SELECTION_UPDATE:
			System.out.println("updating entity: " + message.getBlockPos() + " to " + message.getSelected());
			ResearchSelectorTileEntity tileEntity = (ResearchSelectorTileEntity) sendingPlayer.getEntityWorld()
					.getTileEntity(message.getBlockPos());
			tileEntity.setSelected(message.getSelected());
			tileEntity.markDirty();
			break;
		}
		return;
	}
}