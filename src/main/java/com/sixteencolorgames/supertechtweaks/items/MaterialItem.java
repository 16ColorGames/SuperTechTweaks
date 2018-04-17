package com.sixteencolorgames.supertechtweaks.items;

import com.sixteencolorgames.supertechtweaks.enums.Material;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MaterialItem extends ItemBase {
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
	Material material;

	public MaterialItem(Material material) {
		super("item" + material.getName());
		this.material = material;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.MISC); // items will appear on the
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (I18n.canTranslate(getUnlocalizedNameInefficiently(stack) + '.' + material.getName())) {
			return I18n.translateToLocal(getUnlocalizedNameInefficiently(stack) + '.' + material.getName());
		}
		return String.format(super.getItemStackDisplayName(stack),
				I18n.canTranslate("supertechtweaks.entry." + material.getName())
						? I18n.translateToLocal("supertechtweaks.entry." + material.getName()) : material.getName());
	}

	public Material getMaterial() {
		return this.material;
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
			return "item.supertechtweaks.dustTiny";
		}
		if (metadata == FOIL) {
			return "item.supertechtweaks.foil";
		}
		if (metadata == DIRTY) {
			return "item.supertechtweaks.dustDirty";
		}
		if (metadata == WIRE) {
			return "item.supertechtweaks.wire";
		}
		if (metadata == SHARD) {
			return "item.supertechtweaks.shard";
		}
		if (metadata == CRYSTAL) {
			return "item.supertechtweaks.crystal";
		}
		if (metadata == CLUMP) {
			return "item.supertechtweaks.clump";
		}
		if (metadata == ROD) {
			return "item.supertechtweaks.rod";
		}
		if (metadata == PLATE) {
			return "item.supertechtweaks.plate";
		}
		if (metadata == NUGGET) {
			return "item.supertechtweaks.nugget";
		}
		if (metadata == GEAR) {
			return "item.supertechtweaks.gear";
		}
		if (metadata == DUST) {
			return "item.supertechtweaks.dust";
		}
		if (metadata == INGOT) {
			return "item.supertechtweaks.ingot";
		}
		if (metadata == ORE) {
			return "item.supertechtweaks.ore";
		}
		if (metadata == NETHER_ORE) {
			return "item.supertechtweaks.nether";
		}
		if (metadata == END_ORE) {
			return "item.supertechtweaks.end";
		}
		return "item.itemMaterialObject.ERROR_" + metadata;
	}
}
