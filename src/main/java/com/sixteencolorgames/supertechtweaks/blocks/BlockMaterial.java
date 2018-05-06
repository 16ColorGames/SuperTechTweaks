package com.sixteencolorgames.supertechtweaks.blocks;

import com.sixteencolorgames.supertechtweaks.enums.Material;

public class BlockMaterial extends BlockBase {

	private final Material material;

	public BlockMaterial(Material material) {
		super(net.minecraft.block.material.Material.IRON, "block" + material.getName());
		this.material = material;
	}

	public Material getSuperMaterial() {
		return material;
	}
}