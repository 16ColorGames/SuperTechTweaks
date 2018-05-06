package com.sixteencolorgames.supertechtweaks.tileentities.cable;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class ModelLoaderCable implements ICustomModelLoader {

	public static final ModelCable CABLE_MODEL = new ModelCable();

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation.getResourceDomain().equals(SuperTechTweaksMod.MODID)
				&& "blockcable".equals(modelLocation.getResourcePath());
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		return CABLE_MODEL;
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {

	}
}