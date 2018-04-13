package com.sixteencolorgames.supertechtweaks.gui;

import com.sixteencolorgames.supertechtweaks.tileentities.ResearchViewerTileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class ResearchContainer extends Container {

	private ResearchViewerTileEntity containerTileEntity;

	public ResearchContainer(InventoryPlayer inventory, ResearchViewerTileEntity containerTileEntity2) {
		this.containerTileEntity = containerTileEntity2;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return containerTileEntity.canInteractWith(playerIn);
	}

}
