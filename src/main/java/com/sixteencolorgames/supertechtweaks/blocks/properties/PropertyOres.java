/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.blocks.properties;

import java.util.Arrays;

import net.minecraftforge.common.property.IUnlistedProperty;

/**
 *
 * @author oa10712
 */
public class PropertyOres implements IUnlistedProperty<Integer[]> {

	private final String name;

	public PropertyOres(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isValid(Integer[] value) {
		return true;
	}

	@Override
	public Class<Integer[]> getType() {
		return Integer[].class;
	}

	@Override
	public String valueToString(Integer[] value) {
		return Arrays.toString(value);
	}
}
