package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.blocks.BlockOre;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.items.MaterialItem;
import com.sixteencolorgames.supertechtweaks.render.BlockColor;
import com.sixteencolorgames.supertechtweaks.render.MetalColor;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels.*;

import java.util.function.Consumer;
import java.util.function.Function;

@ObjectHolder("supertechtweaks")
@Mod.EventBusSubscriber()
public class ModRegistry {

	static ModelResourceLocation fluidLocation = new ModelResourceLocation("supertechtweaks:blockFluid", "inventory");

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		System.out.println("Attempting block registry");
		event.getRegistry().register(new BlockOre());
	}

	@SubscribeEvent
	public static void registerRegistry(RegistryEvent.NewRegistry event) {
		IForgeRegistry builder = new RegistryBuilder().setType(Material.class)
				.setName(new ResourceLocation(SuperTechTweaksMod.MODID, "MaterialRegistry")).setIDRange(0, 256)
				.create();
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
		material = new Material("Apatite", "0xc3b89c", 2) {
			@Override
			public ItemStack getDrops(byte base) {
				if (OreDictionary.doesOreNameExist("gemApatite")) {
					return OreDictionary.getOres("gemApatite").get(0);
				} else {
					return super.getDrops(base);
				}
			}
		};
		material.registerMaterial();
		material = new Material("Chromium", "0x18391e", 2);
		material.registerMaterial();
		material = new Material("Aluminum", "0xe0d9cd", 2);
		material.registerMaterial();
		material = new Material("Silver", "0xb5b5bd", 2, _1_flint);
		material.registerMaterial();
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
		material = new Material("Gold", "0xcccc33", 3);
		material.registerMaterial();
		material = new Material("Lead", "0x474c4d", 3, _1_flint);
		material.registerMaterial();
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
		material = new Material("Certus", "0xe6d7df", 5) {
			@Override
			public ItemStack getDrops(byte base) {
				if (OreDictionary.doesOreNameExist("crystalCertusQuartz")) {
					return OreDictionary.getOres("crystalCertusQuartz").get(0);
				} else {
					return super.getDrops(base);
				}
			}
		};
		material.registerMaterial();
		material = new Material("CertusCharged", "0xeadadf", 5);
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
		material = new Material("Basalt", "0x202A29", 2, _0_stone);
		material.registerMaterial();
		material = new Material("Paper", "0xF0EEE1", -1, _0_stone);
		material.registerMaterial();
		material = new Material("Sponge", "0xD8C060", -1, _0_stone);
		material.registerMaterial();
		material = new Material("Firewood", "0x8B0000", -1, _0_stone);
		material.registerMaterial();
		material = new Material("Slime", 0x32CD32, -1, _0_stone);
		material.registerMaterial();
		material = new Material("BlueSlime", 0x20B2AA, -1, _0_stone);
		material.registerMaterial();
		material = new Material("MagmaSlime", 0xFF4500, -1, _0_stone);
		material.registerMaterial();
		material = new Material("TreatedWood", "0x000000", -1, _0_stone);
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
		material = new Material("Xu_demonic_metal", "0x000000", -1, _1_flint);
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
		material = new Material("Obsidian", "0x0c0f04", 3, _3_iron) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Blocks.OBSIDIAN, 1, 0);
			}
		};
		material.registerMaterial();
		material = new Material("Manyullyn", "0xBA55D3", 9, _9_manyullym);
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
		material = new Material("Terrax", "0xA9A9A9", 4, 5);
		material.registerMaterial();
		material = new Material("Karmesine", "0xC71585", 4);
		material.registerMaterial();
		material = new Material("Ovium", "0x8A2BE2", 4);
		material.registerMaterial();
		material = new Material("Jauxum", "0x7FFF00", 4);
		material.registerMaterial();
		material = new Material("Aurorium", "0xFFC0CB", 4, 5);
		material.registerMaterial();
		material = new Material("Tiberium", "0xD4FF00", 2, 3);
		material.registerMaterial();
		material = new Material("Dilithium", "0xC0C0CC", 2);
		material.registerMaterial();
		material = new Material("Fractum", "0x000000", 3, 4);
		material.registerMaterial();
		material = new Material("Triberium", "0x000000", 3, 3);
		material.registerMaterial();
		material = new Material("Abyssum", "0x000000", 4);
		material.registerMaterial();
		material = new Material("Tritonite", "0x000000", 8, 8);
		material.registerMaterial();
		material = new Material("Violium", "0x000000", 7, 8);
		material.registerMaterial();
		material = new Material("Astrium", "0x000000", 5, 6);
		material.registerMaterial();
		material = new Material("Nihilite", "0x000000", 7, 7);
		material.registerMaterial();
		material = new Material("Vibranium", "0x000000", 7, 8);
		material.registerMaterial();
		material = new Material("Solarium", "0x000000", 7, 7);
		material.registerMaterial();
		material = new Material("Valyrium", "0x000000", 6, 7);
		material.registerMaterial();
		material = new Material("Uru", "0x000000", 6, 7);
		material.registerMaterial();
		material = new Material("Adamant", "0x000000", 7, 7);
		material.registerMaterial();
		material = new Material("Nucleum", "0x000000", 7, 7);
		material.registerMaterial();
		material = new Material("Iox", "0x000000", 10);
		material.registerMaterial();
		material = new Material("Seismum", "0x000000", 5, 6);
		material.registerMaterial();
		material = new Material("Imperomite", "0x000000", 6, 6);
		material.registerMaterial();
		material = new Material("Eezo", "0x38596A", 4, 5);
		material.registerMaterial();
		material = new Material("Duranite", "0x000000", 5, 6);
		material.registerMaterial();
		material = new Material("Promethium", "0x000000", 5, 6);
		material.registerMaterial();
		material = new Material("Ignitz", "0x000000", 7, 7);
		material.registerMaterial();
		material = new Material("Proxii", "0x000000", 6, 6);
		material.registerMaterial();
		material = new Material("Osram", "0x000000", 4);
		material.registerMaterial();
		material = new Material("Palladium", "0x000000", 5, 9);
		material.registerMaterial();
		material = new Material("Yrdeen", "0x000000", 7, 7);
		material.registerMaterial();
		material = new Material("Niob", "0x000000", 6, 6);
		material.registerMaterial();
		material = new Material("Obsidiorite", "0x4B0082", 4, 4);
		material.registerMaterial();
		material = new Material("Lumix", "0x000000", 4, 4);
		material.registerMaterial();
		material = new Material("Dyonite", "0x000000", 6, 6);
		material.registerMaterial();
		material = new Material("AluBrass", "0xCAA585", 1);
		material.registerMaterial();
		material = new Material("Nitronite", "0x00000", 4);
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
}
