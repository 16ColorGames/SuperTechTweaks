package com.sixteencolorgames.supertechtweaks.enums;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Research extends IForgeRegistryEntry.Impl<Research> {
	private NonNullList<ResourceLocation> requirements = NonNullList.create();
	private NonNullList<ResourceLocation> dependents = NonNullList.create();
	private int energyRequired = 10000;
	private ItemStack display = new ItemStack(Blocks.DIRT);
	private String title;
	private NonNullList<ItemStack> items = NonNullList.create();

	public Research(String string) {
		this.setRegistryName(string);
		title = string;
	}

	public void addDependent(Research res) {
		dependents.add(res.getRegistryName());
	}

	public Research addRequirement(ResourceLocation req) {
		requirements.add(req);
		return this;
	}

	public int getDependentCount() {
		return dependents.size();
	}

	public NonNullList<ResourceLocation> getDependents() {
		return dependents;
	}

	public ItemStack getDisplay() {
		return display;
	}

	public int getEnergyRequired() {
		return energyRequired;
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