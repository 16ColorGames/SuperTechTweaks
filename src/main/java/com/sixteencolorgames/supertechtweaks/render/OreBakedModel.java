/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.blocks.BlockOre;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;

/**
 *
 * @author oa10712
 */
public class OreBakedModel implements IBakedModel {

	public static final ModelResourceLocation BAKED_MODEL = new ModelResourceLocation(
			SuperTechTweaksMod.MODID + ":superore");

	private TextureAtlasSprite[] ore1;
	private TextureAtlasSprite stone;
	private TextureAtlasSprite nether;
	private TextureAtlasSprite end;
	private VertexFormat format;

	public OreBakedModel(IModelState state, VertexFormat format,
			Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		this.format = format;
		ore1 = new TextureAtlasSprite[7];
		for (int i = 0; i < 7 || i < ore1.length; i++) {
			ore1[i] = bakedTextureGetter.apply(new ResourceLocation(SuperTechTweaksMod.MODID, "blocks/ore" + (i + 1)));
		}
		stone = bakedTextureGetter.apply(new ResourceLocation("minecraft", "blocks/stone"));
		nether = bakedTextureGetter.apply(new ResourceLocation("minecraft", "blocks/netherrack"));
		end = bakedTextureGetter.apply(new ResourceLocation("minecraft", "blocks/endstone"));

	}

	private BakedQuad createQuad(Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, TextureAtlasSprite sprite, Color color,
			int tint) {
		Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();

		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
		builder.setTexture(sprite);
		builder.setQuadTint(tint);
		putVertex(builder, normal, v1.x, v1.y, v1.z, 0, 0, color, sprite);
		putVertex(builder, normal, v2.x, v2.y, v2.z, 0, 16, color, sprite);
		putVertex(builder, normal, v3.x, v3.y, v3.z, 16, 16, color, sprite);
		putVertex(builder, normal, v4.x, v4.y, v4.z, 16, 0, color, sprite);
		return builder.build();
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public ItemOverrideList getOverrides() {
		return null;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return ore1[0];
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		if (side != null) {
			return Collections.emptyList();
		}

		IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;
		Integer[] ores = extendedBlockState.getValue(BlockOre.ORES);
		Byte base = extendedBlockState.getValue(BlockOre.BASE);

		List<BakedQuad> quads = new ArrayList<>();

		switch (base) {
		default:
			quads.add(createQuad(new Vec3d(1, 1, 0), new Vec3d(0, 1, 0), new Vec3d(0, 1, 1), new Vec3d(1, 1, 1), stone,
					Color.white, 0));
			quads.add(createQuad(new Vec3d(1, 0, 1), new Vec3d(0, 0, 1), new Vec3d(0, 0, 0), new Vec3d(1, 0, 0), stone,
					Color.white, 0));
			quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(1, 0, 0), new Vec3d(1, 1, 0), stone,
					Color.white, 0));
			quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), new Vec3d(0, 0, 1), new Vec3d(0, 1, 1), stone,
					Color.white, 0));
			quads.add(createQuad(new Vec3d(1, 0, 0), new Vec3d(0, 0, 0), new Vec3d(0, 1, 0), new Vec3d(1, 1, 0), stone,
					Color.white, 0));
			quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), new Vec3d(1, 0, 1), stone,
					Color.white, 0));
			break;
		case 1:
			quads.add(createQuad(new Vec3d(1, 1, 0), new Vec3d(0, 1, 0), new Vec3d(0, 1, 1), new Vec3d(1, 1, 1), end,
					Color.white, 0));
			quads.add(createQuad(new Vec3d(1, 0, 1), new Vec3d(0, 0, 1), new Vec3d(0, 0, 0), new Vec3d(1, 0, 0), end,
					Color.white, 0));
			quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(1, 0, 0), new Vec3d(1, 1, 0), end,
					Color.white, 0));
			quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), new Vec3d(0, 0, 1), new Vec3d(0, 1, 1), end,
					Color.white, 0));
			quads.add(createQuad(new Vec3d(1, 0, 0), new Vec3d(0, 0, 0), new Vec3d(0, 1, 0), new Vec3d(1, 1, 0), end,
					Color.white, 0));
			quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), new Vec3d(1, 0, 1), end,
					Color.white, 0));
			break;
		case -1:
			quads.add(createQuad(new Vec3d(1, 1, 0), new Vec3d(0, 1, 0), new Vec3d(0, 1, 1), new Vec3d(1, 1, 1), nether,
					Color.white, 0));
			quads.add(createQuad(new Vec3d(1, 0, 1), new Vec3d(0, 0, 1), new Vec3d(0, 0, 0), new Vec3d(1, 0, 0), nether,
					Color.white, 0));
			quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(1, 0, 0), new Vec3d(1, 1, 0), nether,
					Color.white, 0));
			quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), new Vec3d(0, 0, 1), new Vec3d(0, 1, 1), nether,
					Color.white, 0));
			quads.add(createQuad(new Vec3d(1, 0, 0), new Vec3d(0, 0, 0), new Vec3d(0, 1, 0), new Vec3d(1, 1, 0), nether,
					Color.white, 0));
			quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), new Vec3d(1, 0, 1), nether,
					Color.white, 0));
			break;
		}

		for (int i = 0; i < 7 && i < ores.length; i++) {
			// Color firstColor = new
			// Color(com.sixteencolorgames.supertechtweaks.enums.Material.getMaterial(ores[i]).getColor());
			Color firstColor = Color.BLUE;
			quads.add(createQuad(new Vec3d(1, 1, 0), new Vec3d(0, 1, 0), new Vec3d(0, 1, 1), new Vec3d(1, 1, 1),
					ore1[i], firstColor, 0));
			quads.add(createQuad(new Vec3d(1, 0, 1), new Vec3d(0, 0, 1), new Vec3d(0, 0, 0), new Vec3d(1, 0, 0),
					ore1[i], firstColor, 0));
			quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(1, 0, 0), new Vec3d(1, 1, 0),
					ore1[i], firstColor, 0));
			quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), new Vec3d(0, 0, 1), new Vec3d(0, 1, 1),
					ore1[i], firstColor, 0));
			quads.add(createQuad(new Vec3d(1, 0, 0), new Vec3d(0, 0, 0), new Vec3d(0, 1, 0), new Vec3d(1, 1, 0),
					ore1[i], firstColor, 0));
			quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), new Vec3d(1, 0, 1),
					ore1[i], firstColor, 0));
		}
		return quads;
	}

	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return false;
	}

	private void putVertex(UnpackedBakedQuad.Builder builder, Vec3d normal, double x, double y, double z, float u,
			float v, Color color, TextureAtlasSprite sprite) {
		for (int e = 0; e < format.getElementCount(); e++) {
			switch (format.getElement(e).getUsage()) {
			case POSITION:
				builder.put(e, (float) x, (float) y, (float) z, 1.0f);
				break;
			case COLOR:
				builder.put(e, ((float) color.getRed()) / 255, ((float) color.getGreen()) / 255,
						((float) color.getBlue()) / 255, 1.0f);
				break;
			case UV:
				if (format.getElement(e).getIndex() == 0) {
					u = sprite.getInterpolatedU(u);
					v = sprite.getInterpolatedV(v);
					builder.put(e, u, v, 0f, 1f);
					break;
				}
			case NORMAL:
				builder.put(e, (float) normal.x, (float) normal.y, (float) normal.z, 0f);
				break;
			default:
				builder.put(e);
				break;
			}
		}
	}
}
