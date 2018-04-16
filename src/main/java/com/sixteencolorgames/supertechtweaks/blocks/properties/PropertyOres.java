/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.blocks.properties;

import java.util.Arrays;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.property.IUnlistedProperty;

/**
 *
 * @author oa10712
 */
public class PropertyOres implements IUnlistedProperty<ResourceLocation[]> {

	private final String name;

	public PropertyOres(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<ResourceLocation[]> getType() {
		return ResourceLocation[].class;
	}

	@Override
	public boolean isValid(ResourceLocation[] value) {
		return true;
	}

	@Override
	public String valueToString(ResourceLocation[] value) {
		return Arrays.toString(value);
	}
}
