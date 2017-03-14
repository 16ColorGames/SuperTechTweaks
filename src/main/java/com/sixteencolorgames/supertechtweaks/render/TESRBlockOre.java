package com.sixteencolorgames.supertechtweaks.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.sixteencolorgames.supertechtweaks.enums.Metals;
import com.sixteencolorgames.supertechtweaks.tileentities.TileEntityOre;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import scala.actors.threadpool.Arrays;

public class TESRBlockOre extends TileEntitySpecialRenderer<TileEntityOre> {

	private static final ResourceLocation baseTexture = new ResourceLocation("minecraft:textures/blocks/stone.png");
	private static final ResourceLocation[] layerTextures = new ResourceLocation[] {
			new ResourceLocation("supertechtweaks:textures/blocks/ore1.png"),
			new ResourceLocation("supertechtweaks:textures/blocks/ore2.png"),
			new ResourceLocation("supertechtweaks:textures/blocks/ore3.png"),
			new ResourceLocation("supertechtweaks:textures/blocks/ore4.png"),
			new ResourceLocation("supertechtweaks:textures/blocks/ore5.png"),
			new ResourceLocation("supertechtweaks:textures/blocks/ore6.png"),
			new ResourceLocation("supertechtweaks:textures/blocks/ore7.png") };
	private static final ResourceLocation layer1Texture = new ResourceLocation(
			"supertechtweaks:textures/blocks/ore1.png");

