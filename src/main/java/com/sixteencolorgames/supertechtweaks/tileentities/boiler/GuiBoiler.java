package com.sixteencolorgames.supertechtweaks.tileentities.boiler;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiBoiler extends GuiContainer {
	public static final int WIDTH = 180;
	public static final int HEIGHT = 152;
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
		if (container.te.burnTime > 0) {
			int k = getBurnLeftScaled(13);
			this.drawTexturedModalRect(guiLeft + 82, guiTop + 38 - k, WIDTH, 12 - k, 14, k + 1);
		}
		int w = getWaterTankScaled(41);
		this.drawTexturedModalRect(guiLeft + 28, guiTop + 58 - w, WIDTH + 14, 41 - w, 16, w + 1);
		int s = getSteamTankScaled(41);
		this.drawTexturedModalRect(guiLeft + 136, guiTop + 58 - s, WIDTH + 30, 41 - s, 16, s + 1);
		int i = (width - xSize) / 2;
		int j = (height - ySize) / 2;
		this.renderHoveredToolTip(mouseX - i, mouseY - j);
	}

	private int getBurnLeftScaled(int pixels) {
		int i = container.te.getField(1);

		if (i == 0) {
			i = 200;
		}

		return container.te.getField(0) * pixels / i;
	}

	private int getSteamTankScaled(int pixels) {
		int i = container.te.getField(4);

		if (i == 0) {
			i = 2000;
		}

		return container.te.getField(5) * pixels / i;
	}

	private int getWaterTankScaled(int pixels) {
		int i = container.te.getField(2);

		if (i == 0) {
			i = 2000;
		}

		return container.te.getField(3) * pixels / i;
	}
}