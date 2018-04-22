package com.sixteencolorgames.supertechtweaks;

import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels._0_stone;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels._1_flint;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels._2_copper;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels._3_iron;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels._4_bronze;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels._5_diamond;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels._6_obsidian;

import com.sixteencolorgames.supertechtweaks.blocks.BlockMultiWall;
import com.sixteencolorgames.supertechtweaks.blocks.BlockOre;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.enums.Research;
import com.sixteencolorgames.supertechtweaks.items.ItemTechComponent;
import com.sixteencolorgames.supertechtweaks.items.MaterialItem;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiWall;
import com.sixteencolorgames.supertechtweaks.tileentities.basicresearcher.BlockBasicResearcher;
import com.sixteencolorgames.supertechtweaks.tileentities.basicresearcher.TileBasicResearcher;
import com.sixteencolorgames.supertechtweaks.tileentities.cable.BlockCable;
import com.sixteencolorgames.supertechtweaks.tileentities.cable.TileCable;
import com.sixteencolorgames.supertechtweaks.tileentities.multipowerinput.BlockMultiPowerInput;
import com.sixteencolorgames.supertechtweaks.tileentities.multipowerinput.TileMultiPowerInput;
import com.sixteencolorgames.supertechtweaks.tileentities.researchselector.BlockResearchSelector;
import com.sixteencolorgames.supertechtweaks.tileentities.researchselector.TileResearchSelector;
import com.sixteencolorgames.supertechtweaks.tileentities.solidfuelgenerator.BlockSolidFuelGenerator;
import com.sixteencolorgames.supertechtweaks.tileentities.solidfuelgenerator.TileSolidFuelGenerator;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber()
public class ModRegistry {

	public static final int BASIC_RESEARCHER = 1;
	public static final int RESEARCH_SELECTER = 2;
	public static final int SOLID_GENERATOR = 3;
	public static BlockBasicResearcher basicResearcherBlock;
	public static BlockResearchSelector blockResearchViewer;
	public static BlockMultiWall blockMultiWall;
	public static BlockOre superore;
	public static BlockSolidFuelGenerator blockSolidFuelGenerator;
	public static BlockMultiPowerInput blockMultiPowerInput;
	public static BlockCable blockCable;

	public static ItemTechComponent itemTechComponent;

	static ModelResourceLocation fluidLocation = new ModelResourceLocation("supertechtweaks:blockFluid", "inventory");

	@SideOnly(Side.CLIENT)
	public static void initItemModels() {
		blockCable.initItemModel();
	}

	@SideOnly(Side.CLIENT)
	public static void initModels() {
		basicResearcherBlock.initModel();
		blockResearchViewer.initModel();
		blockMultiWall.initModel();
		blockSolidFuelGenerator.initModel();
		blockMultiPowerInput.initModel();
		blockCable.initModel();
		itemTechComponent.registerModels();
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		System.out.println("Attempting block registry");

		basicResearcherBlock = new BlockBasicResearcher();
		event.getRegistry().register(basicResearcherBlock);
		GameRegistry.registerTileEntity(TileBasicResearcher.class, SuperTechTweaksMod.MODID + "_basicresearcherblock");

		blockResearchViewer = new BlockResearchSelector();
		event.getRegistry().register(blockResearchViewer);
		GameRegistry.registerTileEntity(TileResearchSelector.class,
				SuperTechTweaksMod.MODID + "_researchselectorblock");

		superore = new BlockOre();
		event.getRegistry().register(superore);

		blockMultiWall = new BlockMultiWall();
		event.getRegistry().register(blockMultiWall);
		GameRegistry.registerTileEntity(TileMultiWall.class, SuperTechTweaksMod.MODID + "_tilemultiwall");

		blockMultiPowerInput = new BlockMultiPowerInput();
		event.getRegistry().register(blockMultiPowerInput);
		GameRegistry.registerTileEntity(TileMultiPowerInput.class, SuperTechTweaksMod.MODID + "_tilemultipowerinput");

		blockCable = new BlockCable();
		event.getRegistry().register(blockCable);
		GameRegistry.registerTileEntity(TileCable.class, SuperTechTweaksMod.MODID + "_tilecable");

		blockSolidFuelGenerator = new BlockSolidFuelGenerator(false);
		event.getRegistry().register(blockSolidFuelGenerator);
		GameRegistry.registerTileEntity(TileSolidFuelGenerator.class,
				SuperTechTweaksMod.MODID + "_tilesolidfuelgenerator");

	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry()
				.register(new ItemBlock(basicResearcherBlock).setRegistryName(basicResearcherBlock.getRegistryName()));
		event.getRegistry()
				.register(new ItemBlock(blockResearchViewer).setRegistryName(blockResearchViewer.getRegistryName()));
		event.getRegistry().register(new ItemBlock(superore).setRegistryName(superore.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockMultiWall).setRegistryName(blockMultiWall.getRegistryName()));
		event.getRegistry()
				.register(new ItemBlock(blockMultiPowerInput).setRegistryName(blockMultiPowerInput.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockCable).setRegistryName(blockCable.getRegistryName()));
		event.getRegistry().register(
				new ItemBlock(blockSolidFuelGenerator).setRegistryName(blockSolidFuelGenerator.getRegistryName()));

		itemTechComponent = new ItemTechComponent();
		event.getRegistry().register(itemTechComponent);
		itemTechComponent.setupDictionary();
	}

