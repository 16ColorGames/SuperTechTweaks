/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.compat.crafttweaker;

import com.sixteencolorgames.supertechtweaks.ModFluids;
import com.sixteencolorgames.supertechtweaks.Recipies;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.proxy.ClientProxy;
import com.sixteencolorgames.supertechtweaks.proxy.CommonProxy;
import minetweaker.IUndoableAction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraftforge.fluids.BlockFluidClassic;

/**
 *
 * @author oa10712
 */
public class AddMaterial implements IUndoableAction {

    private final String name;
    private final String color;
    private final int harvest;

    public AddMaterial(String name, String color, int harvest) {
        this.name = name;
        this.color = color;
        this.harvest = harvest;
    }

    @Override
    public void apply() {
        Material mat = new Material(name, color, harvest);
        ModFluids.createFluid(mat.getName().toLowerCase(), false,
                    fluid -> fluid.setLuminosity(10).setDensity(800).setViscosity(300),
                    fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.PURPLE)),
                    mat);
        Recipies.register(mat);
        CommonProxy.registerOreDict(mat);
        ClientProxy.registerModels(mat);
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public void undo() {
        Material.remove(name);
    }

    @Override
    public String describe() {
        return "Adding new material type: " + name;
    }

    @Override
    public String describeUndo() {
        return "Removing new material type: " + name;
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}
