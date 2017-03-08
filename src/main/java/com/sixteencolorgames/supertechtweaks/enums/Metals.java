package com.sixteencolorgames.supertechtweaks.enums;

import net.minecraft.util.IStringSerializable;

/**
 * Enumeration of metal types used in this mod.
 * 
 * @author oa10712
 *
 */
public enum Metals implements IStringSerializable {
	NONE("none", "0x000000", 0), 
	COPPER("copper", "0xb4713d", 1), 
	IRON("iron", "0xe0d9cd", 2), 
	ALUMINUM("aluminum", "0xe0d9cd", 2);
	
	/**
	 * The name of the metal
	 */
	private final String name;
	/**
	 * The RGB code for the color of this metal.
	 */
	private String color;
	/**
	 * The harvest level of this metal.
	 */
	private int harvest;

	Metals(String name, String color, int harvest) {
		this.name = name;
		this.color = color;
		this.harvest = harvest;
	}

	public String getColor() {
		return color;
	}

	@Override
	public String getName() {
		return name;
	}

	public int getHarvest() {
		return harvest;
	}
}
