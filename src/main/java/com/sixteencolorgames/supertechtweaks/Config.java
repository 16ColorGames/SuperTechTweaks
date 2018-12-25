package com.sixteencolorgames.supertechtweaks;

import java.io.File;
import java.util.HashMap;

import com.sixteencolorgames.supertechtweaks.proxy.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

public class Config {

	private static final String WORLD_GEN_VANILLA = "assets/supertechtweaks/other/ores.json";

	private static final String CATEGORY_GENERAL = "general";
	private static final String CATEGORY_DIMENSIONS = "dimensions";

	// This values below you can access elsewhere in your mod:
	public static HashMap<IBlockState, ResourceLocation> stone;
	public static HashMap<IBlockState, ResourceLocation> nether;
	public static HashMap<IBlockState, ResourceLocation> end;
	public static boolean removeVanilla;
	public static boolean debug;
	public static String extraDrop;
	public static boolean oreOnly;

	private static String[] types;

	private static String[] netherTypes;

	private static String[] endTypes;

	private static void initGeneralConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
		// cfg.getBoolean() will get the value in the config if it is already
		// specified there. If not it will create the value.
		removeVanilla = cfg.getBoolean("removeVanilla", CATEGORY_GENERAL, true,
				"If vanilla generation should be removed");
		debug = cfg.getBoolean("debug", CATEGORY_GENERAL, false, "If debug info should be printed to the log");
		oreOnly = cfg.getBoolean("oreOnly", CATEGORY_GENERAL, false,
				"If the mod should only register items/blocks required for ore generation");
		// extraDrop = cfg.getString("extraDrop", CATEGORY_GENERAL, "null", "the
		// name of the additional block that an ore should drop when broken.");
		// gets a string list from the config or creates one with default values
		types = cfg.getStringList("stone types", CATEGORY_GENERAL, new String[] {
				"minecraft:stone,minecraft:blocks/stone", "minecraft:stone:1,minecraft:blocks/stone_granite",
				"minecraft:stone:3,minecraft:blocks/stone_diorite", "minecraft:stone:5,minecraft:blocks/stone_andesite",
				"supertechtweaks:slate,supertechtweaks:blocks/slate",
				"supertechtweaks:gneiss,supertechtweaks:blocks/gneiss",
				"supertechtweaks:schist,supertechtweaks:blocks/schist",
				"supertechtweaks:phyllite,supertechtweaks:blocks/phyllite",
				"supertechtweaks:amphibolite,supertechtweaks:blocks/amphibolite",
				"supertechtweaks:shale,supertechtweaks:blocks/shale",
				"supertechtweaks:marble,supertechtweaks:blocks/marble",
				"supertechtweaks:basalt,supertechtweaks:blocks/basalt",
				"supertechtweaks:chert,supertechtweaks:blocks/chert",
				"supertechtweaks:conglomerate,supertechtweaks:blocks/conglomerate",
				"supertechtweaks:dolomite,supertechtweaks:blocks/dolomite",
				"supertechtweaks:gabbro,supertechtweaks:blocks/gabbro",
				"supertechtweaks:limestone,supertechtweaks:blocks/limestone",
				"supertechtweaks:pegmatite,supertechtweaks:blocks/pegmatite",
				"supertechtweaks:rhyolite,supertechtweaks:blocks/rhyolite",
				"supertechtweaks:scoria,supertechtweaks:blocks/scoria" },
				"possible types of block to replace for stone veins");

		netherTypes = cfg.getStringList("nether types", CATEGORY_GENERAL,
				new String[] { "minecraft:netherrack,minecraft:blocks/netherrack" },
				"possible types of block to replace for nether veins");

		endTypes = cfg.getStringList("end types", CATEGORY_GENERAL,
				new String[] { "minecraft:end_stone,minecraft:blocks/end_stone" },
				"possible types of block to replace for ender veins");

	}

	private static void initVanillaOres(File configFolder) {
		try {
			File vanillaGen = new File(configFolder, "ores.json");
			if (vanillaGen.createNewFile()) {
				Utils.copyFileUsingStream(WORLD_GEN_VANILLA, vanillaGen);
			} else if (!vanillaGen.exists()) {
				throw new Error("Unable to create vanilla generation json.");
			}
		} catch (Throwable t) {
		}
	}

	public static void parseTypes() {
		stone = new HashMap();
		for (String type : types) {// Adds the read in stone types to the
			// 'stone' block type. used for generation
			String[] parts = type.split(",");
			String[] split = parts[0].split(":");
			if (split.length == 3) {// This one is called for items such as
									// "minecraft:stone:4", which is a specific
									// type of stone
				stone.put(
						Block.getBlockFromName(split[0] + ":" + split[1]).getStateFromMeta(Integer.parseInt(split[2])),
						new ResourceLocation(parts[1]));
			} else {// This one is called for items without metadata, such as
					// "minecraft:dirt"
				stone.put(Block.getBlockFromName(parts[0]).getDefaultState(), new ResourceLocation(parts[1]));
			}
		}
		nether = new HashMap();
		for (String type : netherTypes) {// Adds the read in nether types to the
			// 'nether' block type. used for generation
			String[] parts = type.split(",");
			String[] split = parts[0].split(":");
			if (split.length == 3) {
				nether.put(
						Block.getBlockFromName(split[0] + ":" + split[1]).getStateFromMeta(Integer.parseInt(split[2])),
						new ResourceLocation(parts[1]));
			} else {
				nether.put(Block.getBlockFromName(parts[0]).getDefaultState(), new ResourceLocation(parts[1]));
			}
		}
		end = new HashMap();
		for (String type : endTypes) {// Adds the read in nether types to the
			// 'nether' block type. used for generation
			String[] parts = type.split(",");
			String[] split = parts[0].split(":");
			if (split.length == 3) {
				end.put(Block.getBlockFromName(split[0] + ":" + split[1]).getStateFromMeta(Integer.parseInt(split[2])),
						new ResourceLocation(parts[1]));
			} else {
				end.put(Block.getBlockFromName(parts[0]).getDefaultState(), new ResourceLocation(parts[1]));
			}
		}
	}

	// Call this from CommonProxy.preInit(). It will create our config if it
	// doesn't exist yet and read the values if it does exist.
	public static void readConfig(File configFolder) {
		Configuration cfg = CommonProxy.config;
		try {
			cfg.load();
			initGeneralConfig(cfg);
			initVanillaOres(configFolder);
		} catch (Exception e1) {
		} finally {
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}
	}
}
