package com.sixteencolorgames.supertechtweaks.compat;

import com.sixteencolorgames.supertechtweaks.compat.top.TOPCompatibility;

import net.minecraftforge.fml.common.Loader;

public class MainCompatHandler {
	public static void registerTOP() {
		if (Loader.isModLoaded("theoneprobe")) {
			System.out.println("TOP support init");
			TOPCompatibility.register();
		}
	}

}