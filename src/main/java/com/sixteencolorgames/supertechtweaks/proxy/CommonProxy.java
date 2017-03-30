package com.sixteencolorgames.supertechtweaks.proxy;

import java.io.File;

import com.sixteencolorgames.supertechtweaks.Config;
import com.sixteencolorgames.supertechtweaks.ModBlocks;
import com.sixteencolorgames.supertechtweaks.ModItems;
import com.sixteencolorgames.supertechtweaks.compat.MainCompatHandler;
import com.sixteencolorgames.supertechtweaks.enums.Ores;
import com.sixteencolorgames.supertechtweaks.tileentities.TileEntityOre;
import com.sixteencolorgames.supertechtweaks.world.GenerationParser;
import com.sixteencolorgames.supertechtweaks.world.ModWorldGeneration;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

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
        MainCompatHandler.registerTiCon();
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
        for (Ores ore : Ores.values()) {
            String oreName = "ingot" + ore.getName();
            if (OreDictionary.doesOreNameExist(oreName) && !OreDictionary.getOres(oreName).isEmpty()) {
                GameRegistry.addSmelting(new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal()),
                        OreDictionary.getOres(oreName).get(0), 1.0f);
            }
        }
        for (Material mat : TinkerRegistry.getAllMaterials()) {
            try {
                System.out.println(mat.identifier + ": " + ((HeadMaterialStats) mat.getStats("head")).harvestLevel);
            } catch (Exception ex) {
            }
        }
    }

    public void registerItemRenderer(Item item, int meta, String id) {

    }
}
