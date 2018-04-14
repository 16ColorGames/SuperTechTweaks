package com.sixteencolorgames.supertechtweaks.gui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.enums.Research;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class GuiResearchPicker extends GuiScreen {

	private EntityPlayer player;
	private int xSizeOfTexture = 256, ySizeOfTexture = 168, scrollButtonY = 0, top, bottom, mouseX, mouseY, slotHeight,
			topScrollBar, bottomScrollBar;
	private float scrollDistance, initialMouseClickY = -2.0F, scrollFactor;
	private int lastMouseY;
	private CustomGuiTextField transferName, transferAmount;
	private IForgeRegistry<Research> research;

	public GuiResearchPicker(EntityPlayer player) {
		this.player = player;
		this.scrollDistance = 0;
		this.initialMouseClickY = 0;
		lastMouseY = 0;
		this.slotHeight = 30;
		research = GameRegistry.findRegistry(Research.class);
	}

	private void applyScrollLimits() {
		int posX = (this.width - xSizeOfTexture) / 2;
		int posY = (this.height - ySizeOfTexture) / 2;
		int top = posY + 25;
		int bottom = posY + 140;
		int var1 = getSize() * this.slotHeight - (bottom - top - 4);

		if (var1 < 0) {
			var1 /= 2;
		}

		if (this.scrollDistance < 0.0F) {
			this.scrollDistance = 0.0F;
		}

		if (this.scrollDistance > (float) var1) {
			this.scrollDistance = (float) var1;
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
		this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y, altText);
		this.zLevel = 0.0F;
		this.itemRender.zLevel = 0.0F;
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		drawDefaultBackground();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(new ResourceLocation(SuperTechTweaksMod.MODID, "textures/gui/GuiFriends.png"));

		int posX = (this.width - xSizeOfTexture) / 2;
		int posY = (this.height - ySizeOfTexture) / 2;

		drawTexturedModalRect(posX, posY, 0, 0, xSizeOfTexture, ySizeOfTexture);
		this.mouseX = x;
		this.mouseY = y;
		int bottomOffset = 140;
		int topOffset = 35;
		this.top = posY + 45;
		this.bottom = posY + 140;
		this.topScrollBar = posY + 10;
		this.bottomScrollBar = posY + 170;
		int listLength = this.getSize();
		int scrollBarXStart = posX + 235;
		int scrollBarXEnd = scrollBarXStart + 12;
		int boxLeft = posX + 50;
		int boxRight = scrollBarXStart - 1;
		int var10;
		int var11;
		int var13;
		int var19;

		if (Mouse.isButtonDown(0)) {
			if (this.initialMouseClickY == -1.0F) {
				boolean var7 = true;
				if (mouseX >= scrollBarXStart && mouseX <= scrollBarXEnd) {
					int function = -(posY - y) - 16;
					if (function < 0) {
						this.scrollButtonY = 0;
						return;
					} else if (function > 132) {
						this.scrollButtonY = 133;
						return;
					}
					this.scrollButtonY = function;
					int dir = Mouse.getDY();
					System.out.println("HEYO!");
					if (dir > 0) {
						this.scrollDistance -= (float) (this.slotHeight * 2 / 3);
						this.initialMouseClickY = -2.0F;
						this.applyScrollLimits();
					} else if (dir < 0) {
						this.scrollDistance += (float) (this.slotHeight * 2 / 3);
						this.initialMouseClickY = -2.0F;
						this.applyScrollLimits();
					}
				}
				if (mouseY >= this.topScrollBar && mouseY <= this.bottomScrollBar) {
					var10 = mouseY - this.top - 0 + (int) this.scrollDistance - 4;
					var11 = var10 / this.slotHeight;

					if (mouseX >= boxLeft && mouseX <= boxRight && var11 >= 0 && var10 >= 0 && var11 < listLength) {
					} else if (mouseX >= boxLeft && mouseX <= boxRight && var10 < 0) {
						var7 = false;
					}

					if (mouseX >= scrollBarXStart && mouseX <= scrollBarXEnd) {
						this.scrollFactor = -.4F;
						var19 = this.getContentHeight() - (this.bottom - this.top - 4);

						if (var19 < 1) {
							var19 = 1;
						}

						var13 = (int) ((float) ((this.bottom - this.top) * (this.bottom - this.top))
								/ (float) this.getContentHeight());

						if (var13 < 32) {
							var13 = 32;
						}

						if (var13 > this.bottom - this.top - 8) {
							var13 = this.bottom - this.top - 8;
						}

						this.scrollFactor /= (float) (this.bottom - this.top - var13) / (float) var19;
					} else {
						this.scrollFactor = .4F;
					}

					if (var7) {
						this.initialMouseClickY = (float) mouseY;
					} else {
						this.initialMouseClickY = -2.0F;
					}
				} else {
					this.initialMouseClickY = -2.0F;
				}
			} else if (this.initialMouseClickY >= 0.0F) {
				this.scrollDistance -= ((float) mouseY - this.initialMouseClickY) * this.scrollFactor;
				this.initialMouseClickY = (float) mouseY;
			}
		} else {
			while (Mouse.next()) {
				int var16 = Mouse.getEventDWheel();

				if (var16 != 0) {
					if (var16 > 0) {
						var16 = -1;
					} else if (var16 < 0) {
						var16 = 1;
					}

					this.scrollDistance += (float) (var16 * this.slotHeight / 2);
				}
			}

			this.initialMouseClickY = -1.0F;
		}
		this.applyScrollLimits();
		var10 = this.top + 4 - (int) this.scrollDistance;

		int var14;

		for (int i = 0; i < listLength; ++i) {
			var19 = var10 + i * this.slotHeight + 0;
			var13 = this.slotHeight - 4;

			if (var19 <= this.bottom && var19 + var13 >= this.top) {
				this.drawString(fontRenderer, research.getValues().get(i).getTitle(), posX + 145, var19,
						Color.black.getRGB());
				this.drawString(fontRenderer, research.getValues().get(i).getTitle(), posX + 145, var19 + 10,
						Color.gray.getRGB());
				this.drawItemStack(research.getValues().get(i).getDisplay(), posX + 125, var19, "");

			}
		}

		var19 = this.getContentHeight() - (this.bottomScrollBar - this.topScrollBar - 4);

		if (var19 > 0) {
			var13 = (this.bottomScrollBar - this.topScrollBar) * (this.bottomScrollBar - this.topScrollBar)
					/ this.getContentHeight();

			if (var13 < 32) {
				var13 = 32;
			}

			if (var13 > this.bottomScrollBar - this.topScrollBar - 8) {
				var13 = this.bottomScrollBar - this.topScrollBar - 8;
			}

			var14 = (int) this.scrollDistance * (this.bottomScrollBar - this.topScrollBar - var13) / var19
					+ this.topScrollBar;

			if (var14 < this.topScrollBar) {
				var14 = this.topScrollBar;
			}
			this.mc.renderEngine
					.bindTexture(new ResourceLocation(SuperTechTweaksMod.MODID, "textures/gui/GuiFriends.png"));
			this.drawTexturedModalRect(scrollBarXStart, var14, xSizeOfTexture - 25, ySizeOfTexture, 12, 16);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		super.drawScreen(x, y, f);
	}

	private int getContentHeight() {
		return research.getValues().size() * this.slotHeight;
	}

	private int getSize() {
		return research.getValues().size();
	}

	@Override
	public void initGui() {
		this.buttonList.clear();

		int posX = (this.width - xSizeOfTexture) / 2;
		int posY = (this.height - ySizeOfTexture) / 2;

		this.buttonList.add(new GuiButton(0, posX + 7, posY + 135, 100, 20, "Transfer"));

		this.transferName = new CustomGuiTextField(fontRenderer, this.width / 2 - 126 / 2, posY + 65, 126, 20);
		this.transferName.setMaxStringLength(19);
		this.transferName.setFocused(false);

		this.transferAmount = new CustomGuiTextField(fontRenderer, this.width / 2 - 126 / 2, posY + 105, 126, 20);
		this.transferAmount.setMaxStringLength(19);
		this.transferAmount.setFocused(false);
	}

	@Override
	public void mouseClicked(int x, int y, int z) throws IOException {
		if (mousePressed(this.transferAmount, x, y)) {
			this.transferAmount.setFocused(true);
			this.transferName.setFocused(false);
		} else if (mousePressed(this.transferName, x, y)) {
			this.transferAmount.setFocused(false);
			this.transferName.setFocused(true);
		} else {
			this.transferName.setFocused(false);
			this.transferAmount.setFocused(false);
		}

		super.mouseClicked(x, y, z);
	}

	public boolean mousePressed(CustomGuiTextField field, int mouseX, int mouseY) {
		return mouseX >= field.xPosition && mouseY >= field.yPosition && mouseX < field.xPosition + field.width
				&& mouseY < field.yPosition + field.height;
	}
}