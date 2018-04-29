package com.sixteencolorgames.supertechtweaks.compat.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sixteencolorgames.supertechtweaks.recipe.ResearchCraftingFactory.ResearchCraftingRecipe;

import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class ResearchCraftingWrapper implements IRecipeWrapper, ITooltipCallback<ItemStack> {
	private ResearchCraftingRecipe recipe;

	public ResearchCraftingWrapper(ResearchCraftingRecipe recipe) {
		this.recipe = recipe; // the recipe
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setOutput(ItemStack.class, recipe.getRecipeOutput());
		List<List<ItemStack>> l = new ArrayList<>();
		for (Ingredient i : recipe.getIngredients()) {
			l.add(Arrays.asList(i.getMatchingStacks()));
		}

		ingredients.setInputLists(ItemStack.class, l);
	}

	@Override
	public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<String> tooltip) {
		// TODO Auto-generated method stub
		tooltip.add("slot: " + slotIndex + " input:" + input);
	}

}
