package com.sixteencolorgames.supertechtweaks.recipe;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.oredict.OreDictionary;

public class ToolDictIngredientFactory implements IIngredientFactory {
	@Override
	public Ingredient parse(final JsonContext context, final JsonObject json) {

		String type = JsonUtils.getString(json, "tool");

		return new ToolDictIngredient(type);
	}

	public class ToolDictIngredient extends Ingredient {

		private NonNullList<ItemStack> types;

		private Class type;

		public ToolDictIngredient(String type) {
			switch (type) {
			case "pickaxe":
				this.type = ItemPickaxe.class;
				break;
			case "axe":
				this.type = ItemAxe.class;
				break;
			case "shovel":
				this.type = ItemSpade.class;
				break;
			case "shears":
				this.type = ItemShears.class;
				break;
			case "sword":
				this.type = ItemSword.class;
				break;
			case "hoe":
				this.type = ItemHoe.class;
				break;
			case "bow":
				this.type = ItemBow.class;
				break;
			case "tool":
				this.type = ItemTool.class;
				break;
			case "any":
				this.type = Item.class;
				break;
			}
		}

		@Override
		public boolean apply(@Nullable ItemStack input) {
			if (input == null)
				return false;

			if (this.type.isInstance(input.getItem())) {
				return true;
			}
			return false;
		}

	}

}