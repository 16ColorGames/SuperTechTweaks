package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.items.ItemBase;
import com.sixteencolorgames.supertechtweaks.items.ItemOreChunk;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
	public static ItemOreChunk itemOreChunk;

	private static <T extends Item> T register(T item) {
		GameRegistry.register(item);

		if (item instanceof ItemBase) {
			((ItemBase) item).registerItemModel();
		}

		return item;
	}

	public static void init() {
		itemOreChunk = (ItemOreChunk) (new ItemOreChunk().setUnlocalizedName("itemOreChunk"));
		itemOreChunk.setRegistryName("itemOreChunk");
		GameRegistry.register(itemOreChunk);		
	}

}