/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.blocks.properties;

import net.minecraftforge.common.property.IUnlistedProperty;

/**
 *
 * @author oa10712
 */
public class PropertyInt implements IUnlistedProperty<Integer> {

	private final String name;

	public PropertyInt(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<Integer> getType() {
		return Integer.class;
	}

	@Override
	public boolean isValid(Integer value) {
		return true;
	}

	@Override
	public String valueToString(Integer value) {
		return value.toString();
	}
}
