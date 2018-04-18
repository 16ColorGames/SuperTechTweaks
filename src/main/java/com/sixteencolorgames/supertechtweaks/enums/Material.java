package com.sixteencolorgames.supertechtweaks.enums;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.blocks.BlockMaterial;
import com.sixteencolorgames.supertechtweaks.items.MaterialItem;
import com.sixteencolorgames.supertechtweaks.items.MaterialItemBlock;
import com.sixteencolorgames.supertechtweaks.proxy.ClientProxy;

import net.minecraft.block.Block;
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

	/**
	 * density measured in g/cm3 at room temperature
	 */
	private double density;
	/**
	 * Electrical resistance measured in nΩ·m (at 20 °C)
	 */
	private double resistance;
	/**
	 * thermal expansion measured in µm/(m·K) (at 25 °C)
	 */
	private double expansion;
	/**
	 * Shear modulus measured in GPa
	 */
	private int shear;
	/**
	 * thermal conductivity measured in W/(m·K)
	 */
	private double conductivity;

	private BlockMaterial block;
	private ItemBlock itemBlock;
	private MaterialItem itemMaterial;

	private Material(String name, int color, int harvest, int mine, double density, double resistance, double expansion,
			int shear, double conductivity) {
		this.name = name;
		this.color = color;
		this.harvest = harvest;
		this.mine = mine;
		this.density = density;
		this.resistance = resistance;
		this.expansion = expansion;
		this.shear = shear;
		this.conductivity = conductivity;
		block = new BlockMaterial(this);

		itemBlock = new MaterialItemBlock(block, this);
		itemBlock.setRegistryName(block.getRegistryName());

		itemMaterial = new MaterialItem(this);
		System.out.println("Calculated for: " + name + "," + (1 / resistance) + ","
				+ Math.floor((1 / resistance) * conductivity * 32) + ","
				+ Math.floor((1 / conductivity) * 100 * density));
	}

	/**
	 *
	 * @param name
	 *            The ore dictionary name
	 * @param color
	 * @param harvest
	 */
	public Material(String name, String color, int harvest, double density, double resistance, double expansion,
			int shear, double conductivity) {
		this(name, color, harvest, -1, density, resistance, expansion, shear, conductivity);
	}

	public Material(String name, String color, int harvest, int mine, double density, double resistance,
			double expansion, int shear, double conductivity) {

		this(name, Color.decode(color).getRGB(), harvest, mine, density, resistance, expansion, shear, conductivity);
	}

	public void addBasicSmelting() {
		GameRegistry.addSmelting(new ItemStack(getMaterialItem(), 1, MaterialItem.ORE),
				new ItemStack(getMaterialItem(), 1, MaterialItem.INGOT), 1);
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

	public BlockMaterial getBlock() {
		return block;
	}

	public int getColor() {
		return color;
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

	public int getHarvest() {
		return harvest;
	}

	public Item getItemBlock() {
		return itemBlock;
	}

	public MaterialItem getMaterialItem() {
		return itemMaterial;
	}

	public int getMine() {
		return mine;
	}

	public String getName() {
		return name;
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

		if (SuperTechTweaksMod.proxy.getSide() == Side.CLIENT) {
			clientPrep();
		}
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
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.COIN);
		OreDictionary.registerOre("coin" + getName(), subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.BLADE);
		OreDictionary.registerOre("blade" + getName(), subItemStack);
	}
}
