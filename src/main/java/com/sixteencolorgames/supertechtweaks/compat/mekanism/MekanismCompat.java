/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.compat.mekanism;

import com.sixteencolorgames.supertechtweaks.ModItems;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 *
 * @author oa10712
 */
public class MekanismCompat {

    public static void registerRecipes() {
        for (Material ore : Material.materials) {
            System.out.println("Attempting to register enrichment: " + ore.getName());
            NBTTagCompound recipeTag = new NBTTagCompound();
            recipeTag.setTag("itemInput", new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal()).writeToNBT(new NBTTagCompound()));
            recipeTag.setTag("itemOutput", new ItemStack(ModItems.itemMaterialObject, 2, ore.ordinal() + DUST).writeToNBT(new NBTTagCompound()));
            FMLInterModComms.sendMessage("Mekanism", "EnrichmentChamberRecipe", recipeTag);
            recipeTag = new NBTTagCompound();
            recipeTag.setTag("itemInput", new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + DIRTY).writeToNBT(new NBTTagCompound()));
            recipeTag.setTag("itemOutput", new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + DUST).writeToNBT(new NBTTagCompound()));
            FMLInterModComms.sendMessage("Mekanism", "EnrichmentChamberRecipe", recipeTag);

            recipeTag = new NBTTagCompound();
            recipeTag.setTag("itemInput", new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + CLUMP).writeToNBT(new NBTTagCompound()));
            recipeTag.setTag("itemOutput", new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + DIRTY).writeToNBT(new NBTTagCompound()));
            FMLInterModComms.sendMessage("Mekanism", "CrusherRecipe", recipeTag);
            recipeTag = new NBTTagCompound();
            recipeTag.setTag("itemInput", new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + INGOT).writeToNBT(new NBTTagCompound()));
            recipeTag.setTag("itemOutput", new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + DUST).writeToNBT(new NBTTagCompound()));
            FMLInterModComms.sendMessage("Mekanism", "CrusherRecipe", recipeTag);

            recipeTag = new NBTTagCompound();
            recipeTag.setTag("itemInput", new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + SHARD).writeToNBT(new NBTTagCompound()));
            recipeTag.setTag("itemOutput", new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + CLUMP).writeToNBT(new NBTTagCompound()));
            FMLInterModComms.sendMessage("Mekanism", "PurificationChamberRecipe", recipeTag);
            recipeTag = new NBTTagCompound();
            recipeTag.setTag("itemInput", new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal()).writeToNBT(new NBTTagCompound()));
            recipeTag.setTag("itemOutput", new ItemStack(ModItems.itemMaterialObject, 3, ore.ordinal() + CLUMP).writeToNBT(new NBTTagCompound()));
            FMLInterModComms.sendMessage("Mekanism", "PurificationChamberRecipe", recipeTag);

            recipeTag = new NBTTagCompound();
            recipeTag.setTag("itemInput", new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + CRYSTAL).writeToNBT(new NBTTagCompound()));
            recipeTag.setTag("itemOutput", new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + SHARD).writeToNBT(new NBTTagCompound()));
            FMLInterModComms.sendMessage("Mekanism", "ChemicalInjectionChamberRecipe", recipeTag);
            recipeTag = new NBTTagCompound();
            recipeTag.setTag("itemInput", new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal()).writeToNBT(new NBTTagCompound()));
            recipeTag.setTag("itemOutput", new ItemStack(ModItems.itemMaterialObject, 4, ore.ordinal() + SHARD).writeToNBT(new NBTTagCompound()));
            FMLInterModComms.sendMessage("Mekanism", "ChemicalInjectionChamberRecipe", recipeTag);
        }
    }
}
