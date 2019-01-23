package com.sixteencolorgames.supertechtweaks.world;

import java.util.Map;
import java.util.Random;

import com.sixteencolorgames.supertechtweaks.enums.Ore;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A generator that creates ores in a manner roughly similar to vanilla
 *
 * @author oa10712
 *
 */
public class WorldGeneratorCluster extends WorldGeneratorBase {
	int perChunk = 1;
	int variance = 1;

	private final int width;

	public WorldGeneratorCluster(Map<Ore, Double> ores, String name, int[] dims, int size, int chance, int perChunk,
			int clusterVariance, String... stones) {
		super(ores, name, dims, size, chance, stones);
		variance = clusterVariance;
		this.perChunk = perChunk;
		width = (int) Math.sqrt(size);
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		if (chance == 1 || rand.nextInt(chance) == 0) {
			for (int i = 0; i < perChunk; i++) {
				generateCluster(worldIn, rand, position.add(rand.nextInt(16), 0, rand.nextInt(16)));
			}

		}
		OreSavedData.get(worldIn).setChunkGenerated((position.getX() / 16), (position.getZ() / 16));
		return true;
	}

	public boolean generateCluster(World worldIn, Random rand, BlockPos position) {
		int x = position.getX();
		int z = position.getZ();
		int height = rand.nextInt(worldIn.getChunkFromChunkCoords(x >> 4, z >> 4).getHeightValue(x & 15, z & 15));
		int numBlocks = size + rand.nextInt(variance * 2) - variance;
		if (numBlocks < 1) {
			numBlocks = 1;
		}

		IBlockState start = worldIn.getBlockState(position.add(0, height, 0));
		for (int i = 0; i < numBlocks; i++) {
			BlockPos newPos = position.add(rand.nextInt(width), height + rand.nextInt(width), rand.nextInt(width));
			super.generateOreBlock(worldIn, newPos, start);
		}
		return true;
	}
}
