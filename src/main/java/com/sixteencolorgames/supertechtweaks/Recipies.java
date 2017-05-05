/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.compat.ticon.TiConCompatability;
import com.sixteencolorgames.supertechtweaks.enums.Alloy;
import com.sixteencolorgames.supertechtweaks.enums.AlloyElement;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.*;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.END;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.NETHER;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 *
 * @author oa10712
 */
public class Recipies {

    public static void addRecipies() {
        for (Material ore : Material.materials) {//Iterate through each material and create recipies for each
            GameRegistry.addSmelting(new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal()), new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + INGOT), 1.0f);//Smelt ore to ingot
            GameRegistry.addSmelting(new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + DUST), new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + INGOT), 1.0f);//Smelt dust to ingot
            GameRegistry.addSmelting(new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal() + NETHER), new ItemStack(ModItems.itemOreChunk, 2, ore.ordinal()), 0);//Smelt nether to regular
            GameRegistry.addSmelting(new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal() + END), new ItemStack(ModItems.itemOreChunk, 3, ore.ordinal()), 0);//Smelt end to regular

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + GEAR),
                    " x ", "xyx", " x ", 'x', "ingot" + ore.getName(), 'y', "nugget" + ore.getName()));//Craft a gear
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + INGOT),
                    "xxx", "xxx", "xxx", 'x', "nugget" + ore.getName()));//Craft nuggets into an ingot
