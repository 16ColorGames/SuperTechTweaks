package com.sixteencolorgames.supertechtweaks.compat.jei;

import javax.annotation.Nonnull;

import com.sixteencolorgames.supertechtweaks.recipe.ResearchCraftingFactory.ResearchCraftingRecipe;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class SttJeiPlugin implements IModPlugin {
	public static IJeiHelpers jeiHelpers;

	@Override
	public void register(@Nonnull IModRegistry registry) {
		jeiHelpers = registry.getJeiHelpers();
		registry.handleRecipes(ResearchCraftingRecipe.class, ResearchCraftingWrapper::new,
				VanillaRecipeCategoryUid.CRAFTING);

		jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(Items.DIAMOND_PICKAXE));
		jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(Items.GOLDEN_PICKAXE));
		jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(Items.IRON_PICKAXE));
		jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(Items.STONE_PICKAXE));
		jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(Items.WOODEN_PICKAXE));
	}
}
