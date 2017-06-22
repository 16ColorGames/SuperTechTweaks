package com.sixteencolorgames.supertechtweaks.compat;

import com.sixteencolorgames.supertechtweaks.compat.crafttweaker.CraftTweaker;
import com.sixteencolorgames.supertechtweaks.compat.mekanism.MekanismCompat;
import com.sixteencolorgames.supertechtweaks.compat.ticon.TiConCompatability;
import com.sixteencolorgames.supertechtweaks.compat.waila.WailaCompatibility;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;

/**
 * Hub for mod compatibility
 *
 * @author oa10712
 *
 */
public class MainCompatHandler {

    public static void registerWaila() {
        if (Loader.isModLoaded("Waila")) {
            WailaCompatibility.register();
        }
    }

    public static void registerTiCon() {
        if (Loader.isModLoaded("tconstruct")) {
            TiConCompatability.registerMelting();
            TiConCompatability logic = new TiConCompatability();
            MinecraftForge.EVENT_BUS.register(logic);
        }
    }

    public static void registerMineTweaker() {
        if (Loader.isModLoaded("MineTweaker3")) {
            CraftTweaker.INSTANCE.register();
        }
    }

    public static void registerMekanism() {
        MekanismCompat.registerRecipes();
    }
}
