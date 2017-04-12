package com.sixteencolorgames.supertechtweaks.world;

import java.util.ArrayList;
import java.util.Random;

import com.sixteencolorgames.supertechtweaks.Config;
import java.util.List;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModWorldGeneration implements IWorldGenerator {

    public ArrayList<WorldGeneratorBase> generators;
    public ArrayList<WorldGeneratorBase> nethers;
    public ArrayList<WorldGeneratorBase> ends;
    private static final ArrayList<EventType> vanillaOreGeneration = new ArrayList<EventType>();

    static {
        vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.COAL);
        vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.DIAMOND);
        vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.DIRT);
        vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.GOLD);
        vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.IRON);
        vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.LAPIS);
        vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.REDSTONE);
        vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.QUARTZ);
        vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.EMERALD);
    }

    public ModWorldGeneration() {
        generators = new ArrayList();
        nethers = new ArrayList();
        ends = new ArrayList();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.ORE_GEN_BUS.register(this);
    }

    public ModWorldGeneration(ArrayList<WorldGeneratorBase> list) {
        System.out.println("Creating world generator: " + list.size());
        generators = list;
        generators.forEach((gen) -> {
            System.out.print(gen.getName());
        });
    }

    public void addGenerators(ArrayList<WorldGeneratorBase> list) {
        list.forEach((WorldGeneratorBase t) -> {
            List dims = t.getDims();
            if (dims.contains(-1)) {
                nethers.add(t);
            }
            if (dims.contains(0)) {
                generators.add(t);
            }
            if (dims.contains(1)) {
                ends.add(t);
            }
        });
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
            IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()) {
            case 0:
                // the overworld
                generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
                break;
            case -1:
                //the nether
                generateNether(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
                break;
            case 1:
                //the end
                generateEnd(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
                break;
            default:
                break;
        }
    }

    private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
            IChunkProvider chunkProvider) {
        generators.forEach((WorldGeneratorBase gen) -> {
            BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16).add(random.nextInt(16), 0, random.nextInt(16));
            world.getMinecraftServer().addScheduledTask(() -> {
                gen.generate(world, random, pos);
            });
        });
    }

    private void generateEnd(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
            IChunkProvider chunkProvider) {
        ends.forEach((WorldGeneratorBase gen) -> {
            BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16).add(random.nextInt(16), 0, random.nextInt(16));
            world.getMinecraftServer().addScheduledTask(() -> {
                gen.generate(world, random, pos);
            });
        });
    }

    private void generateNether(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
            IChunkProvider chunkProvider) {
        nethers.forEach((WorldGeneratorBase gen) -> {
            BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16).add(random.nextInt(16), 0, random.nextInt(16));
            world.getMinecraftServer().addScheduledTask(() -> {
                gen.generate(world, random, pos);
            });
        });
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = false)
    public void handleOreGenEvent(OreGenEvent.GenerateMinable event) {

        if (!Config.removeVanilla) {
            return;
        }
        if (vanillaOreGeneration.contains(event.getType())) {
            event.setResult(Result.DENY);
        }
    }
}
