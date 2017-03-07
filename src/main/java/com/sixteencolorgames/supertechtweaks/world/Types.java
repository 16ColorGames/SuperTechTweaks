package com.sixteencolorgames.supertechtweaks.world;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class Types {
	public static ArrayList<IBlockState> stone;
	
	public Types(){
		stone = new ArrayList();
		stone.add(Block.getBlockFromName("minecraft:stone").getDefaultState());
	}
}
