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
		BlockPos pos = position.add(0, height, 0);

		int numBlocks = size + (Integer) params.getOrDefault("clusterVariance", 0);
		int var = (int) (Math.sqrt(size) + 2);
		int fails = 0;
		for (int i = 0; i < numBlocks; i++) {
			BlockPos newPos = position.add(rand.nextInt(var), height + rand.nextInt(var), rand.nextInt(var));
			if (!super.generateOre(worldIn, newPos)) {
				i--;
				fails++;
			}
			if(fails > 10){
				break;
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
