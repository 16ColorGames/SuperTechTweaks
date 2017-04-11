/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.items;

import com.sixteencolorgames.supertechtweaks.enums.Ores;
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
        for (Ores metal : Ores.values()) {
            ItemStack subItemStack = new ItemStack(itemIn, 1, metal.ordinal());
            subItems.add(subItemStack);
            subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + DUST);
            subItems.add(subItemStack);
            subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + GEAR);
            subItems.add(subItemStack);
            subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + NUGGET);
            subItems.add(subItemStack);
            subItemStack = new ItemStack(itemIn, 1, metal.ordinal() + PLATE);
            subItems.add(subItemStack);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int metadata = stack.getMetadata();
        if (metadata >= PLATE) {
            return super.getUnlocalizedName() + ".plate" + Ores.values()[metadata - PLATE];
        }
        if (metadata >= NUGGET) {
            return super.getUnlocalizedName() + ".nugget" + Ores.values()[metadata - NUGGET];
        }
        if (metadata >= GEAR) {
            return super.getUnlocalizedName() + ".gear" + Ores.values()[metadata - GEAR];
        }
        if (metadata >= DUST) {
            return super.getUnlocalizedName() + ".dust" + Ores.values()[metadata - DUST];
        }
        return super.getUnlocalizedName() + ".ingot" + Ores.values()[metadata];
    }

}
