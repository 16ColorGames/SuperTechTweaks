package com.sixteencolorgames.supertechtweaks.blocks.properties;

import com.sixteencolorgames.supertechtweaks.enums.Material;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.property.IUnlistedProperty;

public class PropertyMaterial implements IUnlistedProperty<Material> {

	private final String name;

	public PropertyMaterial(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<Material> getType() {
		return Material.class;
	}

	@Override
	public boolean isValid(Material value) {
		return true;
	}

	@Override
	public String valueToString(Material value) {
		return value.toString();
	}
}
