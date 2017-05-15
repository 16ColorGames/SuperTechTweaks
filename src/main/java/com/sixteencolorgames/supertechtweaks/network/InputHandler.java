/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.network;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

/**
 *
 * @author oa10712
 */
public class InputHandler {

    @SubscribeEvent
    public void onKeyInput(PlayerLoggedInEvent event) {
        PacketHandler.INSTANCE.sendToServer(new UpdateOresPacket());
    }
}
