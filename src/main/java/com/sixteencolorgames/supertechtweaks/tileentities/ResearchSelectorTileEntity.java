package com.sixteencolorgames.supertechtweaks.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
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
