package com.sixteencolorgames.supertechtweaks.enums;

import net.minecraft.util.IStringSerializable;

public enum Metals implements IStringSerializable {
	NONE("none", 0x000000, -1), COPPER("copper", 0xb4713d, 0), IRON("iron", 0xe0d9cd, 1);

	private final String name;
	private int color;
	private int index;

	Metals(String name, int color, int index) {
		this.name = name;
		this.color = color;
		this.index = index;
		
	}

	public int getColor() {
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
