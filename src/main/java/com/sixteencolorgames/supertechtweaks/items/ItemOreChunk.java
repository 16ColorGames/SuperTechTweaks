package com.sixteencolorgames.supertechtweaks.items;

import java.util.List;

import com.sixteencolorgames.supertechtweaks.enums.Metals;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemOreChunk extends Item {
	public ItemOreChunk() {
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.MISC); // items will appear on the
												// Miscellaneous creative tab
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	// add a subitem for each item we want to appear in the creative tab
	// in this case - a full bottle of each colour
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for (Metals metal : Metals.values()) {
			ItemStack subItemStack = new ItemStack(itemIn, 1, metal.getIndex() + 1);
			subItems.add(subItemStack);
		}
	}

	public String getUnlocalizedName(ItemStack stack) {
		int metadata = stack.getMetadata();
		return super.getUnlocalizedName() + "." + Metals.values()[metadata];
	}
}
