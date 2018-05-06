package com.sixteencolorgames.supertechtweaks.world;

import java.util.HashMap;
import java.util.UUID;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.enums.Research;
import com.sixteencolorgames.supertechtweaks.network.PacketHandler;
import com.sixteencolorgames.supertechtweaks.network.UpdateResearchUnlocksPacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ResearchSavedData extends WorldSavedData {

	private static final String DATA_NAME = SuperTechTweaksMod.MODID + "_ResearchData"; // Required

	public static ResearchSavedData get(World world) {
		MapStorage storage = world.getMapStorage();
		ResearchSavedData instance = (ResearchSavedData) storage.getOrLoadData(ResearchSavedData.class, DATA_NAME);

		if (instance == null) {
			instance = new ResearchSavedData();
			storage.setData(DATA_NAME, instance);
		}
		instance.world = world;
		return instance;
	}

	private World world;

	// constructors
	HashMap<UUID, HashMap<ResourceLocation, Integer>> progress = new HashMap();

	public ResearchSavedData() {
		super(DATA_NAME);
	}

	public ResearchSavedData(String s) {
		super(s);
	}

	public boolean getPlayerHasResearch(EntityPlayer player, ResourceLocation research) {
		return this.getPlayerHasResearch(player.getUniqueID(), research);
	}

	public boolean getPlayerHasResearch(UUID id, ResourceLocation rl) {
		Research value = GameRegistry.findRegistry(Research.class).getValue(rl);
		return (((int) progress.getOrDefault(id, new HashMap()).getOrDefault(rl, 0)) >= value.getEnergyRequired());

	}

	public HashMap<ResourceLocation, Integer> getPlayerResearched(EntityPlayer player) {
		return progress.getOrDefault(player.getUniqueID(), new HashMap());
	}

	public int getPlayerResearchProgress(UUID owner_UUID, String string) {
		return (int) progress.getOrDefault(owner_UUID, new HashMap()).getOrDefault(new ResourceLocation(string), 0);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		progress = new HashMap();
		nbt.getKeySet().forEach((String playerId) -> {
			UUID from = UUID.fromString(playerId);

			NBTTagCompound playerCompound = nbt.getCompoundTag(playerId);
			HashMap<ResourceLocation, Integer> player = new HashMap();

			playerCompound.getKeySet().forEach((String resId) -> {
				player.put(new ResourceLocation(resId), playerCompound.getInteger(resId));
			});

			progress.put(from, player);
		});
	}

	public void readFromUpdate(UUID id, HashMap<ResourceLocation, Integer> locs) {
		progress.put(id, locs);
	}

	public void setPlayerResearchProgress(UUID owner_UUID, String string, int i) {
		if (progress.containsKey(owner_UUID)) {
			progress.get(owner_UUID).put(new ResourceLocation(string), i);
		} else {
			HashMap<ResourceLocation, Integer> value = new HashMap();
			value.put(new ResourceLocation(string), i);
			progress.put(owner_UUID, value);
		}
		markDirty();
		try {
			UpdateResearchUnlocksPacket packet = new UpdateResearchUnlocksPacket(
					world.getPlayerEntityByUUID(owner_UUID));
			PacketHandler.INSTANCE.sendTo(packet, (EntityPlayerMP) world.getPlayerEntityByUUID(owner_UUID));
		} catch (Exception ex) {

		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		System.out.println("Saving research");
		progress.forEach((id, map) -> {
			NBTTagCompound playerList = new NBTTagCompound();
			map.forEach((rl, i) -> {
				playerList.setInteger(rl.toString(), i);
			});
			compound.setTag(id.toString(), playerList);
		});
		return compound;
	}
}
