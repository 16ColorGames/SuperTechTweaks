package com.sixteencolorgames.supertechtweaks.tileentities.multipowerinput;

import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileMultiPowerInput extends TileMultiBlock {

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY && hasMaster()) {
			TileMultiBlock master = getMaster();
			return (T) master;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY && hasMaster()) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		return compound;
	}

}
