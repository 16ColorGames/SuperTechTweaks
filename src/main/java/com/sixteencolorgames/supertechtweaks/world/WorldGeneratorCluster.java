package com.sixteencolorgames.supertechtweaks.world;

import java.util.Map;
import java.util.Random;

import com.sixteencolorgames.supertechtweaks.enums.Ores;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGeneratorCluster extends WorldGeneratorBase {

	public WorldGeneratorCluster(Map<Ores, Double> ores, int size, int min, int max, int chance,
			Map<String, Object> params) {
		super(ores, size, min, max, chance, params);
	}

	public boolean generateCluster(World worldIn, Random rand, BlockPos position) {
		int height = rand.nextInt(maxY - minY) + minY;
		int numBlocks = size + (Integer) params.getOrDefault("clusterVariance", 0);
		int var = (int) (size / 3 + 2);
		for (int i = 0; i < numBlocks; i++) {
			BlockPos newPos = position.add(rand.nextInt(var), height + rand.nextInt(var), rand.nextInt(var));
			super.generateOre(worldIn, newPos);
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
