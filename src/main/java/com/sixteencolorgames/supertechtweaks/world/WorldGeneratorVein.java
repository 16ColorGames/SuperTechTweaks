package com.sixteencolorgames.supertechtweaks.world;

import java.util.Map;
import java.util.Random;

import com.sixteencolorgames.supertechtweaks.enums.Ores;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class WorldGeneratorVein extends WorldGeneratorBase {

    private static final double scale = 1.3726;//e log(2)
    private static final Vec3d[] dirs = new Vec3d[]{
        new Vec3d(+0.0000, +0.0000, +1.0000).scale(scale),
        new Vec3d(+0.0000, +0.0000, -1.0000).scale(scale),
        new Vec3d(+1.0000, +0.0000, +0.0000).scale(scale),
        new Vec3d(-1.0000, +0.0000, +0.0000).scale(scale),
        new Vec3d(+0.7071, +0.0000, +0.7071).scale(scale),
        new Vec3d(-0.7071, +0.0000, +0.7071).scale(scale),
        new Vec3d(+0.7071, +0.0000, -0.7071).scale(scale),
        new Vec3d(-0.7071, +0.0000, -0.7071).scale(scale),
        new Vec3d(+0.0000, +0.4472, +0.8944).scale(scale),
        new Vec3d(+0.0000, +0.4472, -0.8944).scale(scale),
        new Vec3d(+0.8944, +0.4472, +0.0000).scale(scale),
        new Vec3d(-0.8944, +0.7071, +0.0000).scale(scale),
        new Vec3d(+0.6666, +0.3333, +0.6666).scale(scale),
        new Vec3d(-0.6666, +0.3333, +0.6666).scale(scale),
        new Vec3d(+0.6666, +0.3333, -0.6666).scale(scale),
        new Vec3d(-0.6666, +0.3333, -0.6666).scale(scale),
        new Vec3d(+0.0000, -0.4472, +0.8944).scale(scale),
        new Vec3d(+0.0000, -0.4472, -0.8944).scale(scale),
        new Vec3d(+0.8944, -0.4472, +0.0000).scale(scale),
        new Vec3d(-0.8944, -0.4472, +0.0000).scale(scale),
        new Vec3d(+0.6666, -0.3333, +0.6666).scale(scale),
        new Vec3d(-0.6666, -0.3333, +0.6666).scale(scale),
        new Vec3d(+0.6666, -0.3333, -0.6666).scale(scale),
        new Vec3d(-0.6666, -0.3333, -0.6666).scale(scale)};

    public WorldGeneratorVein(Map<Ores, Double> ores, int size, int min, int max, int chance,
            Map<String, Object> params) {
        super(ores, size, min, max, chance, params);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (rand.nextInt(chance - 1) == 0) {
            return generateVein(worldIn, rand, position);
        }
        return true;
    }

    public boolean generateVein(World world, Random rand, BlockPos position) {
        int height = rand.nextInt(maxY - minY) + minY;
        Vec3d pos = new Vec3d(position.getX(), position.getY() + height, position.getZ());
        // wander
        Vec3d dir = dirs[rand.nextInt(dirs.length)];
        for (int i = 0; i < size; i++) {
            BlockPos check = new BlockPos(pos);
            for (BlockPos adj : this.facing(check)) {
                super.generateOre(world, adj);
            }
            while (pos.yCoord + dir.yCoord > this.maxY) {
                dir = dirs[rand.nextInt(dirs.length)];
            }
            while (pos.yCoord + dir.yCoord < this.minY) {
                dir = dirs[rand.nextInt(dirs.length)];
            }
            pos = pos.add(dir);
        }
        // wander
        return true;
    }
}
