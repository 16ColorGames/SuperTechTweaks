/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.compat.crafttweaker;

import java.util.ArrayList;
import java.util.List;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author oa10712
 */
@ZenClass("mods.supertech.tweaks")
public class CraftTweaker {

    public static final List<IMaterialListener> listeners = new ArrayList();
    public static final CraftTweaker INSTANCE = new CraftTweaker();

    public void register() {
        MineTweakerAPI.registerClass(this.getClass());
        System.out.println("Registered ZenScript for supertech");
    }

    @ZenMethod
    public static void addMaterial(String name, String color, int harvest) {
        MineTweakerAPI.apply(new AddMaterial(name, color, harvest));
        System.out.println("Adding new material");
    }

    /**
     * Converts a list of minetweaker item stacks into an array of minecraft
     * item stacks.
     *
     * @param items minetweaker items
     * @return minecraft items
     */
    public static ItemStack[] getItemStacks(List<IItemStack> items) {
        if (items == null) {
            return null;
        }

        ItemStack[] output = new ItemStack[items.size()];
        for (int i = 0; i < items.size(); i++) {
            Object internal = items.get(i).getInternal();
            if (internal != null && internal instanceof ItemStack) {
                output[i] = (ItemStack) internal;
            } else {
                MineTweakerAPI.logError("Invalid item stack: " + items.get(i));
            }
        }
        return output;
    }
}
