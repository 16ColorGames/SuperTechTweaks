package com.sixteencolorgames.supertechtweaks.tileentities.solarpanel;

import static mcjty.theoneprobe.api.IProbeInfo.ENDLOC;
import static mcjty.theoneprobe.api.IProbeInfo.STARTLOC;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.compat.top.TOPInfoProvider;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSolarPanel extends Block implements ITileEntityProvider, TOPInfoProvider {

	public BlockSolarPanel() {
		super(Material.ROCK);
		setUnlocalizedName(SuperTechTweaksMod.MODID + ".blockSolar");
		this.setRegistryName("blocksolar");
		setHardness(2);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return new TileSolarPanel();
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
		if (te instanceof TileSolarPanel) {
			TileSolarPanel dataTileEntity = (TileSolarPanel) te;
			probeInfo.horizontal().text(TextFormatting.GREEN + STARTLOC + "Stored Power:" + ENDLOC + " "
					+ dataTileEntity.getEnergyStored() + "/" + dataTileEntity.getMaxEnergyStored());
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = state.getActualState(source, pos);
		float minSize = 0.33F;
		float maxSize = 0.66F;
		float minX = 0;
		float minY = 0;
		float minZ = 0;
		float maxX = 1;
		float maxY = 0.25f;
		float maxZ = 1;
		return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
	}
}
