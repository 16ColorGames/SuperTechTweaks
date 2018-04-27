package com.sixteencolorgames.supertechtweaks.tileentities.boiler;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBoiler extends Container {
	TileBoiler te;

	public ContainerBoiler(IInventory playerInventory, TileBoiler te) {
		this.te = te;

		// This container references items out of our own inventory (the 9 slots
		// we hold ourselves)
		// as well as the slots from the player inventory so that the user can
		// transfer items between
		// both inventories. The two calls below make sure that slots are
		// defined for both inventories.
		addOwnSlots();
		addPlayerSlots(playerInventory);
	}

	// TODO center the slot and add fluid displays
	private void addOwnSlots() {
		IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		int x = 82;
		int y = 42;

		// Add our own slots
		addSlotToContainer(new SlotItemHandler(itemHandler, 0, x, y));
	}

	private void addPlayerSlots(IInventory playerInventory) {
		// Slots for the main inventory
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				int x = 10 + col * 18;
				int y = row * 18 + 70;
				addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
			}
		}

		// Slots for the hotbar
		for (int row = 0; row < 9; ++row) {
			int x = 9 + row * 18;
			int y = 58 + 70;
			addSlotToContainer(new Slot(playerInventory, row, x, y));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return te.canInteractWith(playerIn);
	}

	@Nullable
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < TileBoiler.SIZE) {
				if (!mergeItemStack(itemstack1, TileBoiler.SIZE, inventorySlots.size(), true)) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 0, TileBoiler.SIZE, false)) {
				return null;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	/**
	 * how many ticks are left for this burn
	 */
	public int burnTime = 0;
	/**
	 * how many ticks this burn started with
	 */
	public int totalBurnTime; // This item handler will hold our nine inventory
								// slots

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = this.listeners.get(i);

			if (this.burnTime != this.te.getField(0)) {
				icontainerlistener.sendWindowProperty(this, 0, this.te.getField(0));
			}

			if (this.totalBurnTime != this.te.getField(1)) {
				icontainerlistener.sendWindowProperty(this, 1, this.te.getField(1));
			}
		}

		this.burnTime = this.te.getField(0);
		this.totalBurnTime = this.te.getField(1);
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.te.setField(id, data);
	}
}
