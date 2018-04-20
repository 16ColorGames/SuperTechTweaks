/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.world;

import java.util.ArrayList;
import java.util.HashMap;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.proxy.ClientProxy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

/**
 *
 * @author oa10712
 */
public class OreSavedData extends WorldSavedData {

	private static final String DATA_NAME = SuperTechTweaksMod.MODID + "_OreData";

	public static OreSavedData get(World world) {
		// OreSavedData data = (OreSavedData)
		// world.loadItemData(OreSavedData.class, DATA_NAME);
		// The IS_GLOBAL constant is there for clarity, and should be simplified
		// into the right branch.
		MapStorage storage = world.getPerWorldStorage();
		OreSavedData instance = (OreSavedData) storage.getOrLoadData(OreSavedData.class, DATA_NAME);

		if (instance == null) {
			instance = new OreSavedData();
			storage.setData(DATA_NAME, instance);
		}
		return instance;
	}

	public static void set(World world, OreSavedData newData) {
		MapStorage storage = world.getPerWorldStorage();
		storage.setData(DATA_NAME, newData);
	}

	/**
	 * <X,<Y,<Z,Data>>> where Data[0] is the base type of the ore and the
	 * remaining elements are the ore data
	 */
	HashMap<Integer, HashMap<Integer, HashMap<Integer, ResourceLocation[]>>> data = new HashMap();

	HashMap<Integer, ArrayList<Integer>> generated = new HashMap();
	// Required constructors

	public OreSavedData() {
		super(DATA_NAME);
	}

	public OreSavedData(String s) {
		super(s);
	}

	public void clearData() {
		data = new HashMap();
		generated = new HashMap();
		this.markDirty();
	}

	public ResourceLocation getBase(BlockPos pos) {
		return getBase(pos.getX(), pos.getY(), pos.getZ());
	}

	public ResourceLocation getBase(int x, int y, int z) {
		try {
			return data.get(x).get(y).get(z)[0];
		} catch (Exception ex) {
			return new ResourceLocation("minecraft:stone");
		}
	}

	/**
	 * Creates a tag of ore data in a chunk. Intended for use with
	 * #updateFromTag.
	 *
	 * @param chunkX
	 * @param chunkZ
	 * @return
	 */
	public NBTTagCompound getForChunk(int chunkX, int chunkZ) {
		NBTTagCompound ret = new NBTTagCompound();
		int xStart = chunkX * 16;
		int zStart = chunkZ * 16;

		for (int i = 0; i < 16; i++) {// cycle the x range
			if (data.containsKey(xStart + i)) {
				NBTTagCompound xTag = new NBTTagCompound();
				HashMap<Integer, HashMap<Integer, ResourceLocation[]>> xData = data.get(xStart + i);
				xData.forEach((Integer y, HashMap<Integer, ResourceLocation[]> yData) -> {
					NBTTagCompound yTag = new NBTTagCompound();
					yData.forEach((Integer z, ResourceLocation[] ores) -> {
						if (z >= zStart && z < zStart + 16) {// if its within
																// the z range
							NBTTagList list = new NBTTagList();
							for (int i1 = 0; i1 < ores.length; i1++) {
								list.appendTag(new NBTTagString(ores[i1].toString()));
							}
							yTag.setTag(z.toString(), list);
						}
					});
					xTag.setTag(y.toString(), yTag);
				});
				ret.setTag((i + xStart) + "", xTag);
			}
		}
		return ret;
	}

	/**
	 * Creates a tag of ore data in a single position. Intended for use with
	 * block break updates.
	 *
	 * @return
	 */
	public NBTTagCompound getForPos(BlockPos pos) {
		NBTTagCompound ret = new NBTTagCompound();
		if (data.containsKey(pos.getX())) {
			NBTTagCompound xTag = new NBTTagCompound();
			if (data.get(pos.getX()).containsKey(pos.getY())) {
				NBTTagCompound yTag = new NBTTagCompound();
				if (data.get(pos.getX()).get(pos.getY()).containsKey(pos.getZ())) {
					ResourceLocation[] get = data.get(pos.getX()).get(pos.getY()).get(pos.getZ());
					ResourceLocation[] arr = new ResourceLocation[get.length];

					NBTTagList list = new NBTTagList();
					for (int i1 = 0; i1 < get.length; i1++) {
						list.appendTag(new NBTTagString(get[i1].toString()));
					}
					yTag.setTag(pos.getZ() + "", list);
				} else {
					NBTTagList list = new NBTTagList();
					yTag.setTag(pos.getZ() + "", list);
				}
				xTag.setTag(pos.getY() + "", yTag);
			}
			ret.setTag(pos.getX() + "", xTag);
		}
		return ret;
	}

	public ResourceLocation[] getOres(BlockPos pos) {
		return getOres(pos.getX(), pos.getY(), pos.getZ());
	}

	public ResourceLocation[] getOres(int x, int y, int z) {
		try {
			ResourceLocation[] get = data.get(x).get(y).get(z);
			ResourceLocation[] ret = new ResourceLocation[get.length - 1];
			for (int i = 1; i < get.length; i++) {
				ret[i - 1] = get[i];
			}
			return ret;
		} catch (Exception ex) {
			return new ResourceLocation[0];
		}
	}

