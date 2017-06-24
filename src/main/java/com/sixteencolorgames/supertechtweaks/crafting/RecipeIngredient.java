/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.crafting;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author oa10712
 */
public class RecipeIngredient {

    List<ItemStack> items = new ArrayList();
    String ore;
    int quantity = 1;

    public RecipeIngredient(ItemStack item) {
        items.add(item);
    }

    public RecipeIngredient(ItemStack item, int amount) {
        item.stackSize = amount;
        items.add(item);
    }

    public RecipeIngredient(String ore) {
        this(OreDictionary.getOres(ore));
    }

    public RecipeIngredient(String ore, int amount) {
        this(OreDictionary.getOres(ore), amount);
        quantity = amount;
    }

    public RecipeIngredient(List<ItemStack> list) {
        list.forEach((stack) -> {
            items.add(stack);
        });
    }

    public RecipeIngredient(List<ItemStack> list, int amount) {
        list.forEach((stack) -> {
            stack.stackSize = amount;
            items.add(stack);
        });
        quantity = amount;
    }

    public boolean matches(Object o) {
        if (o instanceof ItemStack) {
            ItemStack stack = (ItemStack) o;
            for (ItemStack saved : items) {
                if (saved.getItem() == stack.getItem() && saved.getMetadata() == stack.getMetadata() && stack.stackSize >= quantity) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<ItemStack> getExamples() {
        return items;
    }
}
