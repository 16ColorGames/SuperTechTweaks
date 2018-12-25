package com.sixteencolorgames.supertechtweaks.blocks;

import java.util.Random;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockRockSlab extends BlockSlab {

	public static final PropertyEnum<Variant> VARIANT = PropertyEnum.<Variant> create("variant", Variant.class);

	public BlockRockSlab(String name, float hardness, float blastResistance, int toolHardnessLevel, SoundType sound) {
		super(Material.ROCK);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);

		IBlockState iblockstate = this.blockState.getBaseState().withProperty(VARIANT, Variant.DEFAULT);

		if (!this.isDouble()) {
			iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
		}

		this.setDefaultState(iblockstate);
		this.useNeighborBrightness = !this.isDouble();
		this.setHardness((float) hardness); 
		this.setResistance((float) blastResistance); 
		this.setSoundType(sound);
		this.setHarvestLevel("pickaxe", toolHardnessLevel);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.useNeighborBrightness = true;
	}

	@Override
	public String getUnlocalizedName(int meta) {
		return super.getUnlocalizedName();
	}

	@Override
	public IProperty<?> getVariantProperty() {
		return VARIANT;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return Variant.DEFAULT;
	}

	@Override
	public final IBlockState getStateFromMeta(final int meta) {
		IBlockState blockstate = this.blockState.getBaseState().withProperty(VARIANT, Variant.DEFAULT);

		if (!this.isDouble()) {
			blockstate = blockstate.withProperty(HALF, ((meta & 8) != 0) ? EnumBlockHalf.TOP : EnumBlockHalf.BOTTOM);
		}

		return blockstate;
	}

	@Override
	public final int getMetaFromState(final IBlockState state) {
		int meta = 0;

		if (!this.isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP) {
			meta |= 8;
		}

		return meta;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		if (!this.isDouble()) {
			return new BlockStateContainer(this, new IProperty[] { VARIANT, HALF });
		}
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	public static class Double extends BlockRockSlab {

		public Double(String name, float hardness, float blastResistance, int toolHardnessLevel, SoundType sound) {
			super(name, hardness, blastResistance, toolHardnessLevel, sound);
		}

		@Override
		public boolean isDouble() {
			return true;
		}

	}

	public static class Half extends BlockRockSlab {

		public Half(String name, float hardness, float blastResistance, int toolHardnessLevel, SoundType sound) {
			super(name, hardness, blastResistance, toolHardnessLevel, sound);
		}

		@Override
		public boolean isDouble() {
			return false;
		}

	}

	public static enum Variant implements IStringSerializable {
		DEFAULT;

		@Override
		public String getName() {
			return "default";
		}

	}
}