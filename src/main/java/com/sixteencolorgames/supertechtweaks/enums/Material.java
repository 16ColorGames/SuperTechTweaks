/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.enums;

import com.sixteencolorgames.supertechtweaks.ModItems;
import com.sixteencolorgames.supertechtweaks.items.ItemOreChunk;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;

/**
 *
 * @author oa10712
 */
public class Material {

    public static ArrayList<Material> materials = new ArrayList();

    public static Material getMaterial(String asString) {
        for (Material mat : materials) {
            if (mat.getName().equals(asString)) {
                return mat;
            }
        }
        return materials.get(0);
    }

    public static Material getMaterial(int index) {
        return materials.get(index);
    }

    /**
     * The oreDict name of the metal
     */
    private final String name;
    /**
     * The RGB code for the color of this metal.
     */
    private int color;
    /**
     * The harvest level of this metal.
     */
    private int harvest;
    /**
     * Level that a tool made of this can mine
     */
    private int mine;

    private int ordinal;

    /**
     *
     * @param name The ore dictionary name
     * @param color
     * @param harvest
     */
    public Material(String name, String color, int harvest) {
        this(name, color, harvest, -1);
    }

    public Material(String name, String color, int harvest, int mine) {
        this.name = name;
        this.color = Color.decode(color).getRGB();
        this.harvest = harvest;
        this.mine = mine;
        this.ordinal = materials.size();
        materials.add(this);
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getHarvest() {
        return harvest;
    }

    public int getMine() {
        return mine;
    }

    public ItemStack getDrops(byte base) {
        switch (base) {//Switch based on base block
            case -1://NetherRack and similar
                return new ItemStack(ModItems.itemOreChunk, 1, this.ordinal() + ItemOreChunk.NETHER);
            case 1://Endstone and similar
                return new ItemStack(ModItems.itemOreChunk, 1, this.ordinal() + ItemOreChunk.END);
            default://Stone and unspecified
                return new ItemStack(ModItems.itemOreChunk, 1, this.ordinal());
        }
    }

    public int ordinal() {
        return ordinal;
    }
}
