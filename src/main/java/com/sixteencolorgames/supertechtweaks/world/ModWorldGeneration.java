package com.sixteencolorgames.supertechtweaks.world;

import java.awt.List;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class ModWorldGeneration implements IWorldGenerator {

	public ArrayList<WorldGeneratorBase> generators;

	public ModWorldGeneration() {
		generators = new ArrayList();
	}

	public ModWorldGeneration(ArrayList<WorldGeneratorBase> list) {
		System.out.println("Creating world generator: " + list.size());
		generators = list;
		for (WorldGeneratorBase gen : generators) {
			System.out.print(gen.getName());
		}
	}

	public void addGenerators(ArrayList<WorldGeneratorBase> list) {
		generators.addAll(list);
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (world.provider.getDimension() == 0) { // the overworld
			generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}

	private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		for (WorldGeneratorBase gen : generators) {
			BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16).add(random.nextInt(16), 0, random.nextInt(16));
			gen.generate(world, random, pos);
		}
	}
}