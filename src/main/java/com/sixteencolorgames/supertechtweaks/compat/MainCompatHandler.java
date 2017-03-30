package com.sixteencolorgames.supertechtweaks.compat;

import com.sixteencolorgames.supertechtweaks.EventLogic;
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
		EventLogic logic = new EventLogic();

		MinecraftForge.EVENT_BUS.register(logic);
	}
}
