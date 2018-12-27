package com.sixteencolorgames.supertechtweaks;

import org.apache.logging.log4j.Logger;

import com.sixteencolorgames.supertechtweaks.proxy.CommonProxy;

import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SuperTechTweaksMod.MODID, name = SuperTechTweaksMod.MODNAME, version = SuperTechTweaksMod.VERSION, acceptedMinecraftVersions = "[1.12.2]")
public class SuperTechTweaksMod {
	ChunkGeneratorOverworld a;
	public static final String MODID = "supertechtweaks";
	public static final String MODNAME = "Super Tech Tweaks";
	public static final String VERSION = "1.4.0";
	@Mod.Instance(MODID)
	public static SuperTechTweaksMod instance;

	public static Logger logger;
	/**
	 * The proxy to be used. Holds various functions and objects that may need to be
	 * different based on side.
	 */
	@SidedProxy(clientSide = "com.sixteencolorgames.supertechtweaks.proxy.ClientProxy", serverSide = "com.sixteencolorgames.supertechtweaks.proxy.ServerProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		SuperTechTweaksMod.proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		SuperTechTweaksMod.proxy.postInit(event);
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		logger.info(MODNAME + " is loading!");
		SuperTechTweaksMod.proxy.preInit(event);
	}

}
