package com.sixteencolorgames.supertechtweaks.world;

import java.util.Map;
import java.util.Random;

import com.sixteencolorgames.supertechtweaks.enums.Ores;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGeneratorCluster extends WorldGeneratorBase {

    private final int width;

    public WorldGeneratorCluster(Map<Ores, Double> ores, int size, int min, int max, int chance,
            Map<String, Object> params) {
        super(ores, size, min, max, chance, params);
        width = (int) Math.sqrt(size);
    }

    public boolean generateCluster(World worldIn, Random rand, BlockPos position) {
        int height = rand.nextInt(maxY - minY) + minY;
        int variance = ((int) params.getOrDefault("clusterVariance", 1) > 0)
                ? (int) params.getOrDefault("clusterVariance", 1) : 1;
        int numBlocks = size + rand.nextInt(variance);
        for (int i = 0; i < numBlocks; i++) {
            BlockPos newPos = position.add(rand.nextInt(width), height + rand.nextInt(width), rand.nextInt(width));
            super.generateOre(worldIn, newPos);
        }
        return true;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {

        position.add(worldIn.rand.nextInt(16), 0, worldIn.rand.nextInt(16));
        if (chance == 1 || rand.nextInt(chance) == 0) {
            if ((int) params.getOrDefault("perChunk", 1) <= 1) {
                return generateCluster(worldIn, rand, position.add(rand.nextInt(16), 0, rand.nextInt(16)));
            } else {
                for (int i = 0; i < (int) params.getOrDefault("perChunk", 1); i++) {
                    generateCluster(worldIn, rand, position.add(rand.nextInt(16), 0, rand.nextInt(16)));
                }
            }
        }
        return true;
    }
}
