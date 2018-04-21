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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkWatchEvent;
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
	public void onPlayerLogin(EntityJoinWorldEvent e) {
		if (e.getEntity() instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) e.getEntity();
			sentChunks.put(player.getUniqueID(), new ArrayList());
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
		if (sentChunks.get(e.getPlayer().getUniqueID()).contains(new Pair(x, z))) {
			sentChunks.get(e.getPlayer().getUniqueID()).remove(new Pair(x, z));
		}
	}

	@SubscribeEvent
	public void onPlayerWatchChunk(ChunkWatchEvent.Watch e) {
		int x = e.getChunkInstance().x;
		int z = e.getChunkInstance().z;
		handleOreUpdate(e.getPlayer(), x, z);

		OreSavedData get = OreSavedData.get(e.getPlayer().world);
		Chunk chunk = e.getChunkInstance();
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

}
