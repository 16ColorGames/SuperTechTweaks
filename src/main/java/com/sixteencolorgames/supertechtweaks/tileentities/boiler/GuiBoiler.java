package com.sixteencolorgames.supertechtweaks.tileentities.boiler;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

public class GuiBoiler extends GuiContainer {
	public static final int WIDTH = 180;
	public static final int HEIGHT = 152;
	// TODO create texture for boiler
	private static final ResourceLocation background = new ResourceLocation(SuperTechTweaksMod.MODID,
			"textures/gui/boiler.png");
	private ContainerBoiler container;

	public GuiBoiler(TileBoiler tileEntity, ContainerBoiler container) {
		super(container);
		this.container = container;

		xSize = WIDTH;
		ySize = HEIGHT;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		if (this.container.te.burnTime > 0) {
			int k = this.getBurnLeftScaled(13);
			this.drawTexturedModalRect(guiLeft + 82, guiTop + 38 - k, WIDTH, 12 - k, 14, k + 1);
		}
	}

	private int getBurnLeftScaled(int pixels) {
		int i = this.container.te.getField(1);

		if (i == 0) {
			i = 200;
		}

		return this.container.te.getField(0) * pixels / i;
	}
}