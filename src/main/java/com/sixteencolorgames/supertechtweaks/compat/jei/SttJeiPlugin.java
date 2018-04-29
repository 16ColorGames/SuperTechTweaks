package com.sixteencolorgames.supertechtweaks.compat.jei;

import javax.annotation.Nonnull;

import com.sixteencolorgames.supertechtweaks.recipe.ResearchCraftingFactory.ResearchCraftingRecipe;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

@JEIPlugin
public class SttJeiPlugin implements IModPlugin {
	public static IJeiHelpers jeiHelpers;

	@Override
	public void register(@Nonnull IModRegistry registry) {
		jeiHelpers = registry.getJeiHelpers();
		registry.handleRecipes(ResearchCraftingRecipe.class, ResearchCraftingWrapper::new,
				VanillaRecipeCategoryUid.CRAFTING);

	}
}
