package com.sixteencolorgames.supertechtweaks.enums;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Research extends IForgeRegistryEntry.Impl<Research> {
	private NonNullList<ResourceLocation> requirements = NonNullList.create();
	private int energyRequired = 1000;
	private ItemStack display = new ItemStack(Blocks.DIRT);
	private String title;

	public Research(String string) {
		this.setRegistryName(string);
		title = string;
	}

	public Research addRequirement(ResourceLocation req) {
		requirements.add(req);
		return this;
	}

	public ItemStack getDisplay() {
		return display;
	}

	public int getEnergyRequired() {
		return energyRequired;
	}

	public Research getParent() {
		if (getRequirementCount() != 0) {
			return GameRegistry.findRegistry(Research.class).getValue(requirements.get(0));
		}
		return null;
	}

	public int getRequirementCount() {
		return requirements.size();
	}

	public NonNullList<ResourceLocation> getRequirements() {
		return requirements;
	}

	public String getTitle() {
		return title;
	}

	public Research setDisplay(ItemStack st) {
		display = st;
		return this;
	}

	public Research setEnergyRequired(int req) {
		energyRequired = req;
		return this;
	}

	public Research setTitle(String title) {
		this.title = title;
		return this;
	}
}