	public boolean isChunkGenerated(int newChunkX, int newChunkZ) {
		return generated.containsKey(newChunkX) && generated.get(newChunkX).contains(newChunkZ);
	}

	// This is where you load the data that you saved in writeToNBT
	@Override
	public void readFromNBT(NBTTagCompound parentNBTTagCompound) {
		parentNBTTagCompound.getKeySet().forEach((x) -> {
			NBTTagCompound xTag = parentNBTTagCompound.getCompoundTag(x);
			xTag.getKeySet().forEach((y) -> {
				NBTTagCompound yTag = xTag.getCompoundTag(y);
				yTag.getKeySet().forEach((z) -> {

					NBTTagList tag = yTag.getTagList(z, Constants.NBT.TAG_STRING);
					ResourceLocation[] dataList = new ResourceLocation[tag.tagCount()];

					for (int i = 0; i < tag.tagCount(); i++) {
						dataList[i] = new ResourceLocation(tag.getStringTagAt(i));
					}
					setData(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z), dataList);
					BlockPos pos = new BlockPos(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
					if (SuperTechTweaksMod.proxy instanceof ClientProxy) {
						try {
							SuperTechTweaksMod.proxy.getWorld().markBlockRangeForRenderUpdate(pos, pos);
						} catch (Exception ex) {
						}
					}
					setChunkGenerated((Integer.parseInt(x) / 16), (Integer.parseInt(z) / 16));
				});
			});
		});
		this.markDirty();
	}

	public void setBase(int x, int y, int z, ResourceLocation base) {
		if (!data.containsKey(x)) {
			data.put(x, new HashMap());
		}
		if (!data.get(x).containsKey(y)) {
			data.get(x).put(y, new HashMap());
		}
		if (data.get(x).get(y).containsKey(z)) {
			data.get(x).get(y).get(z)[0] = base;
			return;
		}
		data.get(x).get(y).put(z, new ResourceLocation[] { base });
		markDirty();
	}

	public void setChunkGenerated(int chunkX, int chunkZ) {
		if (!generated.containsKey(chunkX)) {
			generated.put(chunkX, new ArrayList());
		}
		if (!generated.get(chunkX).contains(chunkZ)) {
			generated.get(chunkX).add(chunkZ);
		}
	}

	public void setData(int x, int y, int z, ResourceLocation base, ResourceLocation[] ores) {
		if (!data.containsKey(x)) {
			data.put(x, new HashMap());
		}
		if (!data.get(x).containsKey(y)) {
			data.get(x).put(y, new HashMap());
		}
		ResourceLocation[] newData = new ResourceLocation[ores.length + 1];
		newData[0] = base;
		for (int i = 0; i < ores.length; i++) {
			newData[i + 1] = ores[i];
		}
		data.get(x).get(y).put(z, newData);

		markDirty();
	}

	public void setData(int x, int y, int z, ResourceLocation[] dataList) {
		if (!data.containsKey(x)) {
			data.put(x, new HashMap());
		}
		if (!data.get(x).containsKey(y)) {
			data.get(x).put(y, new HashMap());
		}
		ResourceLocation[] newData = new ResourceLocation[dataList.length];
		for (int i = 0; i < dataList.length; i++) {
			newData[i] = dataList[i];
		}
		data.get(x).get(y).put(z, newData);
		markDirty();
	}

	public void setOres(BlockPos pos, ResourceLocation[] ores) {
		setOres(pos.getX(), pos.getY(), pos.getZ(), ores);
	}

	public void setOres(int x, int y, int z, ResourceLocation[] ores) {
		if (!data.containsKey(x)) {
			data.put(x, new HashMap());
		}
		if (!data.get(x).containsKey(y)) {
			data.get(x).put(y, new HashMap());
		}
		ResourceLocation[] newData = new ResourceLocation[ores.length + 1];

		if (data.get(x).get(y).containsKey(z)) {
			newData[0] = getBase(x, y, z);
		} else {
			newData[0] = new ResourceLocation("minecraft:stone");
		}
		for (int i = 0; i < ores.length; i++) {
			newData[i + 1] = ores[i];
		}
		data.get(x).get(y).put(z, newData);
		markDirty();
	}
	// WorldSavedData methods

	/**
	 * This is where you save any data that you don't want to lose when the tile
	 * entity unloads In this case, we only need to store the gem colour. For
	 * examples with other types of data, see MBE20
	 *
	 * @param parentNBTTagCompound
	 * @return
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound parentNBTTagCompound) {
		data.forEach((Integer x, HashMap<Integer, HashMap<Integer, ResourceLocation[]>> xData) -> {
			NBTTagCompound xTag = new NBTTagCompound();
			xData.forEach((Integer y, HashMap<Integer, ResourceLocation[]> yData) -> {
				NBTTagCompound yTag = new NBTTagCompound();
				yData.forEach((Integer z, ResourceLocation[] ores) -> {
					ResourceLocation[] oreArray = new ResourceLocation[ores.length];

					NBTTagList list = new NBTTagList();
					for (int i1 = 0; i1 < ores.length; i1++) {
						list.appendTag(new NBTTagString(ores[i1].toString()));
					}
					yTag.setTag(z.toString(), list);
				});
				xTag.setTag(y.toString(), yTag);
			});
			parentNBTTagCompound.setTag(x.toString(), xTag);
		});
		return parentNBTTagCompound;
	}
}
