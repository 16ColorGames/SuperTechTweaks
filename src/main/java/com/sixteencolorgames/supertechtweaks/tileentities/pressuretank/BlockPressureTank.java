package com.sixteencolorgames.supertechtweaks.tileentities.pressuretank;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.blocks.BlockMulti;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPressureTank extends BlockMulti {

	public static final PropertyBool PART = PropertyBool.create("part");

	public BlockPressureTank() {
		super(Material.ROCK);
		setUnlocalizedName(SuperTechTweaksMod.MODID + ".pressuretank");
		setRegistryName("blockpressuretank");
		setDefaultState(blockState.getBaseState().withProperty(PART, false));

	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { PART });
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TilePressureTank();
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

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks
	 * for render
	 */
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
}
