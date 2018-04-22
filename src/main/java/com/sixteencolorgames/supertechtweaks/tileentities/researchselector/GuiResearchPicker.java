package com.sixteencolorgames.supertechtweaks.tileentities.researchselector;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.enums.Research;
import com.sixteencolorgames.supertechtweaks.network.ResearchUpdatePacket;
import com.sixteencolorgames.supertechtweaks.proxy.CommonProxy;
import com.sixteencolorgames.supertechtweaks.world.ResearchSavedData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Thanks to cmchenry from the forge forums for the basics of this class.
 *
 * @author oa10712
 *
 */
public class GuiResearchPicker extends GuiScreen {
	public static List<Research> cloneList(List<Research> list) {
		List<Research> clone = new ArrayList<Research>(list.size());
		for (Research item : list) {
			clone.add(item);
		}
		return clone;
	}

	// FIXME re-logging causes the selected research to clear on the gui; the
	// server remembers it just fine
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

	private ResourceLocation selected = new ResourceLocation("none:none");
	private ResearchContainer researchContainer;

	private List<Research> values;

	// FIXME doesn't display properly researched items
	public GuiResearchPicker(EntityPlayer player, ResearchContainer researchContainer) {
		this.player = player;
		this.researchContainer = researchContainer;
		scrollDistance = 0;
		initialMouseClickY = 0;
		lastMouseY = 0;
		slotHeight = 30;
		values = cloneList(GameRegistry.findRegistry(Research.class).getValues());
		for (Research r : cloneList(values)) {// only display the valid
			// researches
			for (int j = 0; j < r.getRequirementCount(); j++) {
				if (!ResearchSavedData.get(player.world).getPlayerHasResearch(player, r.getRequirements().get(j))) {
					values.remove(r);
				}
			}
		}
		if (values.contains(researchContainer.getTileEntity().getSelected())) {
			selected = researchContainer.getTileEntity().getSelected();
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.id == 0) {
			System.out.println("clicked the select button, selected " + selected);
			researchContainer.getTileEntity().setSelected(selected);
			ResearchUpdatePacket researchUpdatePacket = new ResearchUpdatePacket(ResearchUpdatePacket.SELECTION_UPDATE,
					selected, researchContainer.getTileEntity().getPos());
			if (player.isCreative()) {
				researchUpdatePacket.setUnlock(true);
			}
			CommonProxy.simpleNetworkWrapper.sendToServer(researchUpdatePacket);
		}
	}

	private void applyScrollLimits() {
		int posY = (height - ySizeOfTexture) / 2;
		int top = posY + 25;
		int bottom = posY + 140;
		int scrollMax = values.size() * slotHeight - (bottom - top - 4);

		if (scrollMax < 0) {
			scrollMax /= 2;
		}

		if (scrollDistance < 0.0F) {
			scrollDistance = 0.0F;
		}

		if (scrollDistance > scrollMax) {
			scrollDistance = scrollMax;
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
		zLevel = 200.0F;
		itemRender.zLevel = 200.0F;
		FontRenderer font = stack.getItem().getFontRenderer(stack);
		if (font == null) {
			font = fontRenderer;
		}
		itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		zLevel = 0.0F;
		itemRender.zLevel = 0.0F;
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		drawDefaultBackground();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation(SuperTechTweaksMod.MODID, "textures/gui/researchpicker.png"));

		int posX = (width - xSizeOfTexture) / 2;
		int posY = (height - ySizeOfTexture) / 2;

		drawTexturedModalRect(posX, posY, 0, 0, xSizeOfTexture, ySizeOfTexture);
		mouseX = x;
		mouseY = y;
		top = posY + 10;
		bottom = posY + 170;
		topScrollBar = posY + 10;
		bottomScrollBar = posY + 170;
		int scrollBarXStart = posX + 235;
		int scrollBarXEnd = scrollBarXStart + 12;
		int boxLeft = posX + 50;
		int boxRight = scrollBarXStart - 1;
		int var10;
		int var13;
		int var19;

		if (Mouse.isButtonDown(0)) {
			if (initialMouseClickY == -1.0F) {
				boolean scrollValid = true;
				if (mouseX >= scrollBarXStart && mouseX <= scrollBarXEnd) {
					int function = -(posY - y) - 16;
					if (function < 0) {
						scrollButtonY = 0;
						return;
					} else if (function > 132) {
						scrollButtonY = 133;
						return;
					}
					scrollButtonY = function;
					int dir = Mouse.getDY();
					System.out.println("HEYO!");
					if (dir > 0) {
						scrollDistance -= slotHeight * 2 / 3;
						initialMouseClickY = -2.0F;
						applyScrollLimits();
					} else if (dir < 0) {
						scrollDistance += slotHeight * 2 / 3;
						initialMouseClickY = -2.0F;
						applyScrollLimits();
					}
				}
				if (mouseY >= topScrollBar && mouseY <= bottomScrollBar) {
					var10 = mouseY - top - 0 + (int) scrollDistance - 4;
					if (mouseX >= boxLeft && mouseX <= boxRight && var10 < 0) {
						scrollValid = false;
					}

					if (mouseX >= scrollBarXStart && mouseX <= scrollBarXEnd) {
						scrollFactor = -.4F;
						var19 = getContentHeight() - (bottom - top - 4);

						if (var19 < 1) {
							var19 = 1;
						}

						var13 = (int) ((float) ((bottom - top) * (bottom - top)) / (float) getContentHeight());

						if (var13 < 32) {
							var13 = 32;
						}

						if (var13 > bottom - top - 8) {
							var13 = bottom - top - 8;
						}

						scrollFactor /= (float) (bottom - top - var13) / (float) var19;
					} else {
						scrollFactor = .4F;
					}

					if (scrollValid) {
						initialMouseClickY = mouseY;
					} else {
						initialMouseClickY = -2.0F;
					}
				} else {
					initialMouseClickY = -2.0F;
				}
			} else if (initialMouseClickY >= 0.0F) {
				scrollDistance -= (mouseY - initialMouseClickY) * scrollFactor;
				initialMouseClickY = mouseY;
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

					scrollDistance += scroll * slotHeight / 2;
				}
			}

			initialMouseClickY = -1.0F;
		}
		applyScrollLimits();
		var10 = top - (int) scrollDistance;
		// TODO render selected unlocks

