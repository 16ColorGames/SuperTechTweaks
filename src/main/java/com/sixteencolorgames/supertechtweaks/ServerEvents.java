/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.network.PacketHandler;
import com.sixteencolorgames.supertechtweaks.network.UpdateOresPacket;
import com.sixteencolorgames.supertechtweaks.world.OreSavedData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

/**
 *
 * @author oa10712
 */
public class ServerEvents {

    @SubscribeEvent
    public void onPlayerEnterChunk(EntityEvent.EnteringChunk e) {
        if (e.getEntity() instanceof EntityPlayerMP) {
            handleOreUpdate((EntityPlayerMP) e.getEntity(), e.getNewChunkX(), e.getNewChunkZ());
        }
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent e) {
        if (e.player instanceof EntityPlayerMP) {
            for (int x = -4; x < 5; x++) {
                for (int z = -4; z < 5; z++) {
                    handleOreUpdate((EntityPlayerMP) e.player, e.player.chunkCoordX + x, e.player.chunkCoordZ + z);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerEnterDimension(PlayerEvent.PlayerChangedDimensionEvent e) {
        if (e.player instanceof EntityPlayerMP) {
            for (int x = -4; x < 5; x++) {
                for (int z = -4; z < 5; z++) {
                    handleOreUpdate((EntityPlayerMP) e.player, e.player.chunkCoordX + x, e.player.chunkCoordZ + z);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent e) {
        if (e.player instanceof EntityPlayerMP) {
            for (int x = -4; x < 5; x++) {
                for (int z = -4; z < 5; z++) {
                    handleOreUpdate((EntityPlayerMP) e.player, e.player.chunkCoordX + x, e.player.chunkCoordZ + z);
                }
            }
        }
    }

    private void handleOreUpdate(EntityPlayerMP e, int newChunkX, int newChunkZ) {
        if (Minecraft.getMinecraft().theWorld != null) {
            UpdateOresPacket packet = new UpdateOresPacket(OreSavedData.get(e.worldObj), newChunkX, newChunkZ);
            PacketHandler.INSTANCE.sendTo(packet, e);
        }
    }
}
