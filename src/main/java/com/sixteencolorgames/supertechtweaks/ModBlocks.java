package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.blocks.BlockBase;
import com.sixteencolorgames.supertechtweaks.blocks.BlockMaterial;
import com.sixteencolorgames.supertechtweaks.blocks.BlockOre;
import com.sixteencolorgames.supertechtweaks.blocks.BlockTileEntity;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.items.ItemModelProvider;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Holds and registers blocks used by the mod
 *
 * @author oa10712
 *
 */
public class ModBlocks {

    public static BlockOre blockOre;
    public static HashMap<Material, BlockMaterial> blockMaterial;

    /**
     * Tasks to perform when the mod is started. should only be called once
     */
    public static void init() {
        blockMaterial = new HashMap();
        blockOre = register(new BlockOre());
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

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockOre.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
        //   blockOre.initItemModel();
    }

}
