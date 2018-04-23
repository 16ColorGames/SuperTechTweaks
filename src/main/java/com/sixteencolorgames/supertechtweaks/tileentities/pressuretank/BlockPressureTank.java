package com.sixteencolorgames.supertechtweaks.tileentities.pressuretank;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.blocks.BlockMulti;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPressureTank extends BlockMulti {
	public BlockPressureTank() {
		super(Material.ROCK);
		setUnlocalizedName(SuperTechTweaksMod.MODID + ".pressuretank");
		setRegistryName("blockpressuretank");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TilePressureTank();
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
