package com.sixteencolorgames.supertechtweaks;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class RockManager {

	public static final LinkedHashSet<IBlockState> allStones = new LinkedHashSet<IBlockState>();

	public static final HashMap<String, LinkedHashSet<IBlockState>> stoneSpawns = new HashMap();

	public static final HashMap<IBlockState, ResourceLocation> textureOverrides = new HashMap();

	public static void addRockTypes(IBlockState state, String... types) {
		allStones.add(state);
		for (String s : types) {
			if (stoneSpawns.containsKey(s)) {
				stoneSpawns.get(s).add(state);
			} else {
				LinkedHashSet<IBlockState> newType = new LinkedHashSet();
				newType.add(state);
				stoneSpawns.put(s, newType);
			}
		}
	}

	public static void addTextureOverride(IBlockState state, ResourceLocation rl) {
		textureOverrides.put(state, rl);
	}

	public static ResourceLocation getTexture(IBlockState state) {
		if (textureOverrides.containsKey(state)) {
			return textureOverrides.get(state);
		} else {
			ResourceLocation rl = new ResourceLocation(state.getBlock().getRegistryName().getResourceDomain(),
					"blocks/" + state.getBlock().getRegistryName().getResourcePath());
			return rl;
		}
	}

	public static LinkedHashSet<IBlockState> getStones(String s) {
		LinkedHashSet<IBlockState> ret = new LinkedHashSet();
		if (stoneSpawns.containsKey(s)) {
			ret.addAll(stoneSpawns.get(s));
		}
		// TODO Auto-generated method stub
		return ret;
	}
}
