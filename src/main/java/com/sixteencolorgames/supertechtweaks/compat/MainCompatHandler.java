package com.sixteencolorgames.supertechtweaks.compat;

import com.sixteencolorgames.supertechtweaks.compat.waila.WailaCompatibility;

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
}
