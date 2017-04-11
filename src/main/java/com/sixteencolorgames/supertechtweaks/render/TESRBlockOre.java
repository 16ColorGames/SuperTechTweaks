package com.sixteencolorgames.supertechtweaks.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.sixteencolorgames.supertechtweaks.enums.Ores;
import com.sixteencolorgames.supertechtweaks.tileentities.TileEntityOre;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class TESRBlockOre extends TileEntitySpecialRenderer<TileEntityOre> {

    private static final ResourceLocation baseTexture = new ResourceLocation("minecraft:textures/blocks/stone.png");
    private static final ResourceLocation[] layerTextures = new ResourceLocation[]{
        new ResourceLocation("supertechtweaks:textures/blocks/ore1.png"),
        new ResourceLocation("supertechtweaks:textures/blocks/ore2.png"),
        new ResourceLocation("supertechtweaks:textures/blocks/ore3.png"),
        new ResourceLocation("supertechtweaks:textures/blocks/ore4.png"),
        new ResourceLocation("supertechtweaks:textures/blocks/ore5.png"),
        new ResourceLocation("supertechtweaks:textures/blocks/ore6.png"),
        new ResourceLocation("supertechtweaks:textures/blocks/ore7.png")};

    /**
     * render the tile entity - called every frame while the tileentity is in
     * view of the player
     *
     * @param tileEntity the associated tile entity
     * @param relativeX the x distance from the player's eye to the tileentity
     * @param relativeY the y distance from the player's eye to the tileentity
     * @param relativeZ the z distance from the player's eye to the tileentity
     * @param partialTicks the fraction of a tick that this frame is being
     * rendered at - used to interpolate frames between ticks, to make
     * animations smoother. For example - if the frame rate is steady at 80
     * frames per second, this method will be called four times per tick, with
     * partialTicks spaced 0.25 apart, (eg) 0, 0.25, 0.5, 0.75
     * @param blockDamageProgress the progress of the block being damaged (0 -
     * 10), if relevant. -1 if not relevant.
     */
    @Override
    public void renderTileEntityAt(TileEntityOre tileEntity, double relativeX, double relativeY, double relativeZ,
            float partialTicks, int blockDamageProgress) {
        if (!(tileEntity instanceof TileEntityOre)) {
            return; // should never happen
        }
        TileEntityOre tileEntityOre = tileEntity;

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

            // First we need to set up the translation so that we render our ore
            // with the bottom point at 0,0,0
            // when the renderTileEntityAt method is called, the tessellator is
            // set up so that drawing a dot at [0,0,0] corresponds to the
            // player's eyes
            // This means that, in order to draw a dot at the TileEntity
            // [x,y,z], we need to translate the reference frame by the
            // difference between the
            // two points, i.e. by the [relativeX, relativeY, relativeZ] passed
            // to the method. If you then draw a cube from [0,0,0] to [1,1,1],
            // it will render exactly over the top of the TileEntity's block.
            GlStateManager.translate(relativeX, relativeY, relativeZ);

            Tessellator tessellator = Tessellator.getInstance();
            VertexBuffer vertexBuffer = tessellator.getBuffer();
            this.bindTexture(baseTexture); // texture for the stone appearance

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
            GL11.glDepthMask(true); // ore is hidden behind other objects

            // set the rendering colour
            float red = 1, green = 1, blue = 1;
            GlStateManager.color(red, green, blue); // change the rendering
            // colour

            vertexBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);//set the system to draw quads, not triangles
            addBlockVertecies(vertexBuffer);
            tessellator.draw();
            for (int i = 0; i < 7; i++) {//for each of the ores inside the block
                int metal = tileEntity.getOres()[i];
                if (metal != Ores.NONE.ordinal()) {
                    this.bindTexture(layerTextures[i]);//set the ore layer texture
                    Ores met = Ores.values()[metal];
                    Color color = Color.decode(met.getColor());//decode the color from the Ores enum
                    GlStateManager.color(((float) color.getRed()) / 255, ((float) color.getGreen()) / 255,
                            ((float) color.getBlue()) / 255);//set the render color
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
            {1.000, 0.000, 1.000, 1.000, 1.000}, // 1
            {1.000, 1.000, 1.000, 1.000, 0.000},
            {0.000, 1.000, 1.000, 0.000, 0.000},
            {0.000, 0.000, 1.000, 0.000, 1.000},
            {0.000, 0.000, 0.000, 1.000, 1.000}, // 2
            {0.000, 1.000, 0.000, 1.000, 0.000},
            {1.000, 1.000, 0.000, 0.000, 0.000},
            {1.000, 0.000, 0.000, 0.000, 1.000},
            {1.000, 0.000, 0.000, 1.000, 1.000}, // 3
            {1.000, 1.000, 0.000, 1.000, 0.000},
            {1.000, 1.000, 1.000, 0.000, 0.000},
            {1.000, 0.000, 1.000, 0.000, 1.000},
            {0.000, 0.000, 1.000, 1.000, 1.000}, // 4
            {0.000, 1.000, 1.000, 1.000, 0.000},
            {0.000, 1.000, 0.000, 0.000, 0.000},
            {0.000, 0.000, 0.000, 0.000, 1.000},
            {0.000, 0.000, 1.000, 1.000, 1.000}, // 5
            {0.000, 0.000, 0.000, 1.000, 0.000},
            {1.000, 0.000, 0.000, 0.000, 0.000},
            {1.000, 0.000, 1.000, 0.000, 1.000},
            {0.000, 1.000, 0.000, 1.000, 1.000}, // 6
            {0.000, 1.000, 1.000, 1.000, 0.000},
            {1.000, 1.000, 1.000, 0.000, 0.000},
            {1.000, 1.000, 0.000, 0.000, 1.000}
        };

        for (double[] vertex : vertexTable) {
            vertexBuffer.pos(vertex[0], vertex[1], vertex[2]).tex(vertex[3], vertex[4]).endVertex();
        }
    }
}
