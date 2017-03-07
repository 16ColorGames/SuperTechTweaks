package com.sixteencolorgames.supertechtweaks.enums;

import net.minecraft.util.IStringSerializable;

public enum Metals implements IStringSerializable {
	NONE("none", "0x000000", 0), COPPER("copper", "0xb4713d", 1), IRON("iron", "0xe0d9cd", 2);

	private final String name;
	private String color;
	private int index;

	Metals(String name, String color, int index) {
		this.name = name;
		this.color = color;
		this.index = index;
		
	}

	public String getColor() {
		return color;
	}

	@Override
	public String getName() {
		return name;
	}

	public int getIndex() {
		return index;
	}
}
