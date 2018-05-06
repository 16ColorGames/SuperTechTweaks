package com.sixteencolorgames.supertechtweaks.tileentities.researchselector;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ResearchContainer extends Container {

	private TileResearchSelector containerTileEntity;

	public ResearchContainer(InventoryPlayer inventory, TileResearchSelector containerTileEntity2) {
		containerTileEntity = containerTileEntity2;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return containerTileEntity.canInteractWith(playerIn);
	}

	public TileResearchSelector getTileEntity() {
		return containerTileEntity;
	}

}