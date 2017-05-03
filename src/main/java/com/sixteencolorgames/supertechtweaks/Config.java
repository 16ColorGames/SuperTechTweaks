package com.sixteencolorgames.supertechtweaks;

import java.util.ArrayList;

import com.sixteencolorgames.supertechtweaks.proxy.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.config.Configuration;

public class Config {

    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_DIMENSIONS = "dimensions";

    // This values below you can access elsewhere in your mod:
    public static ArrayList<IBlockState> stone;
    public static ArrayList<IBlockState> nether;
    public static ArrayList<IBlockState> end;
    public static boolean removeVanilla;

    // Call this from CommonProxy.preInit(). It will create our config if it
    // doesn't exist yet and read the values if it does exist.
    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception e1) {
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
        // cfg.getBoolean() will get the value in the config if it is already
        // specified there. If not it will create the value.
        removeVanilla = cfg.getBoolean("removeVanilla", CATEGORY_GENERAL, false,
                "If vanilla generation should be removed");
        // gets a string list from the config or creates one with default values
        String[] types = cfg.getStringList("stone types", CATEGORY_GENERAL,
                new String[]{"minecraft:stone", "minecraft:stone:1", "minecraft:stone:3", "minecraft:stone:5"},
                "possible types of block to replace for stone veins");
        stone = new ArrayList();
        for (String type : types) {// Adds the read in stone types to the
            // 'stone' block type. used for generation
            String[] split = type.split(":");
            if (split.length == 3) {//This one is called for items such as "minecraft:stone:4", which is a specific type of stone
                stone.add(Block.getBlockFromName(split[0] + ":" + split[1]).getStateFromMeta(Integer.parseInt(split[2])));
            } else {//This one is called for items without metadata, such as "minecraft:dirt"
                stone.add(Block.getBlockFromName(type).getDefaultState());
            }
        }

        String[] netherTypes = cfg.getStringList("nether types", CATEGORY_GENERAL,
                new String[]{"minecraft:netherrack"}, "possible types of block to replace for nether veins");
        nether = new ArrayList();
        for (String type : netherTypes) {// Adds the read in nether types to the
            // 'nether' block type. used for generation
            String[] split = type.split(":");
            if (split.length == 3) {
                nether.add(Block.getBlockFromName(split[0] + ":" + split[1]).getStateFromMeta(Integer.parseInt(split[2])));
            } else {
                nether.add(Block.getBlockFromName(type).getDefaultState());
            }
        }

        String[] endTypes = cfg.getStringList("end types", CATEGORY_GENERAL,
                new String[]{"minecraft:end_stone"}, "possible types of block to replace for ender veins");
        end = new ArrayList();
        for (String type : endTypes) {// Adds the read in nether types to the
            // 'nether' block type. used for generation
            String[] split = type.split(":");
            if (split.length == 3) {
                end.add(Block.getBlockFromName(split[0] + ":" + split[1]).getStateFromMeta(Integer.parseInt(split[2])));
            } else {
                end.add(Block.getBlockFromName(type).getDefaultState());
            }
        }
    }
}
