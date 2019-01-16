package com.sixteencolorgames.supertechtweaks.render;

import com.sixteencolorgames.supertechtweaks.blocks.BlockRock;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;

public class StateMapperRock extends StateMapperBase {

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		BlockRock block = (BlockRock) state.getBlock();
		return new ModelResourceLocation(new ResourceLocation(block.getRegistryName().getResourceDomain(), "rock"),
				block.getRegistryName().getResourcePath());
	}

}
