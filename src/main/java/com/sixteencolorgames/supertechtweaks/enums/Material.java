package com.sixteencolorgames.supertechtweaks.enums;

import java.util.HashMap;
import java.util.Map;

import com.sixteencolorgames.supertechtweaks.ModRegistry;
import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.blocks.BlockMaterial;
import com.sixteencolorgames.supertechtweaks.items.MaterialItem;
import com.sixteencolorgames.supertechtweaks.items.MaterialItemBlock;
import com.sixteencolorgames.supertechtweaks.proxy.ClientProxy;
import com.sixteencolorgames.supertechtweaks.util.ItemHelper;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Material extends IForgeRegistryEntry.Impl<Material> {
	public static class MaterialBuilder {
		Material building;

		public MaterialBuilder(String name) {
			building = new Material(name, 0x000000, -1, -1, 5.0, 999999, 10, 30, 15, 200, 150);
		}

		public Material build() {
			building.block = new BlockMaterial(building);

			building.itemBlock = new MaterialItemBlock(building.block, building);
			building.itemBlock.setRegistryName(building.block.getRegistryName());

			building.itemMaterial = new MaterialItem(building);
			return building;
		}

		/**
		 * Bulk Modulus measured in GPa.
		 *
		 * This is how much pressure applied inward this material can withstand.
		 */
		public MaterialBuilder setBulkModulus(int mod) {
			building.bulk = mod;
			return this;
		}

		/**
		 * The RGB code for the color of this
		 */
		public MaterialBuilder setColor(int color) {
			building.color = color;
			return this;
		}

		public MaterialBuilder setCustomDrops(ItemStack itemStack) {
			building.customDrops = itemStack;
			return this;
		}

		/**
		 * density measured in g/cm3 at room temperature
		 */
		public MaterialBuilder setDensity(double density) {
			building.density = density;
			return this;
		}

		/**
		 * Electrical resistance measured in nΩ·m (at 20 °C)
		 */
		public MaterialBuilder setElectricalResistance(double resistance) {
			building.resistance = resistance;
			return this;
		}

		/**
		 * The harvest level of this
		 */
		public MaterialBuilder setHarvestLevel(int level) {
			building.harvest = level;
			return this;
		}

		/**
		 * Shear modulus measured in GPa
		 *
		 * This is how much pressure applied sideways this material can
		 * withstand.
		 */
		public MaterialBuilder setShearModulus(int shear) {
			building.shear = shear;
			return this;
		}

		/**
		 * thermal conductivity measured in W/(m·K)
		 */
		public MaterialBuilder setThermalConductivity(double conductivity) {
			building.conductivity = conductivity;
			return this;
		}

		/**
		 * thermal expansion measured in µm/(m·K) (at 25 °C)
		 */
		public MaterialBuilder setThermalExpansion(double expansion) {
			building.expansion = expansion;
			return this;
		}

		/**
		 * Level that a tool made of this can mine
		 */
		public MaterialBuilder setToolLevel(int level) {
			building.tool = level;
			return this;
		}

		/**
		 * Young's modulus measured in GPa
		 *
		 * This is how much pressure applied outward this material can
		 * withstand.
		 */
		public MaterialBuilder setYoungsModulus(int mod) {
			building.young = mod;
			return this;
		}
	}

	public static IForgeRegistry<Material> REGISTRY;

	public ItemStack customDrops = null;

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
	private int tool;
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
	/**
	 * Young's modulus measured in GPa
	 *
	 * This is how much pressure applied outward this material can withstand.
	 */
	private int young;
	/**
	 * Bulk Modulus measured in GPa.
	 *
	 * This is how much pressure applied inward this material can withstand.
	 */
	private int bulk;

	private BlockMaterial block;

	private ItemBlock itemBlock;

	private MaterialItem itemMaterial;

	private Ore ore;

	private Material(String name, int color, int harvest, int mine, double density, double resistance, double expansion,
			int shear, double conductivity, int young, int bulk) {
		this.name = name;
		this.color = color;
		this.harvest = harvest;
		tool = mine;
		this.density = density;
		this.resistance = resistance;
		this.expansion = expansion;
		this.shear = shear;
		this.conductivity = conductivity;
		this.young = young;
		this.bulk = bulk;
	}

	public void addBasicProcessing() {
		GameRegistry.findRegistry(IRecipe.class)
				.register(new ShapelessOreRecipe(new ResourceLocation("dusts"),
						new ItemStack(itemMaterial, 1, MaterialItem.DUST),
						new Object[] { new OreIngredient("ore" + name), new OreIngredient("toolHammer") })
								.setRegistryName(SuperTechTweaksMod.MODID, "hammer_ore_" + name));
	}

	@SideOnly(Side.CLIENT)
	public void clientPrep() {
		ModelLoader.setCustomModelResourceLocation(itemBlock, 0, ClientProxy.itemLocation);
		ModelLoader.setCustomModelResourceLocation(itemBlock, 0, ClientProxy.blockLocation);
		ModelLoader.setCustomStateMapper(block, blockIn -> {
			final Map<IBlockState, ModelResourceLocation> loc = new HashMap<IBlockState, ModelResourceLocation>();
			loc.put(blockIn.getDefaultState(), ClientProxy.blockLocation);
			return loc;
		});
	}

	public BlockMaterial getBlock() {
		return block;
	}

	public int getBulk() {
		return bulk;
	}

	public int getColor() {
		return color;
	}

	public double getConductivity() {
		return conductivity;
	}

	public double getDensity() {
		return density;
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

	public String getName() {
		return name;
	}

	public double getResistance() {
		return resistance;
	}

	public int getShear() {
		return shear;
	}

	public int getToolLevel() {
		return tool;
	}

	public int getTransferRate() {
		return (int) Math.floor((1 / getResistance()) * getConductivity() * 32);
	}

	public int getYoungs() {
		return young;
	}

	public void registerMaterial() {
		GameRegistry.findRegistry(Block.class).register(block);
		GameRegistry.findRegistry(Item.class).register(itemBlock);
		OreDictionary.registerOre("block" + getName(), new ItemStack(block));

		GameRegistry.findRegistry(Item.class).register(itemMaterial);
		this.setRegistryName(getName());

		registerOreDict();
		SuperTechTweaksMod.proxy.registerModels(this);

		Material.REGISTRY.register(this);

		if (SuperTechTweaksMod.proxy.getSide() == Side.CLIENT) {
			clientPrep();
		}
	}

	public void registerOreDict() {
		ItemStack subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.INGOT);
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
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.HAMMER);
		OreDictionary.registerOre("hammer" + getName(), subItemStack);
		OreDictionary.registerOre("toolHammer", subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.PICKAXE);
		OreDictionary.registerOre("pickaxe" + getName(), subItemStack);
		OreDictionary.registerOre("toolPickaxe", subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.PLIERS);
		OreDictionary.registerOre("pliers" + getName(), subItemStack);
		OreDictionary.registerOre("toolPliers", subItemStack);
		subItemStack = new ItemStack(itemMaterial, 1, MaterialItem.DRAW_PLATE);
		OreDictionary.registerOre("drawplate" + getName(), subItemStack);
		OreDictionary.registerOre("toolDrawPlate", subItemStack);

		// register block
		GameRegistry.findRegistry(IRecipe.class)
				.register(
						new ShapedOreRecipe(new ResourceLocation("blocks"), new ItemStack(block),
								new Object[] { new String[] { "xxx", "xxx", "xxx" }, 'x',
										new OreIngredient("ingot" + name), }).setRegistryName(SuperTechTweaksMod.MODID,
												"ingot_block" + name));
		// register ingot
		GameRegistry.findRegistry(IRecipe.class).register(
				new ShapedOreRecipe(new ResourceLocation("ingots"), new ItemStack(itemMaterial, 1, MaterialItem.INGOT),
						new Object[] { new String[] { "xxx", "xxx", "xxx" }, 'x', new OreIngredient("nugget" + name), })
								.setRegistryName(SuperTechTweaksMod.MODID, "nugget_ingot" + name));
		GameRegistry.findRegistry(IRecipe.class)
				.register(new ShapelessOreRecipe(new ResourceLocation("nuggets"),
						new ItemStack(itemMaterial, 9, MaterialItem.NUGGET),
						new Object[] { new OreIngredient("ingot" + name) }).setRegistryName(SuperTechTweaksMod.MODID,
								"ingot_nugget" + name));
		GameRegistry.findRegistry(IRecipe.class)
				.register(new ShapelessOreRecipe(new ResourceLocation("ingots"),
						new ItemStack(itemMaterial, 9, MaterialItem.INGOT),
						new Object[] { new OreIngredient("block" + name) }).setRegistryName(SuperTechTweaksMod.MODID,
								"block_ingot" + name));

		// register hammer
		GameRegistry.findRegistry(IRecipe.class)
				.register(new ShapedOreRecipe(new ResourceLocation("hammers"),
						new ItemStack(getMaterialItem(), 1, MaterialItem.HAMMER),
						new Object[] { new String[] { " x ", " sx", "s  " }, 'x', new OreIngredient("ingot" + name),
								's', new OreIngredient("stickWood") }).setRegistryName(SuperTechTweaksMod.MODID,
										"hammer" + name));

		// register pliers
		GameRegistry.findRegistry(IRecipe.class)
				.register(new ShapedOreRecipe(new ResourceLocation("pliers"),
						new ItemStack(getMaterialItem(), 1, MaterialItem.PLIERS),
						new Object[] { new String[] { "x x", " p ", "s s" }, 'x', new OreIngredient("ingot" + name),
								'p', new OreIngredient("plate" + name), 's', new OreIngredient("stickWood") })
										.setRegistryName(SuperTechTweaksMod.MODID, "pliers" + name));

		// register pipe block
		ItemStack pipeStack = new ItemStack(ModRegistry.blockPipe, 3);
		ItemHelper.setItemMaterial(pipeStack, getRegistryName().toString());
		GameRegistry.findRegistry(IRecipe.class)
				.register(
						new ShapedOreRecipe(new ResourceLocation("pipe"), pipeStack,
								new Object[] { new String[] { "xxx", "   ", "xxx" }, 'x',
										new OreIngredient("plate" + name) }).setRegistryName(SuperTechTweaksMod.MODID,
												"pipe" + name));

		// register dust to ingot
		GameRegistry.addSmelting(new ItemStack(getMaterialItem(), 1, MaterialItem.DUST),
				new ItemStack(getMaterialItem(), 1, MaterialItem.INGOT), 1);

	}
}
