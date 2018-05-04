package com.sixteencolorgames.supertechtweaks.enums;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.items.OreItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Ore extends IForgeRegistryEntry.Impl<Ore> {
	public static IForgeRegistry<Ore> REGISTRY;
	private String name;
	private double hardness;
	private OreItem itemOre;

	/**
	 * The RGB code for the color of this
	 */
	private int color;

	public ItemStack customDrops = null;

	/**
	 * The harvest level of this
	 */
	private int harvest;

	public Ore(String string, int harvest, double hardness, int color) {
		name = string;
		this.harvest = harvest;
		this.hardness = hardness;
		this.color = color;
		itemOre = new OreItem(this);
	}

	public int getColor() {
		return color;
	}

	public ItemStack getDrops(byte base) {
		if (customDrops == null) {
			switch (base) {// Switch based on base block
			case -1:// NetherRack and similar
				return new ItemStack(itemOre, 1, OreItem.NETHER_ORE);
			case 1:// Endstone and similar
				return new ItemStack(itemOre, 1, OreItem.END_ORE);
			default:// Stone and unspecified
				return new ItemStack(itemOre, 1, OreItem.ORE);
			}
		} else {
			return customDrops;
		}
	}

	public double getHardness() {
		return hardness;
	}

	public int getHarvest() {
		return harvest;
	}

	public OreItem getItemOre() {
		return itemOre;
	}

	public String getName() {
		return name;
	}

	public void registerOre() {
		this.setRegistryName(getName());
		GameRegistry.findRegistry(Item.class).register(itemOre);

		registerOreDict();
		SuperTechTweaksMod.proxy.registerModels(this);

		Ore.REGISTRY.register(this);
	}

	private void registerOreDict() {
		ItemStack subItemStack = new ItemStack(itemOre, 1, OreItem.ORE);
		OreDictionary.registerOre("ore" + getName(), subItemStack);
		subItemStack = new ItemStack(itemOre, 1, OreItem.NETHER_ORE);
		OreDictionary.registerOre("oreNether" + getName(), subItemStack);
		subItemStack = new ItemStack(itemOre, 1, OreItem.END_ORE);
		OreDictionary.registerOre("oreEnd" + getName(), subItemStack);
	}

}
