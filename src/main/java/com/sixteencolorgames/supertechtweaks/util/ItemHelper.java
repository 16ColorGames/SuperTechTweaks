package com.sixteencolorgames.supertechtweaks.util;

import com.sixteencolorgames.supertechtweaks.enums.Material;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ItemHelper {

	public static Material getItemMaterial(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("sttMaterial")) {
			return Material.REGISTRY.getValue(new ResourceLocation(stack.getTagCompound().getString("sttMaterial")));
		}
		return Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:iron"));
	}

	public static void setItemMaterial(ItemStack stack, Material material) {
		NBTTagCompound tag;
		if (stack.hasTagCompound()) {
			tag = stack.getTagCompound();
		} else {
			tag = new NBTTagCompound();
		}
		tag.setString("sttMaterial", material.getRegistryName().toString());
		stack.setTagCompound(tag);
	}

	public static void setItemMaterial(ItemStack stack, String material) {
		NBTTagCompound tag;
		if (stack.hasTagCompound()) {
			tag = stack.getTagCompound();
		} else {
			tag = new NBTTagCompound();
		}
		tag.setString("sttMaterial", material);
		stack.setTagCompound(tag);
	}

}
