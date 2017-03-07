package com.sixteencolorgames.supertechtweaks.world;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

@Deprecated
public abstract class WorldGeneratorGeneric extends WorldGenerator {
	public Map<IBlockState, Integer> blocks;
	public int size;
	public Map<String, Object> params;
	public int maxY;
	public int minY;
	public int chance;
	private String name;

	public WorldGeneratorGeneric(Map<IBlockState, Integer> blocks, int size, int min, int max, int chance,
			Map<String, Object> params) {
		this.blocks = blocks;
		this.size = size;
		maxY = max;
		minY = min;
		this.chance = chance >= 1 ? chance : 1;
		this.params = params;
	}

	public void setName(String n) {
		name = n;
	}

	public String getName() {
		return name;
	}

	public Map<IBlockState, Integer> getBlocks() {
		return blocks;
	}

	public int getSize() {
		return size;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public int getTotalWeight() {
		int totalWeight = 0;
		for (Integer i : blocks.values()) {
			totalWeight += i;
		}
		return totalWeight;
	}

	public IBlockState getRandomBlock() {
		Random random = new Random();
		return blocks.entrySet().stream()
				.map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), -Math.log(random.nextDouble()) / e.getValue()))
				.min((e0, e1) -> e0.getValue().compareTo(e1.getValue())).orElseThrow(IllegalArgumentException::new)
				.getKey();
	}

	public static boolean generateBlock(World world, BlockPos pos, IBlockState b) {
		return world.setBlockState(pos, b, 3);
	}

	public static BlockPos[] facing(BlockPos center) {
		BlockPos[] ret = new BlockPos[7];
		ret[0] = center;
		ret[1] = center.up();
		ret[2] = center.down();
		ret[3] = center.north();
		ret[4] = center.south();
		ret[5] = center.east();
		ret[6] = center.west();
		return ret;
	}
}
