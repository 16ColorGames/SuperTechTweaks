package com.sixteencolorgames.supertechtweaks.enums;

import net.minecraft.advancements.DisplayInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Research extends IForgeRegistryEntry.Impl<Research> {
	private NonNullList<ResourceLocation> requirements = NonNullList.create();
	private int energyRequired = 1000;
	private DisplayInfo display;

	public Research(String string, DisplayInfo display) {
		this.setRegistryName(string);
		this.display = display;
	}

	public Research addRequirement(ResourceLocation req) {
		requirements.add(req);
		return this;
	}

	public Research setEnergyRequired(int req) {
		this.energyRequired = req;
		return this;
	}

	public int getEnergyRequired() {
		return this.energyRequired;
	}

	public NonNullList<ResourceLocation> getRequirements() {
		return this.requirements;
	}

	public Research setDisplay(DisplayInfo st) {
		this.display = st;
		return this;
	}

	public DisplayInfo getDisplay() {
		return this.display;
	}

	public int getRequirementCount() {
		return this.requirements.size();
	}

	public Research getParent() {
		if (getRequirementCount() != 0) {
			return GameRegistry.findRegistry(Research.class).getValue(requirements.get(0));
		}
		return null;
	}
}
