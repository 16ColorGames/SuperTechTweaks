package com.sixteencolorgames.supertechtweaks.gui;

import com.google.common.collect.Lists;
import com.sixteencolorgames.supertechtweaks.enums.Research;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiResearch extends Gui {
	private static final ResourceLocation WIDGETS = new ResourceLocation("textures/gui/researchs/widgets.png");
	private static final Pattern PATTERN = Pattern.compile("(.+) \\S+");
	private final GuiResearchTab guiResearchTab;
	private final Research research;
	private final DisplayInfo displayInfo;
	private final String title;
	private final int width;
	private final List<String> description;
	private final Minecraft minecraft;
	private GuiResearch parent;
	private final List<GuiResearch> children = Lists.<GuiResearch> newArrayList();
	private ResearchProgress researchProgress;
	private final int x;
	private final int y;

	public GuiResearch(GuiResearchTab p_i47385_1_, Minecraft p_i47385_2_, Research p_i47385_3_,
			DisplayInfo p_i47385_4_) {
		this.guiResearchTab = p_i47385_1_;
		this.research = p_i47385_3_;
		this.displayInfo = p_i47385_4_;
		this.minecraft = p_i47385_2_;
		this.title = p_i47385_2_.fontRenderer.trimStringToWidth(p_i47385_4_.getTitle().getFormattedText(), 163);
		this.x = MathHelper.floor(p_i47385_4_.getX() * 28.0F);
		this.y = MathHelper.floor(p_i47385_4_.getY() * 27.0F);
		int i = p_i47385_3_.getRequirementCount();
		int j = String.valueOf(i).length();
		int k = i > 1 ? p_i47385_2_.fontRenderer.getStringWidth("  ")
				+ p_i47385_2_.fontRenderer.getStringWidth("0") * j * 2 + p_i47385_2_.fontRenderer.getStringWidth("/")
				: 0;
		int l = 29 + p_i47385_2_.fontRenderer.getStringWidth(this.title) + k;
		String s = p_i47385_4_.getDescription().getFormattedText();
		this.description = this.findOptimalLines(s, l);

		for (String s1 : this.description) {
			l = Math.max(l, p_i47385_2_.fontRenderer.getStringWidth(s1));
		}

		this.width = l + 3 + 5;
	}

	private List<String> findOptimalLines(String p_192995_1_, int p_192995_2_) {
		if (p_192995_1_.isEmpty()) {
			return Collections.<String> emptyList();
		} else {
			List<String> list = this.minecraft.fontRenderer.listFormattedStringToWidth(p_192995_1_, p_192995_2_);

			if (list.size() < 2) {
				return list;
			} else {
				String s = list.get(0);
				String s1 = list.get(1);
				int i = this.minecraft.fontRenderer.getStringWidth(s + ' ' + s1.split(" ")[0]);

				if (i - p_192995_2_ <= 10) {
					return this.minecraft.fontRenderer.listFormattedStringToWidth(p_192995_1_, i);
				} else {
					Matcher matcher = PATTERN.matcher(s);

					if (matcher.matches()) {
						int j = this.minecraft.fontRenderer.getStringWidth(matcher.group(1));

						if (p_192995_2_ - j <= 10) {
							return this.minecraft.fontRenderer.listFormattedStringToWidth(p_192995_1_, j);
						}
					}

					return list;
				}
			}
		}
	}

	@Nullable
	private GuiResearch getFirstVisibleParent(Research researchIn) {
		while (true) {
			researchIn = researchIn.getParent();

			if (researchIn == null || researchIn.getDisplay() != null) {
				break;
			}
		}

		if (researchIn != null && researchIn.getDisplay() != null) {
			return this.guiResearchTab.getResearchGui(researchIn);
		} else {
			return null;
		}
	}

	public void drawConnectivity(int p_191819_1_, int p_191819_2_, boolean p_191819_3_) {
		if (this.parent != null) {
			int i = p_191819_1_ + this.parent.x + 13;
			int j = p_191819_1_ + this.parent.x + 26 + 4;
			int k = p_191819_2_ + this.parent.y + 13;
			int l = p_191819_1_ + this.x + 13;
			int i1 = p_191819_2_ + this.y + 13;
			int j1 = p_191819_3_ ? -16777216 : -1;

			if (p_191819_3_) {
				this.drawHorizontalLine(j, i, k - 1, j1);
				this.drawHorizontalLine(j + 1, i, k, j1);
				this.drawHorizontalLine(j, i, k + 1, j1);
				this.drawHorizontalLine(l, j - 1, i1 - 1, j1);
				this.drawHorizontalLine(l, j - 1, i1, j1);
				this.drawHorizontalLine(l, j - 1, i1 + 1, j1);
				this.drawVerticalLine(j - 1, i1, k, j1);
				this.drawVerticalLine(j + 1, i1, k, j1);
			} else {
				this.drawHorizontalLine(j, i, k, j1);
				this.drawHorizontalLine(l, j, i1, j1);
				this.drawVerticalLine(j, i1, k, j1);
			}
		}

		for (GuiResearch guiresearch : this.children) {
			guiresearch.drawConnectivity(p_191819_1_, p_191819_2_, p_191819_3_);
		}
	}

	public void draw(int p_191817_1_, int p_191817_2_) {
		if (!this.displayInfo.isHidden() || this.researchProgress != null && this.researchProgress.isDone()) {
			float f = this.researchProgress == null ? 0.0F : this.researchProgress.getPercent();
			ResearchState researchstate;

			if (f >= 1.0F) {
				researchstate = ResearchState.OBTAINED;
			} else {
				researchstate = ResearchState.UNOBTAINED;
			}

			this.minecraft.getTextureManager().bindTexture(WIDGETS);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableBlend();
			this.drawTexturedModalRect(p_191817_1_ + this.x + 3, p_191817_2_ + this.y,
					this.displayInfo.getFrame().getIcon(), 128 + researchstate.getId() * 26, 26, 26);
			RenderHelper.enableGUIStandardItemLighting();
			this.minecraft.getRenderItem().renderItemAndEffectIntoGUI((EntityLivingBase) null,
					this.displayInfo.getIcon(), p_191817_1_ + this.x + 8, p_191817_2_ + this.y + 5);
		}

		for (GuiResearch guiresearch : this.children) {
			guiresearch.draw(p_191817_1_, p_191817_2_);
		}
	}

	public void getResearchProgress(ResearchProgress researchProgressIn) {
		this.researchProgress = researchProgressIn;
	}

	public void addGuiResearch(GuiResearch guiResearchIn) {
		this.children.add(guiResearchIn);
	}

	public void drawHover(int p_191821_1_, int p_191821_2_, float p_191821_3_, int p_191821_4_, int p_191821_5_) {
		boolean flag = p_191821_4_ + p_191821_1_ + this.x + this.width + 26 >= this.guiResearchTab.getScreen().width;
		String s = this.researchProgress == null ? null : this.researchProgress.getProgressText();
		int i = s == null ? 0 : this.minecraft.fontRenderer.getStringWidth(s);
		boolean flag1 = 113 - p_191821_2_ - this.y - 26 <= 6
				+ this.description.size() * this.minecraft.fontRenderer.FONT_HEIGHT;
		float f = this.researchProgress == null ? 0.0F : this.researchProgress.getPercent();
		int j = MathHelper.floor(f * (float) this.width);
		ResearchState researchstate;
		ResearchState researchstate1;
		ResearchState researchstate2;

		if (f >= 1.0F) {
			j = this.width / 2;
			researchstate = ResearchState.OBTAINED;
			researchstate1 = ResearchState.OBTAINED;
			researchstate2 = ResearchState.OBTAINED;
		} else if (j < 2) {
			j = this.width / 2;
			researchstate = ResearchState.UNOBTAINED;
			researchstate1 = ResearchState.UNOBTAINED;
			researchstate2 = ResearchState.UNOBTAINED;
		} else if (j > this.width - 2) {
			j = this.width / 2;
			researchstate = ResearchState.OBTAINED;
			researchstate1 = ResearchState.OBTAINED;
			researchstate2 = ResearchState.UNOBTAINED;
		} else {
			researchstate = ResearchState.OBTAINED;
			researchstate1 = ResearchState.UNOBTAINED;
			researchstate2 = ResearchState.UNOBTAINED;
		}

		int k = this.width - j;
		this.minecraft.getTextureManager().bindTexture(WIDGETS);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableBlend();
		int l = p_191821_2_ + this.y;
		int i1;

		if (flag) {
			i1 = p_191821_1_ + this.x - this.width + 26 + 6;
		} else {
			i1 = p_191821_1_ + this.x;
		}

		int j1 = 32 + this.description.size() * this.minecraft.fontRenderer.FONT_HEIGHT;

		if (!this.description.isEmpty()) {
			if (flag1) {
				this.render9Sprite(i1, l + 26 - j1, this.width, j1, 10, 200, 26, 0, 52);
			} else {
				this.render9Sprite(i1, l, this.width, j1, 10, 200, 26, 0, 52);
			}
		}

		this.drawTexturedModalRect(i1, l, 0, researchstate.getId() * 26, j, 26);
		this.drawTexturedModalRect(i1 + j, l, 200 - k, researchstate1.getId() * 26, k, 26);
		this.drawTexturedModalRect(p_191821_1_ + this.x + 3, p_191821_2_ + this.y,
				this.displayInfo.getFrame().getIcon(), 128 + researchstate2.getId() * 26, 26, 26);

		if (flag) {
			this.minecraft.fontRenderer.drawString(this.title, (float) (i1 + 5), (float) (p_191821_2_ + this.y + 9), -1,
					true);

			if (s != null) {
				this.minecraft.fontRenderer.drawString(s, (float) (p_191821_1_ + this.x - i),
						(float) (p_191821_2_ + this.y + 9), -1, true);
			}
		} else {
			this.minecraft.fontRenderer.drawString(this.title, (float) (p_191821_1_ + this.x + 32),
					(float) (p_191821_2_ + this.y + 9), -1, true);

			if (s != null) {
				this.minecraft.fontRenderer.drawString(s, (float) (p_191821_1_ + this.x + this.width - i - 5),
						(float) (p_191821_2_ + this.y + 9), -1, true);
			}
		}

		if (flag1) {
			for (int k1 = 0; k1 < this.description.size(); ++k1) {
				this.minecraft.fontRenderer.drawString(this.description.get(k1), (float) (i1 + 5),
						(float) (l + 26 - j1 + 7 + k1 * this.minecraft.fontRenderer.FONT_HEIGHT), -5592406, false);
			}
		} else {
			for (int l1 = 0; l1 < this.description.size(); ++l1) {
				this.minecraft.fontRenderer.drawString(this.description.get(l1), (float) (i1 + 5),
						(float) (p_191821_2_ + this.y + 9 + 17 + l1 * this.minecraft.fontRenderer.FONT_HEIGHT),
						-5592406, false);
			}
		}

		RenderHelper.enableGUIStandardItemLighting();
		this.minecraft.getRenderItem().renderItemAndEffectIntoGUI((EntityLivingBase) null, this.displayInfo.getIcon(),
				p_191821_1_ + this.x + 8, p_191821_2_ + this.y + 5);
	}

	protected void render9Sprite(int p_192994_1_, int p_192994_2_, int p_192994_3_, int p_192994_4_, int p_192994_5_,
			int p_192994_6_, int p_192994_7_, int p_192994_8_, int p_192994_9_) {
		this.drawTexturedModalRect(p_192994_1_, p_192994_2_, p_192994_8_, p_192994_9_, p_192994_5_, p_192994_5_);
		this.renderRepeating(p_192994_1_ + p_192994_5_, p_192994_2_, p_192994_3_ - p_192994_5_ - p_192994_5_,
				p_192994_5_, p_192994_8_ + p_192994_5_, p_192994_9_, p_192994_6_ - p_192994_5_ - p_192994_5_,
				p_192994_7_);
		this.drawTexturedModalRect(p_192994_1_ + p_192994_3_ - p_192994_5_, p_192994_2_,
				p_192994_8_ + p_192994_6_ - p_192994_5_, p_192994_9_, p_192994_5_, p_192994_5_);
		this.drawTexturedModalRect(p_192994_1_, p_192994_2_ + p_192994_4_ - p_192994_5_, p_192994_8_,
				p_192994_9_ + p_192994_7_ - p_192994_5_, p_192994_5_, p_192994_5_);
		this.renderRepeating(p_192994_1_ + p_192994_5_, p_192994_2_ + p_192994_4_ - p_192994_5_,
				p_192994_3_ - p_192994_5_ - p_192994_5_, p_192994_5_, p_192994_8_ + p_192994_5_,
				p_192994_9_ + p_192994_7_ - p_192994_5_, p_192994_6_ - p_192994_5_ - p_192994_5_, p_192994_7_);
		this.drawTexturedModalRect(p_192994_1_ + p_192994_3_ - p_192994_5_, p_192994_2_ + p_192994_4_ - p_192994_5_,
				p_192994_8_ + p_192994_6_ - p_192994_5_, p_192994_9_ + p_192994_7_ - p_192994_5_, p_192994_5_,
				p_192994_5_);
		this.renderRepeating(p_192994_1_, p_192994_2_ + p_192994_5_, p_192994_5_,
				p_192994_4_ - p_192994_5_ - p_192994_5_, p_192994_8_, p_192994_9_ + p_192994_5_, p_192994_6_,
				p_192994_7_ - p_192994_5_ - p_192994_5_);
		this.renderRepeating(p_192994_1_ + p_192994_5_, p_192994_2_ + p_192994_5_,
				p_192994_3_ - p_192994_5_ - p_192994_5_, p_192994_4_ - p_192994_5_ - p_192994_5_,
				p_192994_8_ + p_192994_5_, p_192994_9_ + p_192994_5_, p_192994_6_ - p_192994_5_ - p_192994_5_,
				p_192994_7_ - p_192994_5_ - p_192994_5_);
		this.renderRepeating(p_192994_1_ + p_192994_3_ - p_192994_5_, p_192994_2_ + p_192994_5_, p_192994_5_,
				p_192994_4_ - p_192994_5_ - p_192994_5_, p_192994_8_ + p_192994_6_ - p_192994_5_,
				p_192994_9_ + p_192994_5_, p_192994_6_, p_192994_7_ - p_192994_5_ - p_192994_5_);
	}

	protected void renderRepeating(int p_192993_1_, int p_192993_2_, int p_192993_3_, int p_192993_4_, int p_192993_5_,
			int p_192993_6_, int p_192993_7_, int p_192993_8_) {
		for (int i = 0; i < p_192993_3_; i += p_192993_7_) {
			int j = p_192993_1_ + i;
			int k = Math.min(p_192993_7_, p_192993_3_ - i);

			for (int l = 0; l < p_192993_4_; l += p_192993_8_) {
				int i1 = p_192993_2_ + l;
				int j1 = Math.min(p_192993_8_, p_192993_4_ - l);
				this.drawTexturedModalRect(j, i1, p_192993_5_, p_192993_6_, k, j1);
			}
		}
	}

	public boolean isMouseOver(int p_191816_1_, int p_191816_2_, int p_191816_3_, int p_191816_4_) {
		if (!this.displayInfo.isHidden() || this.researchProgress != null && this.researchProgress.isDone()) {
			int i = p_191816_1_ + this.x;
			int j = i + 26;
			int k = p_191816_2_ + this.y;
			int l = k + 26;
			return p_191816_3_ >= i && p_191816_3_ <= j && p_191816_4_ >= k && p_191816_4_ <= l;
		} else {
			return false;
		}
	}

	public void attachToParent() {
		if (this.parent == null && this.research.getParent() != null) {
			this.parent = this.getFirstVisibleParent(this.research);

			if (this.parent != null) {
				this.parent.addGuiResearch(this);
			}
		}
	}

	public int getY() {
		return this.y;
	}

	public int getX() {
		return this.x;
	}
}