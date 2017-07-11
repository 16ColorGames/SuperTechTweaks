/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.blocks.BlockBase;
import com.sixteencolorgames.supertechtweaks.blocks.BlockOre;
import com.sixteencolorgames.supertechtweaks.blocks.BlockTileEntity;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.items.ItemBase;
import com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject;
import com.sixteencolorgames.supertechtweaks.items.ItemModelProvider;
import com.sixteencolorgames.supertechtweaks.items.ItemOreChunk;
import com.sixteencolorgames.supertechtweaks.items.ItemTechComponent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.common.registry.RegistryBuilder;

/**
 * This class is where all registration will take place
 *
 * @author oa10712
 */
@ObjectHolder("supertechtweaks")
@Mod.EventBusSubscriber()
public class ModRegistry {

    public static final Block superore = null;
    public static final Item itemTechComponent = null;
    
    public static final Item itemOreChunk = null;
    public static final Item itemMaterialObject = null;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        System.out.println("Attempting block registry");
        register(new BlockOre());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        System.out.println("Attempting item registry");
        ItemTechComponent tech = register(new ItemTechComponent());
        tech.setupDictionary();
        register(new ItemOreChunk());
        register(new ItemMaterialObject());
    }

    @SubscribeEvent
    public static void registerMaterials(RegistryEvent.Register<Material> event) {
        System.out.println("Attempting material registry");
    }

    @SubscribeEvent
    public static void registerRegistry(RegistryEvent.NewRegistry event) {
        IForgeRegistry builder = new RegistryBuilder()
                .setType(Material.class)
                .setName(new ResourceLocation(SuperTechTweaksMod.MODID, "MaterialRegistry"))
                .setIDRange(0, 256)
                .create();
    }

    /**
     * Registers a block and associated requirements, such as model and tile
     * entity
     *
     * @param block The block to register
     * @param itemBlock the item block to register
     * @return the block registered
     */
    private static <T extends Block> T register(T block, ItemBlock itemBlock) {
        GameRegistry.register(block);
        if (itemBlock != null) {
            GameRegistry.register(itemBlock);

            if (block instanceof ItemModelProvider) {
                ((ItemModelProvider) block).registerItemModel(itemBlock);
            }
            if (block instanceof BlockBase) {
                ((BlockBase) block).setItemBlock(itemBlock);
            }
            // if (block instanceof ItemOreDict) {
            // ((ItemOreDict)block).initOreDict();
            // } else if (itemBlock instanceof ItemOreDict) {
            // ((ItemOreDict)itemBlock).initOreDict();
            // }
        }

        if (block instanceof BlockTileEntity) {
            GameRegistry.registerTileEntity(((BlockTileEntity<?>) block).getTileEntityClass(),
                    block.getRegistryName().toString());
        }

        return block;
    }

    /**
     * Registers a block and associated requirements
     *
     * @param block the block to register
     * @return the block registered
     */
    public static <T extends Block> T register(T block) {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return register(block, itemBlock);
    }

    /**
     * Registers an item and its model with forge
     *
     * @param item The Item to register
     * @return The Item registered
     */
    private static <T extends Item> T register(T item) {
        GameRegistry.register(item);

        if (item instanceof ItemBase) {
            ((ItemBase) item).registerItemModel();
        }

        return item;
    }
}
