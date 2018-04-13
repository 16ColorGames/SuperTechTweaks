package com.sixteencolorgames.supertechtweaks.gui;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.math.MathHelper;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * Thanks to https://github.com/diesieben07/SevenCommons for this class, this
 * project will switch to that when (if) it goes public
 *
 */
public abstract class ScrollPane extends Gui {

	private boolean clip;

	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int contentHeight;

	protected int scrollbarWidth = 10;
	protected int scrollbarHeight = 30;
	private int scrollbarClickOffset = 0;
	private int scrollbarY = 0;
	private boolean isDragging = false;

	private final GuiScreen screen;
	protected final Minecraft mc = Minecraft.getMinecraft();
	private final List<GuiButton> buttons = Lists.newArrayList();

	public ScrollPane(GuiScreen screen, int x, int y, int width, int height, int contentHeight) {
		this.screen = screen;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.contentHeight = contentHeight;
	}

	private int newScrollIfInside;

	public final void onMouseWheel(int dWheel) {
		if (needsScrollbar() && dWheel != 0) {
			dWheel = -Integer.signum(dWheel) * 5;
			newScrollIfInside = MathHelper.clamp(scrollbarY + dWheel, -1, height - scrollbarHeight);
		}
	}

	public final void draw(int mouseX, int mouseY) {
		if (newScrollIfInside >= 0 && isPointInRegion(x, y, width, height, mouseX, mouseY)) {
			scrollbarY = newScrollIfInside;
			newScrollIfInside = -1;
		}

		float yTranslate = computeYTranslate();
		mouseX -= x;
		mouseY -= yTranslate;

		glColor3f(1, 1, 1);
		int scale = computeGuiScale();

		if (clip) {
			glScissor(0, mc.displayHeight - (y + height) * scale, (width + x) * scale, height * scale);
			glEnable(GL_SCISSOR_TEST);
		}

		glPushMatrix();

		glTranslatef(x, yTranslate, 0);

		drawInternal(mouseX, mouseY);

		glPopMatrix();

		if (clip) {
			glDisable(GL_SCISSOR_TEST);
		}

		if (needsScrollbar()) {
			int scrollbarX = x + (width - scrollbarWidth);
			drawScrollbar(scrollbarX, y + scrollbarY, scrollbarX + scrollbarWidth, y + scrollbarY + scrollbarHeight);
		}
	}

	/**
	 * <p>
	 * Computes the current GUI scale. Calling this method is equivalent to the
	 * following:
	 * 
	 * <pre>
	 * <code>
	 * Minecraft mc = Minecraft.getMinecraft();
	 * int scale = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight).getScaleFactor();</code>
	 * </pre>
	 * </p>
	 *
	 * @return the current GUI scale
	 */
	public static int computeGuiScale() {
		Minecraft mc = Minecraft.getMinecraft();
		int scaleFactor = 1;

		int k = mc.gameSettings.guiScale;

		if (k == 0) {
			k = 1000;
		}

		while (scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320
				&& mc.displayHeight / (scaleFactor + 1) >= 240) {
			++scaleFactor;
		}
		return scaleFactor;
	}

	private float computeYTranslate() {
		return y - ((scrollbarY) / (float) (height - scrollbarHeight)) * (contentHeight - height);
	}

	private void drawInternal(int mouseX, int mouseY) {
		drawImpl(mouseX, mouseY);
		int n = buttons.size();
		for (int i = 0; i < n; ++i) {
			buttons.get(i).drawButton(mc, mouseX, mouseY, 0);
		}
	}

	private boolean needsScrollbar() {
		return height < contentHeight;
	}

	public final void onMouseClick(int mouseX, int mouseY, int btn) {
		if (btn == 0 && needsScrollbar()) {
			if (isPointInRegion(x + (width - scrollbarWidth), y + scrollbarY, scrollbarWidth, scrollbarHeight, mouseX,
					mouseY)) {
				isDragging = true;
				scrollbarClickOffset = mouseY - (y + scrollbarY);
			}
		}

		mouseX -= x;
		mouseY -= computeYTranslate();
		if (isPointInRegion(0, 0, width, height, mouseX, mouseY)) {
			for (GuiButton button : buttons) {
				if (button.mousePressed(mc, mouseX, mouseY)) {
					// SCReflector.instance.actionPerformed(screen, button);
				}
			}
		}
		handleMouseClick(mouseX, mouseY, btn);
	}

	/**
	 * <p>
	 * Determine whether the point with given {@code pointX} and {@code pointY}
	 * coordinates is in the rectangle at position {@code x, y} and with
	 * dimensions {@code width} and {@code height}.
	 * </p>
	 *
	 * @param x
	 *            x position of the rectangle
	 * @param y
	 *            y position of the rectangle
	 * @param width
	 *            width of the rectangle
	 * @param height
	 *            height of the rectangle
	 * @param pointX
	 *            x position of the point to check
	 * @param pointY
	 *            y position of the point to check
	 * @return whether the point lies within the bounds
	 */
	public static boolean isPointInRegion(int x, int y, int width, int height, int pointX, int pointY) {
		return pointX >= x && pointX < x + width && pointY >= y && pointY < y + height;
	}

	public final void onMouseBtnReleased(int btn) {
		if (btn == 0) {
			isDragging = false;
		}
	}

	public final void onMouseMoved(int mouseX, int mouseY) {
		if (isDragging) {
			scrollbarY = MathHelper.clamp(mouseY - y - scrollbarClickOffset, 0, height - scrollbarHeight);
		}
	}

	public final void setScrollbarWidth(int scrollbarWidth) {
		this.scrollbarWidth = scrollbarWidth;
	}

	public final void setX(int x) {
		this.x = x;
	}

	public final void setY(int y) {
		this.y = y;
	}

	public final void setWidth(int width) {
		this.width = width;
	}

	public final void setHeight(int height) {
		if (this.height != height) {
			recalcScrollbar();
		}
		this.height = height;
	}

	public final void setContentHeight(int contentHeight) {
		if (this.contentHeight != contentHeight) {
			recalcScrollbar();
		}
		this.contentHeight = contentHeight;
	}

	private void recalcScrollbar() {
		scrollbarY = 0;
		isDragging = false;
	}

	public final void clearButtons() {
		buttons.clear();
	}

	public final void addButton(GuiButton button) {
		buttons.add(button);
	}

	public final void setClip(boolean clip) {
		this.clip = clip;
	}

	protected void drawScrollbar(int x, int y, int x2, int y2) {
		drawRect(x, y, x2, y2, 0xff000000);
		drawRect(x, y, x + 1, y2, 0xff444444);
		drawRect(x2 - 1, y, x2, y2, 0xff444444);
		drawRect(x, y, x2, y + 1, 0xff444444);
		drawRect(x, y2 - 1, x2, y2, 0xff444444);
	}

	protected void drawImpl(int mouseX, int mouseY) {
		drawImpl();
	}

	protected abstract void drawImpl();

	protected void handleMouseClick(int relX, int relY, int btn) {
	}

}