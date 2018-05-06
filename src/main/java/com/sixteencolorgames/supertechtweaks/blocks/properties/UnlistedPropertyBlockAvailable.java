package com.sixteencolorgames.supertechtweaks.blocks.properties;

import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertyBlockAvailable implements IUnlistedProperty<Boolean> {

	private final String name;

	public UnlistedPropertyBlockAvailable(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<Boolean> getType() {
		return Boolean.class;
	}

	@Override
	public boolean isValid(Boolean value) {
		return true;
	}

	@Override
	public String valueToString(Boolean value) {
		return value.toString();
	}
}