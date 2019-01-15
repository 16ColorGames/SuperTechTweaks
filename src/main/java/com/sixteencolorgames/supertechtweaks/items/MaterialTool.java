package com.sixteencolorgames.supertechtweaks.items;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.Utils;
import com.sixteencolorgames.supertechtweaks.enums.Material;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
import net.minecraft.item.ItemTool;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MaterialTool extends ItemTool {

	public static final int HAMMER = 0;
	public static final int PLIERS = 1;
	public static final int DRAW_PLATE = 2;
	public static final int PICKAXE = 3;
	public static final int SHOVEL = 4;
	public static final int AXE = 5;

	Material material;
	int toolType;

	public MaterialTool(Material materialIn, int toolType) {
		// super(materialIn.getToolMaterial(toolType), new HashSet<>());
		super(ToolMaterial.IRON, new HashSet<>());
		material = materialIn;
		this.toolType = toolType;
		setUnlocalizedName(SuperTechTweaksMod.MODID + ".tool" + materialIn.getName() + toolType);
		setRegistryName("tool" + materialIn.getName() + toolType);
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (material.getToolLevel() >= 0) {
			tooltip.add("Tool Level: " + material.getToolLevel());
		}
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment == Enchantments.EFFICIENCY || enchantment == Enchantments.UNBREAKING
				|| enchantment == Enchantments.MENDING;
	}

	private void damageTool(ItemStack stack, int amount, Random rand, @Nullable EntityLivingBase entityLiving) {
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

		if (entityLiving instanceof EntityPlayerMP) {
			CriteriaTriggers.ITEM_DURABILITY_CHANGED.trigger((EntityPlayerMP) entityLiving, stack, curDamage);
		}

		if (curDamage >= material.getMaxToolDamage(stack.getMetadata())) {
			if (entityLiving != null) {
				entityLiving.renderBrokenItemStack(stack);
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
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		for (String type : getToolClasses(stack)) {
			if (state.getBlock().isToolEffective(type, state)) {
				return (float) (Math.log(material.getShear()) + Math.log(material.getBulk()));
			}
		}
		return 1.0F;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return Utils.getNBTInt(stack, "toolDMG") / (double) material.getMaxToolDamage(stack.getMetadata());
	}

	/**
	 * Queries the harvest level of this item stack for the specified tool class,
	 * Returns -1 if this tool is not of the specified type
	 *
	 * @param stack      This item stack instance
	 * @param toolClass  Tool Class
	 * @param player     The player trying to harvest the given blockstate
	 * @param blockState The block to harvest
	 * @return Harvest level, or -1 if not the specified tool type.
	 */
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player,
			@Nullable IBlockState blockState) {
		int ret = -1;
		switch (toolType) {
		case PICKAXE:
			ret = toolClass.equalsIgnoreCase("pickaxe") ? material.getToolLevel() : -1;
			break;
		case SHOVEL:
			ret = toolClass.equalsIgnoreCase("shovel") ? material.getToolLevel() : -1;
			break;
		case AXE:
			ret = toolClass.equalsIgnoreCase("axe") ? material.getToolLevel() : -1;
			break;
		case HAMMER:
			ret = toolClass.equalsIgnoreCase("hammer") ? material.getToolLevel() : -1;
			break;
		}
		return ret;
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
	public boolean hasContainerItem(ItemStack stack) {
		return stack.getMetadata() >= HAMMER;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return stack.getMetadata() >= HAMMER;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the
	 * "Use Item" statistic.
	 */
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0D && stack.getMetadata() >= HAMMER) {
			damageTool(stack, 1, worldIn.rand, entityLiving);
		}

		return true;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		if (stack.getMetadata() >= HAMMER) {
			return (Utils.getNBTInt(stack, "toolDMG") > 0);
		}
		return false;
	}

	@Override
	public java.util.Set<String> getToolClasses(ItemStack stack) {
		HashSet<String> ret = new HashSet<String>();
		switch (toolType) {
		case PICKAXE:
			ret.add("pickaxe");
			break;
		case SHOVEL:
			ret.add("shovel");
			break;
		case AXE:
			ret.add("axe");
			break;
		case HAMMER:
			ret.add("hammer");
			break;
		}
		return ret;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		switch (toolType) {
		case HAMMER:
			return "item.supertechtweaks.hammer";
		case PLIERS:
			return "item.supertechtweaks.pliers";
		case DRAW_PLATE:
			return "item.supertechtweaks.drawPlate";
		case PICKAXE:
			return "item.supertechtweaks.pickaxe";
		case AXE:
			return "item.supertechtweaks.axe";
		case SHOVEL:
			return "item.supertechtweaks.shovel";

		}
		return "item.itemMaterialTool.ERROR_" + toolType;
	}

	public void registerItemModel() {
		SuperTechTweaksMod.proxy.registerItemRenderer(this, 0, "itemMaterialTool");
		ModelLoader.setCustomModelResourceLocation(this, 0,
				new ModelResourceLocation("supertechtweaks:itemMaterialTool", "inventory"));
	}
}
