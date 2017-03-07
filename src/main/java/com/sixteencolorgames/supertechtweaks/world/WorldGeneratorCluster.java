package com.sixteencolorgames.supertechtweaks.world;

import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class WorldGeneratorCluster extends WorldGeneratorGeneric {

	public WorldGeneratorCluster(Map<IBlockState, Integer> blocks, int size, int min, int max, int chance,
			Map<String, Object> params) {
		super(blocks, size, min, max, chance, params);
	}

	public boolean generateCluster(World worldIn, Random rand, BlockPos position) {
		int height = rand.nextInt(maxY - minY) + minY;
		BlockPos pos = position.add(0, height, 0);

		int numBlocks = size + (Integer) params.getOrDefault("clusterVariance", 0);
		int var = (int) (Math.sqrt(size) + 2);
		for (int i = 0; i < numBlocks; i++) {
			BlockPos newPos = position.add(rand.nextInt(var), height + rand.nextInt(var), rand.nextInt(var));
			if (Types.stone.contains(worldIn.getBlockState(newPos))) {
				super.generateBlock(worldIn, newPos, super.getRandomBlock());
			} else if (blocks.containsKey(worldIn.getBlockState(newPos))) {
				i--;
			}
		}
		return true;
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		if (rand.nextInt(chance - 1) == 0) {
			return generateCluster(worldIn, rand, position);
		}
		return true;
	}
}
