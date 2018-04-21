package com.sixteencolorgames.supertechtweaks.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.sixteencolorgames.supertechtweaks.Config;
import com.sixteencolorgames.supertechtweaks.ModRegistry;
import com.sixteencolorgames.supertechtweaks.enums.Material;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public abstract class WorldGeneratorBase implements IWorldGenerator {

	public Map<Material, Double> ores;// List of metals in this generator along
										// with
	// their chance to generate per block
	public int size;// Size of the generator. This means different things
	// depending on the implementation
	public Map<String, Object> params;// Other parameters specific to
	// implementations
	public int maxY;// Maximum height for the generator (rough)
	public int minY;// Minimum Height for the generator (rough)
	public int chance;// Chance per chunk to generate an instance
	private String name;// The identifying name for this generator
	public ArrayList<Integer> dims;

	public WorldGeneratorBase(Map<Material, Double> ores, int size, int min, int max, int chance,
			Map<String, Object> params) {
		this.ores = ores;
		this.size = size;
		maxY = max;
		minY = min;
		this.chance = chance >= 1 ? chance : 1;
		this.params = params;
		dims = new ArrayList();
	}

	public void addDim(int i) {
		dims.add(i);
	}

	public BlockPos[] facing(BlockPos center) {
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

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (dims.contains(world.provider.getDimension())) {
			BlockPos pos = new BlockPos(chunkX * 16 + world.rand.nextInt(8) + 4, 0,
					chunkZ * 16 + world.rand.nextInt(8) + 4);
			generate(world, random, pos);
		}
	}

	abstract boolean generate(World world, Random random, BlockPos pos);

	public boolean generateOre(World world, BlockPos pos) {
		ResourceLocation name = world.getBlockState(pos).getBlock().getRegistryName();
		ResourceLocation tex = new ResourceLocation(name.getResourceDomain(), "blocks/" + name.getResourcePath());
		if (Config.stone.contains(world.getBlockState(pos))) {
			ArrayList<ResourceLocation> oresAdded = new ArrayList();
			ores.forEach((Material k, Double v) -> {
				if (world.rand.nextDouble() < v) {
					oresAdded.add(k.getRegistryName());
				}
			});
			ResourceLocation[] newOres = new ResourceLocation[oresAdded.size()];
			for (int i = 0; i < newOres.length; i++) {
				newOres[i] = oresAdded.get(i);
			}
			OreSavedData.get(world).setData(pos.getX(), pos.getY(), pos.getZ(), tex, newOres);
			world.setBlockState(pos, ModRegistry.superore.getDefaultState());
		} else if (Config.nether.contains(world.getBlockState(pos))) {
			ArrayList<ResourceLocation> oresAdded = new ArrayList();
			ores.forEach((Material k, Double v) -> {
				if (world.rand.nextDouble() < v) {
					oresAdded.add(k.getRegistryName());
				}
			});
			ResourceLocation[] newOres = new ResourceLocation[oresAdded.size()];
			for (int i = 0; i < newOres.length; i++) {
				newOres[i] = oresAdded.get(i);
			}
			OreSavedData.get(world).setData(pos.getX(), pos.getY(), pos.getZ(), tex, newOres);
			world.setBlockState(pos, ModRegistry.superore.getDefaultState());
		} else if (Config.end.contains(world.getBlockState(pos))) {
			ArrayList<ResourceLocation> oresAdded = new ArrayList();
			ores.forEach((Material k, Double v) -> {
				if (world.rand.nextDouble() < v) {
					oresAdded.add(k.getRegistryName());
				}
			});
			ResourceLocation[] newOres = new ResourceLocation[oresAdded.size()];
			for (int i = 0; i < newOres.length; i++) {
				newOres[i] = oresAdded.get(i);
			}
			OreSavedData.get(world).setData(pos.getX(), pos.getY(), pos.getZ(), tex, newOres);
			world.setBlockState(pos, ModRegistry.superore.getDefaultState());
		} else if (world.getBlockState(pos).getBlock() == ModRegistry.superore) {
			ArrayList<ResourceLocation> oresAdded = new ArrayList();
			ores.forEach((Material k, Double v) -> {
				if (world.rand.nextDouble() < v) {
					oresAdded.add(k.getRegistryName());
				}
			});
			ResourceLocation[] oldOres = OreSavedData.get(world).getOres(pos.getX(), pos.getY(), pos.getZ());
			ResourceLocation[] newOres = Arrays.copyOf(oldOres, oldOres.length + oresAdded.size());
			for (int i = 0; i < oresAdded.size(); i++) {
				newOres[i + oldOres.length] = oresAdded.get(i);
			}
			OreSavedData.get(world).setData(pos.getX(), pos.getY(), pos.getZ(), OreSavedData.get(world).getBase(pos),
					newOres);
		}
		return true;
	}

	public List getDims() {
		return dims;
	}

	public String getName() {
		return name;
	}

	public Map<Material, Double> getOres() {
		return ores;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public int getSize() {
		return size;
	}

	public void setName(String n) {
		name = n;
	}
}
