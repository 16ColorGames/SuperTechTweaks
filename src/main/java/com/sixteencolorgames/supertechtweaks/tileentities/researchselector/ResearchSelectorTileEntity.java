package com.sixteencolorgames.supertechtweaks.tileentities.researchselector;

import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;

public class ResearchSelectorTileEntity extends TileMultiBlock {
	private ResourceLocation selected = new ResourceLocation("none:none");;

	public boolean canInteractWith(EntityPlayer playerIn) {
		// If we are too far away from this tile entity you cannot use it
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	public ResourceLocation getSelected() {
		return selected;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		// Prepare a packet for syncing our TE to the client. Since we only have
		// to sync the stack
		// and that's all we have we just write our entire NBT here. If you have
		// a complex
		// tile entity that doesn't need to have all information on the client
		// you can write
		// a more optimal NBT here.
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		// Here we get the packet from the server and read it into our client
		// side tile entity
		readFromNBT(packet.getNbtCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		if (compound.hasKey("domain")) {
			selected = new ResourceLocation(compound.getString("domain"), compound.getString("path"));
		}
	}

	public void setSelected(ResourceLocation registryName) {
		selected = registryName;
		markDirty();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("domain", selected.getResourceDomain());
		compound.setString("path", selected.getResourcePath());
		return compound;
	}
}
