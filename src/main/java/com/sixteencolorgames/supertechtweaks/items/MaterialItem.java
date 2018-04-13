package com.sixteencolorgames.supertechtweaks.items;

import java.awt.List;
import java.util.ArrayList;

import com.sixteencolorgames.supertechtweaks.enums.Material;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MaterialItem extends ItemBase {
	Material material;
	public static final int INGOT = 0;
	public static final int DUST = 1;
	public static final int GEAR = 2;
	public static final int NUGGET = 3;
	public static final int PLATE = 4;
	public static final int ROD = 5;
	public static final int CLUMP = 6;
	public static final int CRYSTAL = 7;
	public static final int SHARD = 8;
	public static final int WIRE = 9;
	public static final int DIRTY = 10;
	public static final int FOIL = 11;
	public static final int TINY = 12;

	public static final int ORE = 50;
	public static final int NETHER_ORE = 51;
	public static final int END_ORE = 52;

	public MaterialItem(Material material) {
		super("item" + material.getName());
		this.material = material;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.MISC); // items will appear on the
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	} // add a subitem for each item we want to appear in the creative tab
		// in this case - a chunk of each metal

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {

		ItemStack subItemStack = new ItemStack(this, 1, INGOT);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, DUST);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, GEAR);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, NUGGET);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, PLATE);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, ROD);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, CLUMP);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, CRYSTAL);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, SHARD);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, WIRE);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, DIRTY);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, FOIL);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, TINY);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, ORE);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, NETHER_ORE);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, END_ORE);
		subItems.add(subItemStack);

	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int metadata = stack.getMetadata();
		if (metadata == TINY) {
			return "item.itemMaterialObject.dustTiny" + material.getName();
		}
		if (metadata == FOIL) {
			return "item.itemMaterialObject.foil" + material.getName();
		}
		if (metadata == DIRTY) {
			return "item.itemMaterialObject.dustDirty" + material.getName();
		}
		if (metadata == WIRE) {
			return "item.itemMaterialObject.wire" + material.getName();
		}
		if (metadata == SHARD) {
			return "item.itemMaterialObject.shard" + material.getName();
		}
		if (metadata == CRYSTAL) {
			return "item.itemMaterialObject.crystal" + material.getName();
		}
		if (metadata == CLUMP) {
			return "item.itemMaterialObject.clump" + material.getName();
		}
		if (metadata == ROD) {
			return "item.itemMaterialObject.rod" + material.getName();
		}
		if (metadata == PLATE) {
			return "item.itemMaterialObject.plate" + material.getName();
		}
		if (metadata == NUGGET) {
			return "item.itemMaterialObject.nugget" + material.getName();
		}
		if (metadata == GEAR) {
			return "item.itemMaterialObject.gear" + material.getName();
		}
		if (metadata == DUST) {
			return "item.itemMaterialObject.dust" + material.getName();
		}
		if (metadata == INGOT) {
			return "item.itemMaterialObject.ingot" + material.getName();
		}
		if (metadata == ORE) {
			return "item.itemMaterialObject.ore" + material.getName();
		}
		if (metadata == NETHER_ORE) {
			return "item.itemMaterialObject.nether" + material.getName();
		}
		if (metadata == END_ORE) {
			return "item.itemMaterialObject.end" + material.getName();
		}
		return "item.itemMaterialObject.ERROR_" + metadata;// We somehow
															// got a
															// material
															// outside
															// of the
															// preset
															// types
	}

	public Material getMaterial() {
		return this.material;
	}
}
