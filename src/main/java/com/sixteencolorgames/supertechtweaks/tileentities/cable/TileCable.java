package com.sixteencolorgames.supertechtweaks.tileentities.cable;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.enums.Material;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileCable extends TileEntity implements ITickable, IEnergyStorage {
	public int power = 0;
	private Material mat;

	public TileCable() {
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return true;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if (!canExtract()) {
			return 0;
		}

		int energyExtracted = Math.min(getEnergyStored(), Math.min(mat.getTransferRate(), maxExtract));
		if (!simulate) {
			power -= energyExtracted;
		}
		return energyExtracted;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return (T) this;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public int getEnergyStored() {
		return power;
	}

	public Material getMaterial() {
		return mat;
	}

	@Override
	public int getMaxEnergyStored() {
		return mat.getTransferRate() * 6;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("TileCable")) {
			power = compound.getCompoundTag("TileCable").getInteger("power");
			mat = Material.REGISTRY
					.getValue(new ResourceLocation(compound.getCompoundTag("TileCable").getString("material")));
		}
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if (!canReceive()) {
			return 0;
		}

		int energyReceived = Math.min(getMaxEnergyStored() - getEnergyStored(),
				Math.min(mat.getTransferRate(), maxReceive));
		if (!simulate) {
			power += energyReceived;
		}
		return energyReceived;
	}

	public void setMaterial(Material m) {
		mat = m;
	}

	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}

		// if (this.cableType == null) {
		// this.cableType = getCableType();
		// this.transferRate = this.cableType.transferRate *
		// RebornCoreConfig.euPerFU;
		// }

		ArrayList<IEnergyStorage> acceptors = new ArrayList<IEnergyStorage>();

		for (EnumFacing face : EnumFacing.VALUES) {
			BlockPos offPos = getPos().offset(face);
			TileEntity tile = getWorld().getTileEntity(offPos);

			if (tile == null) {
				continue;
			} else if (tile instanceof TileCable) {
				TileCable cable = (TileCable) tile;
				if (power > cable.power) {
					acceptors.add(cable);
				}
			} else if (tile.hasCapability(CapabilityEnergy.ENERGY, face.getOpposite())) {
				IEnergyStorage energyTile = tile.getCapability(CapabilityEnergy.ENERGY, face.getOpposite());
				if (energyTile != null && energyTile.canReceive()) {
					acceptors.add(energyTile);
				}

			}
		}

		if (acceptors.size() > 0) {
			int drain = Math.min(power, mat.getTransferRate());
			int energyShare = (int) Math.ceil(((double) drain) / ((double) acceptors.size()));
			int remainingEnergy = drain;

			if (energyShare > 0) {
				for (IEnergyStorage tile : acceptors) {
					// Push energy to connected tile acceptors
					int move = tile.receiveEnergy(Math.min(energyShare, remainingEnergy), false);
					if (move > 0) {
						remainingEnergy -= move;
					}
				}
				extractEnergy(drain - remainingEnergy, false);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("power", getEnergyStored());
		data.setString("material", mat.getRegistryName().toString());
		compound.setTag("TileCable", data);

		return compound;
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
}
