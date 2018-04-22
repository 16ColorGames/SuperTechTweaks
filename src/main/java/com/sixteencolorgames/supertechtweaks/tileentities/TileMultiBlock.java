package com.sixteencolorgames.supertechtweaks.tileentities;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public abstract class TileMultiBlock extends TileEntity {
	private boolean hasMaster, isMaster;
	private int masterX, masterY, masterZ;

	public boolean checkForMaster() {
		TileEntity tile = world.getTileEntity(new BlockPos(masterX, masterY, masterZ));
		return (tile != null && (tile instanceof TileMultiBlockController));
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

	public boolean hasMaster() {
		return hasMaster;
	}

	public boolean isMaster() {
		return isMaster;
	}

	public TileMultiBlock getMaster() {
		return (TileMultiBlock) world.getTileEntity(new BlockPos(masterX, masterY, masterZ));
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		masterX = data.getInteger("masterX");
		masterY = data.getInteger("masterY");
		masterZ = data.getInteger("masterZ");
		hasMaster = data.getBoolean("hasMaster");
		isMaster = data.getBoolean("isMaster");
		if (hasMaster() && isMaster()) {
			// Any other values should ONLY BE READ BY THE MASTER
		}
	}

	public void reset() {
		masterX = 0;
		masterY = 0;
		masterZ = 0;
		hasMaster = false;
		isMaster = false;
		markDirty();
	}

	public void setHasMaster(boolean bool) {
		hasMaster = bool;
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
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		data.setInteger("masterX", masterX);
		data.setInteger("masterY", masterY);
		data.setInteger("masterZ", masterZ);
		data.setBoolean("hasMaster", hasMaster);
		data.setBoolean("isMaster", isMaster);
		if (hasMaster() && isMaster()) {
			// Any other values should ONLY BE SAVED TO THE MASTER
		}
		return data;
	}
}