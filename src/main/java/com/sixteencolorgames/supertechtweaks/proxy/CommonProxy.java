package com.sixteencolorgames.supertechtweaks.proxy;

import java.io.File;

import com.sixteencolorgames.supertechtweaks.Config;
import com.sixteencolorgames.supertechtweaks.ModRegistry;
import static com.sixteencolorgames.supertechtweaks.ModRegistry.itemMaterialObject;
import static com.sixteencolorgames.supertechtweaks.ModRegistry.itemOreChunk;
import com.sixteencolorgames.supertechtweaks.Recipies;
import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.compat.MainCompatHandler;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels.*;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.*;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.END;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.NETHER;
import com.sixteencolorgames.supertechtweaks.world.GenerationParser;
import com.sixteencolorgames.supertechtweaks.world.ModWorldGeneration;
import com.sixteencolorgames.supertechtweaks.world.WorldGeneratorBase;
import java.util.ArrayList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.ServerEvents;
import com.sixteencolorgames.supertechtweaks.handlers.CustomFuelHandler;
import com.sixteencolorgames.supertechtweaks.network.PacketHandler;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import org.apache.http.config.RegistryBuilder;

/**
 * Proxy functions common to both the client and server side
 *
 * @author oa10712
 *
 */
public abstract class CommonProxy {
    
    public static Configuration config;
    public static ArrayList<WorldGeneratorBase> parsed;
    
    public RegistryBuilder registryInit(RegistryEvent.NewRegistry e) {
        RegistryBuilder<Material> created = RegistryBuilder.create();
        return created;
    }
    
    public void preInit(FMLPreInitializationEvent e) {
        
        PacketHandler.registerMessages(SuperTechTweaksMod.MODID + "Chan");
        //initMaterials();
        GameRegistry.registerFuelHandler(CustomFuelHandler.getInstance());
        MainCompatHandler.registerWaila();
        MainCompatHandler.registerTiCon();
        File configFolder = new File(e.getModConfigurationDirectory().toString() + "/supertechtweaks/");
        config = new Configuration(new File(configFolder.getPath(), "config.cfg"));
        Config.readConfig(configFolder);
        
        ModWorldGeneration generator = new ModWorldGeneration();
        for (File gen : configFolder.listFiles()) {
            if (gen.getName().contains(".json")) {
                try {
                    parsed = GenerationParser.parseScripts(gen);
                    parsed.forEach((WorldGeneratorBase base) -> {
                        GameRegistry.registerWorldGenerator(base, 3 + base.params.hashCode() + base.hashCode());
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        System.out.println("Generators Loaded");
        
        MinecraftForge.EVENT_BUS.register(new ServerEvents());
        
    }
    
    public void init(FMLInitializationEvent e) {
        FMLInterModComms.sendMessage("chiselsandbits", "ignoreblocklogic",
                "supertechtweaks:superore");
        MainCompatHandler.registerMekanism();
    }
    
    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
        MainCompatHandler.registerMineTweaker();
        Recipies.addRecipies();
    }
    
    public void registerItemRenderer(Item item, int meta, String id) {
        
    }
    
    public abstract World getWorld(IBlockAccess world);
    
    public World getWorld() {
        return getWorld(null);
    }
   
}
