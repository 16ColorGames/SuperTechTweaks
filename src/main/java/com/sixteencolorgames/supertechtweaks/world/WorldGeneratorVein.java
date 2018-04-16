package com.sixteencolorgames.supertechtweaks.world;

import com.sixteencolorgames.supertechtweaks.enums.Material;
import java.util.Map;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WorldGeneratorVein extends WorldGeneratorBase {

	private static final double SCALE = 1.3726;// e log(2)
	private static final Vec3d[] DIRS = new Vec3d[] { new Vec3d(+0.0000, +0.0000, +1.0000).scale(SCALE),
			new Vec3d(+0.0000, +0.0000, -1.0000).scale(SCALE), new Vec3d(+1.0000, +0.0000, +0.0000).scale(SCALE),
			new Vec3d(-1.0000, +0.0000, +0.0000).scale(SCALE), new Vec3d(+0.7071, +0.0000, +0.7071).scale(SCALE),
			new Vec3d(-0.7071, +0.0000, +0.7071).scale(SCALE), new Vec3d(+0.7071, +0.0000, -0.7071).scale(SCALE),
			new Vec3d(-0.7071, +0.0000, -0.7071).scale(SCALE), new Vec3d(+0.0000, +0.4472, +0.8944).scale(SCALE),
			new Vec3d(+0.0000, +0.4472, -0.8944).scale(SCALE), new Vec3d(+0.8944, +0.4472, +0.0000).scale(SCALE),
			new Vec3d(-0.8944, +0.7071, +0.0000).scale(SCALE), new Vec3d(+0.6666, +0.3333, +0.6666).scale(SCALE),
			new Vec3d(-0.6666, +0.3333, +0.6666).scale(SCALE), new Vec3d(+0.6666, +0.3333, -0.6666).scale(SCALE),
			new Vec3d(-0.6666, +0.3333, -0.6666).scale(SCALE), new Vec3d(+0.0000, -0.4472, +0.8944).scale(SCALE),
			new Vec3d(+0.0000, -0.4472, -0.8944).scale(SCALE), new Vec3d(+0.8944, -0.4472, +0.0000).scale(SCALE),
			new Vec3d(-0.8944, -0.4472, +0.0000).scale(SCALE), new Vec3d(+0.6666, -0.3333, +0.6666).scale(SCALE),
			new Vec3d(-0.6666, -0.3333, +0.6666).scale(SCALE), new Vec3d(+0.6666, -0.3333, -0.6666).scale(SCALE),
			new Vec3d(-0.6666, -0.3333, -0.6666).scale(SCALE) };

	public WorldGeneratorVein(Map<Material, Double> ores, int size, int min, int max, int chance,
			Map<String, Object> params) {
		super(ores, size, min, max, chance, params);
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		if (chance <= 1) {
			if ((int) params.getOrDefault("perChunk", 1) <= 1) {
				return generateVein(worldIn, rand, position);
			} else {
				for (int i = 0; i < (int) params.getOrDefault("perChunk", 1); i++) {
					generateVein(worldIn, rand, position);
				}
			}
		} else if (rand.nextInt(chance - 1) == 0) {
			if ((int) params.getOrDefault("perChunk", 1) <= 1) {
				return generateVein(worldIn, rand, position);
			} else {
				for (int i = 0; i < (int) params.getOrDefault("perChunk", 1); i++) {
					generateVein(worldIn, rand, position);
				}
			}
		}
		OreSavedData.get(worldIn).setChunkGenerated((position.getX() / 16), (position.getZ() / 16));
		return true;
	}

	public boolean generateVein(World world, Random rand, BlockPos position) {
		int height = rand.nextInt(maxY - minY) + minY;
		Vec3d pos = new Vec3d(position.getX(), position.getY() + height, position.getZ());
		Vec3d dir = DIRS[rand.nextInt(DIRS.length)];
		for (int i = 0; i < size * (int) params.getOrDefault("branchLength", 5); i++) {
			BlockPos check = new BlockPos(pos);
			for (BlockPos adj : this.facing(check)) {
				super.generateOre(world, adj);
			}
			while (pos.y + dir.y > this.maxY) {
				dir = DIRS[rand.nextInt(DIRS.length)];
			}
			while (pos.y + dir.y < this.minY) {
				dir = DIRS[rand.nextInt(DIRS.length)];
			}
			pos = pos.add(dir);
		}
		return true;
	}
}