//            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockMaterial.get(ore)),
//                    "xxx", "xxx", "xxx", 'x', "ingot" + ore.getName()));//Craft ingots into a block
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemMaterialObject, 4, ore.ordinal() + ROD),
                    "x", "x", 'x', "ingot" + ore.getName()));//Craft ingots into a rod

            GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemMaterialObject, 9, ore.ordinal() + NUGGET), new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + INGOT));//Craft ingots into nuggets
            // GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemMaterialObject, 9, ore.ordinal() + INGOT), new ItemStack(ModBlocks.blockMaterial.get(ore)));//Craft blocks into ingots
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemMaterialObject, 2, ore.ordinal() + WIRE), new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + PLATE), "craftingToolWireCutter")); //Craft plates into wires
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + PLATE), new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + INGOT), "craftingToolForgeHammer")); //Craft ingot into plate

        }
        //addAlloys();
        registerAlloys();

        TiConCompatability.registerMelting();
    }

    /*private static void addAlloys() {
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.ELECTRUM, 2), new AlloyElement(Ores.SILVER, 1), new AlloyElement(Ores.GOLD, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.ALUMINUMBRASS, 4), new AlloyElement(Material.getMaterial("Copper"), 1), new AlloyElement(Ores.ALUMINUM, 3)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.BRASS, 3), new AlloyElement(Material.getMaterial("Copper"), 2), new AlloyElement(Ores.ZINC, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.BRONZE, 4), new AlloyElement(Material.getMaterial("Copper"), 3), new AlloyElement(Ores.TIN, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.CONSTANTAN, 2), new AlloyElement(Material.getMaterial("Copper"), 1), new AlloyElement(Ores.NICKEL, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.TRIBERIUM, 1), new AlloyElement(Ores.TIBERIUM, 5), new AlloyElement(Ores.BASALT, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.NITRONITE, 1), new AlloyElement(Ores.MAGMA, 6), new AlloyElement(Ores.OSRAM, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.MANYULLYN, 1), new AlloyElement(Ores.COBALT, 1), new AlloyElement(Ores.ARDITE, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.VIOLIUM, 2), new AlloyElement(Ores.ARDITE, 2), new AlloyElement(Ores.AURORIUM, 3)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.TRIBERIUM, 1), new AlloyElement(Ores.TIBERIUM, 5), new AlloyElement(Ores.DILITHIUM, 2)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.TRITONITE, 2), new AlloyElement(Ores.COBALT, 3), new AlloyElement(Ores.TERRAX, 2)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.ASTRIUM, 2), new AlloyElement(Ores.TERRAX, 3), new AlloyElement(Ores.AURORIUM, 2)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.LUMIX, 1), new AlloyElement(Ores.PALLADIUM, 1), new AlloyElement(Ores.TERRAX, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.OBSIDIORITE, 1), new AlloyElement(Ores.OBSIDIAN, 1), new AlloyElement(Ores.PALLADIUM, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.NIHILITE, 1), new AlloyElement(Ores.VIBRANIUM, 1), new AlloyElement(Ores.SOLARIUM, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.NUCLEUM, 3), new AlloyElement(Ores.IMPEROMITE, 3), new AlloyElement(Ores.EEZO, 1), new AlloyElement(Ores.OSRAM, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.NUCLEUM, 3), new AlloyElement(Ores.NIOB, 3), new AlloyElement(Ores.ABYSSUM, 1), new AlloyElement(Ores.EEZO, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.NUCLEUM, 3), new AlloyElement(Ores.PROXII, 3), new AlloyElement(Ores.ABYSSUM, 1), new AlloyElement(Ores.OSRAM, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.YRDEEN, 3), new AlloyElement(Ores.URU, 3), new AlloyElement(Ores.VALYRIUM, 3), new AlloyElement(Ores.ABYSSUM, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.YRDEEN, 3), new AlloyElement(Ores.URU, 3), new AlloyElement(Ores.VALYRIUM, 3), new AlloyElement(Ores.OSRAM, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.YRDEEN, 3), new AlloyElement(Ores.URU, 3), new AlloyElement(Ores.VALYRIUM, 3), new AlloyElement(Ores.EEZO, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.NIOB, 3), new AlloyElement(Ores.PALLADIUM, 3), new AlloyElement(Ores.DURANITE, 1), new AlloyElement(Ores.OSRAM, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.TERRAX, 2), new AlloyElement(Ores.KARMESINE, 1), new AlloyElement(Ores.OVIUM, 1), new AlloyElement(Ores.JAUXUM, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.SOLARIUM, 2), new AlloyElement(Ores.VALYRIUM, 2), new AlloyElement(Ores.URU, 2), new AlloyElement(Ores.NUCLEUM, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.ADAMANT, 3), new AlloyElement(Ores.IOX, 3), new AlloyElement(Ores.VIBRANIUM, 1), new AlloyElement(Ores.SOLARIUM, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.SEISMUM, 4), new AlloyElement(Ores.OBSIDIAN, 4), new AlloyElement(Ores.TRIBERIUM, 2), new AlloyElement(Ores.EEZO, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.IMPEROMITE, 2), new AlloyElement(Ores.DURANITE, 3), new AlloyElement(Ores.PROMETHIUM, 1), new AlloyElement(Ores.ABYSSUM, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.IGNITZ, 2), new AlloyElement(Ores.ARDITE, 2), new AlloyElement(Ores.TERRAX, 2), new AlloyElement(Ores.OSRAM, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.PROXII, 3), new AlloyElement(Ores.PROMETHIUM, 3), new AlloyElement(Ores.PALLADIUM, 3), new AlloyElement(Ores.EEZO, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.FRACTUM, 2), new AlloyElement(Ores.ABYSSUM, 1), new AlloyElement(Ores.TRIBERIUM, 3), new AlloyElement(Ores.OBSIDIAN, 3)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.DYONITE, 3), new AlloyElement(Ores.TIBERIUM, 12), new AlloyElement(Ores.FRACTUM, 1), new AlloyElement(Ores.SEISMUM, 1), new AlloyElement(Ores.OSRAM, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.DYONITE, 3), new AlloyElement(Ores.TRIBERIUM, 3), new AlloyElement(Ores.FRACTUM, 1), new AlloyElement(Ores.OSRAM, 1), new AlloyElement(Ores.SEISMUM, 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Ores.IOX, 1), new AlloyElement(Ores.EEZO, 2), new AlloyElement(Ores.ABYSSUM, 2), new AlloyElement(Ores.OSRAM, 2), new AlloyElement(Ores.OBSIDIORITE, 9)));
    }*/

    private static void registerAlloys() {
        TiConCompatability.registerAlloys();
        for (Alloy alloy : Alloy.alloys) {
            if (alloy.getInCount() <= 9) {

            }
            if (alloy.getInputs().size() == 2) { //For alloys with 2 inputs
                AlloyElement first = alloy.getInputs().get(0);
                AlloyElement second = alloy.getInputs().get(1);
                AlloyElement result = alloy.getResult();

                //Thermal Expansion - Induction Smelter: from dust
                NBTTagCompound inductionSmelterDust = new NBTTagCompound();

                inductionSmelterDust.setInteger("energy", Alloy.ENERGY_DUST);
                inductionSmelterDust.setTag("primaryInput", new NBTTagCompound());
                inductionSmelterDust.setTag("secondaryInput", new NBTTagCompound());
                inductionSmelterDust.setTag("primaryOutput", new NBTTagCompound());
                (new ItemStack(ModItems.itemMaterialObject, first.getAmount(), first.getOre().ordinal() + DUST)).writeToNBT(inductionSmelterDust.getCompoundTag("primaryInput"));
                (new ItemStack(ModItems.itemMaterialObject, second.getAmount(), second.getOre().ordinal() + DUST)).writeToNBT(inductionSmelterDust.getCompoundTag("secondaryInput"));
                (new ItemStack(ModItems.itemMaterialObject, result.getAmount(), result.getOre().ordinal() + INGOT)).writeToNBT(inductionSmelterDust.getCompoundTag("primaryOutput"));

                FMLInterModComms.sendMessage("ThermalExpansion", "SmelterRecipe", inductionSmelterDust);

                //Thermal Expansion - Induction Smelter: from ingot
                NBTTagCompound inductionSmelterIngot = new NBTTagCompound();

                inductionSmelterIngot.setInteger("energy", Alloy.ENERGY_INGOT);
                inductionSmelterIngot.setTag("primaryInput", new NBTTagCompound());
                inductionSmelterIngot.setTag("secondaryInput", new NBTTagCompound());
                inductionSmelterIngot.setTag("primaryOutput", new NBTTagCompound());
                (new ItemStack(ModItems.itemMaterialObject, first.getAmount(), first.getOre().ordinal() + INGOT)).writeToNBT(inductionSmelterIngot.getCompoundTag("primaryInput"));
                (new ItemStack(ModItems.itemMaterialObject, second.getAmount(), second.getOre().ordinal() + INGOT)).writeToNBT(inductionSmelterIngot.getCompoundTag("secondaryInput"));
                (new ItemStack(ModItems.itemMaterialObject, result.getAmount(), result.getOre().ordinal() + INGOT)).writeToNBT(inductionSmelterIngot.getCompoundTag("primaryOutput"));

                FMLInterModComms.sendMessage("ThermalExpansion", "SmelterRecipe", inductionSmelterIngot);
            }
        }
    }

}
