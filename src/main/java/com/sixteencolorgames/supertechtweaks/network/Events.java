/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.network;

import com.sixteencolorgames.supertechtweaks.world.OreSavedData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 *
 * @author oa10712
 */
public class Events {
    

    @SubscribeEvent
    public void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent e) {
        handleOreUpdate(e);
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent e) {
        handleOreUpdate(e);
    }

    @SubscribeEvent
    public void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent e) {
        handleOreUpdate(e);
    }

    private void handleOreUpdate(PlayerEvent e) {
        System.out.println("Sending ore data: " + e.toString());
        PacketHandler.INSTANCE.sendTo(new UpdateOresPacket(OreSavedData.get(e.player.worldObj)), (EntityPlayerMP) e.player);
    }
}
