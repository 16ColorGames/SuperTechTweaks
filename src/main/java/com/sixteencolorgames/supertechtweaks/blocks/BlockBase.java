package com.sixteencolorgames.supertechtweaks.blocks;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.items.ItemModelProvider;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Helper class for mod blocks
 *
 * @author oa10712
 *
 */
public class BlockBase extends Block implements ItemModelProvider {

	protected String name;
	private Item itemBlock;

	public BlockBase(Material material, String name) {
		super(material);

		this.name = name;

		setUnlocalizedName(name);
		setRegistryName(name);

		// setCreativeTab(SuperTechTweaksMod.creativeTab);
	}

	public Item getItemBlock() {
		return itemBlock;
	}

	@Override
	public void registerItemModel(Item item) {
		SuperTechTweaksMod.proxy.registerItemRenderer(item, 0, name);
	}

	@Override
	public BlockBase setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}

	public void setItemBlock(Item itemBlock) {
		this.itemBlock = itemBlock;
	}
}
