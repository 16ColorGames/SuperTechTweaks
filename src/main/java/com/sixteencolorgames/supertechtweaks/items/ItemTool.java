package com.sixteencolorgames.supertechtweaks.items;

import com.sixteencolorgames.supertechtweaks.enums.Material;

public class ItemTool extends ItemBase {
	Material material;

	public ItemTool(String name, Material material) {
		super(name + material.getName());
		this.material = material;
		setMaxStackSize(1);
		setContainerItem(this);
	}

}
