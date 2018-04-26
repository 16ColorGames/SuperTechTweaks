package com.sixteencolorgames.supertechtweaks.items;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.Utils;
import com.sixteencolorgames.supertechtweaks.enums.Material;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
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
	public static final int COIN = 13;
	public static final int BLADE = 14;

	public static final int ORE = 50;
	public static final int NETHER_ORE = 51;
	public static final int END_ORE = 52;

	public static final int HAMMER = 100;
	public static final int PLIERS = 101;
	public static final int DRAW_PLATE = 102;

	Material material;

	public MaterialItem(Material material) {
		super("item" + material.getName());
		this.material = material;
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabs.MISC); // items will appear on the
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		if (stack.getMetadata() >= HAMMER) {
			return enchantment == Enchantments.EFFICIENCY || enchantment == Enchantments.UNBREAKING
					|| enchantment == Enchantments.MENDING;
		}
		return super.canApplyAtEnchantingTable(stack, enchantment);
	}

	private void damageTool(ItemStack stack, int amount, Random rand, @Nullable EntityPlayer player) {
		if (amount <= 0) {
			return;
		}

		int unbreakLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
		for (int i = 0; unbreakLevel > 0 && i < amount; i++) {
			if (EnchantmentDurability.negateDamage(stack, unbreakLevel, rand)) {
				amount--;
			}
		}
		if (amount <= 0) {
			return;
		}

		String nbtKey = "toolDMG";
		int curDamage = Utils.getNBTInt(stack, nbtKey);
		curDamage += amount;

		if (player instanceof EntityPlayerMP) {
			CriteriaTriggers.ITEM_DURABILITY_CHANGED.trigger((EntityPlayerMP) player, stack, curDamage);
		}

		if (curDamage >= getMaxDamageTool(stack)) {
			if (player != null) {
				player.renderBrokenItemStack(stack);
				player.addStat(StatList.getObjectBreakStats(this));
			}
			stack.shrink(1);
			return;
		}
		Utils.setNBTInt(stack, nbtKey, curDamage);
	}

	@Nonnull
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		if (stack.getMetadata() >= HAMMER) {
			ItemStack container = stack.copy();
			damageTool(container, 1, itemRand, null);
			return container;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		if (stack.getMetadata() >= HAMMER) {
			return Utils.getNBTInt(stack, "toolDMG") / (double) getMaxDamageTool(stack);
		}
		return 0;
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
		return material;
	}

	/**
	 * Return the maxDamage for this ItemStack. Defaults to the maxDamage field
	 * in this item, but can be overridden here for other sources such as NBT.
	 *
	 * @param stack
	 *            The itemstack that is damaged
	 * @return the damage value
	 */
	public int getMaxDamageTool(ItemStack stack) {
		switch (stack.getMetadata()) {
		case HAMMER:
			return material.getShear() * 2;
		// TODO hammer max
		case PLIERS:
			return material.getShear() * 2;
		// TODO pliers max
		case DRAW_PLATE:
			return material.getShear() * 2;
		// todo draw plate max
		default:
			return getMaxDamage();
		}
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

		subItemStack = new ItemStack(this, 1, ORE);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, NETHER_ORE);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, END_ORE);
		subItems.add(subItemStack);

		subItemStack = new ItemStack(this, 1, HAMMER);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, PLIERS);
		subItems.add(subItemStack);
		subItemStack = new ItemStack(this, 1, DRAW_PLATE);
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
		if (metadata == ORE) {
			return "item.supertechtweaks.ore";
		}
		if (metadata == NETHER_ORE) {
			return "item.supertechtweaks.nether";
		}
		if (metadata == END_ORE) {
			return "item.supertechtweaks.end";
		}
		if (metadata == HAMMER) {
			return "item.supertechtweaks.hammer";
		}
		if (metadata == PLIERS) {
			return "item.supertechtweaks.pliers";
		}
		if (metadata == DRAW_PLATE) {
			return "item.supertechtweaks.drawPlate";
		}
		return "item.itemMaterialObject.ERROR_" + metadata;
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return stack.getMetadata() >= HAMMER;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return stack.getMetadata() >= HAMMER;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		if (stack.getMetadata() >= HAMMER) {
			return (Utils.getNBTInt(stack, "toolDMG") > 0);
		}
		return false;
	}
}
