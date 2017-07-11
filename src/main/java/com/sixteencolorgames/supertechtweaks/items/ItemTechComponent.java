/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.items;

import static com.sixteencolorgames.supertechtweaks.ModRegistry.itemTechComponent;
import java.util.List;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author oa10712
 */
public class ItemTechComponent extends ItemBase {

    public ItemTechComponent() {
        super("itemTechComponent");
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.MISC); // items will appear on the
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    // add a subitem for each item we want to appear in the creative tab
    // in this case - each pre-defined component
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (int i = 0; i < 7; i++) {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int metadata = stack.getMetadata();
        switch (metadata) {
            case 0:
                return super.getUnlocalizedName() + ".circuitBasic";
            case 1:
                return super.getUnlocalizedName() + ".circuitAdvanced";
            case 2:
                return super.getUnlocalizedName() + ".circuitElite";
            case 3:
                return super.getUnlocalizedName() + ".circuitUltimate";
            case 4:
                return super.getUnlocalizedName() + ".casingBasic";
            case 5:
                return super.getUnlocalizedName() + ".powerUnitSmall";
            case 6:
                return super.getUnlocalizedName() + ".heatingElement";
            default:
                return super.getUnlocalizedName();
        }
    }

    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(itemTechComponent, 0, new ModelResourceLocation("supertechtweaks:itemBasicCircuit",
                "inventory"));
        ModelLoader.setCustomModelResourceLocation(itemTechComponent, 1, new ModelResourceLocation("supertechtweaks:itemAdvancedCircuit",
                "inventory"));
        ModelLoader.setCustomModelResourceLocation(itemTechComponent, 2, new ModelResourceLocation("supertechtweaks:itemEliteCircuit",
                "inventory"));
        ModelLoader.setCustomModelResourceLocation(itemTechComponent, 3, new ModelResourceLocation("supertechtweaks:itemUltimateCircuit",
                "inventory"));
        ModelLoader.setCustomModelResourceLocation(itemTechComponent, 4, new ModelResourceLocation("supertechtweaks:itemCasingBasic",
                "inventory"));
        ModelLoader.setCustomModelResourceLocation(itemTechComponent, 5, new ModelResourceLocation("supertechtweaks:itemPowerUnitSmall",
                "inventory"));
        ModelLoader.setCustomModelResourceLocation(itemTechComponent, 6, new ModelResourceLocation("supertechtweaks:itemHeatingElement",
                "inventory"));
    }

    public void setupDictionary() {
        OreDictionary.registerOre("circuitBasic", new ItemStack(itemTechComponent, 1, 0));
        OreDictionary.registerOre("circuitAdvanced", new ItemStack(itemTechComponent, 1, 1));
        OreDictionary.registerOre("circuitElite", new ItemStack(itemTechComponent, 1, 2));
        OreDictionary.registerOre("circuitUltimate", new ItemStack(itemTechComponent, 1, 3));
        OreDictionary.registerOre("casingBasic", new ItemStack(itemTechComponent, 1, 4));
        OreDictionary.registerOre("powerUnitSmall", new ItemStack(itemTechComponent, 1, 5));
        OreDictionary.registerOre("heatingElement", new ItemStack(itemTechComponent, 1, 6));
    }
}
