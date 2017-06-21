/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.compat.crafttweaker;

import com.sixteencolorgames.supertechtweaks.enums.Material;
import minetweaker.IUndoableAction;

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
        new Material(name, color, harvest);
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
