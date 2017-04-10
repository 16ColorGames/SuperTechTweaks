/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.enums.Ores;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.END;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.NETHER;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author oa10712
 */
public class Recipies {

    public static void addRecipies() {
        for (Ores ore : Ores.values()) {
            String oreName = "ingot" + ore.getName();
            if (OreDictionary.doesOreNameExist(oreName) && !OreDictionary.getOres(oreName).isEmpty()) {
                GameRegistry.addSmelting(new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal()),
                        OreDictionary.getOres(oreName).get(0), 1.0f);
            }
            GameRegistry.addSmelting(new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal() + NETHER), new ItemStack(ModItems.itemOreChunk, 2, ore.ordinal()), 0);
            GameRegistry.addSmelting(new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal() + END), new ItemStack(ModItems.itemOreChunk, 3, ore.ordinal()), 0);
        }
    }

}
