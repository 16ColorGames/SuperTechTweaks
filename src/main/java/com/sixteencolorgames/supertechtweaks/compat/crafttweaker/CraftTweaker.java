/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.compat.crafttweaker;

import minetweaker.MineTweakerAPI;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author oa10712
 */
@ZenClass("mods.supertech.tweaks")
public class CraftTweaker {

    public static final CraftTweaker instance = new CraftTweaker();

    public void register() {
        MineTweakerAPI.registerClass(this.getClass());
    }

    @ZenMethod
    public static void addMaterial(String name, String color, int harvest) {
        MineTweakerAPI.apply(new AddMaterial(name, color, harvest));
    }
}
