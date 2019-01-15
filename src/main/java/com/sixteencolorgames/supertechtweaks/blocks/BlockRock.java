package com.sixteencolorgames.supertechtweaks.blocks;

import java.util.Arrays;
import java.util.List;

import com.sixteencolorgames.supertechtweaks.render.StateMapperRock;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockRock extends net.minecraft.block.Block {

	public BlockRock(String name, boolean isStoneEquivalent, float hardness, float blastResistance,
			int toolHardnessLevel, SoundType sound) {
		super(Material.ROCK);
		this.setRegistryName(name);
		this.setUnlocalizedName("supertechtweaks." + name);
		this.isStoneEquivalent = isStoneEquivalent;
		this.setHardness((float) hardness); // dirt is 0.5, grass is 0.6, stone
											// is 1.5,iron ore is 3, obsidian is
											// 50
		this.setResistance((float) blastResistance); // dirt is 0, iron ore is
														// 5, stone is 10,
														// obsidian is 2000
		this.setSoundType(sound); // sound for stone
		this.setHarvestLevel("pickaxe", toolHardnessLevel);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	public final boolean isStoneEquivalent;

	public StateMapperBase getStateMapper() {
		return new StateMapperRock(this.getRegistryName().getResourcePath());
	}

	@Override
	public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos,
			com.google.common.base.Predicate<IBlockState> target) {
		return isStoneEquivalent;
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return Arrays.asList(new ItemStack(Item.getItemFromBlock(this)));

	}

}