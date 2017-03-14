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
	ANTIMONY("Antimony", "0xFADA5E", 0), 
	BISMUTH("Bismuth", "0xed7d92", 0), 
	CADMIUM("Cadmium", "0xed872d", 0), 
	MERCURY("Mercury", "0x751f27", 0), 
	COPPER("Copper", "0xb4713d", 1),  
	ZINC("Zinc", "0xbac4c8", 1), 
	COAL("Coal", "0x060607", 1), 
	MONAZIT("Monazit", "0x82c59c", 1), 
	IRON("Iron", "0xd3ad90", 2), 
	APATITE("Apatite", "0xc3b89c", 2), 
	CHROMIUM("Chromium", "0x18391e", 2), 
	ALUMINUM("Aluminum", "0xe0d9cd", 2),
	SILVER("Silver", "0xb5b5bd", 2), 
	LAPIS("Lapis", "0x000094", 2), 
	TIN("Tin", "0x928a98", 3), 
	GOLD("Gold", "0xcccc33", 3), 
	LEAD("Lead", "0x474c4d", 3), 
	REDSTONE("Redstone", "0xd43c2c", 3), 
	NICKEL("Nickel", "0xccd3d8", 3), 
	OSMIUM("Osmium", "0x9090a3", 3), 
	COLD_IRON("Cold Iron", "0x5f6c81", 3), 
	DIAMOND("Diamond", "0x006381", 4), 
	EMERALD("Emerald", "0x318957", 4), 
	RUBY("Ruby", "0x9b111e", 4), 
	SAPPHIRE("Sapphire", "0x297bc1", 4), 
	AMETHYST("Amethyst", "0x642552", 4), 
	MANGANESE("Manganese", "0x242d36", 4), 
	CERTUS("Certus", "0xe6d7df", 5), 
	CHARGED_CERTUS("Certus", "0xeadadf", 5), 
	ARDITE("Ardite", "0xff7b00", 6), 
	URANIUM("Uranium", "0x329832", 6), 
	PLATINUM("Platinum", "0xb8b7b2", 6), 
	YELLORITE("Yellorite", "0xffce00", 6), 
	DRACONIUM("Draconium", "0xd2a8d4", 6), 
	COBALT("Cobalt", "0x0071b6", 7), 
	IRIDIUM("Iridium", "0xe0e2dd", 7), 
	TITANIUM("Titanium", "0x323230", 7);
	
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
