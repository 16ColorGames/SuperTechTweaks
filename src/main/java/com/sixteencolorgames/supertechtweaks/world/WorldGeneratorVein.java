package com.sixteencolorgames.supertechtweaks.world;

import java.util.Map;
import java.util.Random;

import com.sixteencolorgames.supertechtweaks.enums.Ores;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class WorldGeneratorVein extends WorldGeneratorBase {

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
		BlockPos start = position.add(0, height, 0);
		for (int i = 0; i < size; i++) {// For each segment
			double y;
			if (start.getY() > maxY) {
				y = -(rand.nextDouble() / 4);
			} else if (start.getY() < minY) {
				y = rand.nextDouble() / 4;
			} else {
				y = (rand.nextDouble() - .5) / 8;
			}

			Vec3d direction = new Vec3d((rand.nextDouble() - .5) * 2, y, (rand.nextDouble() - 1) * .5).normalize()
					.scale(((int) params.get("branchLength")));// Generate
																// a
																// random
																// vector
																// of
																// length
																// branchLength
			Vec3i end = new Vec3i(start.getX() + direction.xCoord, start.getY() + direction.yCoord,
					start.getZ() + direction.zCoord);
			BlockPos check = start.down().up();
			// Check if box is on line from start to end
			Vec3d checkUnit = direction.normalize().scale(.05d);
			Vec3d checkPos = new Vec3d(0, 0, 0);
			while (checkPos.distanceTo(direction) > .5) {
				check = check.add(checkPos.xCoord, checkPos.yCoord, checkPos.zCoord);
				for (BlockPos pos : this.facing(check)) {
					super.generateOre(world, pos);
				}
				checkPos = checkPos.add(checkUnit);
			}
			start = start.add(direction.xCoord, direction.yCoord, direction.zCoord);
		}
		return true;
	}
}