	/**
	 * render the tile entity - called every frame while the tileentity is in
	 * view of the player
	 * 
	 * @param tileEntity
	 *            the associated tile entity
	 * @param relativeX
	 *            the x distance from the player's eye to the tileentity
	 * @param relativeY
	 *            the y distance from the player's eye to the tileentity
	 * @param relativeZ
	 *            the z distance from the player's eye to the tileentity
	 * @param partialTicks
	 *            the fraction of a tick that this frame is being rendered at -
	 *            used to interpolate frames between ticks, to make animations
	 *            smoother. For example - if the frame rate is steady at 80
	 *            frames per second, this method will be called four times per
	 *            tick, with partialTicks spaced 0.25 apart, (eg) 0, 0.25, 0.5,
	 *            0.75
	 * @param blockDamageProgress
	 *            the progress of the block being damaged (0 - 10), if relevant.
	 *            -1 if not relevant.
	 */
	@Override
	public void renderTileEntityAt(TileEntityOre tileEntity, double relativeX, double relativeY, double relativeZ,
			float partialTicks, int blockDamageProgress) {
		if (!(tileEntity instanceof TileEntityOre))
			return; // should never happen
		TileEntityOre tileEntityOre = tileEntity;

		// the gem changes its appearance and animation as the player
		// approaches.
		// When the player is a long distance away, the gem is dark, resting in
		// the hopper, and does not rotate.
		// As the player approaches closer than 16 blocks, the gem first starts
		// to glow brighter and to spin anti-clockwise
		// When the player gets closer than 4 blocks, the gem is at maximum
		// speed and brightness, and starts to levitate above the pedestal
		// Once the player gets closer than 2 blocks, the gem reaches maximum
		// height.

		// the appearance and animation of the gem is hence made up of several
		// parts:
		// 1) the colour of the gem, which is contained in the tileEntity
		// 2) the brightness of the gem, which depends on player distance
		// 3) the distance that the gem rises above the pedestal, which depends
		// on player distance
		// 4) the speed at which the gem is spinning, which depends on player
		// distance.
		Vec3d playerEye = new Vec3d(0.0, 0.0, 0.0);

		try {
			// save the transformation matrix and the rendering attributes, so
			// that we can restore them after rendering. This
			// prevents us disrupting any vanilla TESR that render after ours.
			// using try..finally is not essential but helps make it more robust
			// in case of exceptions
			// For further information on rendering using the Tessellator, see
			// http://greyminecraftcoder.blogspot.co.at/2014/12/the-tessellator-and-worldrenderer-18.html
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);

			// First we need to set up the translation so that we render our gem
			// with the bottom point at 0,0,0
			// when the renderTileEntityAt method is called, the tessellator is
			// set up so that drawing a dot at [0,0,0] corresponds to the
			// player's eyes
			// This means that, in order to draw a dot at the TileEntity
			// [x,y,z], we need to translate the reference frame by the
			// difference between the
			// two points, i.e. by the [relativeX, relativeY, relativeZ] passed
			// to the method. If you then draw a cube from [0,0,0] to [1,1,1],
			// it will
			// render exactly over the top of the TileEntity's block.
			// In this example, the zero point of our model needs to be in the
			// middle of the block, not at the [x,y,z] of the block, so we need
			// to
			// add an extra offset as well, i.e. [gemCentreOffsetX,
			// gemCentreOffsetY, gemCentreOffsetZ]
			GlStateManager.translate(relativeX, relativeY, relativeZ);

			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer vertexBuffer = tessellator.getBuffer();
			this.bindTexture(baseTexture); // texture for the gem appearance

			// fix dark lighting issue
			int li = tileEntity.getWorld().getCombinedLight(tileEntity.getPos(), 15728640);
			int i1 = li % 65536;
			int j1 = li / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) i1, (float) j1);

			// set the key rendering flags appropriately...
			GL11.glDisable(GL11.GL_LIGHTING); // turn off "item" lighting (face
												// brightness depends on which
												// direction it is facing)
			GL11.glDisable(GL11.GL_BLEND); // turn off "alpha" transparency
											// blending
			GL11.glDepthMask(true); // gem is hidden behind other objects

			// set the rendering colour
			float red = 1, green = 1, blue = 1;
			GlStateManager.color(red, green, blue); // change the rendering
													// colour

			vertexBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			addBlockVertecies(vertexBuffer);
			tessellator.draw();
			for (int i = 0; i < 7; i++) {
				int metal = tileEntity.getOres()[i];
				if (metal != Metals.NONE.ordinal()) {
					this.bindTexture(layerTextures[i]);
					Metals met = Metals.values()[metal];
					Color color = Color.decode(met.getColor());
					GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue());

					vertexBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
					addBlockVertecies(vertexBuffer);
					tessellator.draw();
				}
			}

		} finally {
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
	}

	private void addBlockVertecies(VertexBuffer vertexBuffer) {
		final double[][] vertexTable = {

				{ 1.000, 0.000, 1.000, 1.000, 1.000 }, // 1
				{ 1.000, 1.000, 1.000, 1.000, 0.000 }, { 0.000, 1.000, 1.000, 0.000, 0.000 },
				{ 0.000, 0.000, 1.000, 0.000, 1.000 },

				{ 0.000, 0.000, 0.000, 1.000, 1.000 }, // 2
				{ 0.000, 1.000, 0.000, 1.000, 0.000 }, { 1.000, 1.000, 0.000, 0.000, 0.000 },
				{ 1.000, 0.000, 0.000, 0.000, 1.000 },

				{ 1.000, 0.000, 0.000, 1.000, 1.000 }, // 3
				{ 1.000, 1.000, 0.000, 1.000, 0.000 }, { 1.000, 1.000, 1.000, 0.000, 0.000 },
				{ 1.000, 0.000, 1.000, 0.000, 1.000 },

				{ 0.000, 0.000, 1.000, 1.000, 1.000 }, // 4
				{ 0.000, 1.000, 1.000, 1.000, 0.000 }, { 0.000, 1.000, 0.000, 0.000, 0.000 },
				{ 0.000, 0.000, 0.000, 0.000, 1.000 },

				{ 0.000, 0.000, 1.000, 1.000, 1.000 }, // 5
				{ 0.000, 0.000, 0.000, 1.000, 0.000 }, { 1.000, 0.000, 0.000, 0.000, 0.000 },
				{ 1.000, 0.000, 1.000, 0.000, 1.000 },

				{ 0.000, 1.000, 0.000, 1.000, 1.000 }, // 6
				{ 0.000, 1.000, 1.000, 1.000, 0.000 }, { 1.000, 1.000, 1.000, 0.000, 0.000 },
				{ 1.000, 1.000, 0.000, 0.000, 1.000 }

		};

		for (double[] vertex : vertexTable) {
			vertexBuffer.pos(vertex[0], vertex[1], vertex[2]).tex(vertex[3], vertex[4]).endVertex();
		}
	}
}
