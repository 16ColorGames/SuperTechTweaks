package com.sixteencolorgames.supertechtweaks.proxy;

import java.io.File;

import com.sixteencolorgames.supertechtweaks.ModBlocks;
import com.sixteencolorgames.supertechtweaks.ModItems;
import com.sixteencolorgames.supertechtweaks.compat.MainCompatHandler;
import com.sixteencolorgames.supertechtweaks.tileentities.TileEntityOre;
import com.sixteencolorgames.supertechtweaks.world.GenerationParser;
import com.sixteencolorgames.supertechtweaks.world.ModWorldGeneration;
import com.sixteencolorgames.supertechtweaks.world.Types;

import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public static Configuration config;

	public void preInit(FMLPreInitializationEvent e) {
        MainCompatHandler.registerWaila();
		new Types();
		File configFolder = new File(e.getModConfigurationDirectory().toString() + "/supertechtweaks/");
		if (!new File(configFolder.getPath(), "ores.json").exists()) {
			config = new Configuration(new File(configFolder.getPath(), "ores.json"));
			config.load();
			config.save();
		}
		ModWorldGeneration generator = new ModWorldGeneration(
				GenerationParser.parseScripts(new File(configFolder.getPath(), "ores.json")));
		System.out.println("Generators Loaded");
		ModBlocks.init();
		ModItems.init();
		GameRegistry.registerTileEntity(TileEntityOre.class, e.getModMetadata().modId + "TileEntityOre");
		GameRegistry.registerWorldGenerator(generator, 3);
	}

	public void init(FMLInitializationEvent e) {

	}

	public void postInit(FMLPostInitializationEvent e) {

	}
	
	public void registerItemRenderer(Item item, int meta, String id) {

	}
}
