package com.sixteencolorgames.supertechtweaks.tileentities.crusher;

import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.tileentities.boiler.TileBoiler;

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

public class ContainerCrusher extends Container {

	public int cookEnergy;
	public int totalCookEnergy;
	private int currentStored;
	private int maxStored;
	public TileCrusher te;

	public ContainerCrusher(IInventory playerInventory, TileCrusher te) {
		this.te = te;

		// This container references items out of our own inventory (the 9 slots
		// we hold ourselves)
		// as well as the slots from the player inventory so that the user can
		// transfer items between
		// both inventories. The two calls below make sure that slots are
		// defined for both inventories.
		addOwnSlots(playerInventory);
		addPlayerSlots(playerInventory);
	}

	private void addOwnSlots(IInventory playerInventory) {
		IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		// Add our own slots
		addSlotToContainer(new SlotItemHandler(itemHandler, 0, 56, 35));
		addSlotToContainer(new SlotItemHandler(itemHandler, 1, 116, 35));
		// TODO prevent players from adding to the output slot
	}

	private void addPlayerSlots(IInventory playerInventory) {
		// Slots for the main inventory
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				int x = 8 + col * 18;
				int y = row * 18 + 70;
				addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
			}
		}

		// Slots for the hotbar
		for (int row = 0; row < 9; ++row) {
			int x = 8 + row * 18;
			int y = 58 + 70;
			addSlotToContainer(new Slot(playerInventory, row, x, y));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return te.canInteractWith(playerIn);
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < listeners.size(); ++i) {
			IContainerListener icontainerlistener = listeners.get(i);

			if (cookEnergy != te.getField(0)) {
				icontainerlistener.sendWindowProperty(this, 0, te.getField(0));
			}

			if (totalCookEnergy != te.getField(1)) {
				icontainerlistener.sendWindowProperty(this, 1, te.getField(1));
			}

			if (currentStored != te.getField(2)) {
				icontainerlistener.sendWindowProperty(this, 2, te.getField(2));
			}

			if (maxStored != te.getField(3)) {
				icontainerlistener.sendWindowProperty(this, 3, te.getField(3));
			}
		}

		cookEnergy = te.getField(0);
		totalCookEnergy = te.getField(1);
		currentStored = te.getField(2);
		maxStored = te.getField(3);
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

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		te.setField(id, data);
	}
}
