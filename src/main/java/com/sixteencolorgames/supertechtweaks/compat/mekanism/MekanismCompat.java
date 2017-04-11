/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.compat.mekanism;

import com.sixteencolorgames.supertechtweaks.ModItems;
import com.sixteencolorgames.supertechtweaks.enums.Ores;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author oa10712
 */
public class MekanismCompat {

    public static void registerRecipes() {
        for (Ores ore : Ores.values()) {
            try {
                System.out.println("Attempting to register enrichment: "+ore.getName());
                NBTTagCompound recipeTag = new NBTTagCompound();
                recipeTag.setTag("itemInput", new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal()).writeToNBT(new NBTTagCompound()));

                recipeTag.setTag("itemOutput", OreDictionary.getOres("dust" + ore.getName()).get(0).writeToNBT(new NBTTagCompound()));
                FMLInterModComms.sendMessage("Mekanism", "EnrichmentChamberRecipe", recipeTag);
            } catch (Exception ex) {
                System.out.println("Register failed. No dust?");
            }
        }
    }
}
