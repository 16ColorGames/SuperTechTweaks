package com.sixteencolorgames.supertechtweaks.tileentities.pipe;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class ModelLoaderPipe implements ICustomModelLoader {

	public static final ModelPipe PIPE_MODEL = new ModelPipe();

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation.getResourceDomain().equals(SuperTechTweaksMod.MODID)
				&& "blockpipe".equals(modelLocation.getResourcePath());
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		return PIPE_MODEL;
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {

	}
}