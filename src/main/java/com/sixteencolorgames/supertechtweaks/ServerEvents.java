/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.network.PacketHandler;
import com.sixteencolorgames.supertechtweaks.network.UpdateOresPacket;
import com.sixteencolorgames.supertechtweaks.world.OreSavedData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 *
 * @author oa10712
 */
public class ServerEvents {

    HashMap<UUID, ArrayList<Pair>> sentChunks = new HashMap();

    @SubscribeEvent
    public void onPlayerEnterChunk(EntityEvent.EnteringChunk e) {
        if (e.getEntity() instanceof EntityPlayerMP) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    handleOreUpdate((EntityPlayerMP) e.getEntity(), e.getEntity().chunkCoordX + x, e.getEntity().chunkCoordZ + z);//sent the player the nearby chunks on startup
                }
            }
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
    public void onPlayerLogin(EntityJoinWorldEvent e) {
        if (e.getEntity() instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) e.getEntity();
            sentChunks.put(player.getUniqueID(), new ArrayList());//add the player to the chunk tracker
            for (int x = -4; x < 5; x++) {
                for (int z = -4; z < 5; z++) {
                    handleOreUpdate((EntityPlayerMP) player, player.chunkCoordX + x, player.chunkCoordZ + z);//sent the player the nearby chunks on startup
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent e) {
        if (e.player instanceof EntityPlayerMP) {
            sentChunks.remove(e.player.getUniqueID());//remove the player from the chunk tracker
        }
    }

    private void handleOreUpdate(EntityPlayerMP e, int newChunkX, int newChunkZ) {
        if (e.worldObj != null) {
            if (!sentChunks.get(e.getUniqueID()).contains(new Pair(newChunkX, newChunkZ))) {//Check if we have sent this chunk data already
                UpdateOresPacket packet = new UpdateOresPacket(OreSavedData.get(e.worldObj), newChunkX, newChunkZ);
                PacketHandler.INSTANCE.sendTo(packet, e);
                sentChunks.get(e.getUniqueID()).add(new Pair(newChunkX, newChunkZ));
            }
        }
    }
}
