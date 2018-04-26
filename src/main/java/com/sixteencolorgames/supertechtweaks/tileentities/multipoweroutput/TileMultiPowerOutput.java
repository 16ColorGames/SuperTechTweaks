package com.sixteencolorgames.supertechtweaks.tileentities.multipoweroutput;

import java.util.ArrayList;

import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;
import com.sixteencolorgames.supertechtweaks.tileentities.steamengine.TileSteamEngine;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileMultiPowerOutput extends TileMultiBlock implements ITickable {
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

	private int getTransferRate() {
		return material.getTransferRate();
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
	public void update() {
		if (world.isRemote) {
			return;
		}
		if (hasMaster()) {
			if (getMaster() instanceof TileSteamEngine) {
				TileSteamEngine master = (TileSteamEngine) getMaster();

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
					int drain = Math.min(master.getEnergyStored(), getTransferRate());
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
						master.extractEnergy(drain - remainingEnergy, false);
					}
				}
			}
		}

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("sttMaterial", material.getRegistryName().toString());
		return compound;
	}

}
