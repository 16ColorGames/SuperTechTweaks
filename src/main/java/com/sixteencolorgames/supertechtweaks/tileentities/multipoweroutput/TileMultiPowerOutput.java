package com.sixteencolorgames.supertechtweaks.tileentities.multipoweroutput;

import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileMultiPowerOutput extends TileMultiBlock implements IEnergyStorage {

	protected int energy;
	protected int capacity;
	protected int maxExtract;

	public TileMultiPowerOutput() {
		energy = 0;
		capacity = 10000;
		maxExtract = 1000;
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
	public int getEnergyStored() {
		return energy;
	}

	@Override
	public int getMaxEnergyStored() {
		return capacity;
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
		maxExtract = compound.getInteger("maxExport");
		energy = compound.getInteger("energy");
		capacity = compound.getInteger("capacity");
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("energy", energy);
		compound.setInteger("capacity", capacity);
		compound.setInteger("maxRecieve", maxExtract);
		return compound;
	}

}
