package com.sixteencolorgames.supertechtweaks.tileentities.conveyor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.blocks.BlockBase;
import com.sixteencolorgames.supertechtweaks.blocks.properties.UnlistedPropertyFacing;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockConveyorBase extends BlockBase implements ITileEntityProvider {
	public static final UnlistedPropertyFacing FACING = new UnlistedPropertyFacing("facing");

	protected static final double transportation_speed = 0.06D;

	public BlockConveyorBase(String name) {
		super(Material.CLOTH, name);

		setUnlocalizedName("supertechtweaks." + name);
		this.setHardness(0.5F);
		this.useNeighborBrightness = true;
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityConveyorBase();
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).isOpaqueCube();
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isRemote) {
			if (!this.canPlaceBlockAt(worldIn, pos)) {
				this.dropBlockAsItem(worldIn, pos, state, 0);

				worldIn.setBlockToAir(pos);
			}
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (worldIn.getTileEntity(pos) instanceof TileEntityConveyorBase)
			moveEntity(entityIn, (TileEntityConveyorBase) worldIn.getTileEntity(pos));
	}

	protected boolean validEntity(Entity entity) {
		return !entity.isDead
				&& (entity.canBePushed() || entity instanceof EntityItem || entity instanceof EntityLiving
						|| entity instanceof EntityXPOrb)
				&& (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isFlying);
	}

	protected void moveEntity(Entity entity, TileEntityConveyorBase tileConveyor) {
		if (validEntity(entity)) {
			EnumFacing facing = tileConveyor.getFacing();

			entity.isAirBorne = true;

			double movementX = transportation_speed * facing.getFrontOffsetX();
			double movementZ = transportation_speed * facing.getFrontOffsetZ();
			if (facing.getFrontOffsetX() == 0) {
				double centerX = tileConveyor.getPos().getX() + .5;
				double diff = entity.posX - centerX;
				if (diff > .05)
					movementX = -.04;
				else if (diff < -.05)
					movementX = .04;
			}
			if (facing.getFrontOffsetZ() == 0) {
				double centerZ = tileConveyor.getPos().getZ() + .5;
				double diff = entity.posZ - centerZ;
				if (diff > .05)
					movementZ = -.04;
				else if (diff < -.05)
					movementZ = .04;
			}

			if (tileConveyor instanceof TileEntityConveyor) {
				if (((TileEntityConveyor) tileConveyor).isSlopeUp()
						|| ((TileEntityConveyor) tileConveyor).isSlopeDown()) {
					movementX *= .5;
					movementZ *= .5;
				}
			}

			entity.motionX = movementX * 1.5;
			entity.motionZ = movementZ * 1.5;

			if (entity instanceof EntityItem && entity.world.isRemote)
				entity.getEntityData().setBoolean("onBelt", true);

			if (entity instanceof EntityItem && entity.ticksExisted % 100 == 0) {
				NBTTagCompound tag = new NBTTagCompound();
				((EntityItem) entity).writeEntityToNBT(tag);
				tag.setShort("Age", (short) 2400);
				((EntityItem) entity).readEntityFromNBT(tag);
			}

		}
	}

	@Override
	@Nonnull
	protected BlockStateContainer createBlockState() {
		IProperty[] listedProperties = new IProperty[] {};
		IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { FACING };

		return new ExtendedBlockState(this, listedProperties, unlistedProperties);
	}

	@Override
	@Nonnull
	public IBlockState getExtendedState(@Nonnull IBlockState state, IBlockAccess world, BlockPos pos) {
		if (state instanceof IExtendedBlockState) {
			IExtendedBlockState extendedState = (IExtendedBlockState) state;

			TileEntity tile = world.getTileEntity(pos);

			if (tile != null && tile instanceof TileEntityConveyorBase) {
				TileEntityConveyorBase tileConveyor = (TileEntityConveyorBase) tile;

				return tileConveyor.writeExtendedBlockState(extendedState);
			}
		}

		return super.getExtendedState(state, world, pos);
	}

	@Override
	@Nonnull
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, @Nonnull IBlockAccess blockAccess,
			@Nonnull BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	@Nonnull
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if (worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TileEntityConveyorBase) {
			TileEntityConveyorBase tileICB = (TileEntityConveyorBase) worldIn.getTileEntity(pos);

			if (tileICB != null)
				tileICB.setFacing(placer.getHorizontalFacing());
		}
	}
}