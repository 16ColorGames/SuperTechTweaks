package com.sixteencolorgames.supertechtweaks.world;

import java.util.Map;
import java.util.Random;

import com.sixteencolorgames.supertechtweaks.enums.Ore;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Generates flat bands of ore. Size is the radius of the band
 *
 * @author oa10712
 *
 */
public class WorldGeneratorPlate extends WorldGeneratorBase {

	public WorldGeneratorPlate(Map<Ore, Double> ores, String name, int[] dims, int size, int chance, String... stones) {
		super(ores, name, dims, size, chance, stones);
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		if (chance == 1 || rand.nextInt(chance) == 0) {
			return generatePlate(worldIn, rand, position);
		}
		OreSavedData.get(worldIn).setChunkGenerated((position.getX() / 16), (position.getZ() / 16));
		return true;
	}

	private boolean generatePlate(World worldIn, Random rand, BlockPos position) {
		int x = position.getX();
		int z = position.getZ();
		int height = rand.nextInt(worldIn.getChunkFromChunkCoords(x >> 4, z >> 4).getHeightValue(x & 15, z & 15));
		int r = size;
		IBlockState start = worldIn.getBlockState(new BlockPos(x, height, z));
		for (int i = -r; i < r; i++) {
			for (int j = -r; j < r; j++) {
				if ((i * i + j * j) <= (r * r)) {
					super.generateOreBlock(worldIn, new BlockPos(i + x, height, j + z), start);
				}
			}
		}
		return true;
	}

}
