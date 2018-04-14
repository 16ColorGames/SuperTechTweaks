package com.sixteencolorgames.supertechtweaks;

import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels._0_stone;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels._1_flint;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels._2_copper;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels._3_iron;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels._4_bronze;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels._5_diamond;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels._6_obsidian;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels._7_ardite;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels._8_cobalt;

import com.sixteencolorgames.supertechtweaks.blocks.BlockBasicResearcher;
import com.sixteencolorgames.supertechtweaks.blocks.BlockOre;
import com.sixteencolorgames.supertechtweaks.blocks.BlockResearchViewer;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.enums.Research;
import com.sixteencolorgames.supertechtweaks.items.MaterialItem;
import com.sixteencolorgames.supertechtweaks.tileentities.BasicResearcherTileEntity;
import com.sixteencolorgames.supertechtweaks.tileentities.ResearchViewerTileEntity;

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
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber()
public class ModRegistry {

	public static final int BASIC_RESEARCHER = 1;
	public static final int RESEARCH_VIEWER = 2;
	public static BlockBasicResearcher basicResearcherBlock;
	public static BlockResearchViewer blockResearchViewer;

	static ModelResourceLocation fluidLocation = new ModelResourceLocation("supertechtweaks:blockFluid", "inventory");

	@SideOnly(Side.CLIENT)
	public static void initModels() {
		basicResearcherBlock.initModel();
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		System.out.println("Attempting block registry");
		event.getRegistry().register(new BlockOre());

		basicResearcherBlock = new BlockBasicResearcher();
		event.getRegistry().register(basicResearcherBlock);
		GameRegistry.registerTileEntity(BasicResearcherTileEntity.class,
				SuperTechTweaksMod.MODID + "_basicresearcherblock");

		blockResearchViewer = new BlockResearchViewer();
		event.getRegistry().register(blockResearchViewer);
		GameRegistry.registerTileEntity(ResearchViewerTileEntity.class,
				SuperTechTweaksMod.MODID + "_researchviewerblock");
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry()
				.register(new ItemBlock(basicResearcherBlock).setRegistryName(basicResearcherBlock.getRegistryName()));
		event.getRegistry()
				.register(new ItemBlock(blockResearchViewer).setRegistryName(blockResearchViewer.getRegistryName()));
	}

