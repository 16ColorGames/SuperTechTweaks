package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.enums.Ores;
import com.sixteencolorgames.supertechtweaks.items.ItemBase;
import com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.*;
import com.sixteencolorgames.supertechtweaks.items.ItemOreChunk;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
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
    public static ItemMaterialObject itemMaterialObject;

    static ModelResourceLocation chunkLocation = new ModelResourceLocation("supertechtweaks:itemOreChunk",
            "inventory");
    static ModelResourceLocation ingotLocation = new ModelResourceLocation("supertechtweaks:itemIngot",
            "inventory");
    static ModelResourceLocation dustLocation = new ModelResourceLocation("supertechtweaks:itemDust",
            "inventory");
    static ModelResourceLocation gearLocation = new ModelResourceLocation("supertechtweaks:itemGear",
            "inventory");
    static ModelResourceLocation nuggetLocation = new ModelResourceLocation("supertechtweaks:itemNugget",
            "inventory");
    static ModelResourceLocation plateLocation = new ModelResourceLocation("supertechtweaks:itemPlate",
            "inventory");
    static ModelResourceLocation rodLocation = new ModelResourceLocation("supertechtweaks:itemRod",
            "inventory");
    static ModelResourceLocation clumpLocation = new ModelResourceLocation("supertechtweaks:itemClump",
            "inventory");
    static ModelResourceLocation shardLocation = new ModelResourceLocation("supertechtweaks:itemShard",
            "inventory");
    static ModelResourceLocation crystalLocation = new ModelResourceLocation("supertechtweaks:itemCrystal",
            "inventory");
    static ModelResourceLocation wireLocation = new ModelResourceLocation("supertechtweaks:itemWire",
            "inventory");

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
            ModelLoader.setCustomModelResourceLocation(itemOreChunk, metal.ordinal(), chunkLocation);
            subItemStack = new ItemStack(itemOreChunk, 1, metal.ordinal() + NETHER);
            OreDictionary.registerOre("oreNether" + metal.getName(), subItemStack);
            ModelLoader.setCustomModelResourceLocation(itemOreChunk, metal.ordinal() + NETHER, chunkLocation);
            subItemStack = new ItemStack(itemOreChunk, 1, metal.ordinal() + END);
            OreDictionary.registerOre("oreEnd" + metal.getName(), subItemStack);
            ModelLoader.setCustomModelResourceLocation(itemOreChunk, metal.ordinal() + END, chunkLocation);
        }

        itemMaterialObject = register(new ItemMaterialObject());
        for (Ores metal : Ores.values()) {
            ItemStack subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + INGOT);
            OreDictionary.registerOre("ingot" + metal.getName(), subItemStack);
            ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + INGOT, ingotLocation);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + DUST);
            OreDictionary.registerOre("dust" + metal.getName(), subItemStack);
            ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + DUST, dustLocation);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + GEAR);
            OreDictionary.registerOre("gear" + metal.getName(), subItemStack);
            ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + GEAR, gearLocation);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + NUGGET);
            OreDictionary.registerOre("nugget" + metal.getName(), subItemStack);
            ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + NUGGET, nuggetLocation);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + PLATE);
            OreDictionary.registerOre("plate" + metal.getName(), subItemStack);
            ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + PLATE, plateLocation);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + ROD);
            OreDictionary.registerOre("rod" + metal.getName(), subItemStack);
            OreDictionary.registerOre("stick" + metal.getName(), subItemStack);
            ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + ROD, rodLocation);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + CLUMP);
            OreDictionary.registerOre("clump" + metal.getName(), subItemStack);
            ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + CLUMP, clumpLocation);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + CRYSTAL);
            OreDictionary.registerOre("crystal" + metal.getName(), subItemStack);
            ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + CRYSTAL, crystalLocation);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + SHARD);
            OreDictionary.registerOre("shard" + metal.getName(), subItemStack);
            ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + SHARD, shardLocation);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + WIRE);
            OreDictionary.registerOre("wire" + metal.getName(), subItemStack);
            ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + WIRE, wireLocation);
        }
    }

}
