/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.compat.mekanism;

import static com.sixteencolorgames.supertechtweaks.ModItems.itemMaterialObject;
import static com.sixteencolorgames.supertechtweaks.ModItems.itemOreChunk;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.*;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.api.recipe.RecipeHelper;
import net.minecraft.item.ItemStack;

/**
 *
 * @author oa10712
 */
public class MekanismCompatability {

    public static MekanismCompatability INSTANCE = new MekanismCompatability();

    public void register() {
        Material.materials.forEach((mat) -> {
            Gas slurry = GasRegistry.register(new Gas(mat.getName()+"Slurry", ""));
            Gas clean = GasRegistry.register(new Gas(mat.getName()+"CleanSlurry", ""));
            
            RecipeHelper.addEnrichmentChamberRecipe(new ItemStack(itemOreChunk, 1, mat.ordinal()), new ItemStack(itemMaterialObject, 2, mat.ordinal() + DUST));
            RecipeHelper.addEnrichmentChamberRecipe(new ItemStack(itemMaterialObject, 1, mat.ordinal() + DIRTY), new ItemStack(itemMaterialObject, 1, mat.ordinal() + DUST));

            RecipeHelper.addCrusherRecipe(new ItemStack(itemMaterialObject, 1, mat.ordinal() + CLUMP), new ItemStack(itemMaterialObject, 1, mat.ordinal() + DIRTY));

            RecipeHelper.addPurificationChamberRecipe(new ItemStack(itemOreChunk, 1, mat.ordinal()), new ItemStack(itemMaterialObject, 3, mat.ordinal() + CLUMP));
            RecipeHelper.addPurificationChamberRecipe(new ItemStack(itemMaterialObject, 1, mat.ordinal() + SHARD), new ItemStack(itemMaterialObject, 1, mat.ordinal() + CLUMP));

            RecipeHelper.addChemicalInjectionChamberRecipe(new ItemStack(itemOreChunk, 1, mat.ordinal()), "hydrogenchloride", new ItemStack(itemMaterialObject, 4, mat.ordinal() + SHARD));
            RecipeHelper.addChemicalInjectionChamberRecipe(new ItemStack(itemMaterialObject, 1, mat.ordinal() + CRYSTAL), "hydrogenchloride", new ItemStack(itemMaterialObject, 1, mat.ordinal() + SHARD));

            RecipeHelper.addChemicalDissolutionChamberRecipe(new ItemStack(itemOreChunk, 1, mat.ordinal()), new GasStack(slurry, 1000));
            
            RecipeHelper.addChemicalCrystallizerRecipe(new GasStack(clean, 200), new ItemStack(itemMaterialObject, 1, mat.ordinal() + CRYSTAL));
            
            RecipeHelper.addChemicalWasherRecipe(new GasStack(slurry, 200), new GasStack(clean, 200));
        });
    }
}
