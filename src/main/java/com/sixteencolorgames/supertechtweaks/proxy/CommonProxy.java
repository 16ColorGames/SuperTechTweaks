package com.sixteencolorgames.supertechtweaks.proxy;

import java.io.File;

import com.sixteencolorgames.supertechtweaks.Config;
import com.sixteencolorgames.supertechtweaks.ModBlocks;
import com.sixteencolorgames.supertechtweaks.ModItems;
import com.sixteencolorgames.supertechtweaks.compat.MainCompatHandler;
import com.sixteencolorgames.supertechtweaks.tileentities.TileEntityOre;
import com.sixteencolorgames.supertechtweaks.world.GenerationParser;
import com.sixteencolorgames.supertechtweaks.world.ModWorldGeneration;

import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Proxy functions common to both the client and server side
 * 
 * @author oa10712
 *
 */
public class CommonProxy {

	public static Configuration config;

	public void preInit(FMLPreInitializationEvent e) {
		MainCompatHandler.registerWaila();
		File configFolder = new File(e.getModConfigurationDirectory().toString() + "/supertechtweaks/");
		config = new Configuration(new File(configFolder.getPath(), "config.cfg"));
		Config.readConfig();

		ModWorldGeneration generator = new ModWorldGeneration();
		for (File gen : configFolder.listFiles()) {
			if (gen.getName().contains(".json")) {
				try {
					generator.addGenerators(GenerationParser.parseScripts(gen));
				} catch (Exception ex) {
				}
			}
		}
		System.out.println("Generators Loaded");
		ModBlocks.init();
		ModItems.init();
		GameRegistry.registerTileEntity(TileEntityOre.class, e.getModMetadata().modId + "TileEntityOre");
		GameRegistry.registerWorldGenerator(generator, 3);
	}

	public void init(FMLInitializationEvent e) {

	}

	public void postInit(FMLPostInitializationEvent e) {
		if (config.hasChanged()) {
			config.save();
		}
	}

	public void registerItemRenderer(Item item, int meta, String id) {

	}
}
