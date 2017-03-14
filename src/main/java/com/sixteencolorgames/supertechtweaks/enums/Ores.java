package com.sixteencolorgames.supertechtweaks.enums;

import net.minecraft.util.IStringSerializable;

/**
 * Enumeration of metal types used in this mod.
 * 
 * @author oa10712
 *
 */
public enum Ores implements IStringSerializable {
	NONE("none", "0x000000", 0), 
	COPPER("Copper", "0xb4713d", 1),  
	ZINC("Zinc", "0xbac4c8", 1), 
	COAL("Coal", "0x060607", 1), 
	IRON("Iron", "0xd3ad90", 2), 
	ALUMINUM("Aluminum", "0xe0d9cd", 2),
	SILVER("Silver", "0xb5b5bd", 2), 
	LAPIS("Lapis", "0x000094", 2), 
	TIN("Tin", "0x928a98", 3), 
	GOLD("Gold", "0xcccc33", 3), 
	LEAD("Lead", "0x474c4d", 3), 
	REDSTONE("Redstone", "0xd43c2c", 3), 
	NICKEL("Nickel", "0xccd3d8", 3), 
	OSMIUM("Osmium", "0x9090a3", 3), 
	DIAMOND("Diamond", "0x006381", 4), 
	EMERALD("Emerald", "0x318957", 4), 
	RUBY("Ruby", "0x9b111e", 4), 
	SAPPHIRE("Sapphire", "0x297bc1", 4), 
	AMETHYST("Amethyst", "0x642552", 4), 
	MANGANESE("Manganese", "0x242d36", 4), 
	QUARTZ("Quartz", "0xe6d7df", 5), 
	ARDITE("Ardite", "0x000000", 6), 
	URANIUM("Uranium", "0x329832", 6), 
	PLATINUM("Platinum", "0xb8b7b2", 6), 
	YELLORITE("Yellorite", "0xffce00", 6), 
	COBALT("cobalt", "0x0071b6", 7), 
	IRIDIUM("iridium", "0xe0e2dd", 7), 
	TITANIUM("titanium", "0x323230", 7);
	
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

	Ores(String name, String color, int harvest) {
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
