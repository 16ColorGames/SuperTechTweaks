package com.sixteencolorgames.supertechtweaks.tileentities.extractor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.blocks.properties.UnlistedPropertyBoolean;
import com.sixteencolorgames.supertechtweaks.tileentities.conveyor.BlockConveyorBase;
import com.sixteencolorgames.supertechtweaks.tileentities.conveyor.TileEntityConveyor;
import com.sixteencolorgames.supertechtweaks.tileentities.conveyor.TileEntityConveyorBase;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockExtractor extends BlockConveyorBase implements ITileEntityProvider {

	public BlockExtractor() {
		super("extractor");
	}

	private static final AxisAlignedBB BOUNDS_FLAT = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.15, 1.0);

	private static final AxisAlignedBB COLLISION = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.125, 1.0);

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityExtractor();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDS_FLAT;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		AxisAlignedBB box = getBoundingBox(blockState, worldIn, pos);

		if (box.equals(BOUNDS_FLAT))
			return COLLISION;

		return box;
	}

	@Override
	@Nonnull
	protected BlockStateContainer createBlockState() {
		IProperty[] listedProperties = new IProperty[] {};
		IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { FACING };

		return new ExtendedBlockState(this, listedProperties, unlistedProperties);
	}

	protected TileEntityExtractor getTileEntity(World worldIn, BlockPos pos) {
		if (worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TileEntityExtractor)
			return (TileEntityExtractor) worldIn.getTileEntity(pos);

		return null;
	}
}