		if (!selected.equals(new ResourceLocation("none:none"))) {
			Research value = GameRegistry.findRegistry(Research.class).getValue(selected);
			fontRenderer.drawSplitString(value.getTitle(), posX + 5, posY + 5, 100, Color.black.getRGB());
			if (value.getDependentCount() != 0) {
				fontRenderer.drawString("Dependents:", posX + 5, posY + fontRenderer.FONT_HEIGHT * 2 + 5,
						Color.black.getRGB());
				int rowC = 3;
				for (int i = 0; i < value.getDependentCount(); i++) {
					List<String> split = fontRenderer.listFormattedStringToWidth(
							GameRegistry.findRegistry(Research.class).getValue(value.getDependents().get(i)).getTitle(),
							100);
					for (int j = 0; j < split.size(); j++) {
						if (j == 0) {
							fontRenderer.drawString(split.get(j), posX + 15,
									posY + fontRenderer.FONT_HEIGHT * (rowC) + 5, Color.black.getRGB());
						} else {
							fontRenderer.drawString(split.get(j), posX + 25,
									posY + fontRenderer.FONT_HEIGHT * (rowC) + 5, Color.black.getRGB());
						}
						rowC++;
					}
				}
			}
		}

		// cut out nonvisible stuff
		GuiResearchPicker.scissor(posX + 125, bottom - 12, 100, bottom - top - 12);
		for (int i = 0; i < values.size(); ++i) {
			var19 = var10 + i * slotHeight + 0;
			var13 = slotHeight - 4;

			if (var19 <= bottom && var19 + var13 >= top) {
				if (values.get(i).getRegistryName().equals(selected)) {
					Gui.drawRect(posX + 125, var19, posX + 125 + 100, var19 + slotHeight, 0xffa4a1a1);
				}
				fontRenderer.drawSplitString(values.get(i).getTitle(), posX + 145, var19 + slotHeight / 2 - 4, 75,
						Color.white.getRGB());

				drawItemStack(values.get(i).getDisplay(), posX + 125, var19 + slotHeight / 2 - 8, "");
			}
		}
		GL11.glDisable(GL11.GL_SCISSOR_TEST);

		var19 = getContentHeight() - (bottomScrollBar - topScrollBar - 4);

		if (var19 > 0) {
			var13 = (bottomScrollBar - topScrollBar) * (bottomScrollBar - topScrollBar) / getContentHeight();

			if (var13 < 32) {
				var13 = 32;
			}

			if (var13 > bottomScrollBar - topScrollBar - 8) {
				var13 = bottomScrollBar - topScrollBar - 8;
			}

			int scrollY = (int) scrollDistance * (bottomScrollBar - topScrollBar - var13) / var19 + topScrollBar;

			if (scrollY < topScrollBar) {
				scrollY = topScrollBar;
			}
			mc.renderEngine
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
		return values.size() * slotHeight;
	}

	@Override
	public void initGui() {
		buttonList.clear();

		int posX = (width - xSizeOfTexture) / 2;
		int posY = (height - ySizeOfTexture) / 2;

		buttonList.add(new GuiButton(0, posX + 7, posY + 135, 100, 20, "Select"));
	}

	@Override
	public void mouseClicked(int x, int y, int z) throws IOException {
		int var10 = top - (int) scrollDistance;

		int posX = (width - xSizeOfTexture) / 2;
		for (int i = 0; i < values.size(); ++i) {
			int var19 = var10 + i * slotHeight + 0;
			int var13 = slotHeight - 4;

			if (var19 <= bottom && var19 + var13 >= top) {

				if (x > posX + 125 && x < posX + 225 && y > var19 && y < var19 + slotHeight) {

					System.out.println(values.get(i).getTitle());
					selected = values.get(i).getRegistryName();
					return;
				}
			}
		}

		super.mouseClicked(x, y, z);
	}
}