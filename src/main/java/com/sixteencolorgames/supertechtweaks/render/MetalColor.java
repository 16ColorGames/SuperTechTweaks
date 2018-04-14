package com.sixteencolorgames.supertechtweaks.render;

import java.awt.Color;

import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.items.MaterialItem;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class MetalColor implements IItemColor {

	public static final MetalColor INSTANCE = new MetalColor();

	/**
	 * Returns the colour for rendering, based on 1) the itemstack 2) the
	 * "tintindex" (layer in the item model json) For example:
	 * bottle_drinkable.json contains "layer0": "items/potion_overlay",
	 * "layer1": "items/potion_bottle_drinkable" layer0 = tintindex 0 = for the
	 * bottle outline, whose colour doesn't change layer1 = tintindex 1 = for
	 * the bottle contents, whose colour changes depending on the type of potion
	 *
	 * @param stack
	 * @param tintIndex
	 * @return an RGB colour (to be multiplied by the texture colours)
	 */
	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		try {
			MaterialItem item = (MaterialItem) stack.getItem();
			Material metal = item.getMaterial();
			if (stack.getMetadata() == MaterialItem.NETHER_ORE || stack.getMetadata() == MaterialItem.END_ORE
					|| stack.getMetadata() == MaterialItem.ORE) {
				switch (tintIndex) {
				case 0:// base rock
					if (stack.getMetadata() == MaterialItem.NETHER_ORE) {
						return Color.RED.darker().getRGB();
					} else if (stack.getMetadata() == MaterialItem.END_ORE) {
						return Color.WHITE.getRGB();
					} else {
						return Color.gray.getRGB();
					}

				case 1:
					return metal.getColor();

				default:
					// oops! should never get here.
					return Color.BLACK.getRGB();

				}
			} else {
				switch (tintIndex) {
				case 0:
					return metal.getColor();
				default:
					return Color.BLACK.getRGB();
				}
			}
		} catch (Exception ex) {
			return Color.BLACK.getRGB();
		}
	}
}
