/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.handlers;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

/**
 *
 * @author oa10712
 */
public class CustomFuelHandler implements IFuelHandler {
    
    private static final CustomFuelHandler INSTANCE = new CustomFuelHandler();
    private final Map<ItemStack, Integer> fuels = new HashMap();
    
    public static CustomFuelHandler getInstance() {
        return INSTANCE;
    }
    
    @Override
    public int getBurnTime(ItemStack fuel) {
        for (ItemStack stack : fuels.keySet()) {
            if (stack.getItem() == fuel.getItem() && stack.getMetadata() == fuel.getMetadata()) {
                
                return fuels.getOrDefault(fuel, 0);
            }
        }
        return 0;
    }
    
    public void addFuel(ItemStack fuel, int burnTime) {
        fuels.put(fuel, burnTime);
        System.out.println("Adding fuel: " + fuel.getItem() + ":" + fuel.getDisplayName() + ":" + fuel.getMetadata());
    }
    
    public void removeFuel(ItemStack fuel) {
        fuels.remove(fuel);
    }
    
}
