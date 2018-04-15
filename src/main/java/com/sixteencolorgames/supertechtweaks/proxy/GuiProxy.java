package com.sixteencolorgames.supertechtweaks.proxy;

import com.sixteencolorgames.supertechtweaks.gui.GuiResearchPicker;
import com.sixteencolorgames.supertechtweaks.gui.ResearchContainer;
import com.sixteencolorgames.supertechtweaks.tileentities.BasicResearcherContainer;
import com.sixteencolorgames.supertechtweaks.tileentities.BasicResearcherGui;
import com.sixteencolorgames.supertechtweaks.tileentities.BasicResearcherTileEntity;
import com.sixteencolorgames.supertechtweaks.tileentities.ResearchViewerTileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiProxy implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof BasicResearcherTileEntity) {
			BasicResearcherTileEntity containerTileEntity = (BasicResearcherTileEntity) te;
			return new BasicResearcherGui(containerTileEntity,
					new BasicResearcherContainer(player.inventory, containerTileEntity));
		}
		if (te instanceof ResearchViewerTileEntity) {
			ResearchViewerTileEntity containerTileEntity = (ResearchViewerTileEntity) te;
			ResearchContainer researchContainer = new ResearchContainer(player.inventory, containerTileEntity);
			return new GuiResearchPicker(player, researchContainer);
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof BasicResearcherTileEntity) {
			return new BasicResearcherContainer(player.inventory, (BasicResearcherTileEntity) te);
		}
		if (te instanceof ResearchViewerTileEntity) {
			return new ResearchContainer(player.inventory, (ResearchViewerTileEntity) te);
		}
		return null;
	}
}