package com.sixteencolorgames.supertechtweaks.proxy;

import com.sixteencolorgames.supertechtweaks.tileentities.basicresearcher.ContainerBasicResearcher;
import com.sixteencolorgames.supertechtweaks.tileentities.basicresearcher.GuiBasicResearcher;
import com.sixteencolorgames.supertechtweaks.tileentities.basicresearcher.TileBasicResearcher;
import com.sixteencolorgames.supertechtweaks.tileentities.boiler.ContainerBoiler;
import com.sixteencolorgames.supertechtweaks.tileentities.boiler.GuiBoiler;
import com.sixteencolorgames.supertechtweaks.tileentities.boiler.TileBoiler;
import com.sixteencolorgames.supertechtweaks.tileentities.researchselector.GuiResearchPicker;
import com.sixteencolorgames.supertechtweaks.tileentities.researchselector.ResearchContainer;
import com.sixteencolorgames.supertechtweaks.tileentities.researchselector.TileResearchSelector;

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
		if (te instanceof TileBasicResearcher) {
			TileBasicResearcher containerTileEntity = (TileBasicResearcher) te;
			return new GuiBasicResearcher(containerTileEntity,
					new ContainerBasicResearcher(player.inventory, containerTileEntity));
		}
		if (te instanceof TileResearchSelector) {
			TileResearchSelector containerTileEntity = (TileResearchSelector) te;
			ResearchContainer researchContainer = new ResearchContainer(player.inventory, containerTileEntity);
			return new GuiResearchPicker(player, researchContainer);
		}
		if (te instanceof TileBoiler) {
			TileBoiler containerTileEntity = (TileBoiler) te;
			ContainerBoiler researchContainer = new ContainerBoiler(player.inventory, containerTileEntity);
			return new GuiBoiler(containerTileEntity, researchContainer);
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileBasicResearcher) {
			return new ContainerBasicResearcher(player.inventory, (TileBasicResearcher) te);
		}
		if (te instanceof TileResearchSelector) {
			return new ResearchContainer(player.inventory, (TileResearchSelector) te);
		}
		if (te instanceof TileBoiler) {
			return new ContainerBoiler(player.inventory, (TileBoiler) te);
		}
		return null;
	}
}