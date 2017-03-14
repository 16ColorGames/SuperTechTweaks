package com.sixteencolorgames.supertechtweaks.tileentities;

import java.awt.Color;

import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.enums.Metals;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
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
		return new int[] { 0 };
	}

	public void setMetal(int index, Metals metal) {
		metals[index] = metal.ordinal();
	}

	public String getBase() {
		return base;
	}

	public void setBase(String original) {
		base = original;
	}

	// When the world loads from disk, the server needs to send the TileEntity
	// information to the client
	// it uses getUpdatePacket(), getUpdateTag(), onDataPacket(), and
	// handleUpdateTag() to do this:
	// getUpdatePacket() and onDataPacket() are used for one-at-a-time
	// TileEntity updates
	// getUpdateTag() and handleUpdateTag() are used by vanilla to collate
	// together into a single chunk update packet
	// In this case, we need it for the gem colour. There's no need to save the
	// gem angular position because
	// the player will never notice the difference and the client<-->server
	// synchronisation lag will make it
	// inaccurate anyway
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	/*
	 * Creates a tag containing the TileEntity information, used by vanilla to
	 * transmit from server to client
	 */
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return nbtTagCompound;
	}

	/*
	 * Populates this TileEntity with information from the tag, used by vanilla
	 * to transmit from server to client
	 */
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	// This is where you save any data that you don't want to lose when the tile
	// entity unloads
	// In this case, we only need to store the gem colour. For examples with
	// other types of data, see MBE20
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound parentNBTTagCompound) {
		super.writeToNBT(parentNBTTagCompound); // The super call is required to
												// save the tiles location
		parentNBTTagCompound.setIntArray("ores", metals);
		return parentNBTTagCompound;
	}

	// This is where you load the data that you saved in writeToNBT
	@Override
	public void readFromNBT(NBTTagCompound parentNBTTagCompound) {
		super.readFromNBT(parentNBTTagCompound); // The super call is required
													// to load the tiles
													// location

		// important rule: never trust the data you read from NBT, make sure it
		// can't cause a crash

		final int NBT_INT_ARR_ID = 11; // see NBTBase.createNewByType()
		int[] readMetals = metals;
		if (parentNBTTagCompound.hasKey("ores", NBT_INT_ARR_ID)) { // check if
																	// the key
																	// exists
																	// and is an
																	// Int
																	// array.
																	// You can
																	// omit this
																	// if a
																	// default
																	// value of
																	// 0 is ok.
			readMetals = parentNBTTagCompound.getIntArray("ores");
		}
		metals = readMetals;
	}

}
