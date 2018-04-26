package com.sixteencolorgames.supertechtweaks.tileentities.multipowerinput;

import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.registry.GameRegistry;

//TODO limit power transfer by this blocks material
public class TileMultiPowerInput extends TileMultiBlock {
	private Material material;

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY && hasMaster()) {
			TileMultiBlock master = getMaster();
			return (T) master;
		}
		return super.getCapability(capability, facing);
	}

	public Material getMaterial() {
		return material;
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
		setMaterial(GameRegistry.findRegistry(Material.class)
				.getValue(new ResourceLocation(compound.getString("sttMaterial"))));
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
