/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.blocks.BlockMaterial;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.common.registry.RegistryBuilder;
import net.minecraftforge.oredict.OreDictionary;

/**
 * This class is where all registration will take place
 *
 * @author oa10712
 */
@ObjectHolder(SuperTechTweaksMod.MODID)
@Mod.EventBusSubscriber()
public class ModRegistry {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        System.out.println("Attempting block registry");
        for (Material mat : Material.materials) {
            Block value = new BlockMaterial(mat);
            OreDictionary.registerOre("block" + mat.getName(), value);
            event.getRegistry().register(value);
            System.out.println("Registered block: " + value.getUnlocalizedName());
        }
    }

    @SubscribeEvent
    public static void registerMaterials(RegistryEvent.Register<Material> event) {
        System.out.println("Attempting material registry");
       event.getRegistry().register(value);
    }

    @SubscribeEvent
    public static void registerRegistry(RegistryEvent.NewRegistry event) {
        IForgeRegistry builder = new RegistryBuilder()
                .setType(Material.class)
                .setName(new ResourceLocation(SuperTechTweaksMod.MODID, "MaterialRegistry"))
                .setIDRange(0, 256)
                .create();
    }
}
