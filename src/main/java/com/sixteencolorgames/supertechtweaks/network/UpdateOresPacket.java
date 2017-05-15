/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.network;

import com.sixteencolorgames.supertechtweaks.world.OreSavedData;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 *
 * @author oa10712
 */
public class UpdateOresPacket implements IMessage {

    private NBTTagCompound tag;

    public UpdateOresPacket() {
    }

    public UpdateOresPacket(OreSavedData a) {
        this.tag = a.serializeNBT();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        tag = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, tag);
    }

    public static class Handler implements IMessageHandler<UpdateOresPacket, IMessage> {

        @Override
        public IMessage onMessage(UpdateOresPacket message, MessageContext ctx) {
            System.out.println("Recieved ore update:" + message.toString());
            OreSavedData.get(Minecraft.getMinecraft().theWorld).deserializeNBT(message.tag);
            OreSavedData.get(Minecraft.getMinecraft().theWorld).markDirty();
            return null;
        }

    }

}
