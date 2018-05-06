package com.sixteencolorgames.supertechtweaks.recipe;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sixteencolorgames.supertechtweaks.Pair;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.items.MaterialItem;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreIngredient;

public class CrusherRecipes {
	private static final CrusherRecipes CRUSHER_BASE = new CrusherRecipes();

	/**
	 * Returns an instance of FurnaceRecipes.
	 */
	public static CrusherRecipes instance() {
		return CRUSHER_BASE;
	}

	/** The list of smelting results. */
	private final Map<Ingredient, Pair<ItemStack, ArrayList<ResourceLocation>>> crushingList = Maps
			.<Ingredient, Pair<ItemStack, ArrayList<ResourceLocation>>> newHashMap();

	private CrusherRecipes() {
		addCrusherRecipeForIngredient(new OreIngredient("oreIron"),
				new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:iron")).getMaterialItem(), 2,
						MaterialItem.DUST),
				new ResourceLocation("supertechtweaks:advancediron1"));
	}

	private void addCrusherRecipeForIngredient(Ingredient ingredient, ItemStack itemStack,
			ResourceLocation... requiredResearch) {
		// TODO check for overwritten recipes
		// if (getCrushingResult(ingredient) != ItemStack.EMPTY) {
		// net.minecraftforge.fml.common.FMLLog.log.info("Ignored smelting
		// recipe with conflicting input: {} = {}",
		// ingredient, itemStack);
		// return;
		// }
		crushingList.put(ingredient, new Pair(itemStack, Lists.newArrayList(requiredResearch)));

	}

	/**
	 * Compares two itemstacks to ensure that they are the same. This checks
	 * both the item and the metadata of the item.
	 */
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem()
				&& (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	/**
	 * Returns the smelting result of an item.
	 */
	public Pair<ItemStack, ArrayList<ResourceLocation>> getCrushingResult(ItemStack stack) {
		for (Entry<Ingredient, Pair<ItemStack, ArrayList<ResourceLocation>>> entry : crushingList.entrySet()) {
			if (entry.getKey().apply(stack)) {
				return new Pair(entry.getValue().getLeft().copy(), entry.getValue().getRight().clone());
			}
		}

		return new Pair(ItemStack.EMPTY, new ArrayList());
	}

}