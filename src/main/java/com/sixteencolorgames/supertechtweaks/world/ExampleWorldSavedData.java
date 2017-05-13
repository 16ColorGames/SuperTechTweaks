/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.world;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import java.util.HashMap;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

/**
 *
 * @author oa10712
 */
public class ExampleWorldSavedData extends WorldSavedData {

    private static final String DATA_NAME = SuperTechTweaksMod.MODID + "_OreData";
    /**
     * <X,<Y,<Z,Data>>> where Data[0] is the base type of the ore and the
     * remaining elements are the ore data
     */
    HashMap<Integer, HashMap<Integer, HashMap<Integer, Integer[]>>> data = new HashMap();

// Required constructors
    public ExampleWorldSavedData() {
        super(DATA_NAME);
    }

    public ExampleWorldSavedData(String s) {
        super(s);
    }

    public static ExampleWorldSavedData get(World world) {
        // The IS_GLOBAL constant is there for clarity, and should be simplified into the right branch.
        MapStorage storage = false ? world.getMapStorage() : world.getPerWorldStorage();
        ExampleWorldSavedData instance = (ExampleWorldSavedData) storage.getOrLoadData(ExampleWorldSavedData.class, DATA_NAME);

        if (instance == null) {
            instance = new ExampleWorldSavedData();
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }

    public void setData(int x, int y, int z, int base, int[] ores) {
        if (!data.containsKey(x)) {
            data.put(x, new HashMap());
        }
        if (!data.get(x).containsKey(y)) {
            data.get(x).put(y, new HashMap());
        }
        Integer[] newData = new Integer[ores.length + 1];
        newData[0] = base;
        for (int i = 0; i < ores.length; i++) {
            newData[i + 1] = ores[i];
        }
        data.get(x).get(y).put(z, newData);
    }

    public void setData(int x, int y, int z, int[] dataList) {
        if (!data.containsKey(x)) {
            data.put(x, new HashMap());
        }
        if (!data.get(x).containsKey(y)) {
            data.get(x).put(y, new HashMap());
        }
        Integer[] newData = new Integer[dataList.length];
        for (int i = 0; i < dataList.length; i++) {
            newData[i] = dataList[i];
        }
        data.get(x).get(y).put(z, newData);
    }

    public int getBase(int x, int y, int z) {
        try {
            return data.get(x).get(y).get(z)[0];
        } catch (Exception ex) {
            return Integer.MIN_VALUE;
        }
    }

    public int getBase(BlockPos pos) {
        return getBase(pos.getX(), pos.getY(), pos.getZ());
    }

    public void setBase(int x, int y, int z, int base) {
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
        data.get(x).get(y).put(z, new Integer[]{base});
    }

    public int[] getOres(int x, int y, int z) {
        try {
            Integer[] get = data.get(x).get(y).get(z);
            int[] ret = new int[get.length - 1];
            for (int i = 1; i < get.length; i++) {
                ret[i - 1] = get[i];
            }
            return ret;
        } catch (Exception ex) {
            return new int[0];
        }
    }

    public int[] getOres(BlockPos pos) {
        return getOres(pos.getX(), pos.getY(), pos.getZ());
    }

    public void setOres(BlockPos pos, int[] ores) {
        setOres(pos.getX(), pos.getY(), pos.getZ(), ores);
    }

    public void setOres(int x, int y, int z, int[] ores) {
        if (!data.containsKey(x)) {
            data.put(x, new HashMap());
        }
        if (!data.get(x).containsKey(y)) {
            data.get(x).put(y, new HashMap());
        }
        Integer[] newData = new Integer[ores.length + 1];;
        if (data.get(x).get(y).containsKey(z)) {
            newData[0] = getBase(x, y, z);
        } else {
            newData[0] = 0;
        }
        for (int i = 0; i < ores.length; i++) {
            newData[i + 1] = ores[i];
        }
        data.get(x).get(y).put(z, newData);
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
        data.forEach((Integer x, HashMap<Integer, HashMap<Integer, Integer[]>> xData) -> {
            NBTTagCompound xTag = new NBTTagCompound();
            xData.forEach((Integer y, HashMap<Integer, Integer[]> yData) -> {
                NBTTagCompound yTag = new NBTTagCompound();
                yData.forEach((Integer z, Integer[] ores) -> {
                    int[] oreArray = new int[ores.length];
                    for (int i = 0; i < ores.length; i++) {
                        oreArray[i] = ores[i];
                    }
                    yTag.setIntArray(z.toString(), oreArray);
                });
                xTag.setTag(y.toString(), yTag);
            });
            parentNBTTagCompound.setTag(x.toString(), xTag);
        });
        return parentNBTTagCompound;
    }

    // This is where you load the data that you saved in writeToNBT
    @Override
    public void readFromNBT(NBTTagCompound parentNBTTagCompound) {
        parentNBTTagCompound.getKeySet().forEach((x) -> {
            NBTTagCompound xTag = parentNBTTagCompound.getCompoundTag(x);
            xTag.getKeySet().forEach((y) -> {
                NBTTagCompound yTag = xTag.getCompoundTag(y);
                yTag.getKeySet().forEach((z) -> {
                    int[] dataList = yTag.getIntArray(z);
                    setData(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z), dataList);
                });
            });
        });
    }

}
