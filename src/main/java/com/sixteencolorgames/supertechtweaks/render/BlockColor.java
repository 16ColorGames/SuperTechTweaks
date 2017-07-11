/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.render;

import com.sixteencolorgames.supertechtweaks.enums.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 *
 * @author oa10712
 */
public class BlockColor implements IItemColor, IBlockColor {

    public static BlockColor INSTANCE = new BlockColor();

    @Override
    public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
        for (Material mat : Material.materials) {
            if (mat.getBlock() == state.getBlock()) {
                return mat.getColor();
            }
        }
        return -1;
    }

    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        for (Material mat : Material.materials) {
            if (mat.getItemBlock() == stack.getItem()) {
                return mat.getColor();
            }
        }
        return -1;
    }
}
