package com.sixteencolorgames.supertechtweaks.tileentities.multifluidinput;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMultiFluidInput extends Block implements ITileEntityProvider {

	public BlockMultiFluidInput() {
		super(Material.ROCK);
		setUnlocalizedName(SuperTechTweaksMod.MODID + ".multifluidinput");
		setRegistryName("multifluidinputblock");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileMultiFluidInput();
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}

}
