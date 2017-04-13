/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.world;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

/**
 *
 * @author oa10712
 */
public class SingleGenerator implements IWorldGenerator {
    
    private final WorldGeneratorBase base;
    
    public SingleGenerator(WorldGeneratorBase base) {
        this.base = base;
    }
    
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (base.dims.contains(world.provider.getDimension())) {
            BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16).add(random.nextInt(16), 0, random.nextInt(16));
            base.generate(world, random, pos);
        }
    }
    
}
