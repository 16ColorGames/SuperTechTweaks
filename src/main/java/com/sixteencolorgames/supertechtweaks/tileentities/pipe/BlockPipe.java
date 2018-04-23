package com.sixteencolorgames.supertechtweaks.tileentities.pipe;

import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.blocks.properties.UnlistedPropertyBlockAvailable;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//TODO handle material differences
public class BlockPipe extends BlockContainer {

	public static final UnlistedPropertyBlockAvailable NORTH = new UnlistedPropertyBlockAvailable("north");
	public static final UnlistedPropertyBlockAvailable SOUTH = new UnlistedPropertyBlockAvailable("south");
	public static final UnlistedPropertyBlockAvailable WEST = new UnlistedPropertyBlockAvailable("west");
	public static final UnlistedPropertyBlockAvailable EAST = new UnlistedPropertyBlockAvailable("east");
	public static final UnlistedPropertyBlockAvailable UP = new UnlistedPropertyBlockAvailable("up");
	public static final UnlistedPropertyBlockAvailable DOWN = new UnlistedPropertyBlockAvailable("down");

	public BlockPipe() {
		super(Material.ROCK);
		setUnlocalizedName(SuperTechTweaksMod.MODID + ".blockpipe");
		setRegistryName("blockpipe");
	}

	private boolean canConnect(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		TileEntity tileEntity = getTileEntitySafely(world, pos.offset(facing));
		if (tileEntity != null) {
			return tileEntity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
		}
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		IProperty[] listedProperties = new IProperty[0]; // no listed properties
		IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { NORTH, SOUTH, WEST, EAST, UP, DOWN };
		return new ExtendedBlockState(this, listedProperties, unlistedProperties);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TilePipe();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = state.getActualState(source, pos);
		float minSize = 0.2F;
		float maxSize = 0.9F;
		float minX = canConnect(source, pos, EnumFacing.WEST) ? 0.0F : minSize;
		float minY = canConnect(source, pos, EnumFacing.DOWN) ? 0.0F : minSize;
		float minZ = canConnect(source, pos, EnumFacing.NORTH) ? 0.0F : minSize;
		float maxX = canConnect(source, pos, EnumFacing.EAST) ? 1.0F : maxSize;
		float maxY = canConnect(source, pos, EnumFacing.UP) ? 1.0F : maxSize;
		float maxZ = canConnect(source, pos, EnumFacing.SOUTH) ? 1.0F : maxSize;
		return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;

		boolean north = canConnect(world, pos, EnumFacing.NORTH);
		boolean south = canConnect(world, pos, EnumFacing.SOUTH);
		boolean west = canConnect(world, pos, EnumFacing.WEST);
		boolean east = canConnect(world, pos, EnumFacing.EAST);
		boolean up = canConnect(world, pos, EnumFacing.UP);
		boolean down = canConnect(world, pos, EnumFacing.DOWN);

		return extendedBlockState.withProperty(NORTH, north).withProperty(SOUTH, south).withProperty(WEST, west)
				.withProperty(EAST, east).withProperty(UP, up).withProperty(DOWN, down);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
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

	@SideOnly(Side.CLIENT)
	public void initItemModel() {
		// For our item model we want to use a normal json model. This has to be
		// called in
		// ClientProxy.postInit (not preInit) so that's why it is a separate
		// method.
		Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation(SuperTechTweaksMod.MODID, "blockpipe"));
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(getRegistryName(), "inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, DEFAULT_ITEM_SUBTYPE,
				itemModelResourceLocation);
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		// To make sure that our baked model model is chosen for all states we
		// use this custom state mapper:
		StateMapperBase ignoreState = new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
				return BakedModelPipe.BAKED_MODEL;
			}
		};
		ModelLoader.setCustomStateMapper(this, ignoreState);
	}

	@Override
	public boolean isBlockNormalCube(IBlockState blockState) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState blockState) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking()) {
			return false;
		} else {
			if (!worldIn.isRemote) {
				if (worldIn.getTileEntity(pos) instanceof TilePipe) {
					TilePipe te = (TilePipe) worldIn.getTileEntity(pos);
					playerIn.sendMessage(new TextComponentString(
							"Transfer Rate: " + te.getTransferRate() + ", current fill: " + te.tank.getFluidAmount()));
				}
			}
		}
		return true;

	}

	// @Override
	// public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
	// for (EnumCableType cableType : EnumCableType.values()) {
	// list.add(new ItemStack(this, 1, cableType.ordinal()));
	// }
	// }
}
