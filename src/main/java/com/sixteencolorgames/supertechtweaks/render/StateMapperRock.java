package com.sixteencolorgames.supertechtweaks.render;

import com.sixteencolorgames.supertechtweaks.blocks.BlockRock;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;

public class StateMapperRock extends StateMapperBase {

	public StateMapperRock(String resourcePath) {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		BlockRock block = (BlockRock) state.getBlock();
		// TODO Auto-generated method stub
		return new ModelResourceLocation(block.getRegistryName()+"test", "normal");
	}

}
