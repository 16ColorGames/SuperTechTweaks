package com.sixteencolorgames.supertechtweaks.tileentities.boiler;

import java.util.Random;

import com.sixteencolorgames.supertechtweaks.ModRegistry;
import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.blocks.BlockContainerBase;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBoiler extends BlockContainerBase implements ITileEntityProvider {
	public static final PropertyBool PART = PropertyBool.create("part");

	public static void setState(boolean active, World worldIn, BlockPos pos) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (active) {
			// worldIn.setBlockState(pos,
			// BlocksCore.lit_extruder.getDefaultState().withProperty(FACING,
			// iblockstate.getValue(FACING)), 3);
			// worldIn.setBlockState(pos,
			// BlocksCore.lit_extruder.getDefaultState().withProperty(FACING,
			// iblockstate.getValue(FACING)), 3);
		} else {
			worldIn.setBlockState(pos,
					ModRegistry.blockBoiler.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
		}

		if (tileentity != null) {
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}

	boolean isBurning = false;

	public BlockBoiler() {
		super(net.minecraft.block.material.Material.ROCK, "blockboiler");
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(PART, false));
	}

	/**
	 * Called serverside after this block is replaced with another in Chunk, but
	 * before the Tile Entity is updated
	 */
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileBoiler tile = (TileBoiler) worldIn.getTileEntity(pos);
		tile.resetStructure();
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, PART });
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileBoiler();
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity te = worldIn.getTileEntity(pos);
		boolean part = false;
		if (te != null && te instanceof TileMultiBlock) {
			part = ((TileMultiBlock) te).hasMaster();
		}
		return state.withProperty(PART, part);
	}

	@Override
	public Item getItemDropped(IBlockState meta, Random random, int fortune) {
		return null;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	/**
	 * Called when the block is right clicked by a player.
	 */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		// Only execute on the server
		if (world.isRemote) {
			return true;
		}
		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof TileBoiler)) {
			return false;
		}
		player.openGui(SuperTechTweaksMod.instance, ModRegistry.BOILER, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow
	 * post-place logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		TileBoiler cable = (TileBoiler) worldIn.getTileEntity(pos);
		if (!stack.hasTagCompound()) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("sttMaterial", "supertechtweaks:iron");
			stack.setTagCompound(tag);
		}
		String mat = stack.getTagCompound().getString("sttMaterial");
		cable.setMaterial(Material.REGISTRY.getValue(new ResourceLocation(mat)));
		cable.markDirty();
		// TODO figure out why adding facing breaks stuff
		// worldIn.setBlockState(pos, state.withProperty(FACING,
		// placer.getHorizontalFacing().getOpposite()), 2);
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player,
			boolean willHarvest) {
		if (!worldIn.isRemote) {
			if (player.isCreative()) {// If the player is in creative...
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
				return true;
			}
			ItemStack drop = new ItemStack(this);
			TileBoiler cable = (TileBoiler) worldIn.getTileEntity(pos);
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("sttMaterial", cable.getMaterial().getRegistryName().toString());
			drop.setTagCompound(tag);

			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
		return true;
	}

	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			IBlockState iblockstate = worldIn.getBlockState(pos.north());
			IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
			IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
			IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
			EnumFacing enumfacing = state.getValue(FACING);

			if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
				enumfacing = EnumFacing.SOUTH;
			} else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
				enumfacing = EnumFacing.NORTH;
			} else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
				enumfacing = EnumFacing.EAST;
			} else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
				enumfacing = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}
}
