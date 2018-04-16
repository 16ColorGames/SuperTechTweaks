package com.sixteencolorgames.supertechtweaks.tileentities.researchselector;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.enums.Research;
import com.sixteencolorgames.supertechtweaks.network.ResearchUpdatePacket;
import com.sixteencolorgames.supertechtweaks.proxy.CommonProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Thanks to cmchenry from the forge forums for the basics of this class.
 * 
 * @author oa10712
 *
 */
public class GuiResearchPicker extends GuiScreen {

	public static void scissor(int x, int y, int w, int h) {

		Minecraft client = Minecraft.getMinecraft();
		ScaledResolution res = new ScaledResolution(client);
		double scaleW = client.displayWidth / res.getScaledWidth_double();
		double scaleH = client.displayHeight / res.getScaledHeight_double();
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor((int) (x * scaleW), (int) (client.displayHeight - (y * scaleH)), (int) (w * scaleW),
				(int) (h * scaleH));
	}
	private EntityPlayer player;
	private int xSizeOfTexture = 256, ySizeOfTexture = 168, scrollButtonY = 0, top, bottom, mouseX, mouseY, slotHeight,
			topScrollBar, bottomScrollBar;
	private float scrollDistance, initialMouseClickY = -2.0F, scrollFactor;
	private int lastMouseY;
	private IForgeRegistry<Research> research;
	private ResourceLocation selected = new ResourceLocation("none:none");

	private ResearchContainer researchContainer;

	public GuiResearchPicker(EntityPlayer player, ResearchContainer researchContainer) {
		this.player = player;
		this.researchContainer = researchContainer;
		this.scrollDistance = 0;
		this.initialMouseClickY = 0;
		lastMouseY = 0;
		this.slotHeight = 30;
		research = GameRegistry.findRegistry(Research.class);
		if (research.containsKey(researchContainer.getTileEntity().getSelected())) {
			selected = researchContainer.getTileEntity().getSelected();
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.id == 0) {
			System.out.println("clicked the select button, selected " + selected);
			researchContainer.getTileEntity().setSelected(selected);
			ResearchUpdatePacket airstrikeMessageToServer = new ResearchUpdatePacket(
					ResearchUpdatePacket.SELECTION_UPDATE, selected, researchContainer.getTileEntity().getPos());
			CommonProxy.simpleNetworkWrapper.sendToServer(airstrikeMessageToServer);
		}
	}

	private void applyScrollLimits() {
		int posX = (this.width - xSizeOfTexture) / 2;
		int posY = (this.height - ySizeOfTexture) / 2;
		int top = posY + 25;
		int bottom = posY + 140;
		int scrollMax = getSize() * this.slotHeight - (bottom - top - 4);

		if (scrollMax < 0) {
			scrollMax /= 2;
		}

		if (this.scrollDistance < 0.0F) {
			this.scrollDistance = 0.0F;
		}

		if (this.scrollDistance > (float) scrollMax) {
			this.scrollDistance = (float) scrollMax;
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
		FontRenderer font = stack.getItem().getFontRenderer(stack);
		if (font == null)
			font = fontRenderer;
		this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		this.zLevel = 0.0F;
		this.itemRender.zLevel = 0.0F;
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		drawDefaultBackground();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine
				.bindTexture(new ResourceLocation(SuperTechTweaksMod.MODID, "textures/gui/researchpicker.png"));

		int posX = (this.width - xSizeOfTexture) / 2;
		int posY = (this.height - ySizeOfTexture) / 2;

		drawTexturedModalRect(posX, posY, 0, 0, xSizeOfTexture, ySizeOfTexture);
		this.mouseX = x;
		this.mouseY = y;
		int bottomOffset = 140;
		int topOffset = 35;
		this.top = posY + 10;
		this.bottom = posY + 170;
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
				boolean scrollValid = true;
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
					if (mouseX >= boxLeft && mouseX <= boxRight && var10 < 0) {
						scrollValid = false;
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

					if (scrollValid) {
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
				int scroll = Mouse.getEventDWheel();

				if (scroll != 0) {
					if (scroll > 0) {
						scroll = -1;
					} else if (scroll < 0) {
						scroll = 1;
					}

					this.scrollDistance += (float) (scroll * this.slotHeight / 2);
				}
			}

			this.initialMouseClickY = -1.0F;
		}
		this.applyScrollLimits();
		var10 = this.top - (int) this.scrollDistance;

		// cut out nonvisible stuff
		this.scissor(posX + 125, bottom - 12, 100, bottom - top - 12);
		for (int i = 0; i < listLength; ++i) {
			var19 = var10 + i * this.slotHeight + 0;
			var13 = this.slotHeight - 4;

			if (var19 <= this.bottom && var19 + var13 >= this.top) {
				if (research.getValues().get(i).getRegistryName().equals(selected)) {
					this.drawRect(posX + 125, var19, posX + 125 + 100, var19 + slotHeight, 0xffa4a1a1);
				}

				this.drawString(fontRenderer, research.getValues().get(i).getTitle(), posX + 145,
						var19 + slotHeight / 2 - 4, Color.white.getRGB());
				this.drawItemStack(research.getValues().get(i).getDisplay(), posX + 125, var19 + slotHeight / 2 - 8,
						"");
			}
		}
		GL11.glDisable(GL11.GL_SCISSOR_TEST);

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

			int scrollY = (int) this.scrollDistance * (this.bottomScrollBar - this.topScrollBar - var13) / var19
					+ this.topScrollBar;

			if (scrollY < this.topScrollBar) {
				scrollY = this.topScrollBar;
			}
			this.mc.renderEngine
					.bindTexture(new ResourceLocation(SuperTechTweaksMod.MODID, "textures/gui/researchpicker.png"));
			this.drawTexturedModalRect(scrollBarXStart, scrollY, xSizeOfTexture - 25, ySizeOfTexture, 12, 16);
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

		this.buttonList.add(new GuiButton(0, posX + 7, posY + 135, 100, 20, "Select"));
	}

	@Override
	public void mouseClicked(int x, int y, int z) throws IOException {
		int var10 = this.top - (int) this.scrollDistance;

		int posX = (this.width - xSizeOfTexture) / 2;
		for (int i = 0; i < getSize(); ++i) {
			int var19 = var10 + i * this.slotHeight + 0;
			int var13 = this.slotHeight - 4;

			if (var19 <= this.bottom && var19 + var13 >= this.top) {

				if (x > posX + 125 && x < posX + 225 && y > var19 && y < var19 + this.slotHeight) {
					System.out.println(research.getValues().get(i).getTitle());
					selected = research.getValues().get(i).getRegistryName();
					return;
				}
			}
		}

		super.mouseClicked(x, y, z);
	}
}