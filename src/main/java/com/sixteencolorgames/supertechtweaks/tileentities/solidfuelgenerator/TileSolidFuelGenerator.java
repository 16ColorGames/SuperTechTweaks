package com.sixteencolorgames.supertechtweaks.tileentities.solidfuelgenerator;

import javax.annotation.Nonnull;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileSolidFuelGenerator extends TileEntity implements IEnergyStorage, ITickable {
	public static final int SIZE = 1;
	/**
	 * how many ticks are left for this burn
	 */
	public int burnTime = 0;
	/**
	 * how many ticks this burn started with
	 */
	public int totalBurnTime;

	protected int energy;
	protected int capacity;
	protected int maxExtract;

	// This item handler will hold our nine inventory slots
	private ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE) {
		@Override
		protected void onContentsChanged(int slot) {
			// We need to tell the tile entity that something has changed so
			// that the chest contents is persisted
			TileSolidFuelGenerator.this.markDirty();
		}
	};

	public TileSolidFuelGenerator() {
		energy = 0;
		capacity = 10000;
		maxExtract = 128;
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		// If we are too far away from this tile entity you cannot use it
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	@Override
	public boolean canReceive() {
		return false;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
		if (!simulate)
			energy -= energyExtracted;
		return energyExtracted;
	}

	public int getBurnTime(@Nonnull ItemStack item) {
		return (int) Math.round(TileEntityFurnace.getItemBurnTime(item) * getBurnTimeMultiplier());
	}

	private double getBurnTimeMultiplier() {
		return .25;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
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

	private int getPowerGenPerTick() {
		return 40;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("items")) {
			itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
		}
		burnTime = compound.getInteger("burnTime");
		energy = compound.getInteger("energy");
		capacity = compound.getInteger("capacity");
		totalBurnTime = compound.getInteger("totalBurnTime");
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		// return 0 since this tile shouldn't be able to receive energy
		return 0;
	}

	private void setEnergyStored(int newAmount) {
		int energyReceived = Math.min(capacity, newAmount);

		energy = energyReceived;
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			boolean needsUpdate = false;
			boolean sendBurnTimePacket = false;

			if (burnTime > 0) {
				if (getEnergyStored() < getMaxEnergyStored()) {
					setEnergyStored(getEnergyStored() + getPowerGenPerTick());
				}
				burnTime--;
			}

			if (burnTime <= 0 && getEnergyStored() < getMaxEnergyStored()) {
				final ItemStack fuelStack = this.itemStackHandler.getStackInSlot(0);
				if (fuelStack != null && !fuelStack.isEmpty()) {
					burnTime = getBurnTime(fuelStack);
					if (burnTime > 0) {
						totalBurnTime = burnTime;
						ItemStack containedItem = fuelStack.getItem().getContainerItem(fuelStack.splitStack(1));
						if (!containedItem.isEmpty()) {
							if (fuelStack.isEmpty()) {
								itemStackHandler.setStackInSlot(0, containedItem);
							} else {
								world.spawnEntity(new EntityItem(world, pos.getX() + .5, pos.getY() + .5,
										pos.getZ() + .5, containedItem));
							}
						}
						markDirty();
						needsUpdate = true;
					}
				}
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("items", itemStackHandler.serializeNBT());
		compound.setInteger("burnTime", this.burnTime);
		compound.setInteger("energy", this.energy);
		compound.setInteger("capacity", this.capacity);
		compound.setInteger("totalBurnTime", this.totalBurnTime);
		return compound;
	}
}
