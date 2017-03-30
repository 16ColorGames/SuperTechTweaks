package com.sixteencolorgames.supertechtweaks.enums;

import java.util.HashMap;

/**
 * List of material strength, so WOOD_0 is the harvest level of a wood pick
 * 
 * @author oa10712
 *
 */
public class HarvestLevels {
	
    public static int _0_stone = 0;
    public static int _1_flint = 1;
    public static int _2_copper = 2;
    public static int _3_iron = 3;
    public static int _4_bronze = 4;
    public static int _5_diamond = 5;
    public static int _6_obsidian = 6;
    public static int _7_ardite = 7;
    public static int _8_cobalt = 8;
    public static int _9_manyullym = 9;
    
	public static HashMap<String, Integer> values = new HashMap();
	{
		values.put("stone", _0_stone);
		values.put("flint", _1_flint);
		values.put("copper", _2_copper);
		values.put("iron", _3_iron);
		values.put("iron", _4_bronze);
		values.put("iron", _5_diamond);
		values.put("iron", _6_obsidian);
		values.put("iron", _7_ardite);
		values.put("iron", _8_cobalt);
		values.put("iron", _9_manyullym);
	}
}
