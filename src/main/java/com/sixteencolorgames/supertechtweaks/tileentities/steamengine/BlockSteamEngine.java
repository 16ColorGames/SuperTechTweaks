package com.sixteencolorgames.supertechtweaks.tileentities.steamengine;

import static mcjty.theoneprobe.api.IProbeInfo.ENDLOC;
import static mcjty.theoneprobe.api.IProbeInfo.STARTLOC;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.blocks.BlockMaster;
import com.sixteencolorgames.supertechtweaks.tileentities.boiler.TileBoiler;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSteamEngine extends Block implements ITileEntityProvider, BlockMaster {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");

	public BlockSteamEngine() {
		super(Material.ROCK);
		setUnlocalizedName(SuperTechTweaksMod.MODID + ".blocksteamengine");
		this.setRegistryName("blocksteamengine");
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN));
		setHardness(2);
	}

	/**
	 * Called serverside after this block is replaced with another in Chunk, but
	 * before the Tile Entity is updated
	 */
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileSteamEngine tile = (TileSteamEngine) worldIn.getTileEntity(pos);
		tile.resetStructure();
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileSteamEngine();
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies
	 * properties not visible in the metadata, such as fence connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity te = worldIn.getTileEntity(pos);
		EnumFacing part = EnumFacing.DOWN;
		if (te != null && te instanceof TileSteamEngine) {
			part = ((TileSteamEngine) te).getFacing();
		}
		return state.withProperty(FACING, part);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for
	 * render
	 */
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world,
			IBlockState blockState, IProbeHitData data) {
		TileEntity te = world.getTileEntity(data.getPos());
		if (te instanceof TileSteamEngine) {
			TileSteamEngine dataTileEntity = (TileSteamEngine) te;
			probeInfo.horizontal().text(TextFormatting.GREEN + STARTLOC + "Fluid (Steam):" + ENDLOC + " "
					+ dataTileEntity.steamInternal.getFluidAmount() + "/" + dataTileEntity.steamInternal.getCapacity());
		}
	}

}
