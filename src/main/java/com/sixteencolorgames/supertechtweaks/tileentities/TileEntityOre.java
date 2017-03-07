package com.sixteencolorgames.supertechtweaks.tileentities;

import com.sixteencolorgames.supertechtweaks.enums.Metals;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityOre extends TileEntity {

	private int[] metals = new int[] { 0, 0, 0, 0, 0, 0, 0};

	public boolean addMetal(Metals metal) {
		for (int i = 0; i < 7; i++) {
			if (metals[i] == Metals.NONE.getIndex()) {
				metals[i] = metal.getIndex();
				return true;
			}
		}
		return false;
	}
	
	public int[] getOres(){
		return metals;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setIntArray("metals", metals);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		metals = compound.getIntArray("metals");
		super.readFromNBT(compound);
	}

}
