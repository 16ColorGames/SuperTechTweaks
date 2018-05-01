package com.sixteencolorgames.supertechtweaks.network;

import java.util.HashMap;
import java.util.UUID;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.world.ResearchSavedData;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class UpdateResearchUnlocksPacket implements IMessage {
	public static class Handler implements IMessageHandler<UpdateResearchUnlocksPacket, IMessage> {

		@Override
		public IMessage onMessage(UpdateResearchUnlocksPacket message, MessageContext ctx) {
			if (SuperTechTweaksMod.proxy.getWorld(null) != null) {
				ResearchSavedData.get(SuperTechTweaksMod.proxy.getWorld(null)).readFromUpdate(message.id, message.locs);
				ResearchSavedData.get(SuperTechTweaksMod.proxy.getWorld(null)).markDirty();
			}

			return null;
		}

	}

	UUID id;
	HashMap<ResourceLocation, Integer> locs;

	public UpdateResearchUnlocksPacket() {
	}

	public UpdateResearchUnlocksPacket(EntityPlayer player) {
		id = player.getUniqueID();
		locs = ResearchSavedData.get(player.world).getPlayerResearched(player);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = UUID.fromString(PacketHandler.readStringFromBuffer(buf));
		locs = new HashMap();
		int len = buf.readInt();
		for (int i = 0; i < len; i++) {
			locs.put(new ResourceLocation(PacketHandler.readStringFromBuffer(buf)), buf.readInt());
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketHandler.writeStringToBuffer(buf, id.toString());
		buf.writeInt(locs.size());
		locs.forEach((rl, i) -> {
			PacketHandler.writeStringToBuffer(buf, rl.toString());
			buf.writeInt(i);
		});
	}

}
