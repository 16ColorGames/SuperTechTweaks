package com.sixteencolorgames.supertechtweaks.world;

import java.util.Map;

import com.sixteencolorgames.supertechtweaks.ModBlocks;
import com.sixteencolorgames.supertechtweaks.enums.Ores;
import com.sixteencolorgames.supertechtweaks.tileentities.TileEntityOre;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class WorldGeneratorBase extends WorldGenerator {
	public Map<Ores, Double> ores;// List of metals in this generator
	public int size;// Size of the generator. This means different things
					// depending on the implementation
	public Map<String, Object> params;// Other parameters specific to
										// implementations
	public int maxY;// Maximum height for the generator (rough)
	public int minY;// Minimum Height for the generator (rough)
	public int chance;// Chance per chunk to generate an instance
	private String name;// The identifying name for this generator

	public WorldGeneratorBase(Map<Ores, Double> ores, int size, int min, int max, int chance,
			Map<String, Object> params) {
		this.ores = ores;
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

	public Map<Ores, Double> getOres() {
		return ores;
	}

	public int getSize() {
		return size;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public boolean generateOre(World world, BlockPos pos) {
		if (Types.stone.contains(world.getBlockState(pos))) {
			String base = world.getBlockState(pos).getBlock().getUnlocalizedName();
			world.setBlockState(pos, ModBlocks.blockOre.getDefaultState());
			TileEntity entity = world.getTileEntity(pos);
			if (entity instanceof TileEntityOre) {
				TileEntityOre tile = (TileEntityOre) entity;
				tile.setBase(base);
				ores.forEach((k, v) -> {
					if (world.rand.nextDouble() < v) {
						tile.addMetal(k);
					}
				});
			}
		} else if (world.getBlockState(pos).getBlock() == ModBlocks.blockOre) {
			TileEntityOre tile = (TileEntityOre) world.getTileEntity(pos);
			ores.forEach((k, v) -> {
				if (world.rand.nextDouble() < v) {
					tile.addMetal(k);
				}
			});
		}
		return true;
	}

	public static boolean generateBlock(World world, BlockPos pos, IBlockState b) {
		return world.setBlockState(pos, b, 3);
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
}
