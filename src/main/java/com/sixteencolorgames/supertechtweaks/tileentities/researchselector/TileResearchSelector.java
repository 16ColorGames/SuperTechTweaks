package com.sixteencolorgames.supertechtweaks.tileentities.researchselector;

import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.enums.Research;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileResearchSelector extends TileMultiBlock {
	private ResourceLocation selected = new ResourceLocation("none:none");;

	public boolean canInteractWith(EntityPlayer playerIn) {
		// If we are too far away from this tile entity you cannot use it
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	public ResourceLocation getSelected() {
		return selected;
	}

	public Research getSelectedResearch() {
		return GameRegistry.findRegistry(Research.class).getValue(getSelected());
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 3, getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
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
