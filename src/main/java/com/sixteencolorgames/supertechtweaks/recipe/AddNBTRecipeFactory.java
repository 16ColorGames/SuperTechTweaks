package com.sixteencolorgames.supertechtweaks.recipe;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.common.util.Constants;

public class AddNBTRecipeFactory implements IRecipeFactory {

	public static class AddNBTRecipe extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe>
			implements IRecipe {
		public final NonNullList<Ingredient> input;
		public final int height, width;
		public final String nbtTag;
		public final String tooltip;

		public AddNBTRecipe(ResourceLocation group, ShapedPrimer primer, String nbt, String tip) {
			input = primer.input;
			height = primer.height;
			width = primer.width;
			nbtTag = nbt;
			tooltip = tip;
		}

		@Override
		public boolean canFit(int width, int height) {
			return width >= this.width && height >= this.height;
		}

		/**
		 * Checks if the region of a crafting inventory is match for the recipe.
		 */
		private boolean checkMatch(InventoryCrafting p_77573_1_, int p_77573_2_, int p_77573_3_, boolean p_77573_4_) {
			for (int i = 0; i < p_77573_1_.getWidth(); ++i) {
				for (int j = 0; j < p_77573_1_.getHeight(); ++j) {
					int k = i - p_77573_2_;
					int l = j - p_77573_3_;
					Ingredient ingredient = Ingredient.EMPTY;

					if (k >= 0 && l >= 0 && k < width && l < height) {
						if (p_77573_4_) {
							ingredient = input.get(width - k - 1 + l * width);
						} else {
							ingredient = input.get(k + l * width);
						}
					}

					if (!ingredient.apply(p_77573_1_.getStackInRowAndColumn(i, j))) {
						return false;
					}
				}
			}

			return true;
		}

		@Override
		@Nonnull
		public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {

			ItemStack itemstack = var1.getStackInRowAndColumn(1, 1).copy();
			NBTTagCompound tag;
			if (itemstack.getTagCompound() != null) {
				tag = itemstack.getTagCompound();
			} else {
				tag = new NBTTagCompound();
			}

			tag.setBoolean(nbtTag, true);

			if (tooltip != null && tooltip != "") {
				NBTTagCompound display = tag.getCompoundTag("display");
				if (display != null) {
					NBTTagList lore = display.getTagList("Lore", Constants.NBT.TAG_STRING);
					if (lore != null) {
						String[] split = tooltip.split("\n");
						for (String s : split) {
							lore.appendTag(new NBTTagString(s));
						}
					} else {
						lore = new NBTTagList();
						String[] split = tooltip.split("\n");
						for (String s : split) {
							lore.appendTag(new NBTTagString(s));
						}
					}
					display.setTag("Lore", lore);
				} else {
					display = new NBTTagCompound();
					NBTTagList lore = new NBTTagList();
					String[] split = tooltip.split("\n");
					for (String s : split) {
						lore.appendTag(new NBTTagString(s));
					}
					display.setTag("Lore", lore);
				}
				tag.setTag("display", display);
			}

			ItemStack out = itemstack.copy();
			out.setCount(1);
			out.setTagCompound(tag);

			return out;
		}

		@Override
		public ItemStack getRecipeOutput() {
			return ItemStack.EMPTY;
		}

		@Override
		public boolean matches(InventoryCrafting inv, World worldIn) {
			for (int i = 0; i <= inv.getWidth() - width; ++i) {
				for (int j = 0; j <= inv.getHeight() - height; ++j) {
					if (checkMatch(inv, i, j, true)) {
						return true;
					}

					if (checkMatch(inv, i, j, false)) {
						return true;
					}
				}
			}

			return false;
		}

	}

	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {

		Map<Character, Ingredient> ingMap = Maps.newHashMap();
		for (Entry<String, JsonElement> entry : JsonUtils.getJsonObject(json, "key").entrySet()) {
			if (entry.getKey().length() != 1) {
				throw new JsonSyntaxException(
						"Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
			}
			if (" ".equals(entry.getKey())) {
				throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
			}

			ingMap.put(entry.getKey().toCharArray()[0], CraftingHelper.getIngredient(entry.getValue(), context));
		}

		ingMap.put(' ', Ingredient.EMPTY);
		JsonArray patternJ = JsonUtils.getJsonArray(json, "pattern");

		if (patternJ.size() == 0) {
			throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
		}

		String[] pattern = new String[patternJ.size()];
		for (int x = 0; x < pattern.length; ++x) {
			String line = JsonUtils.getString(patternJ.get(x), "pattern[" + x + "]");
			if (x > 0 && pattern[0].length() != line.length()) {
				throw new JsonSyntaxException("Invalid pattern: each row must  be the same width");
			}
			pattern[x] = line;
		}

		ShapedPrimer primer = new ShapedPrimer();
		primer.width = pattern[0].length();
		primer.height = pattern.length;

		primer.input = NonNullList.withSize(primer.width * primer.height, Ingredient.EMPTY);

		Set<Character> keys = Sets.newHashSet(ingMap.keySet());
		keys.remove(' ');

		int x = 0;
		for (String line : pattern) {
			for (char chr : line.toCharArray()) {
				Ingredient ing = ingMap.get(chr);
				if (ing == null) {
					throw new JsonSyntaxException(
							"Pattern references symbol '" + chr + "' but it's not defined in the key");
				}
				primer.input.set(x++, ing);
				keys.remove(chr);
			}
		}

		if (!keys.isEmpty()) {
			throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + keys);
		}

		return new AddNBTRecipe(new ResourceLocation(SuperTechTweaksMod.MODID, "addNBT_crafting"), primer,
				JsonUtils.getString(json, "nbt"), JsonUtils.getString(json, "tooltip"));
	}
}
