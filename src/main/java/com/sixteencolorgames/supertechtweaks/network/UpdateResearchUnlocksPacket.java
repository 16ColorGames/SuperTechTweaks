package com.sixteencolorgames.supertechtweaks.network;

import java.util.UUID;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.world.ResearchSavedData;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.NonNullList;
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
	NonNullList<ResourceLocation> locs;

	public UpdateResearchUnlocksPacket() {
	}

	public UpdateResearchUnlocksPacket(EntityPlayer player) {
		id = player.getUniqueID();
		locs = ResearchSavedData.get(player.world).getPlayerResearched(player);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = UUID.fromString(PacketHandler.readStringFromBuffer(buf));
		locs = NonNullList.create();
		int len = buf.readInt();
		for (int i = 0; i < len; i++) {
			locs.add(new ResourceLocation(PacketHandler.readStringFromBuffer(buf)));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketHandler.writeStringToBuffer(buf, id.toString());
		buf.writeInt(locs.size());
		for (ResourceLocation l : locs) {
			PacketHandler.writeStringToBuffer(buf, l.toString());
		}
	}

}
