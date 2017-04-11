/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.enums.Ores;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.*;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.END;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.NETHER;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 *
 * @author oa10712
 */
public class Recipies {

    public static void addRecipies() {
        for (Ores ore : Ores.values()) {
            GameRegistry.addSmelting(new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal()), new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + INGOT), 1.0f);//Smelt ore to ingot
            GameRegistry.addSmelting(new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + DUST), new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + INGOT), 1.0f);//Smelt dust to ingot
            GameRegistry.addSmelting(new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal() + NETHER), new ItemStack(ModItems.itemOreChunk, 2, ore.ordinal()), 0);//Smelt nether to regular
            GameRegistry.addSmelting(new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal() + END), new ItemStack(ModItems.itemOreChunk, 3, ore.ordinal()), 0);//Smelt end to regular

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + GEAR),
                    " x ", "xyx", " x ", 'x', "ingot" + ore.getName(), 'y', "nugget" + ore.getName()));//Craft a gear
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + INGOT),
                    "xxx", "xxx", "xxx", 'x', "nugget" + ore.getName()));//Craft nuggets into an ingot
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemMaterialObject, 2, ore.ordinal() + ROD),
                    "x", "x", 'x', "ingot" + ore.getName()));//Craft nuggets into an ingot
            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemMaterialObject, 9, ore.ordinal() + NUGGET), new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + INGOT));//Craft ingots into nuggets
        }
    }

}
