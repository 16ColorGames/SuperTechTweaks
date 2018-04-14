package com.sixteencolorgames.supertechtweaks.enums;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Research extends IForgeRegistryEntry.Impl<Research> {
	private NonNullList<ResourceLocation> requirements = NonNullList.create();
	private int energyRequired = 1000;
	private ItemStack display;
	private String title;

	public Research(String string) {
		this.setRegistryName(string);
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

	public Research setDisplay(ItemStack st) {
		this.display = st;
		return this;
	}

	public ItemStack getDisplay() {
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

	public Research setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getTitle() {
		return title;
	}
}