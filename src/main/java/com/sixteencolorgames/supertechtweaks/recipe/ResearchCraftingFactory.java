package com.sixteencolorgames.supertechtweaks.recipe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.sixteencolorgames.supertechtweaks.world.ResearchSavedData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ResearchCraftingFactory implements IRecipeFactory {
	public static class ResearchCraftingRecipe extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe>
			implements IRecipe {
		// XXX SRG names for non-dev environment
		/*
		 * private static final Field eventHandlerField =
		 * ReflectionHelper.findField(InventoryCrafting.class, "field_70465_c");
		 * private static final Field containerPlayerPlayerField =
		 * ReflectionHelper.findField(ContainerPlayer.class, "field_82862_h");
		 * private static final Field slotCraftingPlayerField =
		 * ReflectionHelper.findField(SlotCrafting.class, "field_75238_b");
		 */
		private static final Field eventHandlerField = ReflectionHelper.findField(InventoryCrafting.class,
				"eventHandler");
		private static final Field containerPlayerPlayerField = ReflectionHelper.findField(ContainerPlayer.class,
				"player");
		private static final Field slotCraftingPlayerField = ReflectionHelper.findField(SlotCrafting.class, "player");

		private static EntityPlayer findPlayer(InventoryCrafting inv) {

			try {
				Container container = (Container) eventHandlerField.get(inv);
				if (container instanceof ContainerPlayer) {
					return (EntityPlayer) containerPlayerPlayerField.get(container);
				} else if (container instanceof ContainerWorkbench) {
					return (EntityPlayer) slotCraftingPlayerField.get(container.getSlot(0));
				} else {
					// don't know the player
					return null;
				}
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		}

		public final NonNullList<Ingredient> input;

		public final int height, width;
		protected ItemStack output = ItemStack.EMPTY;

		public final NonNullList<ResourceLocation> research;

		public ResearchCraftingRecipe(ResourceLocation group, @Nonnull ItemStack result, ShapedPrimer primer,
				ResourceLocation... required) {
			input = primer.input;
			height = primer.height;
			width = primer.width;
			research = NonNullList.from(new ResourceLocation("none:none"), required);
			output = result.copy();
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

		/**
		 * Returns an Item that is the result of this recipe
		 */
		@Override
		@Nonnull
		public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
			return output.copy();
		}

		@Override
		@Nonnull
		public ItemStack getRecipeOutput() {
			return output;
		}

		@Override
		public boolean matches(InventoryCrafting inv, World worldIn) {
			for (ResourceLocation req : research) {
				EntityPlayer player = findPlayer(inv);
				if (player == null || player.world == null
						|| !ResearchSavedData.get(player.world).getPlayerHasResearch(player, req)) {
					return false;
				}

			}
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
		String group = JsonUtils.getString(json, "group", "");
		// if (!group.isEmpty() && group.indexOf(':') == -1)
		// group = context.getModId() + ":" + group;

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
		primer.mirrored = JsonUtils.getBoolean(json, "mirrored", true);
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

		ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
		ArrayList<ResourceLocation> reqs = new ArrayList();
		JsonArray jsonArray = JsonUtils.getJsonArray(json, "requiredResearch");
		for (int i = 0; i < jsonArray.size(); i++) {
			reqs.add(new ResourceLocation(jsonArray.get(i).getAsString()));
		}
		return new ResearchCraftingRecipe(group.isEmpty() ? null : new ResourceLocation(group), result, primer,
				reqs.toArray(new ResourceLocation[] {}));
	}
}
