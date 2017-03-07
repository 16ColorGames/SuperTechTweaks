package com.sixteencolorgames.supertechtweaks.tileentities;

import com.sixteencolorgames.supertechtweaks.enums.Metals;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityOre extends TileEntity {

	public int[] metals = new int[] { -1, -1, -1, -1, -1, -1, -1 };

	public boolean addMetal(Metals metal) {
		for (int i = 0; i < 7; i++) {
			if (metals[i] == -1) {
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
