package com.sixteencolorgames.supertechtweaks.world;

import java.util.ArrayList;

import com.sixteencolorgames.supertechtweaks.Config;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModWorldGeneration {

	private static final ArrayList<EventType> vanillaOreGeneration = new ArrayList<EventType>();

	static {
		vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.COAL);
		vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.DIAMOND);
		vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.DIRT);
		vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.GOLD);
		vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.IRON);
		vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.LAPIS);
		vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.REDSTONE);
		vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.QUARTZ);
		vanillaOreGeneration.add(OreGenEvent.GenerateMinable.EventType.EMERALD);
	}

	public ModWorldGeneration() {
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.ORE_GEN_BUS.register(this);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = false)
	public void handleOreGenEvent(OreGenEvent.GenerateMinable event) {

		if (!Config.removeVanilla) {
			return;
		}
		if (vanillaOreGeneration.contains(event.getType())) {
			event.setResult(Result.DENY);
		}
	}
}
