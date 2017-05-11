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
public class PropertyByte implements IUnlistedProperty<Byte> {

    private final String name;

    public PropertyByte(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isValid(Byte value) {
        return true;
    }

    @Override
    public Class<Byte> getType() {
        return Byte.class;
    }

    @Override
    public String valueToString(Byte value) {
        return value.toString();
    }
}
