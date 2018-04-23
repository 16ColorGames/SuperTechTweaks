package com.sixteencolorgames.supertechtweaks.blocks;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiWall;
import com.sixteencolorgames.supertechtweaks.tileentities.basicresearcher.TileBasicResearcher;
import com.sixteencolorgames.supertechtweaks.tileentities.researchselector.TileResearchSelector;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMultiWall extends BlockMulti {

	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");
	public static final PropertyBool PART = PropertyBool.create("part");

	public BlockMultiWall() {
		super(Material.ROCK);
		setUnlocalizedName(SuperTechTweaksMod.MODID + ".multiwall");
		setRegistryName("blockmultiwall");
		setDefaultState(blockState.getBaseState().withProperty(NORTH, false).withProperty(EAST, false)
				.withProperty(SOUTH, false).withProperty(WEST, false).withProperty(UP, false).withProperty(DOWN, false)
				.withProperty(PART, false));

	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { NORTH, EAST, WEST, SOUTH, UP, DOWN, PART });
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileMultiWall();
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
		return state.withProperty(NORTH, isMulti(worldIn, pos, EnumFacing.NORTH))
				.withProperty(EAST, isMulti(worldIn, pos, EnumFacing.EAST))
				.withProperty(SOUTH, isMulti(worldIn, pos, EnumFacing.SOUTH))
				.withProperty(WEST, isMulti(worldIn, pos, EnumFacing.WEST))
				.withProperty(UP, isMulti(worldIn, pos, EnumFacing.UP))
				.withProperty(DOWN, isMulti(worldIn, pos, EnumFacing.DOWN)).withProperty(PART, part);
	}
	// see for more info
	// https://www.reddit.com/r/feedthebeast/comments/5mxwq9/psa_mod_devs_do_you_call_worldgettileentity_from/

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	public TileEntity getTileEntitySafely(IBlockAccess blockAccess, BlockPos pos) {
		if (blockAccess instanceof ChunkCache) {
			return ((ChunkCache) blockAccess).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
		} else {
			return blockAccess.getTileEntity(pos);
		}
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	private boolean isMulti(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		TileEntity tileEntity = getTileEntitySafely(world, pos.offset(facing));
		if (tileEntity != null) {
			return tileEntity instanceof TileMultiBlock;
		}
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking()) {
			return false;
		} else {
			if (!worldIn.isRemote) {
				if (worldIn.getTileEntity(pos) instanceof TileMultiWall) {
					TileMultiWall te = (TileMultiWall) worldIn.getTileEntity(pos);
					if (te.hasMaster()) {
						TileBasicResearcher master = (TileBasicResearcher) te.getMaster();
						TileResearchSelector selector = (TileResearchSelector) worldIn
								.getTileEntity(master.getSelectorpos());
						playerIn.sendMessage(new TextComponentString("Selected: " + selector.getSelected().toString()));
					}
				}
			}
		}
		return true;

	}
}
