package com.sixteencolorgames.supertechtweaks.world;

import java.util.Map;
import java.util.Random;

import com.sixteencolorgames.supertechtweaks.enums.Ores;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class WorldGeneratorVein extends WorldGeneratorBase {

	private double scale = 1.5;

	public WorldGeneratorVein(Map<Ores, Double> ores, int size, int min, int max, int chance,
			Map<String, Object> params) {
		super(ores, size, min, max, chance, params);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		if (rand.nextInt(chance - 1) == 0) {
			return generateVein(worldIn, rand, position);
		}
		return true;
	}

	public boolean generateVein(World world, Random rand, BlockPos position) {
		// TODO Auto-generated method stub
		int height = rand.nextInt(maxY - minY) + minY;
		Vec3d pos = new Vec3d(position.getX(), position.getY() + height, position.getZ());
		// wander
		Vec3d dir = new Vec3d((rand.nextDouble() - .5) * 2, (rand.nextDouble() - .5) * 2, (rand.nextDouble() - .5) * 2)
				.normalize().scale(scale);
		for (int i = 0; i < size; i++) {
			BlockPos check = new BlockPos(pos);
			for (BlockPos adj : this.facing(check)) {
				super.generateOre(world, adj);
			}
			while (pos.yCoord + dir.yCoord > this.maxY) {
				dir = new Vec3d((rand.nextDouble() - 1.0), (rand.nextDouble() - 1.0), (rand.nextDouble() - 1.0))
						.normalize().scale(scale);
			}
			while (pos.yCoord + dir.yCoord < this.minY) {
				dir = new Vec3d((rand.nextDouble()), (rand.nextDouble()), (rand.nextDouble())).normalize().scale(scale);
			}
			pos = pos.add(dir);
		}
		// wander
		return true;
	}
}
