package com.sixteencolorgames.supertechtweaks.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BasicResearcherTileEntity extends TileMultiBlockController {
	private BlockPos selectorpos;
	public static final int SIZE = 9;

	// This item handler will hold our nine inventory slots
	private ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE) {
		@Override
		protected void onContentsChanged(int slot) {
			// We need to tell the tile entity that something has changed so
			// that the chest contents is persisted
			BasicResearcherTileEntity.this.markDirty();
		}
	};

	public boolean canInteractWith(EntityPlayer playerIn) {
		// If we are too far away from this tile entity you cannot use it
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	@Override
	public boolean checkMultiBlockForm() {
		boolean selectorPresent = false;
		// Scan a 3x3x3 area, starting with the bottom left corner
		for (int x = this.getPos().getX() - 1; x < this.getPos().getX() + 2; x++) {
			for (int y = this.getPos().getY() - 1; y < this.getPos().getY() + 2; y++) {
				for (int z = this.getPos().getZ() - 1; z < this.getPos().getZ() + 2; z++) {
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
					if (tile instanceof ResearchSelectorTileEntity) {
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
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
		}
		return super.getCapability(capability, facing);
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
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("items")) {
			itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
		}
	}

	@Override
	public void resetStructure() {
		for (int x = this.getPos().getX() - 1; x < this.getPos().getX() + 2; x++) {
			for (int y = this.getPos().getY() - 1; y < this.getPos().getY() + 2; y++) {
				for (int z = this.getPos().getZ() - 1; z < this.getPos().getZ() + 2; z++) {
					TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
					if (tile != null && (tile instanceof TileMultiBlock))
						((TileMultiBlock) tile).reset();
				}
			}
		}
	}

	@Override
	public void setupStructure() {
		for (int x = this.getPos().getX() - 1; x < this.getPos().getX() + 2; x++) {
			for (int y = this.getPos().getY() - 1; y < this.getPos().getY() + 2; y++) {
				for (int z = this.getPos().getZ() - 1; z < this.getPos().getZ() + 2; z++) {
					TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
					// Check if block is center block
					boolean master = (x == this.getPos().getX() && y == this.getPos().getY()
							&& z == this.getPos().getZ());
					if (tile != null && (tile instanceof TileMultiBlock)) {
						((TileMultiBlock) tile).setMasterCoords(this.getPos().getX(), this.getPos().getY(),
								this.getPos().getZ());
						((TileMultiBlock) tile).setHasMaster(true);
						((TileMultiBlock) tile).setIsMaster(master);
					}
					if (tile instanceof ResearchSelectorTileEntity) {
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
		return compound;
	}

}
