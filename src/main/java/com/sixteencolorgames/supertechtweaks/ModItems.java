package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.enums.Ores;
import com.sixteencolorgames.supertechtweaks.items.ItemBase;
import com.sixteencolorgames.supertechtweaks.items.ItemOreChunk;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.END;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.NETHER;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

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
        itemOreChunk = register(new ItemOreChunk());
        for (Ores metal : Ores.values()) {
            ItemStack subItemStack = new ItemStack(itemOreChunk, 1, metal.ordinal());
            OreDictionary.registerOre("ore" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemOreChunk, 1, metal.ordinal() + NETHER);
            OreDictionary.registerOre("oreNether" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemOreChunk, 1, metal.ordinal() + END);
            OreDictionary.registerOre("oreEnd" + metal.getName(), subItemStack);
        }
    }

}
