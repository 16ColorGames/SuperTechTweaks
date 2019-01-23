/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import com.sixteencolorgames.supertechtweaks.network.PacketHandler;
import com.sixteencolorgames.supertechtweaks.network.UpdateOresPacket;
import com.sixteencolorgames.supertechtweaks.noise.SimplexNoise;
import com.sixteencolorgames.supertechtweaks.proxy.CommonProxy;
import com.sixteencolorgames.supertechtweaks.tileentities.conveyor.TileEntityConveyorBase;
import com.sixteencolorgames.supertechtweaks.world.OreSavedData;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 *
 * @author oa10712
 */

public class ServerEvents {

	private static final ArrayList<EventType> vanillaOreGeneration = new ArrayList<EventType>();

	double genScale = 0.004;

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
		vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.ANDESITE);
		vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.DIORITE);
		vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.GRANITE);
	}

	HashMap<UUID, ArrayList<Pair>> sentChunks = new HashMap();

	@SubscribeEvent
	public void handleOreGenEvent(OreGenEvent.GenerateMinable event) {
		if (Config.removeVanilla && vanillaOreGeneration.contains(event.getType())) {
			event.setResult(Result.DENY);
		}
	}

	@SubscribeEvent
	public void tick(TickEvent.ClientTickEvent event) {
		World world = Minecraft.getMinecraft().world;

		if (world == null)
			return;

		for (Entity e : world.loadedEntityList) {
			if (e instanceof EntityItem && e.ticksExisted % 15 == 0
					&& !(world.getTileEntity(new BlockPos(e)) instanceof TileEntityConveyorBase))
				e.getEntityData().setBoolean("onBelt", false);
		}
	}

	private void handleOreUpdate(EntityPlayerMP e, int newChunkX, int newChunkZ) {
		if (e.world != null) {
			if (!sentChunks.get(e.getUniqueID()).contains(new Pair(newChunkX, newChunkZ))) {
				UpdateOresPacket packet = new UpdateOresPacket(OreSavedData.get(e.world), newChunkX, newChunkZ);
				PacketHandler.INSTANCE.sendTo(packet, e);
				sentChunks.get(e.getUniqueID()).add(new Pair(newChunkX, newChunkZ));
			}
		}
	}

	@SubscribeEvent
	public void onPlayerLogin(EntityJoinWorldEvent e) {
		if (e.getEntity() instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) e.getEntity();
			sentChunks.put(player.getUniqueID(), new ArrayList());

			if (Config.debug) {
				HashMap<ResourceLocation, Integer> oreCount = new HashMap();
				for (int xC = -4; xC < 5; xC++) {
					for (int zC = -4; zC < 5; zC++) {
						NBTTagCompound forChunk = OreSavedData.get(e.getWorld()).getForChunk(player.chunkCoordX + xC,
								player.chunkCoordZ + zC);
						forChunk.getKeySet().forEach((x) -> {
							NBTTagCompound xTag = forChunk.getCompoundTag(x);
							xTag.getKeySet().forEach((y) -> {
								NBTTagCompound yTag = xTag.getCompoundTag(y);
								yTag.getKeySet().forEach((z) -> {
									NBTTagList tag = yTag.getTagList(z, Constants.NBT.TAG_STRING);
									for (int i = 0; i < tag.tagCount(); i++) {
										ResourceLocation rl = new ResourceLocation(tag.getStringTagAt(i));
										if (oreCount.containsKey(rl)) {
											oreCount.put(rl, oreCount.get(rl) + 1);
										} else {
											oreCount.put(rl, 1);
										}
									}
								});
							});
						});
					}
				}
				System.out.println("Ores found near login:");
				oreCount.forEach((mat, count) -> {
					System.out.println("  " + mat.toString() + ": " + count);
				});
			}
		}
	}

	@SubscribeEvent
	public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent e) {
		if (e.player instanceof EntityPlayerMP) {
			sentChunks.remove(e.player.getUniqueID());// remove the player from
														// the chunk tracker
		}
	}

	/**
	 * cleanup to keep the server ram usage down
	 *
	 * @param e
	 */
	@SubscribeEvent
	public void onPlayerUnWatchChunk(ChunkWatchEvent.UnWatch e) {
		int x = e.getChunkInstance().x;
		int z = e.getChunkInstance().z;
		if (sentChunks.containsKey(e.getPlayer().getUniqueID())) {
			sentChunks.get(e.getPlayer().getUniqueID()).remove(new Pair(x, z));
		}
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		// noise = new OpenSimplexNoise(event.getWorld().getSeed());
	}

	@SubscribeEvent
	public void onPlayerWatchChunk(ChunkWatchEvent.Watch e) {
		int x = e.getChunk().x;
		int z = e.getChunk().z;
		handleOreUpdate(e.getPlayer(), x, z);

		OreSavedData get = OreSavedData.get(e.getPlayer().world);
		Chunk chunk = e.getPlayer().world.getChunkFromChunkCoords(x, z);
		NBTTagCompound forChunk = get.getForChunk(x, z);

		World world = e.getPlayer().world;
		if (forChunk.hasNoTags() || !get.isChunkGenerated(x, z)) {
			for (int x1 = chunk.x * 16; x1 < chunk.x * 16 + 16; x1++) {
				for (int y1 = 0; y1 < 256; y1++) {
					for (int z1 = chunk.z * 16; z1 < chunk.z * 16 + 16; z1++) {

						BlockPos targetBlockPos = new BlockPos(x1, y1, z1);
						IBlockState targetBlockState = world.getBlockState(targetBlockPos);
						Block targetBlock = targetBlockState.getBlock();

						if (targetBlock.equals(ModRegistry.superore)
								&& get.getBase(targetBlockPos) == new ResourceLocation("minecraft:stone")) {
							world.setBlockState(targetBlockPos, Blocks.STONE.getDefaultState());
						}

					}
				}
			}
			Random random = chunk.getRandomWithSeed(world.getSeed());
			CommonProxy.parsed.forEach((gen) -> {
				gen.generate(random, chunk.x, chunk.z, world, null, world.getChunkProvider());
			});

			get.setChunkGenerated(x, z);
		}

	}

	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onChunkLoadEvent(ChunkEvent.Load event) {
		// replace all blocks of a type with another block type
		// diesieben07 came up with this method
		// (http://www.minecraftforge.net/forum/index.php/topic,21625.0.html)

		Chunk chunk = event.getChunk();
		long seed = event.getWorld().getSeed();
		NoiseGeneratorPerlin ngo = new NoiseGeneratorPerlin(event.getWorld().rand, 8);
		SimplexNoise noise = new SimplexNoise();
		Random offsetRandom = new Random(seed);
		Vec3d offset1 = new Vec3d(10000.0F * offsetRandom.nextFloat(), 10000.0F * offsetRandom.nextFloat(),
				10000.0F * offsetRandom.nextFloat());
		Vec3d offset2 = new Vec3d(10000.0F * offsetRandom.nextFloat(), 10000.0F * offsetRandom.nextFloat(),
				10000.0F * offsetRandom.nextFloat());
		Vec3d offset3 = new Vec3d(10000.0F * offsetRandom.nextFloat(), 10000.0F * offsetRandom.nextFloat(),
				10000.0F * offsetRandom.nextFloat());
		for (ExtendedBlockStorage storage : chunk.getBlockStorageArray()) {
			if (storage != null) {
				for (int x = 0; x < 16; ++x) {
					for (int z = 0; z < 16; ++z) {
						int igneous = (int) (noise.get2dNoiseValue(x + chunk.x * 16, z + chunk.z * 16, offset1,
								genScale) * 7) + 15;
						int sedimentary = (int) (noise.get2dNoiseValue(x + chunk.x * 16, z + chunk.z * 16, offset1,
								genScale) * 7) + 15;
//						int height = event.getWorld().getHeight(x, z);
						int height = 255;
						for (int y = 0; y < height; y++) {

							BlockPos coord = new BlockPos(x, y, z);

							if (CommonProxy.vanillaReplace.contains(chunk.getBlockState(coord))) {
								double val = noise.get3dNoiseValue(x + chunk.x * 16, y, z + chunk.z * 16, offset3,
										genScale);
								if (y < igneous) {
									// RockType.IGNEOUS;
									chunk.setBlockState(coord,
											pickBlockFromSet(val, RockManager.stoneSpawns.get("igneous")));
								} else if (y > height - sedimentary) {
									// RockType.SEDIMENTARY;
									chunk.setBlockState(coord,
											pickBlockFromSet(val, RockManager.stoneSpawns.get("sedimentary")));
								} else {
									// RockType.METAMORPHIC;
									chunk.setBlockState(coord,
											pickBlockFromSet(val, RockManager.stoneSpawns.get("metamorphic")));
								}
							}
						}
					}
				}
			}
		}
		Random chunkRandom = chunk.getRandomWithSeed(seed);
		if (chunkRandom.nextDouble() <= 0.25) {
			int cx = chunkRandom.nextInt(10) + 3;
			int cz = chunkRandom.nextInt(10) + 3;
			double height = chunkRandom.nextInt(6) + 12;
			for (double y = 0; y < height; y++) {
				int s = (int) (4.0d * ((height - y) / height)) + 1;
				for (int x = -s; x < s; x++) {
					for (int z = -s; z < s; z++) {
						BlockPos pos = new BlockPos(cx + x + chunk.x * 16, y, cz + z + chunk.z * 16);
						if (!chunk.getBlockState(pos).equals(Blocks.BEDROCK.getDefaultState())) {
							chunk.setBlockState(pos, RockManager.stoneSpawns.get("kimberlite").iterator().next());
						}
					}
				}
			}
		}
		chunk.setModified(true);// this is important as it marks it to be saved
	}

	private IBlockState pickBlockFromSet(double value, Set<IBlockState> list) {
		value = ((value + 1) / 2) * list.size();
		return list.stream().skip((int) value).findFirst().get();
	}
}
