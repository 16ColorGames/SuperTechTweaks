package com.sixteencolorgames.supertechtweaks.recipe;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class ToolDictIngredientFactory implements IIngredientFactory {
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
			if (input == null) {
				return false;
			}

			if (type.isInstance(input.getItem())) {
				return true;
			}
			return false;
		}

	}

	@Override
	public Ingredient parse(final JsonContext context, final JsonObject json) {

		String type = JsonUtils.getString(json, "tool");

		return new ToolDictIngredient(type);
	}

}