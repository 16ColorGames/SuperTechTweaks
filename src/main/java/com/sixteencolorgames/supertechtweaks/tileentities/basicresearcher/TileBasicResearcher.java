package com.sixteencolorgames.supertechtweaks.tileentities.basicresearcher;

import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlockController;
import com.sixteencolorgames.supertechtweaks.tileentities.researchselector.TileResearchSelector;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileBasicResearcher extends TileMultiBlockController implements IEnergyStorage {
	public static final int SIZE = 9;
	private BlockPos selectorpos;
	protected int energy;
	protected int capacity;
	protected int maxReceive;

	// This item handler will hold our nine inventory slots
	private ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE) {
		@Override
		protected void onContentsChanged(int slot) {
			// We need to tell the tile entity that something has changed so
			// that the chest contents is persisted
			TileBasicResearcher.this.markDirty();
		}
	};

	public TileBasicResearcher() {
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

	@Override
	public boolean canReceive() {
		return true;
	}

	@Override
	public boolean checkMultiBlockForm() {
		boolean selectorPresent = false;
		// Scan a 3x3x3 area, starting with the bottom left corner
		for (int x = getPos().getX() - 1; x < getPos().getX() + 2; x++) {
			for (int y = getPos().getY() - 1; y < getPos().getY() + 2; y++) {
				for (int z = getPos().getZ() - 1; z < getPos().getZ() + 2; z++) {
					TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
					// Make sure tile isn't null, is an instance of the same
					// Tile, and isn't already a part of a multiblock
					if (tile != null && (tile instanceof TileMultiBlock)) {
						if (((TileMultiBlock) tile).hasMaster()) {
							return false;
						}
					} else {
						return false;
					}
					if (tile instanceof TileResearchSelector) {
						if (selectorPresent) {// we only want 1 selector
							return false;
						}
						selectorPresent = true;
					}
				}
			}
		}
		return selectorPresent;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
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

	@Override
	public int getMaxEnergyStored() {
		return capacity;
	}

	public BlockPos getSelectorpos() {
		return selectorpos;
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 3, getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public void masterTick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("items")) {
			itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
		}
		if (compound.hasKey("selector")) {
			int[] arr = compound.getIntArray("selector");
			selectorpos = new BlockPos(arr[0], arr[1], arr[2]);
		}
		maxReceive = compound.getInteger("maxRecieve");
		energy = compound.getInteger("energy");
		capacity = compound.getInteger("capacity");
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
		for (int x = getPos().getX() - 1; x < getPos().getX() + 2; x++) {
			for (int y = getPos().getY() - 1; y < getPos().getY() + 2; y++) {
				for (int z = getPos().getZ() - 1; z < getPos().getZ() + 2; z++) {
					TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
					if (tile != null && (tile instanceof TileMultiBlock)) {
						((TileMultiBlock) tile).reset();
					}
				}
			}
		}
	}

	@Override
	public void setupStructure() {
		for (int x = getPos().getX() - 1; x < getPos().getX() + 2; x++) {
			for (int y = getPos().getY() - 1; y < getPos().getY() + 2; y++) {
				for (int z = getPos().getZ() - 1; z < getPos().getZ() + 2; z++) {
					TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
					// Check if block is center block
					boolean master = (x == getPos().getX() && y == getPos().getY() && z == getPos().getZ());
					if (tile != null && (tile instanceof TileMultiBlock)) {
						((TileMultiBlock) tile).setIsMaster(master);
						((TileMultiBlock) tile).setMasterCoords(getPos().getX(), getPos().getY(), getPos().getZ());
					}
					if (tile instanceof TileResearchSelector) {
						selectorpos = new BlockPos(x, y, z);
					}
				}
			}
		}
		System.out.println("multiblock formed, selector at " + selectorpos);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("items", itemStackHandler.serializeNBT());
		if (selectorpos != null) {
			compound.setIntArray("selector", new int[] { selectorpos.getX(), selectorpos.getY(), selectorpos.getZ() });
		}
		compound.setInteger("energy", energy);
		compound.setInteger("capacity", capacity);
		compound.setInteger("maxRecieve", maxReceive);
		return compound;
	}

}
