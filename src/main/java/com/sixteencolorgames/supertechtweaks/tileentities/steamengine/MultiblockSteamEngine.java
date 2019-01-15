package com.sixteencolorgames.supertechtweaks.tileentities.steamengine;

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

public class MultiblockSteamEngine implements IMultiblock {
	static Block[][][] structure = new Block[3][3][4];
	static {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				for (int z = 0; z < 4; z++) {
					if (x == 0 || x == 2 || y == 0 || y == 2) {
						structure[x][y][z] = ModRegistry.blockMultiWall;
					} else {
						switch (z) {
						case 0:
							structure[x][y][z] = ModRegistry.blockSteamEngine;
							break;
						case 1:
						case 2:
							structure[x][y][z] = ModRegistry.blockSteamEngine;
							break;
						case 3:
							structure[x][y][z] = ModRegistry.blockMultiWall;
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public String getUniqueName() {
		return SuperTechTweaksMod.MODID + ":steamengine";
	}

	@Override
	public boolean isBlockTrigger(IBlockState state) {
		return state.getBlock().equals(ModRegistry.blockSteamEngine);
	}

	@Override
	public boolean createStructure(World world, BlockPos pos, EnumFacing side, EntityPlayer player) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Block[][][] getStructureManual() {
		// TODO Auto-generated method stub
		return structure.clone();
	}

	@Override
	public Vec3i getTriggerOffset() {
		// TODO Auto-generated method stub
		return new Vec3i(1, 1, 0);
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
