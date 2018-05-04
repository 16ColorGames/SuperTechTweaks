package com.sixteencolorgames.supertechtweaks.tileentities.crusher;

import java.util.ArrayList;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiCrusher extends GuiContainer {

	public static final int WIDTH = 176;
	public static final int HEIGHT = 152;
	private static final ResourceLocation background = new ResourceLocation(SuperTechTweaksMod.MODID,
			"textures/gui/Crusher.png");
	private ContainerCrusher container;

	public GuiCrusher(TileCrusher tileEntity, ContainerCrusher container) {
		super(container);
		this.container = container;

		xSize = WIDTH;
		ySize = HEIGHT;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int k = getEnergyLeftScaled(48);
		this.drawTexturedModalRect(guiLeft + 10, guiTop + 66 - k, 0, 30 - k + 206, 14, k);// render
		// power

		// TODO display current progress
		int l = getCookProgressScaled(24);
		this.drawTexturedModalRect(guiLeft + 76, guiTop + 34, 176, 0, l + 1, 16);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		int i = (width - xSize) / 2;
		int j = (height - ySize) / 2;
		if (mouseX - i > 9 && mouseX - i < 24 && mouseY > j + 18 && mouseY < j + 66) {
			ArrayList list = new ArrayList();
			list.add(container.te.getEnergyStored() + "/" + container.te.getMaxEnergyStored() + " FE");
			this.drawHoveringText(list, mouseX - i, mouseY - j);
		}
	}

	private int getCookProgressScaled(int pixels) {
		int i = container.cookEnergy;
		int j = container.totalCookEnergy;
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}

	public int getEnergyLeftScaled(int pixels) {
		return (container.te.getEnergyStored() * pixels) / (container.te.getMaxEnergyStored());
	}
}
