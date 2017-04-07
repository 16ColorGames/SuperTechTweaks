package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.items.ItemBase;
import com.sixteencolorgames.supertechtweaks.items.ItemOreChunk;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Holds and registers items used by the mod
 *
 * @author oa10712
 *
 */
public class ModItems {

    public static ItemOreChunk itemOreChunk;

    /**
     * Registers an item and its model with forge
     *
     * @param item The Item to register
     * @return The Item registered
     */
    private static <T extends Item> T register(T item) {
        GameRegistry.register(item);

        if (item instanceof ItemBase) {
            ((ItemBase) item).registerItemModel();
        }

        return item;
    }

    /**
     * Performs tasks to do with blocks on mod startup
     */
    public static void init() {
        itemOreChunk = (ItemOreChunk) (new ItemOreChunk().setUnlocalizedName("itemOreChunk"));
        itemOreChunk.setRegistryName("itemOreChunk");
        GameRegistry.register(itemOreChunk);
    }

}
