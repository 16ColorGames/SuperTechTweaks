package com.sixteencolorgames.supertechtweaks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;

public class BlockRockStairs extends BlockStairs {

	public BlockRockStairs(String name,Block materialBlock, float hardness, float blastResistance, int toolHardnessLevel,
			SoundType sound) {
		super(materialBlock.getDefaultState());
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setHardness((float) hardness); // dirt is 0.5, grass is 0.6, stone
											// is 1.5,iron ore is 3, obsidian is
											// 50
		this.setResistance((float) blastResistance); // dirt is 0, iron ore is
														// 5, stone is 10,
														// obsidian is 2000
		this.setSoundType(sound); // sound for stone
		this.setHarvestLevel("pickaxe", toolHardnessLevel);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.useNeighborBrightness = true;
	}

}