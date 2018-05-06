package com.sixteencolorgames.supertechtweaks.blocks;

import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlockController;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockMulti extends Block implements ITileEntityProvider {

	public BlockMulti(Material materialIn) {
		super(materialIn);
	}

	/**
	 * Called serverside after this block is replaced with another in Chunk, but
	 * before the Tile Entity is updated
	 */
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileMultiBlock tile = (TileMultiBlock) worldIn.getTileEntity(pos);
		TileMultiBlockController master = (TileMultiBlockController) worldIn
				.getTileEntity(new BlockPos(tile.getMasterX(), tile.getMasterY(), tile.getMasterZ()));
		if (master != null) {
			master.resetStructure();
		}
		super.breakBlock(worldIn, pos, state);
	}

}
