/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.render;

import java.util.ArrayList;
import java.util.HashMap;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.tileentities.cable.ModelCable;
import com.sixteencolorgames.supertechtweaks.tileentities.conveyor.ModelConveyor;
import com.sixteencolorgames.supertechtweaks.tileentities.extractor.ModelExtractor;
import com.sixteencolorgames.supertechtweaks.tileentities.inserter.ModelInserter;
import com.sixteencolorgames.supertechtweaks.tileentities.pipe.ModelPipe;

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
	public static final ModelExtractor EXTRACTOR_MODEL = new ModelExtractor();
	public static final ModelInserter INSERTER_MODEL = new ModelInserter();
	public static final ModelConveyor CONVEYOR_MODEL = new ModelConveyor();
	public static final ModelCable CABLE_MODEL = new ModelCable();
	public static final ModelPipe PIPE_MODEL = new ModelPipe();
	
	ArrayList<String> blocks = new ArrayList();
	{
		blocks.add("conveyor");
		blocks.add("extractor");
		blocks.add("inserter");
		blocks.add("superore");
		blocks.add("blockcable");
		blocks.add("blockpipe");
	}

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation.getResourceDomain().equals(SuperTechTweaksMod.MODID)
				&& blocks.contains(modelLocation.getResourcePath());
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		switch (modelLocation.getResourcePath()) {
		case "extractor":
			return EXTRACTOR_MODEL;
		case "inserter":
			return INSERTER_MODEL;
		case "superore":
			return ORE_MODEL;
		case "blockcable":
			return CABLE_MODEL;
		case "blockpipe":
			return PIPE_MODEL;
		default:
			return CONVEYOR_MODEL;
		}
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {

	}
}