package com.sixteencolorgames.supertechtweaks.world;

import java.util.Map;
import java.util.Random;

import com.sixteencolorgames.supertechtweaks.enums.Ore;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Generates a vein based loosely on perlin worms. This one needs heavy work
 * still
 * 
 * @author oa10712
 *
 */
public class WorldGeneratorVein extends WorldGeneratorBase {
	int heightVar = 10;
	int perChunk = 1;
	int length = 5;

	public WorldGeneratorVein(Map<Ore, Double> ores, String name, int[] dims, int size, int chance, int perChunk,
			int length, String... stones) {
		super(ores, name, dims, size, chance, stones);
		this.perChunk = perChunk;
		this.length = length;
	}

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

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		Block start = worldIn.getBlockState(position).getBlock();

		if (chance == 1 || rand.nextInt(chance) == 0) {
			for (int i = 0; i < perChunk; i++) {
				generateVein(worldIn, rand, position);
			}
		}

		OreSavedData.get(worldIn).setChunkGenerated((position.getX() / 16), (position.getZ() / 16));
		return true;
	}

	public boolean generateVein(World world, Random rand, BlockPos position) {
		int x = position.getX();
		int z = position.getZ();
		int height = rand.nextInt(world.getChunkFromChunkCoords(x >> 4, z >> 4).getHeightValue(x & 15, z & 15));
		Vec3d pos = new Vec3d(position.getX(), position.getY() + height, position.getZ());
		Vec3d dir = DIRS[rand.nextInt(DIRS.length)];
		IBlockState start = world.getBlockState(position.add(0, height, 0));
		for (int i = 0; i < size * length; i++) {
			BlockPos check = new BlockPos(pos);
			for (BlockPos adj : facing(check)) {
				super.generateOreBlock(world, adj, start);
			}
			while (pos.y + dir.y > height + heightVar) {
				dir = DIRS[rand.nextInt(DIRS.length)];
			}
			while (pos.y + dir.y < height - heightVar) {
				dir = DIRS[rand.nextInt(DIRS.length)];
			}
			pos = pos.add(dir);
		}
		return true;
	}
}
