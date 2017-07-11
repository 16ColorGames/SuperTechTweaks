package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.blocks.BlockBase;
import com.sixteencolorgames.supertechtweaks.blocks.BlockMaterial;
import com.sixteencolorgames.supertechtweaks.blocks.BlockTileEntity;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.items.ItemModelProvider;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Holds and registers blocks used by the mod
 *
 * @author oa10712
 *
 */
public class ModBlocks {

    public static HashMap<Material, BlockMaterial> blockMaterial;
    public static ArrayList<BlockMaterial> materialBlocks = new ArrayList();
    public static ArrayList<ItemBlock> itemBlocks = new ArrayList();

    /**
     * Tasks to perform when the mod is started. should only be called once
     */
    public static void init() {
        blockMaterial = new HashMap();

        for (Material mat : Material.materials) {
            System.out.println("Adding block for " + mat.getName());
            BlockMaterial value = new BlockMaterial(mat);
            ItemBlock itemBlock = new ItemBlock(value);
            itemBlock.setRegistryName(value.getRegistryName());
            register(value, itemBlock);
            OreDictionary.registerOre("block" + mat.getName(), new ItemStack(value));
            System.out.println("Registered block: " + value.getUnlocalizedName());
            materialBlocks.add(value);
            itemBlocks.add(itemBlock);
        }
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

}
