package com.sixteencolorgames.supertechtweaks.tileentities.conveyor;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;
import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

public class ModelConveyor implements IModel {
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format,
			Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		return new BakedModelConveyor();
	}

	@Override
	public IModelState getDefaultState() {
		return TRSRTransformation.identity();
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return Collections.emptySet();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		return ImmutableSet.of(new ResourceLocation(SuperTechTweaksMod.MODID, "blocks/conveyor"),
				new ResourceLocation(SuperTechTweaksMod.MODID, "blocks/conveyor_turn"),
				new ResourceLocation(SuperTechTweaksMod.MODID, "blocks/conveyor_turn_reverse"));
	}
}