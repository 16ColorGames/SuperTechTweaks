package com.sixteencolorgames.supertechtweaks.gui;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.enums.Research;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ResearchGui extends GuiContainer {
	public static final int WIDTH = 180;
	public static final int HEIGHT = 152;

	int xoff = 0;
	int scrollbarY = 0;
	private boolean isDragging;

	private static final ResourceLocation background = new ResourceLocation("minecraft", "textures/blocks/brick.png");

	public ResearchGui(ResearchContainer researchContainer) {
		super(researchContainer);

		xSize = WIDTH;
		ySize = HEIGHT;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		int row = 0;
		int col = 0;
		for (Research research : GameRegistry.findRegistry(Research.class).getValues()) {
			this.drawItemStack(research.getDisplayStack(), guiLeft + row * 16 + xoff, guiTop + col * 16 + scrollbarY, "");
			row++;
			col++;
		}
	}

	/**
	 * Draws an ItemStack.
	 * 
	 * The z index is increased by 32 (and not decreased afterwards), and the
	 * item is then rendered at z=200.
	 */
	private void drawItemStack(ItemStack stack, int x, int y, String altText) {
		GlStateManager.translate(0.0F, 0.0F, 32.0F);
		this.zLevel = 200.0F;
		this.itemRender.zLevel = 200.0F;
		net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
		if (font == null)
			font = fontRenderer;
		this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y - 8, altText);
		this.zLevel = 0.0F;
		this.itemRender.zLevel = 0.0F;
	}

}
