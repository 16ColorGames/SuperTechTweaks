/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.items;

import com.sixteencolorgames.supertechtweaks.enums.Material;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author oa10712
 */
public class ItemMaterialObject extends ItemBase {

    public static final int INGOT = 0;
    public static final int DUST = 1000;
    public static final int GEAR = 2000;
    public static final int NUGGET = 3000;
    public static final int PLATE = 4000;
    public static final int ROD = 5000;
    public static final int CLUMP = 6000;
    public static final int CRYSTAL = 7000;
    public static final int SHARD = 8000;
    public static final int WIRE = 9000;
    public static final int DIRTY = 10000;
    public static final int FOIL = 11000;
    public static final int TINY = 12000;

    public ItemMaterialObject() {
        super("itemMaterialObject");
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.MISC); // items will appear on the
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    // add a subitem for each item we want to appear in the creative tab
    // in this case - a chunk of each metal
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (Material metal : Material.materials) {
            ItemStack subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + INGOT);
            subItems.add(subItemStack);
            subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + DUST);
            subItems.add(subItemStack);
            subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + GEAR);
            subItems.add(subItemStack);
            subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + NUGGET);
            subItems.add(subItemStack);
            subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + PLATE);
            subItems.add(subItemStack);
            subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + ROD);
            subItems.add(subItemStack);
            subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + CLUMP);
            subItems.add(subItemStack);
            subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + CRYSTAL);
            subItems.add(subItemStack);
            subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + SHARD);
            subItems.add(subItemStack);
            subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + WIRE);
            subItems.add(subItemStack);
            subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + DIRTY);
            subItems.add(subItemStack);
            subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + FOIL);
            subItems.add(subItemStack);
            subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + TINY);
            subItems.add(subItemStack);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int metadata = stack.getMetadata();
        if (metadata >= TINY) {
            return super.getUnlocalizedName() + ".dustTiny" + Material.materials.get(metadata - TINY).getName();
        }
        if (metadata >= FOIL) {
            return super.getUnlocalizedName() + ".foil" + Material.materials.get(metadata - FOIL).getName();
        }
        if (metadata >= DIRTY) {
            return super.getUnlocalizedName() + ".dustDirty" + Material.materials.get(metadata - DIRTY).getName();
        }
        if (metadata >= WIRE) {
            return super.getUnlocalizedName() + ".wire" + Material.materials.get(metadata - WIRE).getName();
        }
        if (metadata >= SHARD) {
            return super.getUnlocalizedName() + ".shard" + Material.materials.get(metadata - SHARD).getName();
        }
        if (metadata >= CRYSTAL) {
            return super.getUnlocalizedName() + ".crystal" + Material.materials.get(metadata - CRYSTAL).getName();
        }
        if (metadata >= CLUMP) {
            return super.getUnlocalizedName() + ".clump" + Material.materials.get(metadata - CLUMP).getName();
        }
        if (metadata >= ROD) {
            return super.getUnlocalizedName() + ".rod" + Material.materials.get(metadata - ROD).getName();
        }
        if (metadata >= PLATE) {
            return super.getUnlocalizedName() + ".plate" + Material.materials.get(metadata - PLATE).getName();
        }
        if (metadata >= NUGGET) {
            return super.getUnlocalizedName() + ".nugget" + Material.materials.get(metadata - NUGGET).getName();
        }
        if (metadata >= GEAR) {
            return super.getUnlocalizedName() + ".gear" + Material.materials.get(metadata - GEAR).getName();
        }
        if (metadata >= DUST) {
            return super.getUnlocalizedName() + ".dust" + Material.materials.get(metadata - DUST).getName();
        }
        if (metadata >= INGOT) {
            return super.getUnlocalizedName() + ".ingot" + Material.materials.get(metadata - INGOT).getName();
        }
        return super.getUnlocalizedName() + ".ERROR_" + metadata;//We somehow got a material outside of the preset types
    }

}
