package com.sixteencolorgames.supertechtweaks.items;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;

import net.minecraft.item.Item;

public class ItemBase extends Item {

	protected String name;

	public ItemBase(String name) {
		this.name = name;
		setUnlocalizedName(name);
		setRegistryName(name);
	}

	public void registerItemModel() {
		SuperTechTweaksMod.proxy.registerItemRenderer(this, 0, name);
	}

}