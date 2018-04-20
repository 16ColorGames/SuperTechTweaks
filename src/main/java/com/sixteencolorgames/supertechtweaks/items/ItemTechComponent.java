package com.sixteencolorgames.supertechtweaks.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for (int i = 0; i < 7; i++) {
			subItems.add(new ItemStack(this, 1, i));
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
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation("supertechtweaks:itemBasicCircuit", "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 1,
				new ModelResourceLocation("supertechtweaks:itemAdvancedCircuit", "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 2,
				new ModelResourceLocation("supertechtweaks:itemEliteCircuit", "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 3,
				new ModelResourceLocation("supertechtweaks:itemUltimateCircuit", "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 4,
				new ModelResourceLocation("supertechtweaks:itemCasingBasic", "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 5,
				new ModelResourceLocation("supertechtweaks:itemPowerUnitSmall", "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 6,
				new ModelResourceLocation("supertechtweaks:itemHeatingElement", "inventory"));
	}

	public void setupDictionary() {
		OreDictionary.registerOre("circuitBasic", new ItemStack(this, 1, 0));
		OreDictionary.registerOre("circuitAdvanced", new ItemStack(this, 1, 1));
		OreDictionary.registerOre("circuitElite", new ItemStack(this, 1, 2));
		OreDictionary.registerOre("circuitUltimate", new ItemStack(this, 1, 3));
		OreDictionary.registerOre("casingBasic", new ItemStack(this, 1, 4));
		OreDictionary.registerOre("powerUnitSmall", new ItemStack(this, 1, 5));
		OreDictionary.registerOre("heatingElement", new ItemStack(this, 1, 6));
	}
}