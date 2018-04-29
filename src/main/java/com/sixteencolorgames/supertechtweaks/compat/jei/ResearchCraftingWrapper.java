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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;

public class ResearchCraftingWrapper implements IRecipeWrapper {
	private ResearchCraftingRecipe recipe;

	public ResearchCraftingWrapper(ResearchCraftingRecipe recipe) {
		this.recipe = recipe; // the recipe
	}
//TODO the lore seems to be added multiple times. Fix that
	@Override
	public void getIngredients(IIngredients ingredients) {
		ItemStack out = recipe.getRecipeOutput();
		NBTTagCompound tag;
		if (out.getTagCompound() != null) {
			tag = out.getTagCompound();
		} else {
			tag = new NBTTagCompound();
		}
		NBTTagCompound display = tag.getCompoundTag("display");
		NBTTagList lore = display.getTagList("Lore", Constants.NBT.TAG_STRING);
		if (lore != null) {
			String[] split = getTooltip().split("\n");
			for (String s : split) {
				lore.appendTag(new NBTTagString(s));
			}
		} else {
			lore = new NBTTagList();
			String[] split = getTooltip().split("\n");
			for (String s : split) {
				lore.appendTag(new NBTTagString(s));
			}
		}
		display.setTag("Lore", lore);
		tag.setTag("display", display);
		out.setTagCompound(tag);
		ingredients.setOutput(ItemStack.class, out);
		List<List<ItemStack>> l = new ArrayList<>();
		for (Ingredient i : recipe.getIngredients()) {
			l.add(Arrays.asList(i.getMatchingStacks()));
		}

		ingredients.setInputLists(ItemStack.class, l);
	}

	private String getTooltip() {
		StringBuilder b = new StringBuilder();
		b.append("Required Research:\n");
		recipe.research.forEach((rl) -> {
			b.append(rl.toString() + "\n");
		});
		return b.toString();
	}

}
