package com.sixteencolorgames.supertechtweaks.tileentities.researchselector;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ResearchContainer extends Container {

	private ResearchSelectorTileEntity containerTileEntity;

	public ResearchContainer(InventoryPlayer inventory, ResearchSelectorTileEntity containerTileEntity2) {
		containerTileEntity = containerTileEntity2;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return containerTileEntity.canInteractWith(playerIn);
	}

	public ResearchSelectorTileEntity getTileEntity() {
		return containerTileEntity;
	}

}