/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.compat.ticon;

import com.sixteencolorgames.supertechtweaks.enums.Ores;
import com.sixteencolorgames.supertechtweaks.items.ItemOreChunk;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.MaterialEvent;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;

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

    public static void registerMelting() {
        for (Ores ore : Ores.values()) {
            try {
                Fluid fluid = FluidRegistry.getFluid(ore.getName().toLowerCase());
                if (fluid != null) {
                    System.out.println("Attempting to add melting for " + ore.getName() + ": " + fluid.getUnlocalizedName());
                    TinkerRegistry.registerMelting(new ItemStack(new ItemOreChunk(), 1, ore.ordinal()), fluid, 288);
                    
                }
            } catch (Exception ex) {
                System.out.println("Failed to add melting.");
            }
        }
    }
}
