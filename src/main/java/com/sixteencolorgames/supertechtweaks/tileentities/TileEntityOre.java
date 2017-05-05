package com.sixteencolorgames.supertechtweaks.tileentities;

import com.sixteencolorgames.supertechtweaks.enums.Material;
import javax.annotation.Nullable;


import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Tile entity for ore block. Stores the actual data for it.
 *
 * @author oa10712
 *
 */
public class TileEntityOre extends TileEntity {

    /**
     * array of metals in this block. Uses Ores index
     */
    private int[] metals = new int[]{0, 0, 0, 0, 0, 0, 0};
    /**
     * unlocalized name for the base block
     */
    private byte base = 0;

    public boolean addMetal(Material metal) {
        for (int i = 0; i < 7; i++) {
            if (metals[i] == 0) {
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

    public void setMetal(int index, Material metal) {
        metals[index] = metal.ordinal();
    }

    public byte getBase() {
        return base;
    }

    public void setBase(byte original) {
        base = original;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public double getMaxRenderDistanceSquared() {
        return 256.0D;
    }

    // When the world loads from disk, the server needs to send the TileEntity
    // information to the client
    // it uses getUpdatePacket(), getUpdateTag(), onDataPacket(), and
    // handleUpdateTag() to do this:
    // getUpdatePacket() and onDataPacket() are used for one-at-a-time
    // TileEntity updates
    // getUpdateTag() and handleUpdateTag() are used by vanilla to collate
    // together into a single chunk update packet
    // inaccurate anyway
    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), getBlockMetadata(), getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    /**
     * Creates a tag containing the TileEntity information, used by vanilla to
     * transmit from server to client
     */
    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        writeToNBT(nbtTagCompound);
        return nbtTagCompound;
    }

    /**
     * Populates this TileEntity with information from the tag, used by vanilla
     * to transmit from server to client
     */
    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    /**
     * This is where you save any data that you don't want to lose when the tile
     * entity unloads In this case, we only need to store the gem colour. For
     * examples with other types of data, see MBE20
     */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound parentNBTTagCompound) {
        super.writeToNBT(parentNBTTagCompound); // The super call is required to
        // save the tiles location
        parentNBTTagCompound.setIntArray("ores", metals);
        parentNBTTagCompound.setByte("base", base);
        return parentNBTTagCompound;
    }

    // This is where you load the data that you saved in writeToNBT
    @Override
    public void readFromNBT(NBTTagCompound parentNBTTagCompound) {
        super.readFromNBT(parentNBTTagCompound); // The super call is required to load the tiles location important rule: never trust the data you read from NBT, make sure it can't cause a crash
        final int NBT_INT_ARR_ID = 11; // see NBTBase.createNewByType()
        int[] readMetals = metals;
        if (parentNBTTagCompound.hasKey("ores", NBT_INT_ARR_ID)) { // check if the key exists and is an Int array. You can omit this if a default value of 0 is ok.
            readMetals = parentNBTTagCompound.getIntArray("ores");
        }
        if (parentNBTTagCompound.hasKey("base", 1)) {
            base = parentNBTTagCompound.getByte("base");
        }
        metals = readMetals;
    }

}
