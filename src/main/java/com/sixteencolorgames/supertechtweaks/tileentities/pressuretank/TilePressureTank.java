package com.sixteencolorgames.supertechtweaks.tileentities.pressuretank;

import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;
import com.sixteencolorgames.supertechtweaks.tileentities.boiler.TileBoiler;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TilePressureTank extends TileMultiBlock {
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && hasMaster()) {
			if (getMaster() instanceof TileBoiler) {
				TileBoiler master = (TileBoiler) getMaster();
				return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(master.getSteam());

			}
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && hasMaster()) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
}
