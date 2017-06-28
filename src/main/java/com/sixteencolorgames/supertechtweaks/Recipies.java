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
import net.minecraftforge.fml.common.Loader;
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
        Material.materials.forEach((ore) -> {
            //Iterate through each material and create recipies for each
            register(ore);
        });

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemTechComponent, 1, 4),
                "zxz", "xyx", "zxz", 'x', "barsIron", 'y', "powerUnitSmall", 'z', "ingotIron"));//Craft a basic casing

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemTechComponent, 1, 5),
                "x x", "yxy", "   ", 'x', "plateIron", 'y', "wireCopper"));//Craft a small power unit
        
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemTechComponent, 1, 6),
                "xxx", "xyx", "xxx", 'x', "wireNichrome", 'y', "plateCopper"));//Craft a heating element

        addAlloys();
        registerAlloys();

        if (Loader.isModLoaded("tconstruct")) {
            TiConCompatability.registerMelting();
            TiConCompatability.registerCasting();
        }
    }

    private static void addAlloys() {
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Nichrome"), 5), new AlloyElement(Material.getMaterial("Nickel"), 4), new AlloyElement(Material.getMaterial("Chromium"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Electrum"), 2), new AlloyElement(Material.getMaterial("Silver"), 1), new AlloyElement(Material.getMaterial("Gold"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("AluBrass"), 4), new AlloyElement(Material.getMaterial("Copper"), 1), new AlloyElement(Material.getMaterial("Aluminum"), 3)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Brass"), 3), new AlloyElement(Material.getMaterial("Copper"), 2), new AlloyElement(Material.getMaterial("Zinc"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Bronze"), 4), new AlloyElement(Material.getMaterial("Copper"), 3), new AlloyElement(Material.getMaterial("Tin"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Constantan"), 2), new AlloyElement(Material.getMaterial("Copper"), 1), new AlloyElement(Material.getMaterial("Nickel"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Triberium"), 1), new AlloyElement(Material.getMaterial("Tiberium"), 5), new AlloyElement(Material.getMaterial("Basalt"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Nitronite"), 1), new AlloyElement(Material.getMaterial("Magma"), 6), new AlloyElement(Material.getMaterial("Osram"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Manyullyn"), 1), new AlloyElement(Material.getMaterial("Cobalt"), 1), new AlloyElement(Material.getMaterial("Ardite"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Violium"), 2), new AlloyElement(Material.getMaterial("Ardite"), 2), new AlloyElement(Material.getMaterial("Aurorium"), 3)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Triberium"), 1), new AlloyElement(Material.getMaterial("Tiberium"), 5), new AlloyElement(Material.getMaterial("Dilithium"), 2)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Tritonite"), 2), new AlloyElement(Material.getMaterial("Cobalt"), 3), new AlloyElement(Material.getMaterial("Terrax"), 2)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Astrium"), 2), new AlloyElement(Material.getMaterial("Terrax"), 3), new AlloyElement(Material.getMaterial("Aurorium"), 2)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Lumix"), 1), new AlloyElement(Material.getMaterial("Palladium"), 1), new AlloyElement(Material.getMaterial("Terrax"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Obsidiorite"), 1), new AlloyElement(Material.getMaterial("Obsidian"), 1), new AlloyElement(Material.getMaterial("Palladium"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Nihilite"), 1), new AlloyElement(Material.getMaterial("Vibranium"), 1), new AlloyElement(Material.getMaterial("Solarium"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Nucleum"), 3), new AlloyElement(Material.getMaterial("Imperomite"), 3), new AlloyElement(Material.getMaterial("Eezo"), 1), new AlloyElement(Material.getMaterial("Osram"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Nucleum"), 3), new AlloyElement(Material.getMaterial("Niob"), 3), new AlloyElement(Material.getMaterial("Abyssum"), 1), new AlloyElement(Material.getMaterial("Eezo"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Nucleum"), 3), new AlloyElement(Material.getMaterial("Proxii"), 3), new AlloyElement(Material.getMaterial("Abyssum"), 1), new AlloyElement(Material.getMaterial("Osram"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Yrdeen"), 3), new AlloyElement(Material.getMaterial("Uru"), 3), new AlloyElement(Material.getMaterial("Valyrium"), 3), new AlloyElement(Material.getMaterial("Abyssum"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Yrdeen"), 3), new AlloyElement(Material.getMaterial("Uru"), 3), new AlloyElement(Material.getMaterial("Valyrium"), 3), new AlloyElement(Material.getMaterial("Osram"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Yrdeen"), 3), new AlloyElement(Material.getMaterial("Uru"), 3), new AlloyElement(Material.getMaterial("Valyrium"), 3), new AlloyElement(Material.getMaterial("Eezo"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Niob"), 3), new AlloyElement(Material.getMaterial("Palladium"), 3), new AlloyElement(Material.getMaterial("Duranite"), 1), new AlloyElement(Material.getMaterial("Osram"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Terrax"), 2), new AlloyElement(Material.getMaterial("Karmesine"), 1), new AlloyElement(Material.getMaterial("Ovium"), 1), new AlloyElement(Material.getMaterial("Jauxum"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Solarium"), 2), new AlloyElement(Material.getMaterial("Valyrium"), 2), new AlloyElement(Material.getMaterial("Uru"), 2), new AlloyElement(Material.getMaterial("Nucleum"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Adamant"), 3), new AlloyElement(Material.getMaterial("Iox"), 3), new AlloyElement(Material.getMaterial("Vibranium"), 1), new AlloyElement(Material.getMaterial("Solarium"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Seismum"), 4), new AlloyElement(Material.getMaterial("Obsidian"), 4), new AlloyElement(Material.getMaterial("Triberium"), 2), new AlloyElement(Material.getMaterial("Eezo"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Imperomite"), 2), new AlloyElement(Material.getMaterial("Duranite"), 3), new AlloyElement(Material.getMaterial("Promethium"), 1), new AlloyElement(Material.getMaterial("Abyssum"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Ignitz"), 2), new AlloyElement(Material.getMaterial("Ardite"), 2), new AlloyElement(Material.getMaterial("Terrax"), 2), new AlloyElement(Material.getMaterial("Osram"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Proxii"), 3), new AlloyElement(Material.getMaterial("Promethium"), 3), new AlloyElement(Material.getMaterial("Palladium"), 3), new AlloyElement(Material.getMaterial("Eezo"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Fractum"), 2), new AlloyElement(Material.getMaterial("Abyssum"), 1), new AlloyElement(Material.getMaterial("Triberium"), 3), new AlloyElement(Material.getMaterial("Obsidian"), 3)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Dyonite"), 3), new AlloyElement(Material.getMaterial("Tiberium"), 12), new AlloyElement(Material.getMaterial("Fractum"), 1), new AlloyElement(Material.getMaterial("Seismum"), 1), new AlloyElement(Material.getMaterial("Osram"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Dyonite"), 3), new AlloyElement(Material.getMaterial("Triberium"), 3), new AlloyElement(Material.getMaterial("Fractum"), 1), new AlloyElement(Material.getMaterial("Osram"), 1), new AlloyElement(Material.getMaterial("Seismum"), 1)));
        Alloy.alloys.add(new Alloy(new AlloyElement(Material.getMaterial("Iox"), 1), new AlloyElement(Material.getMaterial("Eezo"), 2), new AlloyElement(Material.getMaterial("Abyssum"), 2), new AlloyElement(Material.getMaterial("Osram"), 2), new AlloyElement(Material.getMaterial("Obsidiorite"), 9)));
    }

    private static void registerAlloys() {
        if (Loader.isModLoaded("tconstruct")) {
            TiConCompatability.registerAlloys();
        }
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

    public static void register(Material ore) {
        GameRegistry.addSmelting(new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal()), new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + INGOT), 1.0f);//Smelt ore to ingot
        GameRegistry.addSmelting(new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + DUST), new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + INGOT), 1.0f);//Smelt dust to ingot
        GameRegistry.addSmelting(new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal() + NETHER), new ItemStack(ModItems.itemOreChunk, 2, ore.ordinal()), 0);//Smelt nether to regular
        GameRegistry.addSmelting(new ItemStack(ModItems.itemOreChunk, 1, ore.ordinal() + END), new ItemStack(ModItems.itemOreChunk, 3, ore.ordinal()), 0);//Smelt end to regular

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + GEAR),
                " x ", "xyx", " x ", 'x', "ingot" + ore.getName(), 'y', "nugget" + ore.getName()));//Craft a gear
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + INGOT),
                "xxx", "xxx", "xxx", 'x', "nugget" + ore.getName()));//Craft nuggets into an ingot
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + DUST),
                "xxx", "xxx", "xxx", 'x', "dustTiny" + ore.getName()));//Craft nuggets into an ingot
//            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockMaterial.get(ore)),
//                    "xxx", "xxx", "xxx", 'x', "ingot" + ore.getName()));//Craft ingots into a block
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemMaterialObject, 4, ore.ordinal() + ROD),
                "x", "x", 'x', "ingot" + ore.getName()));//Craft ingots into a rod

        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemMaterialObject, 9, ore.ordinal() + NUGGET), new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + INGOT));//Craft ingots into nuggets
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemMaterialObject, 9, ore.ordinal() + TINY), new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + DUST));//Craft dust into dust
        // GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemMaterialObject, 9, ore.ordinal() + INGOT), new ItemStack(ModBlocks.blockMaterial.get(ore)));//Craft blocks into ingots
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemMaterialObject, 2, ore.ordinal() + WIRE), "plate" + ore.getName(), "craftingToolWireCutter")); //Craft plates into wires
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + PLATE), "ingot" + ore.getName(), "craftingToolForgeHammer")); //Craft ingot into plate
    }

}
