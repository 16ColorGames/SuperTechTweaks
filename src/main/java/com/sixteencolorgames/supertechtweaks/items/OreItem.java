package com.sixteencolorgames.supertechtweaks.items;

import java.util.List;

import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.enums.Ore;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OreItem extends ItemBase {
	public static final int ORE = 0;

	public static final int NETHER_ORE = 1;

	public static final int END_ORE = 2;

	private Ore ore;

	public OreItem(String name, Ore ore) {
		super(name);
		this.ore = ore;
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabs.MISC); // items will appear on the
	}

	/**
	 * allows items to add custom lines of information to the mouseover
	 * description
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.getMetadata() == ORE || stack.getMetadata() == NETHER_ORE || stack.getMetadata() == END_ORE) {
			tooltip.add("Harvest Level: " + ore.getHarvest());

		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (I18n.canTranslate(getUnlocalizedNameInefficiently(stack) + '.' + ore.getName())) {
			return I18n.translateToLocal(getUnlocalizedNameInefficiently(stack) + '.' + ore.getName());
		}
		return String.format(super.getItemStackDisplayName(stack),
				I18n.canTranslate("supertechtweaks.entry." + ore.getName())
						? I18n.translateToLocal("supertechtweaks.entry." + ore.getName()) : ore.getName());
	}

	public Ore getOre() {
		return ore;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		ItemStack subItemStack = new ItemStack(this, 1, ORE);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, NETHER_ORE);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, END_ORE);
		subItems.add(subItemStack);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int metadata = stack.getMetadata();
		if (metadata == ORE) {
			return "item.supertechtweaks.ore";
		}
		if (metadata == NETHER_ORE) {
			return "item.supertechtweaks.nether";
		}
		if (metadata == END_ORE) {
			return "item.supertechtweaks.end";
		}
		return "item.itemOreObject.ERROR_" + metadata;
	}

}
