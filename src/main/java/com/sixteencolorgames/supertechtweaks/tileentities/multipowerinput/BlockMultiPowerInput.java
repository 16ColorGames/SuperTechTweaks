package com.sixteencolorgames.supertechtweaks.tileentities.multipowerinput;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMultiPowerInput extends Block implements ITileEntityProvider {

	public BlockMultiPowerInput() {
		super(Material.ROCK);
		setUnlocalizedName(SuperTechTweaksMod.MODID + ".multipowerinput");
		setRegistryName("multipowerinputblock");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileMultiPowerInput();
	}

}
