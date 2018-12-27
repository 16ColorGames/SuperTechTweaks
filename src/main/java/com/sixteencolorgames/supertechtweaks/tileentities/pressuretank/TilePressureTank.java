package com.sixteencolorgames.supertechtweaks.tileentities.pressuretank;

import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;
import com.sixteencolorgames.supertechtweaks.tileentities.boiler.TileBoiler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TilePressureTank extends TileMultiBlock {

	private Material material;

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

	public Material getMaterial() {
		return material;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && hasMaster()) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		setMaterial(Material.REGISTRY.getValue(new ResourceLocation(compound.getString("sttMaterial"))));
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("sttMaterial", material.getRegistryName().toString());
		return compound;
	}
}
