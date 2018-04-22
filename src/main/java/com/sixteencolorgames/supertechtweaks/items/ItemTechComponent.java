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
	public final static int BASIC_CIRCUIT = 0;
	public final static int ADVANCED_CIRCUIT = 1;
	public final static int ELITE_CIRCUIT = 2;
	public final static int ULTIMATE_CIRCUIT = 3;
	public final static int BASIC_CASING = 4;
	public final static int SMALL_POWER_UNIT = 5;
	public final static int HEATING_UNIT = 6;

	public ItemTechComponent() {
		super("itemTechComponent");
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabs.MISC); // items will appear on the
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
		case BASIC_CIRCUIT:
			return super.getUnlocalizedName() + ".circuitBasic";
		case ADVANCED_CIRCUIT:
			return super.getUnlocalizedName() + ".circuitAdvanced";
		case ELITE_CIRCUIT:
			return super.getUnlocalizedName() + ".circuitElite";
		case ULTIMATE_CIRCUIT:
			return super.getUnlocalizedName() + ".circuitUltimate";
		case BASIC_CASING:
			return super.getUnlocalizedName() + ".casingBasic";
		case SMALL_POWER_UNIT:
			return super.getUnlocalizedName() + ".powerUnitSmall";
		case HEATING_UNIT:
			return super.getUnlocalizedName() + ".heatingElement";
		default:
			return super.getUnlocalizedName();
		}
	}

	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, BASIC_CIRCUIT,
				new ModelResourceLocation("supertechtweaks:itemBasicCircuit", "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, ADVANCED_CIRCUIT,
				new ModelResourceLocation("supertechtweaks:itemAdvancedCircuit", "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, ELITE_CIRCUIT,
				new ModelResourceLocation("supertechtweaks:itemEliteCircuit", "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, ULTIMATE_CIRCUIT,
				new ModelResourceLocation("supertechtweaks:itemUltimateCircuit", "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, BASIC_CASING,
				new ModelResourceLocation("supertechtweaks:itemCasingBasic", "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, SMALL_POWER_UNIT,
				new ModelResourceLocation("supertechtweaks:itemPowerUnitSmall", "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, HEATING_UNIT,
				new ModelResourceLocation("supertechtweaks:itemHeatingElement", "inventory"));
	}

	public void setupDictionary() {
		OreDictionary.registerOre("circuitBasic", new ItemStack(this, 1, BASIC_CIRCUIT));
		OreDictionary.registerOre("circuitAdvanced", new ItemStack(this, 1, ADVANCED_CIRCUIT));
		OreDictionary.registerOre("circuitElite", new ItemStack(this, 1, ELITE_CIRCUIT));
		OreDictionary.registerOre("circuitUltimate", new ItemStack(this, 1, ULTIMATE_CIRCUIT));
		OreDictionary.registerOre("casingBasic", new ItemStack(this, 1, BASIC_CASING));
		OreDictionary.registerOre("powerUnitSmall", new ItemStack(this, 1, SMALL_POWER_UNIT));
		OreDictionary.registerOre("heatingElement", new ItemStack(this, 1, HEATING_UNIT));
	}
}