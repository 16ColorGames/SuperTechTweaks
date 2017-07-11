/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.compat.ticon;

import com.sixteencolorgames.supertechtweaks.ModRegistry;
import com.sixteencolorgames.supertechtweaks.enums.Alloy;
import com.sixteencolorgames.supertechtweaks.enums.AlloyElement;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.*;
import com.sixteencolorgames.supertechtweaks.items.ItemOreChunk;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.MaterialEvent;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

/**
 *
 * @author oa10712
 */
public class TiConCompatability {
    
    public static void registerMaterials() {
        
        TinkerRegistry.getAllMaterials().forEach((mat) -> {
            try {
                Material material = Material.getMaterial(mat.identifier);
                mat.addItemIngot("ingot" + material.getName());
            } catch (Exception ex) {
            }
        });
    }
    
    @SubscribeEvent
    public void onStatRegister(MaterialEvent.StatRegisterEvent<HeadMaterialStats> statRegisterEvent) {
        if (statRegisterEvent.stats instanceof HeadMaterialStats) {
            HeadMaterialStats oldStats = statRegisterEvent.newStats != null ? statRegisterEvent.newStats
                    : statRegisterEvent.stats;
            int newHarvestLevel = -1;
            try {
                newHarvestLevel = Material.getMaterial(statRegisterEvent.material.identifier).getMine();
            } catch (Exception ex) {
            }
            System.out.println("Changing level: " + statRegisterEvent.material.identifier + " from " + oldStats.harvestLevel + " to " + newHarvestLevel);
            if (newHarvestLevel != oldStats.harvestLevel) {
                HeadMaterialStats newStats = new HeadMaterialStats(oldStats.durability, oldStats.miningspeed,
                        oldStats.attack, newHarvestLevel);
                statRegisterEvent.overrideResult(newStats);
            }
        }
    }
    
    public static void registerMelting() { //Overrides for melting
        Material.materials.forEach((ore) -> {
            try {
                Fluid fluid = FluidRegistry.getFluid(ore.getName().toLowerCase());
                System.out.println("Attempting to add melting for " + ore.getName() + ": " + fluid.getName());
                TinkerRegistry.registerMelting(new ItemStack(ModRegistry.itemMaterialObject, 1, ore.ordinal() + INGOT), fluid, 144);
                TinkerRegistry.registerMelting(new ItemStack(ModRegistry.itemMaterialObject, 1, ore.ordinal() + DUST), fluid, 144);
                TinkerRegistry.registerMelting(new ItemStack(ModRegistry.itemMaterialObject, 1, ore.ordinal() + PLATE), fluid, 144);
                TinkerRegistry.registerMelting(new ItemStack(ModRegistry.itemMaterialObject, 1, ore.ordinal() + WIRE), fluid, 72);
                TinkerRegistry.registerMelting(new ItemStack(ModRegistry.itemMaterialObject, 1, ore.ordinal() + GEAR), fluid, 576);
                TinkerRegistry.registerMelting(new ItemStack(ModRegistry.itemMaterialObject, 1, ore.ordinal() + NUGGET), fluid, 16);
                TinkerRegistry.registerMelting(new ItemStack(ModRegistry.itemOreChunk, 1, ore.ordinal()), fluid, 288);
                TinkerRegistry.registerMelting(new ItemStack(ModRegistry.itemOreChunk, 1, ore.ordinal() + ItemOreChunk.NETHER), fluid, 576);
            } catch (Exception ex) {
                System.out.println("Failed to add melting.");
            }
        });
    }
    
    public static void registerCasting() {
        Material.materials.forEach((ore) -> {
            try {
                Fluid fluid = FluidRegistry.getFluid(ore.getName().toLowerCase());
                System.out.println("Attempting to add casting for " + ore.getName() + ": " + fluid.getName());
                TinkerRegistry.registerBasinCasting(OreDictionary.getOres("block" + ore.getName()).get(0), null, fluid, 1296);
                TinkerRegistry.registerTableCasting(new ItemStack(ModRegistry.itemMaterialObject, 1, ore.ordinal() + INGOT), TinkerSmeltery.castIngot, fluid, 144);
                TinkerRegistry.registerTableCasting(new ItemStack(ModRegistry.itemMaterialObject, 1, ore.ordinal() + NUGGET), TinkerSmeltery.castNugget, fluid, 16);
                TinkerRegistry.registerTableCasting(new ItemStack(ModRegistry.itemMaterialObject, 1, ore.ordinal() + GEAR), TinkerSmeltery.castGear, fluid, 576);
                TinkerRegistry.registerTableCasting(new ItemStack(ModRegistry.itemMaterialObject, 1, ore.ordinal() + PLATE), TinkerSmeltery.castPlate, fluid, 144);
            } catch (Exception ex) {
                System.out.println("Failed to add melting.");
            }
        });
    }
    
    public static void registerAlloys() {
        Alloy.alloys.forEach((Alloy t) -> {
            try {
                Fluid out = FluidRegistry.getFluid(t.getResult().getOre().getName().toLowerCase());
                System.out.println("Registering alloy with ticon:" + t.getResult().getOre().getName());
                System.out.println("Result: " + out.getName() + ", Amount: " + t.getResult().getAmount());
                ArrayList<FluidStack> inputs = new ArrayList();
                for (AlloyElement e : t.getInputs()) {
                    FluidStack in = new FluidStack(FluidRegistry.getFluid(e.getOre().getName().toLowerCase()), e.getAmount());
                    inputs.add(in);
                    System.out.println("In: " + in.getLocalizedName() + ", Amount: " + in.amount);
                }
                TinkerRegistry.registerAlloy(new FluidStack(out, t.getResult().getAmount()), inputs.toArray(new FluidStack[1]));
            } catch (Exception ex) {
            }
        });
    }
}
