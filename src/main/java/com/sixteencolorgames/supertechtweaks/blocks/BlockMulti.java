package com.sixteencolorgames.supertechtweaks.blocks;

import static mcjty.theoneprobe.api.IProbeInfo.ENDLOC;
import static mcjty.theoneprobe.api.IProbeInfo.STARTLOC;

import com.sixteencolorgames.supertechtweaks.compat.top.TOPInfoProvider;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlockController;
import com.sixteencolorgames.supertechtweaks.tileentities.boiler.TileBoiler;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public abstract class BlockMulti extends Block implements ITileEntityProvider, TOPInfoProvider {

	public BlockMulti(Material materialIn) {
		super(materialIn);
	}

	/**
	 * Called serverside after this block is replaced with another in Chunk, but
	 * before the Tile Entity is updated
	 */
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileMultiBlock tile = (TileMultiBlock) worldIn.getTileEntity(pos);
		TileMultiBlockController master = (TileMultiBlockController) worldIn
				.getTileEntity(new BlockPos(tile.getMasterX(), tile.getMasterY(), tile.getMasterZ()));
		if (master != null) {
			master.resetStructure();
		}
		super.breakBlock(worldIn, pos, state);
	}
	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world,
			IBlockState blockState, IProbeHitData data) {
		TileEntity te = world.getTileEntity(data.getPos());
		if (te instanceof TileMultiBlock) {
			TileMultiBlock tmb = (TileMultiBlock) te;
			if(tmb.isMaster()) {
				probeInfo.horizontal().text(TextFormatting.GREEN + STARTLOC + "Multiblock Master" + ENDLOC + " ");
			}else if(tmb.hasMaster()) {
				probeInfo.horizontal().text(TextFormatting.GREEN + STARTLOC + "Multiblock Formed" + ENDLOC + " ");
			}else {
				probeInfo.horizontal().text(TextFormatting.GREEN + STARTLOC + "Multiblock Not Formed" + ENDLOC + " ");
			}
		}
	}
}
