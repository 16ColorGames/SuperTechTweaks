package com.sixteencolorgames.supertechtweaks.proxy;

import com.sixteencolorgames.supertechtweaks.tileentities.basicresearcher.ContainerBasicResearcher;
import com.sixteencolorgames.supertechtweaks.tileentities.basicresearcher.GuiBasicResearcher;
import com.sixteencolorgames.supertechtweaks.tileentities.basicresearcher.TileBasicResearcher;
import com.sixteencolorgames.supertechtweaks.tileentities.researchselector.GuiResearchPicker;
import com.sixteencolorgames.supertechtweaks.tileentities.researchselector.ResearchContainer;
import com.sixteencolorgames.supertechtweaks.tileentities.researchselector.TileResearchSelector;
import com.sixteencolorgames.supertechtweaks.tileentities.solidfuelgenerator.ContainerSolidFuelGenerator;
import com.sixteencolorgames.supertechtweaks.tileentities.solidfuelgenerator.GuiSolidFuelGenerator;
import com.sixteencolorgames.supertechtweaks.tileentities.solidfuelgenerator.TileSolidFuelGenerator;

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
		if (te instanceof TileSolidFuelGenerator) {
			TileSolidFuelGenerator containerTileEntity = (TileSolidFuelGenerator) te;
			return new GuiSolidFuelGenerator(containerTileEntity,
					new ContainerSolidFuelGenerator(player.inventory, containerTileEntity));
		}
		if (te instanceof TileResearchSelector) {
			TileResearchSelector containerTileEntity = (TileResearchSelector) te;
			ResearchContainer researchContainer = new ResearchContainer(player.inventory, containerTileEntity);
			return new GuiResearchPicker(player, researchContainer);
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
		if (te instanceof TileSolidFuelGenerator) {
			return new ContainerSolidFuelGenerator(player.inventory, (TileSolidFuelGenerator) te);
		}
		if (te instanceof TileResearchSelector) {
			return new ResearchContainer(player.inventory, (TileResearchSelector) te);
		}
		return null;
	}
}