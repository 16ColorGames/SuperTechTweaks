/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.tileentities;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

/**
 *
 * @author oa10712
 */
public abstract class TileEntityPoweredBase extends TileEntityBase implements IEnergyReceiver, IEnergyStorage {

    protected EnergyStorage storage;
    /**
     * The optimal energy input per tick
     */
    protected int energyRate;

    public TileEntityPoweredBase(String name, int rate, int max) {
        super(name);
        energyRate = rate;
        storage = new EnergyStorage(max);
    }

    public void setEnergy(int energy) {
        storage.setEnergyStored(energy);
    }

    /**
     * Use some of the energy stored within.
     *
     * @param consumed the amount to consume
     * @return the amount actually consumed
     */
    public int consumeEnergy(int consumed) {
        return storage.extractEnergy(consumed, false);
    }

    // -----------------------------------------------------------
    // For IEnergyReceiver
    /**
     * adds energy to this tile, returns how much was used.
     *
     * @param from
     * @param maxReceive
     * @param simulate
     * @return
     */
    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    /**
     * Returns how much energy is in this tile
     *
     * @param from
     * @return
     */
    @Override
    public int getEnergyStored(EnumFacing from) {
        return storage.getEnergyStored();
    }

    /**
     * Returns maximum energy storable
     *
     * @param from
     * @return
     */
    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return storage.getMaxEnergyStored();
    }

    /**
     * True, since we accept power from all sides
     *
     * @param from
     * @return
     */
    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }

    // -----------------------------------------------------------
    // For IEnergyStorage
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    /**
     * This is where a battery would discharge. This tile isn't a battery though
     *
     * @param maxExtract
     * @param simulate
     * @return
     */
    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return storage.getMaxEnergyStored();
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (T) this;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        storage.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        return storage.writeToNBT(tagCompound);
    }
}
