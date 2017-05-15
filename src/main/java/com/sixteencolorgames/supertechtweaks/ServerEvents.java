/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.network.PacketHandler;
import com.sixteencolorgames.supertechtweaks.network.UpdateOresPacket;
import com.sixteencolorgames.supertechtweaks.world.OreSavedData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 *
 * @author oa10712
 */
public class ServerEvents {

    @SubscribeEvent
    public void onPlayerEnterChunk(EntityEvent.EnteringChunk e) {
        if (e.getEntity() instanceof EntityPlayer) {
            handleOreUpdate((EntityPlayer) e.getEntity(), e.getNewChunkX(), e.getNewChunkZ());
        }
    }

    private void handleOreUpdate(EntityPlayer e, int newChunkX, int newChunkZ) {
        System.out.println("Sending ore data: " + e.toString());
        PacketHandler.INSTANCE.sendTo(new UpdateOresPacket(OreSavedData.get(e.worldObj), newChunkX, newChunkZ), (EntityPlayerMP) e);
    }
}
