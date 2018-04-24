package com.sixteencolorgames.supertechtweaks.tileentities.cable;

import java.util.ArrayList;

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
import net.minecraftforge.fml.common.registry.GameRegistry;

//TODO handle material differences
public class TileCable extends TileEntity implements ITickable, IEnergyStorage {
	public int power = 0;
	private Material mat;

	public TileCable() {
		mat = GameRegistry.findRegistry(Material.class).getValue(new ResourceLocation("supertechtweaks:Copper"));
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

		int energyExtracted = Math.min(getEnergyStored(), Math.min(getTransferRate(), maxExtract));
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

	@Override
	public int getMaxEnergyStored() {
		return getTransferRate() * 6;
	}

	public int getTransferRate() {
		return (int) Math.floor((1 / mat.getResistance()) * mat.getConductivity() * 32);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		// Prepare a packet for syncing our TE to the client. Since we only have
		// to sync the stack
		// and that's all we have we just write our entire NBT here. If you have
		// a complex
		// tile entity that doesn't need to have all information on the client
		// you can write
		// a more optimal NBT here.
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		// Here we get the packet from the server and read it into our client
		// side tile entity
		readFromNBT(packet.getNbtCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("TileCable")) {
			power = compound.getCompoundTag("TileCable").getInteger("power");
			mat = GameRegistry.findRegistry(Material.class)
					.getValue(new ResourceLocation(compound.getCompoundTag("TileCable").getString("material")));
		}
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if (!canReceive()) {
			return 0;
		}

		int energyReceived = Math.min(getMaxEnergyStored() - getEnergyStored(),
				Math.min(getTransferRate(), maxReceive));
		if (!simulate) {
			power += energyReceived;
		}
		return energyReceived;
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
			int drain = Math.min(power, getTransferRate());
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
		if (power > 0) {
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("power", getEnergyStored());
			data.setString("material", mat.getRegistryName().toString());
			compound.setTag("TileCable", data);
		}
		return compound;
	}

}
