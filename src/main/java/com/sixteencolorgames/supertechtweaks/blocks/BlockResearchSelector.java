package com.sixteencolorgames.supertechtweaks.blocks;

import com.sixteencolorgames.supertechtweaks.ModRegistry;
import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.tileentities.ResearchSelectorTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockResearchSelector extends Block implements ITileEntityProvider {

	public BlockResearchSelector() {
		super(Material.ROCK);
		setUnlocalizedName(SuperTechTweaksMod.MODID + ".researchselector");
		setRegistryName("researchselectorblock");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new ResearchSelectorTileEntity();
	}

	/**
	 * Called when the block is right clicked by a player.
	 */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		// Only execute on the server
		if (world.isRemote) {
			return true;
		}
		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof ResearchSelectorTileEntity)) {
			return false;
		}
		player.openGui(SuperTechTweaksMod.instance, ModRegistry.RESEARCH_SELECTER, world, pos.getX(), pos.getY(),
				pos.getZ());
		return true;
	}
}
