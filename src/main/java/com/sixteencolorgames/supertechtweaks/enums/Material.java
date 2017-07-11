/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks.enums;

import com.sixteencolorgames.supertechtweaks.ModRegistry;
import com.sixteencolorgames.supertechtweaks.blocks.BlockMaterial;
import com.sixteencolorgames.supertechtweaks.items.ItemOreChunk;
import com.sixteencolorgames.supertechtweaks.proxy.ClientProxy;
import static com.sixteencolorgames.supertechtweaks.proxy.ClientProxy.itemLocation;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author oa10712
 */
public class Material extends IForgeRegistryEntry.Impl<Material> {

    public static ArrayList<Material> materials = new ArrayList();

    public static Material getMaterial(String asString) {
        for (Material mat : materials) {
            if (mat.getName().equalsIgnoreCase(asString)) {
                return mat;
            }
        }
        return materials.get(0);
    }

    public static Material getMaterial(int index) {
        if (materials.size() > index) {
            return materials.get(index);
        } else {
            return materials.get(0);
        }
    }

    public static void remove(String name) {
        materials.stream().filter((m) -> (m.name.equals(name))).forEachOrdered((m) -> {
            materials.remove(m);
        });
    }

    /**
     * The oreDict name of the metal
     */
    private final String name;
    /**
     * The RGB code for the color of this metal.
     */
    private int color;
    /**
     * The harvest level of this metal.
     */
    private int harvest;
    /**
     * Level that a tool made of this can mine
     */
    private int mine;

    private int ordinal;

    private BlockMaterial block;
    private ItemBlock itemBlock;

    /**
     *
     * @param name The ore dictionary name
     * @param color
     * @param harvest
     */
    public Material(String name, String color, int harvest) {
        this(name, color, harvest, -1);
    }

    public Material(String name, String color, int harvest, int mine) {
        this(name, Color.decode(color).getRGB(), harvest, mine);
    }

    public Material(String name, int color, int harvest, int mine) {
        System.out.println("Creating mat: " + name);
        this.name = name;
        this.color = color;
        this.harvest = harvest;
        this.mine = mine;
        this.ordinal = materials.size();
        materials.add(this);
        System.out.println("  Registering associated blocks/items");
        block = new BlockMaterial(this);

        itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        System.out.println("  Registering block ties");
        ModRegistry.register(block, itemBlock);
        OreDictionary.registerOre("block" + getName(), new ItemStack(block));
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getHarvest() {
        return harvest;
    }

    public int getMine() {
        return mine;
    }

    public ItemStack getDrops(byte base) {
        switch (base) {//Switch based on base block
            case -1://NetherRack and similar
                return new ItemStack(ModRegistry.itemOreChunk, 1, this.ordinal() + ItemOreChunk.NETHER);
            case 1://Endstone and similar
                return new ItemStack(ModRegistry.itemOreChunk, 1, this.ordinal() + ItemOreChunk.END);
            default://Stone and unspecified
                return new ItemStack(ModRegistry.itemOreChunk, 1, this.ordinal());
        }
    }

    public int ordinal() {
        return ordinal;
    }

    public BlockMaterial getBlock() {
        return block;
    }

    @SideOnly(Side.CLIENT)
    public void clientPrep() {

        ModelLoader.setCustomModelResourceLocation(itemBlock, 0, itemLocation);
        ModelLoader.setCustomModelResourceLocation(block.getItemBlock(), 0, ClientProxy.itemLocation);
        ModelLoader.setCustomStateMapper(block, new IStateMapper() {
            @Override
            public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
                final Map<IBlockState, ModelResourceLocation> loc = new HashMap<IBlockState, ModelResourceLocation>();
                loc.put(blockIn.getDefaultState(), ClientProxy.blockLocation);
                return loc;
            }
        });
    }
}
