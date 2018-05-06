package com.sixteencolorgames.supertechtweaks.world;

import java.util.Map;
import java.util.Random;

import com.sixteencolorgames.supertechtweaks.enums.Ore;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGeneratorCluster extends WorldGeneratorBase {

	private final int width;

	public WorldGeneratorCluster(Map<Ore, Double> ores, int size, int min, int max, int chance,
			Map<String, Object> params) {
		super(ores, size, min, max, chance, params);
		width = (int) Math.sqrt(size);
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		if (chance == 1 || rand.nextInt(chance) == 0) {
			if ((int) params.getOrDefault("perChunk", 1) <= 1) {
				return generateCluster(worldIn, rand, position);
			} else {
				for (int i = 0; i < (int) params.getOrDefault("perChunk", 1); i++) {
					generateCluster(worldIn, rand, position.add(rand.nextInt(16), 0, rand.nextInt(16)));
				}
			}
		}
		OreSavedData.get(worldIn).setChunkGenerated((position.getX() / 16), (position.getZ() / 16));
		return true;
	}

	public boolean generateCluster(World worldIn, Random rand, BlockPos position) {
		int height = rand.nextInt(maxY - minY) + minY;
		int variance = ((int) params.getOrDefault("clusterVariance", 1) > 0)
				? (int) params.getOrDefault("clusterVariance", 1) : 1;
		int numBlocks = size + rand.nextInt(variance * 2) - variance;
		if (numBlocks < 1) {
			numBlocks = 1;
		}
		for (int i = 0; i < numBlocks; i++) {
			BlockPos newPos = position.add(rand.nextInt(width), height + rand.nextInt(width), rand.nextInt(width));
			super.generateOre(worldIn, newPos);
		}
		return true;
	}
}
