/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.compat.ticon;

import com.sixteencolorgames.supertechtweaks.ModItems;
import com.sixteencolorgames.supertechtweaks.enums.Alloy;
import com.sixteencolorgames.supertechtweaks.enums.Ores;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.ROD;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.MaterialEvent;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.smeltery.AlloyRecipe;

/**
 *
 * @author oa10712
 */
public class TiConCompatability {

    @SubscribeEvent
    public void onStatRegister(MaterialEvent.StatRegisterEvent<HeadMaterialStats> statRegisterEvent) {
        if (statRegisterEvent.stats instanceof HeadMaterialStats) {
            HeadMaterialStats oldStats = statRegisterEvent.newStats != null ? statRegisterEvent.newStats
                    : statRegisterEvent.stats;
            int newHarvestLevel = -1;
            try {
                newHarvestLevel = Ores.valueOf(statRegisterEvent.material.identifier.toUpperCase()).getMine();
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

    public static void registerMelting() {//Overrides for melting
        for (Ores ore : Ores.values()) {
            try {
                Fluid fluid = FluidRegistry.getFluid(ore.getName().toLowerCase());
                if (fluid != null) {
                    System.out.println("Attempting to add melting for " + ore.getName() + ": " + fluid.getUnlocalizedName());
                    TinkerRegistry.registerMelting(new ItemStack(ModItems.itemMaterialObject, 1, ore.ordinal() + ROD), fluid, 144);
                }
            } catch (Exception ex) {
                System.out.println("Failed to add melting.");
            }
        }
    }

    public static void registerAlloys() {
        Alloy.alloys.forEach((Alloy t) -> {
            TinkerRegistry.getAlloys().forEach((AlloyRecipe t1) -> {
              
            });
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });
    }
}
