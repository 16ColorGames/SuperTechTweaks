/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.render;

import java.util.HashMap;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

/**
 *
 * @author oa10712
 */
public class BakedModelLoader implements ICustomModelLoader {

	public static final OreModel ORE_MODEL = new OreModel();

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation.getResourceDomain().equals(SuperTechTweaksMod.MODID)
				&& "superore".equals(modelLocation.getResourcePath());
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		return ORE_MODEL;
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {

	}
}