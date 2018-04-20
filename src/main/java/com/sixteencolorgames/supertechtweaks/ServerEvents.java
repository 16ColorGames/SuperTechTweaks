/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import com.sixteencolorgames.supertechtweaks.network.PacketHandler;
import com.sixteencolorgames.supertechtweaks.network.UpdateOresPacket;
import com.sixteencolorgames.supertechtweaks.proxy.CommonProxy;
import com.sixteencolorgames.supertechtweaks.world.OreSavedData;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 *
 * @author oa10712
 */

public class ServerEvents {

	HashMap<UUID, ArrayList<Pair>> sentChunks = new HashMap();

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
	public void onPlayerEnterChunk(EntityEvent.EnteringChunk e) {
		if (e.getEntity() instanceof EntityPlayerMP) {
			EntityPlayerMP en = (EntityPlayerMP) e.getEntity();
			for (int x = -3; x < 4; x++) {
				for (int z = -3; z < 4; z++) {
					handleOreUpdate((EntityPlayerMP) e.getEntity(), e.getEntity().chunkCoordX + x,
							e.getEntity().chunkCoordZ + z);// sent the player
															// the nearby chunks
															// on startup

					MinecraftServer server = e.getEntity().getServer();

					OreSavedData get = OreSavedData.get(e.getEntity().world);
					Chunk chunk = e.getEntity().world.getChunkFromChunkCoords(x, z);
					NBTTagCompound forChunk = get.getForChunk(x, z);

					World world = e.getEntity().world;
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
			}
		}
	}

	@SubscribeEvent
	public void onPlayerEnterDimension(PlayerEvent.PlayerChangedDimensionEvent e) {
		if (e.player instanceof EntityPlayerMP) {
			for (int x = -4; x < 5; x++) {
				for (int z = -4; z < 5; z++) {
					handleOreUpdate((EntityPlayerMP) e.player, e.player.chunkCoordX + x, e.player.chunkCoordZ + z);
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerLogin(EntityJoinWorldEvent e) {
		if (e.getEntity() instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) e.getEntity();
			sentChunks.put(player.getUniqueID(), new ArrayList());
			for (int x = -4; x < 5; x++) {
				for (int z = -4; z < 5; z++) {
					handleOreUpdate(player, player.chunkCoordX + x, player.chunkCoordZ + z);
				}
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

}
