/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.render;

import com.google.common.collect.ImmutableSet;
import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

/**
 *
 * @author oa10712
 */
public class OreModel implements IModel {

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new OreBakedModel(state, format, bakedTextureGetter);
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return ImmutableSet.of(new ResourceLocation(SuperTechTweaksMod.MODID, "blocks/ore1"),
                new ResourceLocation(SuperTechTweaksMod.MODID, "blocks/ore2"), 
                new ResourceLocation(SuperTechTweaksMod.MODID, "blocks/ore3"),
                new ResourceLocation(SuperTechTweaksMod.MODID, "blocks/ore4"),
                new ResourceLocation(SuperTechTweaksMod.MODID, "blocks/ore5"),
                new ResourceLocation(SuperTechTweaksMod.MODID, "blocks/ore6"), 
                new ResourceLocation(SuperTechTweaksMod.MODID, "blocks/ore7"));
    }

    @Override
    public IModelState getDefaultState() {
        return TRSRTransformation.identity();
    }
}
