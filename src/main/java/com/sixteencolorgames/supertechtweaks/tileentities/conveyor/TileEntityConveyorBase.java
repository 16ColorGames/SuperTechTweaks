package com.sixteencolorgames.supertechtweaks.tileentities.conveyor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityConveyorBase extends TileEntity implements ITickable {
	protected static final String TAG_FACING = "facing";
	protected EnumFacing facing;

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		if (compound.hasKey(TAG_FACING))
			getTileData().setInteger(TAG_FACING, compound.getInteger(TAG_FACING));
	}

	@Override
	@Nonnull
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger(TAG_FACING, getFacing().getIndex());

		return compound;
	}

	public IExtendedBlockState writeExtendedBlockState(IExtendedBlockState state) {
		EnumFacing facing = getFacing();

		state = state.withProperty(BlockConveyorBase.FACING, facing);

		return state;
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = getTileData().copy();

		writeToNBT(tag);

		return new SPacketUpdateTileEntity(this.getPos(), this.getBlockMetadata(), tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound tag = pkt.getNbtCompound();

		NBTBase facing = tag.getTag(TAG_FACING);

		getTileData().setTag(TAG_FACING, facing);

		readFromNBT(tag);
	}

	@Override
	@Nonnull
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(@Nullable NBTTagCompound tag) {
		readFromNBT(tag);
	}

	@Nonnull
	public EnumFacing getFacing() {
		return EnumFacing.getFront(getTileData().getInteger(TAG_FACING));
	}

	public void setFacing(EnumFacing facing) {
		getTileData().setInteger(TAG_FACING, facing.getIndex());
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}