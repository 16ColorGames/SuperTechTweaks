package com.sixteencolorgames.supertechtweaks.tileentities;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public abstract class TileMultiBlock extends TileEntity {
	private boolean isMaster;
	private int masterX, masterY, masterZ;

	public boolean checkForMaster() {
		TileEntity tile = world.getTileEntity(new BlockPos(masterX, masterY, masterZ));
		return (tile != null && (tile instanceof TileMultiBlockController));
	}

	public TileMultiBlock getMaster() {
		return (TileMultiBlock) world.getTileEntity(new BlockPos(masterX, masterY, masterZ));
	}

	public int getMasterX() {
		return masterX;
	}

	public int getMasterY() {
		return masterY;
	}

	public int getMasterZ() {
		return masterZ;
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 3, getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	public boolean hasMaster() {
		if (masterX == 0 && masterY == 0 && masterZ == 0) {
			return false;
		}
		return true;
	}

	public boolean isMaster() {
		return isMaster;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		masterX = data.getInteger("masterX");
		masterY = data.getInteger("masterY");
		masterZ = data.getInteger("masterZ");
		isMaster = data.getBoolean("isMaster");
	}

	public void reset() {
		setMasterCoords(0, 0, 0);
		isMaster = false;
		markDirty();
	}

	public void setIsMaster(boolean bool) {
		isMaster = bool;
		markDirty();
	}

	public void setMasterCoords(int x, int y, int z) {
		masterX = x;
		masterY = y;
		masterZ = z;
		markDirty();
		IBlockState iblockstate = world.getBlockState(pos);
		final int FLAGS = 3; // I'm not sure what these flags do, exactly.
		world.notifyBlockUpdate(pos, iblockstate, iblockstate, FLAGS);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setInteger("masterX", masterX);
		data.setInteger("masterY", masterY);
		data.setInteger("masterZ", masterZ);
		data.setBoolean("isMaster", isMaster);
		return data;
	}
}