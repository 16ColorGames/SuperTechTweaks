package com.sixteencolorgames.supertechtweaks.tileentities.cable;

import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.energy.CapabilityEnergy;

//TODO handle material differences
public class BlockCable extends BlockContainer {
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");
	// public static final IProperty<ResourceLocation> TYPE = ;

	public BlockCable() {
		super(Material.ROCK);
		setHardness(1F);
		setResistance(8F);
		setRegistryName("blockCable");
		setUnlocalizedName(SuperTechTweaksMod.MODID + ".powercable");
		setDefaultState(getDefaultState().withProperty(EAST, false).withProperty(WEST, false).withProperty(NORTH, false)
				.withProperty(SOUTH, false).withProperty(UP, false).withProperty(DOWN, false)
		// .withProperty(TYPE, new ResourceLocation("supertechtweaks:Copper"))
		);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, EAST, WEST, NORTH, SOUTH, UP,
				DOWN/* , TYPE */);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileCable();
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		IBlockState actualState = state;
		for (EnumFacing facing : EnumFacing.values()) {
			TileEntity tileEntity = getTileEntitySafely(worldIn, pos.offset(facing));
			if (tileEntity != null) {
				actualState = actualState.withProperty(getProperty(facing),
						tileEntity.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite()));
			}
		}
		return actualState;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = state.getActualState(source, pos);
		float minSize = 0.3125F;
		float maxSize = 0.6875F;
		float minX = state.getValue(WEST) ? 0.0F : minSize;
		float minY = state.getValue(DOWN) ? 0.0F : minSize;
		float minZ = state.getValue(NORTH) ? 0.0F : minSize;
		float maxX = state.getValue(EAST) ? 1.0F : maxSize;
		float maxY = state.getValue(UP) ? 1.0F : maxSize;
		float maxZ = state.getValue(SOUTH) ? 1.0F : maxSize;
		return new AxisAlignedBB((double) minX, (double) minY, (double) minZ, (double) maxX, (double) maxY,
				(double) maxZ);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	public IProperty<Boolean> getProperty(EnumFacing facing) {
		switch (facing) {
		case EAST:
			return EAST;
		case WEST:
			return WEST;
		case NORTH:
			return NORTH;
		case SOUTH:
			return SOUTH;
		case UP:
			return UP;
		case DOWN:
			return DOWN;
		default:
			return EAST;
		}
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}

	// see for more info
	// https://www.reddit.com/r/feedthebeast/comments/5mxwq9/psa_mod_devs_do_you_call_worldgettileentity_from/
	public TileEntity getTileEntitySafely(IBlockAccess blockAccess, BlockPos pos) {
		if (blockAccess instanceof ChunkCache) {
			return ((ChunkCache) blockAccess).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
		} else {
			return blockAccess.getTileEntity(pos);
		}
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	// @Override
	// public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
	// for (EnumCableType cableType : EnumCableType.values()) {
	// list.add(new ItemStack(this, 1, cableType.ordinal()));
	// }
	// }
}
