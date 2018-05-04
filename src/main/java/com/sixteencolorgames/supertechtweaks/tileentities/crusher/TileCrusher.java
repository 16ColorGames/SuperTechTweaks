package com.sixteencolorgames.supertechtweaks.tileentities.crusher;

import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;
import com.sixteencolorgames.supertechtweaks.Pair;
import com.sixteencolorgames.supertechtweaks.recipe.CrusherRecipes;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlockController;
import com.sixteencolorgames.supertechtweaks.world.ResearchSavedData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileCrusher extends TileMultiBlockController implements IEnergyStorage {

	public static final int SIZE = 2;
	private static final int INPUT_SLOT = 0;
	private static final int OUTPUT_SLOT = 1;
	private int cookEnergy;
	private int totalCookEnergy;
	private ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE) {
		@Override
		protected void onContentsChanged(int slot) {
			// We need to tell the tile entity that something has changed so
			// that the chest contents is persisted
			TileCrusher.this.markDirty();
		}
	};

	protected int energy;

	protected int capacity;

	protected int maxReceive;
	private int processRate = 20;

	private UUID owner_UUID;

	public TileCrusher() {
		energy = 0;
		capacity = 10000;
		maxReceive = 1000;
	}

	@Override
	public boolean canExtract() {
		return false;
	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		// If we are too far away from this tile entity you cannot use it
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item,
	 * destination stack isn't full, etc.
	 */
	private boolean canProcess() {
		if (itemStackHandler.getStackInSlot(INPUT_SLOT) == null) {
			return false;
		}
		if (getEnergyStored() <= 0) {// if there is no energy left
			return false;
		}
		Pair<ItemStack, ArrayList<ResourceLocation>> res = CrusherRecipes.instance()
				.getCrushingResult(itemStackHandler.getStackInSlot(INPUT_SLOT));
		for (ResourceLocation rl : res.getRight()) {
			boolean has = ResearchSavedData.get(world).getPlayerHasResearch(owner_UUID, rl);
			if (!has) {
				return false;
			}
		}
		if (res.getLeft() == ItemStack.EMPTY) {// if there is no valid recipe
			return false;
		}
		if (itemStackHandler.getStackInSlot(OUTPUT_SLOT) == null
				|| itemStackHandler.getStackInSlot(OUTPUT_SLOT) == ItemStack.EMPTY) {
			return true;
		}
		if (!itemStackHandler.getStackInSlot(OUTPUT_SLOT).isItemEqual(res.getLeft())) {
			// if the output has a different output
			return false;
		}
		int result = itemStackHandler.getStackInSlot(OUTPUT_SLOT).getCount() + res.getLeft().getCount();
		return result <= itemStackHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
		// Finally, return if the new stack is small enough to fit
	}

	@Override
	public boolean canReceive() {
		return true;
	}

	@Override
	public boolean checkMultiBlockForm() {
		return true;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}

	private int extractEnergyInternal(int processRate2) {
		int energySent = Math.min(energy, Math.min(processRate, processRate2));
		energy -= energySent;
		return energySent;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
		}
		if (capability == CapabilityEnergy.ENERGY) {
			return (T) this;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public int getEnergyStored() {
		return energy;
	}

	public int getField(int id) {
		switch (id) {
		case 0:
			return cookEnergy;
		case 1:
			return totalCookEnergy;
		case 2:
			return energy;
		case 3:
			return capacity;
		default:
			return 0;
		}
	}

	@Override
	public int getMaxEnergyStored() {
		return capacity;
	}

	public GameProfile getOwner() {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerProfileCache()
				.getProfileByUUID(owner_UUID);
	}

	public int getTotalCookEnergy(@Nullable ItemStack stack) {
		// TODO make other cook energies possible
		return 2000;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public void masterTick() {
		if (canProcess()) {// If we can do a process tick
			if (!world.isRemote) {
				if (cookEnergy == 0) {// If this is the start of processing
					totalCookEnergy = getTotalCookEnergy(itemStackHandler.getStackInSlot(INPUT_SLOT));
				}
				int consumedEnergy = extractEnergyInternal(processRate);
				cookEnergy += consumedEnergy;
				if (cookEnergy >= totalCookEnergy) {
					cookEnergy = 0;
					processItem();
					markDirty();
				}
			}
		} else {
			if (itemStackHandler.getStackInSlot(INPUT_SLOT) == null) {
				cookEnergy = 0;// Clear out any progress that had been made
				totalCookEnergy = 1;
			}
		}
	}

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted
	 * item in the furnace result stack
	 */
	public void processItem() {
		System.out.println("Processing finished");
		Pair<ItemStack, ArrayList<ResourceLocation>> res = CrusherRecipes.instance()
				.getCrushingResult(itemStackHandler.getStackInSlot(INPUT_SLOT));
		itemStackHandler.insertItem(OUTPUT_SLOT, res.getLeft(), false);
		itemStackHandler.extractItem(INPUT_SLOT, 1, false);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if (tag.hasKey("items")) {
			itemStackHandler.deserializeNBT((NBTTagCompound) tag.getTag("items"));
		}
		cookEnergy = tag.getInteger("cookEnergy");
		totalCookEnergy = tag.getInteger("totalCookEnergy");
		maxReceive = tag.getInteger("maxRecieve");
		energy = tag.getInteger("energy");
		capacity = tag.getInteger("capacity");
		owner_UUID = UUID.fromString(tag.getString("owner"));
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
		if (!simulate) {
			energy += energyReceived;
		}
		return energyReceived;
	}

	@Override
	public void resetStructure() {
		reset();
	}

	public void setField(int id, int value) {
		switch (id) {
		case 0:
			cookEnergy = value;
			break;
		case 1:
			totalCookEnergy = value;
			break;
		case 2:
			energy = value;
			break;
		case 3:
			capacity = value;
			break;
		}
	}

	public void setOwner(EntityPlayer placer) {
		owner_UUID = placer.getUniqueID();
		markDirty();
	}

	@Override
	public void setupStructure() {
		setIsMaster(true);
		setMasterCoords(getPos().getX(), getPos().getY(), getPos().getZ());
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("items", itemStackHandler.serializeNBT());
		compound.setInteger("cookEnergy", cookEnergy);
		compound.setInteger("totalCookEnergy", totalCookEnergy);
		compound.setInteger("energy", energy);
		compound.setInteger("capacity", capacity);
		compound.setInteger("maxRecieve", maxReceive);
		if (owner_UUID != null) {
			compound.setString("owner", owner_UUID.toString());
		}
		return compound;
	}

}
