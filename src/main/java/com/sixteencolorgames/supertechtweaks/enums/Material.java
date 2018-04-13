package com.sixteencolorgames.supertechtweaks.enums;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.sixteencolorgames.supertechtweaks.ModRegistry;
import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.blocks.BlockMaterial;
import com.sixteencolorgames.supertechtweaks.items.MaterialItem;
import com.sixteencolorgames.supertechtweaks.proxy.ClientProxy;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryEntry;
import com.sixteencolorgames.supertechtweaks.items.MaterialItem.*;

public class Material extends IForgeRegistryEntry.Impl<Material> {
	/**
	 * The oreDict name of the metal
	 */
	private final String name;
	/**
	 * The RGB code for the color of this
	 */
	private int color;
	/**
	 * The harvest level of this
	 */
	private int harvest;
	/**
	 * Level that a tool made of this can mine
	 */
	private int mine;

	private BlockMaterial block;
	private ItemBlock itemBlock;
	private MaterialItem itemMaterial;

	/**
	 *
	 * @param name
	 *            The ore dictionary name
	 * @param color
	 * @param harvest
	 */
	public Material(String name, String color, int harvest) {
		this(name, color, harvest, -1);
	}

	public MaterialItem getMaterialItem() {
		return itemMaterial;
	}

	public Item getItemBlock() {
		return itemBlock;
	}

	public BlockMaterial getBlock() {
		return block;
	}

	public Material(String name, String color, int harvest, int mine) {

		this(name, Color.decode(color).getRGB(), harvest, mine);
	}

	public Material(String name, int color, int harvest, int mine) {
		this.name = name;
		this.color = color;
		this.harvest = harvest;
		this.mine = mine;
		block = new BlockMaterial(this);

		itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(block.getRegistryName());

		itemMaterial = new MaterialItem(this);

	}

	public void registerMaterial() {
		GameRegistry.findRegistry(Block.class).register(block);
		GameRegistry.findRegistry(Item.class).register(itemBlock);
		OreDictionary.registerOre("block" + getName(), new ItemStack(block));

		GameRegistry.findRegistry(Item.class).register(itemMaterial);

		registerOreDict();
		SuperTechTweaksMod.proxy.registerModels(this);

		this.setRegistryName(getName());

		GameRegistry.findRegistry(Material.class).register(this);
		this.clientPrep();
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
		switch (base) {// Switch based on base block
		case -1:// NetherRack and similar
			return new ItemStack(itemMaterial, 1, MaterialItem.NETHER_ORE);
		case 1:// Endstone and similar
			return new ItemStack(itemMaterial, 1, MaterialItem.END_ORE);
		default:// Stone and unspecified
			return new ItemStack(itemMaterial, 1, MaterialItem.ORE);
		}
	}

	@SideOnly(Side.CLIENT)
	public void clientPrep() {
		ModelLoader.setCustomModelResourceLocation(itemBlock, 0, ClientProxy.itemLocation);
		ModelLoader.setCustomModelResourceLocation(itemBlock, 0, ClientProxy.blockLocation);
		ModelLoader.setCustomStateMapper(block, new IStateMapper() {
			@Override
			public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
				final Map<IBlockState, ModelResourceLocation> loc = new HashMap<IBlockState, ModelResourceLocation>();
				loc.put(blockIn.getDefaultState(), ClientProxy.blockLocation);
				return loc;
			}
		});
	}

	public void registerOreDict() {
		ItemStack subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.ORE);
		OreDictionary.registerOre("ore" + getName(), subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.NETHER_ORE);
		OreDictionary.registerOre("oreNether" + getName(), subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.END_ORE);
		OreDictionary.registerOre("oreEnd" + getName(), subItemStack);

		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.INGOT);
		OreDictionary.registerOre("ingot" + getName(), subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.DUST);
		OreDictionary.registerOre("dust" + getName(), subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.GEAR);
		OreDictionary.registerOre("gear" + getName(), subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.NUGGET);
		OreDictionary.registerOre("nugget" + getName(), subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.PLATE);
		OreDictionary.registerOre("plate" + getName(), subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.ROD);
		OreDictionary.registerOre("rod" + getName(), subItemStack);
		OreDictionary.registerOre("stick" + getName(), subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.CLUMP);
		OreDictionary.registerOre("clump" + getName(), subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.CRYSTAL);
		OreDictionary.registerOre("crystal" + getName(), subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.SHARD);
		OreDictionary.registerOre("shard" + getName(), subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.WIRE);
		OreDictionary.registerOre("wire" + getName(), subItemStack);
		OreDictionary.registerOre("cable" + getName(), subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.DIRTY);
		OreDictionary.registerOre("dustDirty" + getName(), subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.FOIL);
		OreDictionary.registerOre("foil" + getName(), subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.TINY);
		OreDictionary.registerOre("dustTiny" + getName(), subItemStack);
	}

	public void addBasicSmelting() {
		GameRegistry.addSmelting(new ItemStack(getMaterialItem(), 1, MaterialItem.ORE),
				new ItemStack(getMaterialItem(), 1, MaterialItem.INGOT), 1);
	}
}
