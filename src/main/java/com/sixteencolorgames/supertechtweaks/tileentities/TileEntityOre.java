package com.sixteencolorgames.supertechtweaks.tileentities;

import com.sixteencolorgames.supertechtweaks.blocks.BlockOre;
import com.sixteencolorgames.supertechtweaks.enums.Metals;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Tile entity for ore block. Stores the actual data for it.
 * 
 * @author oa10712
 *
 */
public class TileEntityOre extends TileEntity {
	/**
	 * array of metals in this block. Uses Metals index
	 */
	private int[] metals = new int[] { 0, 0, 0, 0, 0, 0, 0 };
	/**
	 * unlocalized name for the base block
	 */
	private String base;

	public boolean addMetal(Metals metal) {
		for (int i = 0; i < 7; i++) {
			if (metals[i] == Metals.NONE.ordinal()) {
				metals[i] = metal.ordinal();
				return true;
			}
		}
		return false;
	}

	public int[] getOres() {
		if (metals.length != 0) {
			return metals;
		}
		return new int[]{0};
	}

	public void setMetal(int index, Metals metal) {
		metals[index] = metal.ordinal();
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

	public String getBase() {
		return base;
	}

	public void setBase(String original) {
		base = original;
	}

}
