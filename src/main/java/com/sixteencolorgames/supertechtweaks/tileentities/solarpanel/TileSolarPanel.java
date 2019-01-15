package com.sixteencolorgames.supertechtweaks.tileentities.solarpanel;

import java.util.ArrayList;

import com.sixteencolorgames.supertechtweaks.tileentities.steamengine.TileSteamEngine;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileSolarPanel extends TileEntity implements IEnergyStorage, ITickable {

	protected int energy;
	protected int capacity;
	protected int maxExtract;

	public TileSolarPanel() {
		energy = 0;
		capacity = 10000;
		maxExtract = 100;
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return false;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int energySent = Math.min(energy, Math.min(this.maxExtract, maxExtract));
		if (!simulate) {
			energy -= energySent;
		}
		return energySent;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return (T) this;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public int getEnergyStored() {
		return energy;
	}

	@Override
	public int getMaxEnergyStored() {
		return capacity;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		if (this.getWorld().canBlockSeeSky(this.getPos().add(0, 1, 0))) {
			energy += Math.min(this.getWorld().getSunBrightness(0) * 5, (capacity - energy));
		}

		ArrayList<IEnergyStorage> acceptors = new ArrayList<IEnergyStorage>();

		for (EnumFacing face : EnumFacing.VALUES) {
			BlockPos offPos = getPos().offset(face);
			TileEntity tile = getWorld().getTileEntity(offPos);

			if (tile == null) {
				continue;
			} else if (tile.hasCapability(CapabilityEnergy.ENERGY, face.getOpposite())) {
				IEnergyStorage energyTile = tile.getCapability(CapabilityEnergy.ENERGY, face.getOpposite());
				if (energyTile != null && energyTile.canReceive()) {
					acceptors.add(energyTile);
				}

			}
		}

		if (acceptors.size() > 0) {
			int drain = Math.min(getEnergyStored(), maxExtract);
			int energyShare = (int) Math.ceil(drain / acceptors.size());
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
		compound.setInteger("energy", energy);
		compound.setInteger("capacity", capacity);
		compound.setInteger("maxExport", maxExtract);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		maxExtract = compound.getInteger("maxExport");
		energy = compound.getInteger("energy");
		capacity = compound.getInteger("capacity");
	}

}