	@SubscribeEvent
	public static void registerMaterials(RegistryEvent.Register<Material> event) {

		Material material = new Material("Antimony", "0xFADA5E", 0);
		material.registerMaterial();
		material = new Material("Bismuth", "0xed7d92", 0);
		material.registerMaterial();
		material = new Material("Cadmium", "0xed872d", 0);
		material.registerMaterial();
		material = new Material("Mercury", "0x751f27", 0);
		material.registerMaterial();
		material = new Material("Copper", "0xb4713d", 1, _2_copper);
		material.registerMaterial();
		material.addBasicSmelting();
		material = new Material("Zinc", "0xbac4c8", 1);
		material.registerMaterial();
		material = new Material("Coal", "0x060607", 1) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.COAL, 1, 0);
			}
		};
		material.registerMaterial();
		material = new Material("Monazit", "0x82c59c", 1);
		material.registerMaterial();
		material = new Material("Iron", "0xd3ad90", 2, _3_iron);
		material.registerMaterial();
		material.addBasicSmelting();
		material = new Material("Chromium", "0x18391e", 2);
		material.registerMaterial();
		material = new Material("Aluminum", "0xe0d9cd", 2);
		material.registerMaterial();
		material = new Material("Silver", "0xb5b5bd", 2, _1_flint);
		material.registerMaterial();
		material.addBasicSmelting();
		material = new Material("Tellurium", "0xb5b5bd", 2);
		material.registerMaterial();
		material = new Material("Lapis", "0x000094", 2) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.DYE, 5, 4);
			}
		};
		material.registerMaterial();
		material = new Material("Tin", "0x726a78", 3);
		material.registerMaterial();
		material.addBasicSmelting();
		material = new Material("Gold", "0xcccc33", 3);
		material.registerMaterial();
		material.addBasicSmelting();
		material = new Material("Lead", "0x474c4d", 3, _1_flint);
		material.registerMaterial();
		material.addBasicSmelting();
		material = new Material("Redstone", "0xd43c2c", 3) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.REDSTONE, 4, 0);
			}
		};
		material.registerMaterial();
		material = new Material("Sulfur", "0xedff21", 3) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(this.getMaterialItem(), 1, MaterialItem.DUST);
			}
		};
		material.registerMaterial();
		material = new Material("Nickel", "0xccd3d8", 3);
		material.registerMaterial();
		material.addBasicSmelting();
		material = new Material("Osmium", "0x9090a3", 3);
		material.registerMaterial();
		material = new Material("ColdIron", "0x5f6c81", 3);
		material.registerMaterial();
		material = new Material("Diamond", "0xb9f2ff", 4) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.DIAMOND, 1, 0);
			}
		};
		material.registerMaterial();
		material = new Material("Emerald", "0x50c878", 4) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.EMERALD, 1, 0);
			}
		};
		material.registerMaterial();
		material = new Material("Ruby", "0x9b111e", 4) {
			@Override
			public ItemStack getDrops(byte base) {
				if (OreDictionary.doesOreNameExist("gemRuby")) {
					return OreDictionary.getOres("gemRuby").get(0);
				} else {
					return super.getDrops(base);
				}
			}
		};
		material.registerMaterial();
		material = new Material("Sapphire", "0x297bc1", 4) {
			@Override
			public ItemStack getDrops(byte base) {
				if (OreDictionary.doesOreNameExist("gemSapphire")) {
					return OreDictionary.getOres("gemSapphire").get(0);
				} else {
					return super.getDrops(base);
				}
			}
		};
		material.registerMaterial();
		material = new Material("Amethyst", "0x642552", 4) {
			@Override
			public ItemStack getDrops(byte base) {
				if (OreDictionary.doesOreNameExist("gemAmethyst")) {
					return OreDictionary.getOres("gemAmethyst").get(0);
				} else {
					return super.getDrops(base);
				}
			}
		};
		material.registerMaterial();
		material = new Material("Manganese", "0x242d36", 4);
		material.registerMaterial();
		material = new Material("Ardite", "0xd94925", 6, _7_ardite);
		material.registerMaterial();
		material = new Material("Uranium", "0x329832", 6);
		material.registerMaterial();
		material = new Material("Platinum", "0xb8b7b2", 6);
		material.registerMaterial();
		material = new Material("Yellorium", "0xffce00", 6);
		material.registerMaterial();
		material = new Material("Draconium", "0xd2a8d4", 6);
		material.registerMaterial();
		material = new Material("Cobalt", "0x0071b6", 7, _8_cobalt);
		material.registerMaterial();
		material = new Material("Iridium", "0xe0e2dd", 7);
		material.registerMaterial();
		material = new Material("Titanium", "0x323230", 7);
		material.registerMaterial();
		material = new Material("Stone", "0x8B8D7A", -1, _0_stone);
		material.registerMaterial();
		material = new Material("Paper", "0xF0EEE1", -1, _0_stone);
		material.registerMaterial();
		material = new Material("Sponge", "0xD8C060", -1, _0_stone);
		material.registerMaterial();
		material = new Material("Slime", 0x32CD32, -1, _0_stone);
		material.registerMaterial();
		material = new Material("Cactus", "0x64F566", -1, _0_stone);
		material.registerMaterial();
		material = new Material("Netherrack", "0x800000", -1, _0_stone);
		material.registerMaterial();
		material = new Material("Wood", "0x000000", -1, _0_stone);
		material.registerMaterial();
		material = new Material("Bone", "0xFFF9ED", -1, _1_flint);
		material.registerMaterial();
		material = new Material("Flint", "0x6E6460", 0, _1_flint) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.FLINT, 1, 0);
			}
		};
		material.registerMaterial();
		material = new Material("Prismarine", "0x000000", -1, _1_flint);
		material.registerMaterial();
		material = new Material("Electrum", "0x928729", 2, _1_flint);
		material.registerMaterial();
		material = new Material("Brass", "0xE4AD5B", 2, _2_copper);
		material.registerMaterial();
		material = new Material("Constantan", "0xE0A050", 3, _3_iron);
		material.registerMaterial();
		material = new Material("Endstone", "0xDCDEA4", 1, _3_iron);
		material.registerMaterial();
		material = new Material("Bronze", "0xE69E2F", 4, _4_bronze);
		material.registerMaterial();
		material = new Material("Steel", "0xdfdfdf", 5, _5_diamond);
		material.registerMaterial();
		material = new Material("Pigiron", "0xff9999", 5, _5_diamond);
		material.registerMaterial();
		material = new Material("Obsidian", "0x3d354b", 3, _3_iron) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Blocks.OBSIDIAN, 1, 0);
			}
		};
		material.registerMaterial();
		material = new Material("Mithril", "0xAEBBDB", 5, _6_obsidian);
		material.registerMaterial();
		material = new Material("NetherQuartz", "0xdddddd", 1) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.QUARTZ, 1, 0);
			}
		};
		material.registerMaterial();
		material = new Material("Adamantine", "0xb30000", 8, 9);
		material.registerMaterial();
		material = new Material("AluBrass", "0xCAA585", 1);
		material.registerMaterial();
		material = new Material("Magma", "0x000000", -1);
		material.registerMaterial();
		material = new Material("Resonating", "0xFF4500", 2);
		material.registerMaterial();
		material = new Material("BlackQuartz", "0x1A1A1A", 2) {
			@Override
			public ItemStack getDrops(byte base) {
				if (OreDictionary.doesOreNameExist("gemQuartzBlack")) {
					return OreDictionary.getOres("gemQuartzBlack").get(0);
				} else {
					return super.getDrops(base);
				}
			}
		};
		material.registerMaterial();
		material = new Material("Invar", "0xD0C0B3", 3);
		material.registerMaterial();
		material = new Material("Nichrome", "0x858F80", 3);
		material.registerMaterial();
		material = new Material("Osmiridium", "0x191970", 7);
		material.registerMaterial();
		material = new Material("Signalum", "0x800000", 7);
		material.registerMaterial();
		material = new Material("StainlessSteel", "0xE0DFDB", 2);
		material.registerMaterial();
	}

	@SubscribeEvent
	public static void registerRegistry(RegistryEvent.NewRegistry event) {
		IForgeRegistry builder = new RegistryBuilder().setType(Material.class)
				.setName(new ResourceLocation(SuperTechTweaksMod.MODID, "MaterialRegistry")).setIDRange(0, 256)
				.create();
		builder = new RegistryBuilder().setType(Research.class)
				.setName(new ResourceLocation(SuperTechTweaksMod.MODID, "ResearchRegistry")).setIDRange(0, 512)
				.create();
	}

	@SubscribeEvent
	public static void registerResearch(RegistryEvent.Register<Research> event) {
		Research r = new Research("sample").setEnergyRequired(1000).addRequirement(new ResourceLocation("crafting"))
				.setDisplay(new ItemStack(Items.APPLE)).setTitle("First Sample");
		event.getRegistry().register(r);
		r = new Research("sample2").setEnergyRequired(1000).addRequirement(new ResourceLocation("crafting"))
				.setDisplay(new ItemStack(Items.ACACIA_BOAT, 4)).setTitle("Second Sample");
		event.getRegistry().register(r);
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		// add smelting, since there's no json for that
	}
}
