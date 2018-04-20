package com.sixteencolorgames.supertechtweaks.tileentities;

import net.minecraft.util.ITickable;

public abstract class TileMultiBlockController extends TileMultiBlock implements ITickable {

	/**
	 * Runs the required checks to see if a valid multiblock exists
	 *
	 * @return if the structure is valid
	 */
	public abstract boolean checkMultiBlockForm();

	public abstract void masterTick();

	/**
	 * clear multiblock data from the structure blocks here
	 */
	public abstract void resetStructure();

	/**
	 * tell each component block that it is part of a structure, and tell the
	 * controller that it is the master of this structure
	 */
	public abstract void setupStructure();

	@Override
	public void update() {
		if (!world.isRemote) {
			if (hasMaster()) {
				masterTick();
			} else {
				// Constantly check if structure is formed until it is.
				if (checkMultiBlockForm()) {
					setupStructure();
				}
			}
		}
	}
}
