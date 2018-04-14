/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.render;

import com.sixteencolorgames.supertechtweaks.blocks.BlockMaterial;
import com.sixteencolorgames.supertechtweaks.items.MaterialItem;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemBlock;
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
		BlockMaterial block = (BlockMaterial) state.getBlock();
		return block.getSuperMaterial().getColor();
	}

	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		try {
			MaterialItem item = (MaterialItem) stack.getItem();
			return item.getMaterial().getColor();
		} catch (Exception ex) {
			ItemBlock item = (ItemBlock) stack.getItem();
			return ((BlockMaterial) item.getBlock()).getSuperMaterial().getColor();
		}
	}
}
