package com.sixteencolorgames.supertechtweaks.compat;

import com.sixteencolorgames.supertechtweaks.compat.waila.WailaCompatibility;

import net.minecraftforge.fml.common.Loader;

public class MainCompatHandler {
    public static void registerWaila() {
        if (Loader.isModLoaded("Waila")) {
            WailaCompatibility.register();
        }
    }
}
