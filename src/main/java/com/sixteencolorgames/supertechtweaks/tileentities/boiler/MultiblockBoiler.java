package com.sixteencolorgames.supertechtweaks.tileentities.boiler;

import com.sixteencolorgames.supertechtweaks.ModRegistry;
import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.tileentities.MultiblockHandler.IMultiblock;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class MultiblockBoiler implements IMultiblock {

	@Override
	public String getUniqueName() {
		return SuperTechTweaksMod.MODID + ":boiler";
	}

	@Override
	public boolean isBlockTrigger(IBlockState state) {
		return state.getBlock().equals(ModRegistry.blockBoiler);
	}

	@Override
	public boolean createStructure(World world, BlockPos pos, EnumFacing side, EntityPlayer player) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Block[][][] getStructureManual() {
		return new Block[][][] { { { ModRegistry.blockBoiler }, { ModRegistry.blockPressureTank } } };
	}

	@Override
	public Vec3i getTriggerOffset() {
		return new Vec3i(0, 0, 0);
	}

	@Override
	public boolean overwriteBlockRender(ItemStack stack, int iterator) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canRenderFormedStructure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void renderFormedStructure() {
		// TODO Auto-generated method stub

	}

}
