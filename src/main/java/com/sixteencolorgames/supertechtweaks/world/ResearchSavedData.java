package com.sixteencolorgames.supertechtweaks.world;

import java.util.HashMap;
import java.util.UUID;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.network.PacketHandler;
import com.sixteencolorgames.supertechtweaks.network.UpdateResearchUnlocksPacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

public class ResearchSavedData extends WorldSavedData {

	private static final String DATA_NAME = SuperTechTweaksMod.MODID + "_ResearchData"; // Required

	public static ResearchSavedData get(World world) {
		// The IS_GLOBAL constant is there for clarity, and should be simplified
		// into the right branch.
		MapStorage storage = world.getMapStorage();
		ResearchSavedData instance = (ResearchSavedData) storage.getOrLoadData(ResearchSavedData.class, DATA_NAME);

		if (instance == null) {
			instance = new ResearchSavedData();
			storage.setData(DATA_NAME, instance);
		}
		return instance;
	}

	// constructors
	HashMap<UUID, NonNullList<ResourceLocation>> researched = new HashMap();

	public ResearchSavedData() {
		super(DATA_NAME);
	}

	public ResearchSavedData(String s) {
		super(s);
	}

	public boolean getPlayerHasResearch(EntityPlayer player, ResourceLocation research) {
		if (!researched.containsKey(player.getUniqueID())) {
			researched.put(player.getUniqueID(), NonNullList.create());
		}
		return researched.get(player.getUniqueID()).contains(research);
	}

	public NonNullList<ResourceLocation> getPlayerResearched(EntityPlayer player) {
		if (!researched.containsKey(player.getUniqueID())) {
			researched.put(player.getUniqueID(), NonNullList.create());
		}
		return researched.get(player.getUniqueID());
	}

	public void playerUnlockResearch(EntityPlayer player, ResourceLocation selected) {
		if (!researched.containsKey(player.getUniqueID())) {
			researched.put(player.getUniqueID(), NonNullList.create());
		}
		if (!researched.get(player.getUniqueID()).contains(selected)) {
			researched.get(player.getUniqueID()).add(selected);
		}
		markDirty();
		UpdateResearchUnlocksPacket packet = new UpdateResearchUnlocksPacket(player);
		PacketHandler.INSTANCE.sendTo(packet, (EntityPlayerMP) player);
		System.out.println("Unlocking " + selected + " for " + player.getName());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		researched = new HashMap();
		nbt.getKeySet().forEach((String id) -> {
			UUID from = UUID.fromString(id);
			NonNullList<ResourceLocation> locs = NonNullList.create();

			NBTTagList tagList = nbt.getTagList(id, Constants.NBT.TAG_STRING);

			for (int i = 0; i < tagList.tagCount(); i++) {
				locs.add(new ResourceLocation(tagList.getStringTagAt(i)));
			}
			researched.put(from, locs);
		});
	}

	public void readFromUpdate(UUID id, NonNullList<ResourceLocation> locs) {
		researched.put(id, locs);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {

		researched.forEach((UUID id, NonNullList<ResourceLocation> list) -> {

			NBTTagList listSave = new NBTTagList();
			for (ResourceLocation loc : list) {
				listSave.appendTag(new NBTTagString(loc.toString()));
			}
			compound.setTag(id.toString(), listSave);
		});
		return compound;
	}
}