	@SubscribeEvent
	public static void registerMaterials(RegistryEvent.Register<Material> event) {
		Material material = new Material("Antimony", "0xFADA5E", 0, 6.697, 417, 11, 20, 24.4);
		material.registerMaterial();
		material = new Material("Bismuth", "0xed7d92", 0, 9.78, 1290, 13.4, 12, 7.97);
		material.registerMaterial();
		material = new Material("Cadmium", "0xed872d", 0, 8.65, 72.7, 30.8, 19, 96.6);
		material.registerMaterial();
		material = new Material("Mercury", "0x751f27", 0, 13.546, 961, 60.4, 0, 8.3);
		material.registerMaterial();
		material = new Material("Copper", "0xb4713d", 1, _2_copper, 8.96, 16.78, 16.5, 48, 401);
		material.registerMaterial();
		material.addBasicSmelting();
		material = new Material("Zinc", "0xbac4c8", 1, 7.14, 59, 30.2, 43, 116);
		material.registerMaterial();
		material = new Material("Coal", "0x060607", 1, 2.09, 30000, 6, 6, 293) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.COAL, 1, 0);
			}
		};
		material.registerMaterial();
		material = new Material("Iron", "0xd3ad90", 2, _3_iron, 7.874, 96.1, 11.8, 82, 80.4);
		material.registerMaterial();
		material.addBasicSmelting();
		material = new Material("Chromium", "0x18391e", 2, 7.19, 125, 4.9, 115, 93.9);
		material.registerMaterial();
		material = new Material("Aluminum", "0xe0d9cd", 2, 2.7, 28.2, 23.1, 26, 237);
		material.registerMaterial();
		material = new Material("Silver", "0xb5b5bd", 2, _1_flint, 10.49, 15.87, 18.9, 30, 429);
		material.registerMaterial();
		material.addBasicSmelting();
		material = new Material("Tellurium", "0xb5b5bd", 2, 6.24, 10000, 18, 16, 2.5);
		material.registerMaterial();
		material = new Material("Lapis", "0x000094", 2, 2.8, 999999, 6, 200, 293) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.DYE, 5, 4);
			}
		};
		material.registerMaterial();
		material = new Material("Tin", "0x726a78", 3, 7.265, 115, 22, 18, 66.8);
		material.registerMaterial();
		material.addBasicSmelting();
		material = new Material("Gold", "0xcccc33", 3, 19.3, 22.14, 14.2, 27, 318);
		material.registerMaterial();
		material.addBasicSmelting();
		material = new Material("Lead", "0x474c4d", 3, _1_flint, 11.34, 208, 28.9, 5, 35.3);
		material.registerMaterial();
		material.addBasicSmelting();
		material = new Material("Redstone", "0xd43c2c", 3, 8.96, 16.78, 16.5, 48, 200) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.REDSTONE, 4, 0);
			}
		};
		material.registerMaterial();
		material = new Material("Sulfur", "0xedff21", 3, 2.07, 2 * Math.pow(10, 15), 0, 0, .205) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(getMaterialItem(), 1, MaterialItem.DUST);
			}
		};
		material.registerMaterial();
		material = new Material("Nickel", "0xccd3d8", 3, 8.908, 69.3, 13.4, 76, 90.9);
		material.registerMaterial();
		material.addBasicSmelting();
		material = new Material("Osmium", "0x9090a3", 3, 22.59, 81.2, 5.1, 222, 87.6);
		material.registerMaterial();
		material = new Material("Diamond", "0xb9f2ff", 3, 3.52, 999999, 100, 455, 1800) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.DIAMOND, 1, 0);
			}
		};
		material.registerMaterial();
		material = new Material("Emerald", "0x50c878", 4, 2.76, 999999, 100, 150, 1800) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.EMERALD, 1, 0);
			}
		};
		material.registerMaterial();
		material = new Material("Manganese", "0x242d36", 4, 7.21, 1440, 21.7, 0, 7.81);
		material.registerMaterial();
		material = new Material("Uranium", "0x329832", 6, 19.1, 280, 13.9, 111, 27.5);
		material.registerMaterial();
		material = new Material("Platinum", "0xb8b7b2", 6, 21.45, 105, 8.8, 61, 71.6);
		material.registerMaterial();
		material = new Material("Iridium", "0xe0e2dd", 7, 22.56, 47.1, 6.4, 210, 147);
		material.registerMaterial();
		material = new Material("Titanium", "0x323230", 7, 4.506, 420, 8.6, 44, 21.9);
		material.registerMaterial();
		material = new Material("Stone", "0x8B8D7A", -1, _0_stone, 1.6, 999999, 2.7, 30, 12.83);
		material.registerMaterial();
		material = new Material("Netherrack", "0x800000", -1, _0_stone, 1.24, 999999, 2.7, 30, 14.87);
		material.registerMaterial();
		material = new Material("Wood", "0x4f2412", -1, _0_stone, .75, 10000, 30, 13, .15);
		material.registerMaterial();
		material = new Material("Electrum", "0x928729", 2, _1_flint, 14.2, 24.67, 20, 35, 400);
		material.registerMaterial();
		material = new Material("Brass", "0xE4AD5B", 2, _2_copper, 8.78, 59.92, 20.5, 40, 109);
		material.registerMaterial();
		material = new Material("Constantan", "0xE0A050", 3, _3_iron, 8.885, 490, 14.9, 62, 21.2);
		material.registerMaterial();
		material = new Material("Bronze", "0xE69E2F", 4, _4_bronze, 8.73, 111.86, 18.2, 44, 26);
		material.registerMaterial();
		material = new Material("Steel", "0xdfdfdf", 5, _5_diamond, 7.9, 169, 72, 79, 50.2);
		material.registerMaterial();
		material = new Material("Obsidian", "0x3d354b", 3, _3_iron, 2.4, 9999999, 2.9, 12, .8) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Blocks.OBSIDIAN, 1, 0);
			}
		};
		material.registerMaterial();
		material = new Material("Mithril", "0xAEBBDB", 5, _6_obsidian, 2.3, 14.3, 20, 200, 521);
		material.registerMaterial();
		material = new Material("NetherQuartz", "0xdddddd", 1, 2.329, 23000, 2.6, 72, 149) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.QUARTZ, 1, 0);
			}
		};
		material.registerMaterial();
		material = new Material("Adamantine", "0xb30000", 8, 9, 24, 400, 1, 9001, 50);
		material.registerMaterial();
		material = new Material("AluBrass", "0xCAA585", 1, 7.78, 115, 17.8, 30, 65);
		material.registerMaterial();
		material = new Material("Invar", "0xD0C0B3", 3, 8.1, 80, 7.3, 326, 13);
		material.registerMaterial();
		material = new Material("Nichrome", "0x858F80", 3, 8.4, 125, 14, 100, 11.3);
		material.registerMaterial();
		material = new Material("StainlessSteel", "0xE0DFDB", 2, 8, 7200, 17.3, 86, 16.2);
		material.registerMaterial();
	}

	@SubscribeEvent
	public static void registerRegistry(RegistryEvent.NewRegistry event) {
		new RegistryBuilder().setType(Material.class)
				.setName(new ResourceLocation(SuperTechTweaksMod.MODID, "MaterialRegistry")).setIDRange(0, 256)
				.create();
		new RegistryBuilder().setType(Research.class)
				.setName(new ResourceLocation(SuperTechTweaksMod.MODID, "ResearchRegistry")).setIDRange(0, 512)
				.create();
	}

	@SubscribeEvent
	public static void registerResearch(RegistryEvent.Register<Research> event) {
		IForgeRegistry<Material> mats = GameRegistry.findRegistry(Material.class);
		event.getRegistry()
				.register(new Research("automation").setEnergyRequired(1000)
						.setDisplay(new ItemStack(ModRegistry.itemTechComponent, 1, ItemTechComponent.SMALL_POWER_UNIT))
						.setTitle("Basic Automation"));
		event.getRegistry()
				.register(
						new Research("electronics")
								.setDisplay(new ItemStack(ModRegistry.itemTechComponent, 1,
										ItemTechComponent.BASIC_CIRCUIT))
								.setTitle("Basic Electrinics").setEnergyRequired(1000)
								.addRequirement(new ResourceLocation("supertechtweaks:automation")));
		event.getRegistry().register(new Research("optics").setTitle("Optics").setDisplay(new ItemStack(Blocks.TORCH)));
		event.getRegistry()
				.register(new Research("combustion").setTitle("Combustion").setDisplay(new ItemStack(Items.GUNPOWDER)));
		event.getRegistry()
				.register(new Research("logistics").setTitle("Logistics").setDisplay(new ItemStack(Blocks.HOPPER)));
		event.getRegistry().register(
				new Research("metallurgy").setTitle("Metallurgy").setDisplay(new ItemStack(Items.BLAZE_POWDER)));
		event.getRegistry()
				.register(new Research("advancediron1").setTitle("Advanced Iron Processing 1")
						.setDisplay(new ItemStack(Items.IRON_INGOT))
						.addRequirement(new ResourceLocation("supertechtweaks:metallurgy")));
		event.getRegistry()
				.register(new Research("advancedcopper1").setTitle("Advanced Copper Processing 1")
						.setDisplay(new ItemStack(
								mats.getValue(new ResourceLocation("supertechtweaks:copper")).getMaterialItem(), 1,
								MaterialItem.INGOT))
				.addRequirement(new ResourceLocation("supertechtweaks:metallurgy")));
		event.getRegistry().register(new Research("steel").setTitle("Steel").setDisplay(new ItemStack(Items.IRON_INGOT))
				.addRequirement(new ResourceLocation("supertechtweaks:advancediron1")));
		event.getRegistry()
				.register(new Research("solarenergy").setTitle("Solar Energy").setDisplay(new ItemStack(Blocks.TORCH))
						.addRequirement(new ResourceLocation("supertechtweaks:optics"))
						.addRequirement(new ResourceLocation("supertechtweaks:electronics")));
		event.getRegistry()
				.register(new Research("engines").setTitle("Engines").setDisplay(new ItemStack(Blocks.LIT_FURNACE))
						.addRequirement(new ResourceLocation("supertechtweaks:steel"))
						.addRequirement(new ResourceLocation("supertechtweaks:combustion"))
						.addRequirement(new ResourceLocation("automation2")));
		event.getRegistry()
				.register(new Research("automation2").setTitle("Automation 2").setDisplay(new ItemStack(Blocks.TORCH))
						.addRequirement(new ResourceLocation("supertechtweaks:electronics")));
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		// add smelting, since there's no json for that
	}
}
