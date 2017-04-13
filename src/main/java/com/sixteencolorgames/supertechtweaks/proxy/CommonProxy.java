package com.sixteencolorgames.supertechtweaks.proxy;

import java.io.File;

import com.sixteencolorgames.supertechtweaks.Config;
import com.sixteencolorgames.supertechtweaks.ModBlocks;
import com.sixteencolorgames.supertechtweaks.ModItems;
import static com.sixteencolorgames.supertechtweaks.ModItems.itemMaterialObject;
import static com.sixteencolorgames.supertechtweaks.ModItems.itemOreChunk;
import com.sixteencolorgames.supertechtweaks.Recipies;
import com.sixteencolorgames.supertechtweaks.compat.MainCompatHandler;
import com.sixteencolorgames.supertechtweaks.enums.Ores;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.CLUMP;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.CRYSTAL;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.DIRTY;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.DUST;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.GEAR;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.INGOT;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.NUGGET;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.PLATE;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.ROD;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.SHARD;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.WIRE;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.END;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.NETHER;
import com.sixteencolorgames.supertechtweaks.tileentities.TileEntityOre;
import com.sixteencolorgames.supertechtweaks.world.GenerationParser;
import com.sixteencolorgames.supertechtweaks.world.ModWorldGeneration;
import com.sixteencolorgames.supertechtweaks.world.SingleGenerator;
import com.sixteencolorgames.supertechtweaks.world.WorldGeneratorBase;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

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
        MainCompatHandler.registerMekanism();
        File configFolder = new File(e.getModConfigurationDirectory().toString() + "/supertechtweaks/");
        config = new Configuration(new File(configFolder.getPath(), "config.cfg"));
        Config.readConfig();

        ModWorldGeneration generator = new ModWorldGeneration();
        for (File gen : configFolder.listFiles()) {
            if (gen.getName().contains(".json")) {
                try {
//                    generator.addGenerators(GenerationParser.parseScripts(gen));
                    ArrayList<WorldGeneratorBase> parsed = GenerationParser.parseScripts(gen);
                    parsed.forEach((WorldGeneratorBase base) -> {
                        GameRegistry.registerWorldGenerator(new SingleGenerator(base), 3 + base.params.hashCode()+base.hashCode());
                    });
                } catch (Exception ex) {
                }
            }
        }
        System.out.println("Generators Loaded");
        ModBlocks.init();
        ModItems.init();
        GameRegistry.registerTileEntity(TileEntityOre.class, e.getModMetadata().modId + "TileEntityOre");
        //GameRegistry.registerWorldGenerator(generator, 3);
        for (Ores metal : Ores.values()) {
            ItemStack subItemStack = new ItemStack(itemOreChunk, 1, metal.ordinal());
            OreDictionary.registerOre("ore" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemOreChunk, 1, metal.ordinal() + NETHER);
            OreDictionary.registerOre("oreNether" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemOreChunk, 1, metal.ordinal() + END);
            OreDictionary.registerOre("oreEnd" + metal.getName(), subItemStack);

            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + INGOT);
            OreDictionary.registerOre("ingot" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + DUST);
            OreDictionary.registerOre("dust" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + GEAR);
            OreDictionary.registerOre("gear" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + NUGGET);
            OreDictionary.registerOre("nugget" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + PLATE);
            OreDictionary.registerOre("plate" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + ROD);
            OreDictionary.registerOre("rod" + metal.getName(), subItemStack);
            OreDictionary.registerOre("stick" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + CLUMP);
            OreDictionary.registerOre("clump" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + CRYSTAL);
            OreDictionary.registerOre("crystal" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + SHARD);
            OreDictionary.registerOre("shard" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + WIRE);
            OreDictionary.registerOre("wire" + metal.getName(), subItemStack);
            OreDictionary.registerOre("cable" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + DIRTY);
            OreDictionary.registerOre("dustDirty" + metal.getName(), subItemStack);
        }

    }

    public void init(FMLInitializationEvent e) {

    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
        Recipies.addRecipies();
    }

    public void registerItemRenderer(Item item, int meta, String id) {

    }
}
