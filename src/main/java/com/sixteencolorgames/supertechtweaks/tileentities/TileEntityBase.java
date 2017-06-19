/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.tileentities;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

/**
 *
 * @author oa10712
 */
public abstract class TileEntityBase extends TileEntityLockable implements ITickable, ISidedInventory {

    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    protected ItemStack[] itemStacks;
    protected String customName;
    private String name;

    public TileEntityBase(String name) {
        this.name = name;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return itemStacks.length;
    }

    /**
     * Returns the stack in the given slot.
     */
    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return itemStacks[index];
    }

    /**
     * Removes up to a specified number of items from an inventory slot and
     * returns them in a new stack.
     */
    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(itemStacks, index, count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(itemStacks, index);
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes
     * with Container
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.pos) != this ? false
                : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
                        (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    /**
     * Returns true if automation can insert the given item in the given slot
     * from the given side.
     */
    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public void clear() {
        for (int i = 0; i < itemStacks.length; ++i) {
            itemStacks[i] = null;
        }
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "container." + name;
    }

    /**
     * Returns true if this thing is named
     */
    @Override
    public boolean hasCustomName() {
        return this.customName != null && !this.customName.isEmpty();
    }

    public void setCustomInventoryName(String p_145951_1_) {
        this.customName = p_145951_1_;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be
     * 64, possibly will be extended.
     */
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

}
