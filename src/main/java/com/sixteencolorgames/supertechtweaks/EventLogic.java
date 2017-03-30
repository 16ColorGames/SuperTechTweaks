package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.enums.HarvestLevels;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.MaterialEvent;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;

public class EventLogic {
	@SubscribeEvent
	public void onStatRegister(MaterialEvent.StatRegisterEvent<HeadMaterialStats> statRegisterEvent) {
		if (statRegisterEvent.stats instanceof HeadMaterialStats) {
			HeadMaterialStats oldStats = statRegisterEvent.newStats != null ? statRegisterEvent.newStats
					: statRegisterEvent.stats;
			int newHarvestLevel = HarvestLevels.values.getOrDefault(statRegisterEvent.material.identifier, -1);
			if (newHarvestLevel != oldStats.harvestLevel) {
				HeadMaterialStats newStats = new HeadMaterialStats(oldStats.durability, oldStats.miningspeed,
						oldStats.attack, newHarvestLevel);
				statRegisterEvent.overrideResult(newStats);
			}
		}
	}
}