package com.sixteencolorgames.supertechtweaks.tileentities.boiler;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiBoiler extends GuiContainer {
	public static final int WIDTH = 180;
	public static final int HEIGHT = 152;
	// TODO create texture for boiler
	private static final ResourceLocation background = new ResourceLocation(SuperTechTweaksMod.MODID,
			"textures/gui/boiler.png");

	public GuiBoiler(TileBoiler tileEntity, ContainerBoiler container) {
		super(container);

		xSize = WIDTH;
		ySize = HEIGHT;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}