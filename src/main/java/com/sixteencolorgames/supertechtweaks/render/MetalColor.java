package com.sixteencolorgames.supertechtweaks.render;

import com.sixteencolorgames.supertechtweaks.ModItems;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import java.awt.Color;

import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.END;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.NETHER;
import com.sixteencolorgames.supertechtweaks.tileentities.TileEntityOre;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class MetalColor implements IItemColor, IBlockColor {

    /**
     * Returns the colour for rendering, based on 1) the itemstack 2) the
     * "tintindex" (layer in the item model json) For example:
     * bottle_drinkable.json contains "layer0": "items/potion_overlay",
     * "layer1": "items/potion_bottle_drinkable" layer0 = tintindex 0 = for the
     * bottle outline, whose colour doesn't change layer1 = tintindex 1 = for
     * the bottle contents, whose colour changes depending on the type of potion
     *
     * @param stack
     * @param tintIndex
     * @return an RGB colour (to be multiplied by the texture colours)
     */
    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        // when rendering, choose the colour multiplier based on the contents
        // we want layer 0 (the bottle glass) to be unaffected (return white as
        // the multiplier)
        // layer 1 will change colour depending on the contents.

        if (stack.getItem() == ModItems.itemOreChunk) {
            switch (tintIndex) {
                case 0:
                    if (stack.getMetadata() < NETHER) {
                        return Color.GRAY.getRGB();
                    } else if (stack.getMetadata() < END) {
                        return Color.RED.darker().getRGB();
                    } else {
                        return Color.WHITE.getRGB();
                    }
                
                case 1:
                    int metadata = stack.getMetadata();
                    while (metadata >= 1000) {
                        metadata -= 1000;
                    }
                    Material metal = Material.materials.get(metadata);
                    return Color.decode(metal.getColor()).getRGB();
                
                default:
                    // oops! should never get here.
                    return Color.BLACK.getRGB();
                
            }
        } else {
            switch (tintIndex) {
                case 0:
                    int metadata = stack.getMetadata();
                    while (metadata >= 1000) {
                        metadata -= 1000;
                    }
                    Material metal = Material.materials.get(metadata);
                    return Color.decode(metal.getColor()).getRGB();
                default:
                    return Color.BLACK.getRGB();
            }
        }
    }
    
    @Override
    public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
        if (worldIn != null && pos != null) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileEntityOre) {
                TileEntityOre ore = (TileEntityOre) tileEntity;
                int[] ores = ore.getOres();
                switch (tintIndex) {
                    case 0:
                        return Color.WHITE.getRGB();
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        if (ores[tintIndex - 1] != 0) {
                            return Color.decode(Material.materials.get(ores[tintIndex - 1]).getColor()).getRGB();
                        } else {
                            return new Color(0, 0, 0, 0).getRGB();
                        }
                }
            }
        }
        return Color.WHITE.getRGB();
        
    }
}
