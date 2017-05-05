/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.blocks;

import com.sixteencolorgames.supertechtweaks.enums.Material;

public class BlockMaterial extends BlockBase {

    private final Material material;

    public BlockMaterial(Material material) {
        super(net.minecraft.block.material.Material.IRON, "block" + material.getName());
        this.material = material;
    }

}
