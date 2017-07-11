/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.compat.crafttweaker;

import com.google.common.collect.Lists;
import com.sixteencolorgames.supertechtweaks.ModFluids;
import com.sixteencolorgames.supertechtweaks.ModRegistry;
import static com.sixteencolorgames.supertechtweaks.ModRegistry.itemMaterialObject;
import static com.sixteencolorgames.supertechtweaks.ModRegistry.itemOreChunk;
import com.sixteencolorgames.supertechtweaks.Recipies;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.*;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.*;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 *
 * @author oa10712
 */
public class AddMaterial implements IUndoableAction {

    private final String name;
    private final String color;
    private final int harvest;

    public AddMaterial(String name, String color, int harvest) {
        this.name = name;
        this.color = color;
        this.harvest = harvest;
    }

    @Override
    public void apply() {
        Material mat = new Material(name, color, harvest);
        ModFluids.createFluid(mat.getName().toLowerCase(), false,
                fluid -> fluid.setLuminosity(10).setDensity(800).setViscosity(300),
                fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.PURPLE)),
                mat);
        ItemStack ore = new ItemStack(itemOreChunk, 1, mat.ordinal());
        OreDictionary.registerOre("ore" + mat.getName(), ore);
        MineTweakerAPI.ijeiRecipeRegistry.addItem(ore);
        ItemStack oreNether = new ItemStack(itemOreChunk, 1, mat.ordinal() + NETHER);
        OreDictionary.registerOre("oreNether" + mat.getName(), oreNether);
        MineTweakerAPI.ijeiRecipeRegistry.addItem(oreNether);
        ItemStack oreEnd = new ItemStack(itemOreChunk, 1, mat.ordinal() + END);
        OreDictionary.registerOre("oreEnd" + mat.getName(), oreEnd);
        MineTweakerAPI.ijeiRecipeRegistry.addItem(oreEnd);

        ItemStack ingot = new ItemStack(itemMaterialObject, 1, mat.ordinal() + INGOT);
        OreDictionary.registerOre("ingot" + mat.getName(), ingot);
        MineTweakerAPI.ijeiRecipeRegistry.addItem(ingot);
        ItemStack dust = new ItemStack(itemMaterialObject, 1, mat.ordinal() + DUST);
        OreDictionary.registerOre("dust" + mat.getName(), dust);
        MineTweakerAPI.ijeiRecipeRegistry.addItem(dust);
        ItemStack gear = new ItemStack(itemMaterialObject, 1, mat.ordinal() + GEAR);
        OreDictionary.registerOre("gear" + mat.getName(), gear);
        MineTweakerAPI.ijeiRecipeRegistry.addItem(gear);
        ItemStack nugget = new ItemStack(itemMaterialObject, 1, mat.ordinal() + NUGGET);
        OreDictionary.registerOre("nugget" + mat.getName(), nugget);
        MineTweakerAPI.ijeiRecipeRegistry.addItem(nugget);
        ItemStack plate = new ItemStack(itemMaterialObject, 1, mat.ordinal() + PLATE);
        OreDictionary.registerOre("plate" + mat.getName(), plate);
        MineTweakerAPI.ijeiRecipeRegistry.addItem(plate);
        ItemStack rod = new ItemStack(itemMaterialObject, 1, mat.ordinal() + ROD);
        OreDictionary.registerOre("rod" + mat.getName(), rod);
        OreDictionary.registerOre("stick" + mat.getName(), rod);
        MineTweakerAPI.ijeiRecipeRegistry.addItem(rod);
        ItemStack clump = new ItemStack(itemMaterialObject, 1, mat.ordinal() + CLUMP);
        OreDictionary.registerOre("clump" + mat.getName(), clump);
        MineTweakerAPI.ijeiRecipeRegistry.addItem(clump);
        ItemStack crystal = new ItemStack(itemMaterialObject, 1, mat.ordinal() + CRYSTAL);
        OreDictionary.registerOre("crystal" + mat.getName(), crystal);
        MineTweakerAPI.ijeiRecipeRegistry.addItem(crystal);
        ItemStack shard = new ItemStack(itemMaterialObject, 1, mat.ordinal() + SHARD);
        OreDictionary.registerOre("shard" + mat.getName(), shard);
        MineTweakerAPI.ijeiRecipeRegistry.addItem(shard);
        ItemStack wire = new ItemStack(itemMaterialObject, 1, mat.ordinal() + WIRE);
        OreDictionary.registerOre("wire" + mat.getName(), wire);
        OreDictionary.registerOre("cable" + mat.getName(), wire);
        MineTweakerAPI.ijeiRecipeRegistry.addItem(wire);
        ItemStack dirty = new ItemStack(itemMaterialObject, 1, mat.ordinal() + DIRTY);
        OreDictionary.registerOre("dustDirty" + mat.getName(), dirty);
        MineTweakerAPI.ijeiRecipeRegistry.addItem(dirty);
        ItemStack foil = new ItemStack(itemMaterialObject, 1, mat.ordinal() + FOIL);
        OreDictionary.registerOre("foil" + mat.getName(), foil);
        MineTweakerAPI.ijeiRecipeRegistry.addItem(foil);
        ItemStack tiny = new ItemStack(itemMaterialObject, 1, mat.ordinal() + TINY);
        OreDictionary.registerOre("dustTiny" + mat.getName(), tiny);
        MineTweakerAPI.ijeiRecipeRegistry.addItem(tiny);

        Recipies.register(mat);
        MineTweakerAPI.ijeiRecipeRegistry.addFurnace(Lists.newArrayList(ore), ingot);
        MineTweakerAPI.ijeiRecipeRegistry.addFurnace(Lists.newArrayList(oreNether),
                new ItemStack(ModRegistry.itemOreChunk, 2, mat.ordinal()));
        MineTweakerAPI.ijeiRecipeRegistry.addFurnace(Lists.newArrayList(oreEnd),
                new ItemStack(ModRegistry.itemOreChunk, 3, mat.ordinal()));
        MineTweakerAPI.ijeiRecipeRegistry.addFurnace(Lists.newArrayList(dust), ingot);

        ShapedOreRecipe gearRec = new ShapedOreRecipe(gear, " x ", "xyx", " x ", 'x', "ingot" + mat.getName(), 'y', "nugget" + mat.getName());
        GameRegistry.addRecipe(gearRec);
        MineTweakerAPI.ijeiRecipeRegistry.addRecipe(gearRec);
        ShapedOreRecipe nuggetRec = new ShapedOreRecipe(ingot, "xxx", "xxx", "xxx", 'x', "nugget" + mat.getName());
        GameRegistry.addRecipe(nuggetRec);
        MineTweakerAPI.ijeiRecipeRegistry.addRecipe(nuggetRec);
        ShapedOreRecipe tinyRec = new ShapedOreRecipe(dust, "xxx", "xxx", "xxx", 'x', "dustTiny" + mat.getName());
        GameRegistry.addRecipe(tinyRec);
        MineTweakerAPI.ijeiRecipeRegistry.addRecipe(tinyRec);
        ShapedOreRecipe rodRec = new ShapedOreRecipe(rod, "x", "x", 'x', "ingot" + mat.getName());
        GameRegistry.addRecipe(rodRec);
        MineTweakerAPI.ijeiRecipeRegistry.addRecipe(rodRec);
        ShapelessOreRecipe wireRec = new ShapelessOreRecipe(new ItemStack(ModRegistry.itemMaterialObject, 2, mat.ordinal() + WIRE), plate, "craftingToolWireCutter");
        GameRegistry.addRecipe(wireRec);
        MineTweakerAPI.ijeiRecipeRegistry.addRecipe(wireRec);
        ShapelessOreRecipe plateRec = new ShapelessOreRecipe(plate, ingot, "craftingToolForgeHammer");
        GameRegistry.addRecipe(plateRec);
        MineTweakerAPI.ijeiRecipeRegistry.addRecipe(plateRec);

        CraftTweaker.listeners.forEach((listener) -> {
            listener.addMaterial(mat);
        });
        MineTweakerAPI.ijeiRecipeRegistry.reloadItemList();
        
        
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public void undo() {
        Material.remove(name);
        CraftTweaker.listeners.forEach((listener) -> {
            listener.removeMaterial(name);
        });
    }

    @Override
    public String describe() {
        return "Adding new material type: " + name;
    }

    @Override
    public String describeUndo() {
        return "Removing new material type: " + name;
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}
