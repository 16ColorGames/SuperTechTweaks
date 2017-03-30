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
        values.put("basalt", _0_stone);
        values.put("paper", _0_stone);
        values.put("sponge", _0_stone);
        values.put("firewood", _0_stone);
        values.put("slime", _0_stone);
        values.put("blueslime", _0_stone);
        values.put("magmaslime", _0_stone);
        values.put("treatedwood", _0_stone);
        values.put("cactus", _0_stone);
        values.put("netherrack", _0_stone);
        values.put("wood", _0_stone);

        values.put("bone", _1_flint);
        values.put("prismarine", _1_flint);
        values.put("flint", _1_flint);
        values.put("lead", _1_flint);
        values.put("silver", _1_flint);
        values.put("electrum", _1_flint);
        values.put("xu_demonic_metal", _1_flint);

        values.put("copper", _2_copper);
        values.put("brass", _2_copper);

        values.put("iron", _3_iron);
        values.put("constantan", _3_iron);
        values.put("endstone", _3_iron);

        values.put("bronze", _4_bronze);

        values.put("steel", _5_diamond);
        values.put("pigiron", _5_diamond);

        values.put("obsidian", _6_obsidian);
        values.put("ardite", _6_obsidian);
        
        values.put("cobalt", _7_ardite);
        
        values.put("manyullen", _9_manyullym);
    }
}
