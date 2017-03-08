package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SuperTechTweaksMod.modId, name = SuperTechTweaksMod.name, version = SuperTechTweaksMod.version, acceptedMinecraftVersions = "[1.10.2]")
public class SuperTechTweaksMod {

	public static final String modId = "supertechtweaks";
	public static final String name = "Super Tech Tweaks";
	public static final String version = "1.0.0";

	@Mod.Instance(modId)
	public static SuperTechTweaksMod instance;

	/**
	 * The proxy to be used. Holds various functions and objects that may need
	 * to be different based on side.
	 */
	@SidedProxy(clientSide = "com.sixteencolorgames.supertechtweaks.proxy.ClientProxy", serverSide = "com.sixteencolorgames.supertechtweaks.proxy.ServerProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(name + " is loading!");
		this.proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		this.proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		this.proxy.postInit(event);
	}

}