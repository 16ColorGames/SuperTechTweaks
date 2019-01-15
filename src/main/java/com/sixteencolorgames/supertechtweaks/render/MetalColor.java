package com.sixteencolorgames.supertechtweaks.render;

import java.awt.Color;

import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.items.MaterialItem;
import com.sixteencolorgames.supertechtweaks.items.MaterialTool;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class MetalColor implements IItemColor {

	public static final MetalColor INSTANCE = new MetalColor();

	/**
	 * Returns the colour for rendering, based on 1) the itemstack 2) the
	 * "tintindex" (layer in the item model json) For example: bottle_drinkable.json
	 * contains "layer0": "items/potion_overlay", "layer1":
	 * "items/potion_bottle_drinkable" layer0 = tintindex 0 = for the bottle
	 * outline, whose colour doesn't change layer1 = tintindex 1 = for the bottle
	 * contents, whose colour changes depending on the type of potion
	 *
	 * @param stack
	 * @param tintIndex
	 * @return an RGB colour (to be multiplied by the texture colours)
	 */
	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		Material metal;
		if (stack.getItem() instanceof MaterialItem) {
			MaterialItem item = (MaterialItem) stack.getItem();
			metal = item.getMaterial();
		} else {
			MaterialTool item = (MaterialTool) stack.getItem();
			metal = item.getMaterial();

		}
		try {
			switch (tintIndex) {
			case 0:
				return metal.getColor();
			default:
				return Color.WHITE.getRGB();
			}
		} catch (Exception ex) {
			return Color.WHITE.getRGB();
		}
	}
}
