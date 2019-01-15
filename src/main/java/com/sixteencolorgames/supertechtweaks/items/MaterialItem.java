package com.sixteencolorgames.supertechtweaks.items;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.Utils;
import com.sixteencolorgames.supertechtweaks.enums.Material;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
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
	public static final int COIN = 13;
	public static final int BLADE = 14;

	Material material;

	public MaterialItem(Material material) {
		super("item" + material.getName());
		this.material = material;
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabs.MISC); // items will appear on the
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (I18n.canTranslate(getUnlocalizedNameInefficiently(stack) + '.' + material.getName())) {
			return I18n.translateToLocal(getUnlocalizedNameInefficiently(stack) + '.' + material.getName());
		}
		return String.format(super.getItemStackDisplayName(stack),
				I18n.canTranslate("supertechtweaks.entry." + material.getName())
						? I18n.translateToLocal("supertechtweaks.entry." + material.getName())
						: material.getName());
	}

	public Material getMaterial() {
		return material;
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

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
		subItemStack = new ItemStack(this, 1, COIN);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, BLADE);
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
		if (metadata == COIN) {
			return "item.supertechtweaks.coin";
		}
		if (metadata == BLADE) {
			return "item.supertechtweaks.blade";
		}
		return "item.itemMaterialObject.ERROR_" + metadata;
	}

}
