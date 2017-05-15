package com.sixteencolorgames.supertechtweaks.proxy;

import java.io.File;

import com.sixteencolorgames.supertechtweaks.Config;
import com.sixteencolorgames.supertechtweaks.ModBlocks;
import com.sixteencolorgames.supertechtweaks.ModFluids;
import com.sixteencolorgames.supertechtweaks.ModItems;
import static com.sixteencolorgames.supertechtweaks.ModItems.itemMaterialObject;
import static com.sixteencolorgames.supertechtweaks.ModItems.itemOreChunk;
import com.sixteencolorgames.supertechtweaks.Recipies;
import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.compat.MainCompatHandler;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels.*;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.*;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.END;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.NETHER;
import com.sixteencolorgames.supertechtweaks.world.GenerationParser;
import com.sixteencolorgames.supertechtweaks.world.ModWorldGeneration;
import com.sixteencolorgames.supertechtweaks.world.WorldGeneratorBase;
import java.util.ArrayList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.network.Events;
import com.sixteencolorgames.supertechtweaks.network.PacketHandler;
import net.minecraftforge.common.MinecraftForge;

/**
 * Proxy functions common to both the client and server side
 *
 * @author oa10712
 *
 */
public class CommonProxy {

    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        PacketHandler.registerMessages(SuperTechTweaksMod.MODID + "Channel");
        initMaterials();
        MainCompatHandler.registerWaila();
        MainCompatHandler.registerTiCon();
        MainCompatHandler.registerMekanism();
        File configFolder = new File(e.getModConfigurationDirectory().toString() + "/supertechtweaks/");
        config = new Configuration(new File(configFolder.getPath(), "config.cfg"));
        Config.readConfig();

        ModWorldGeneration generator = new ModWorldGeneration();
        for (File gen : configFolder.listFiles()) {
            if (gen.getName().contains(".json")) {
                try {
                    ArrayList<WorldGeneratorBase> parsed = GenerationParser.parseScripts(gen);
                    parsed.forEach((WorldGeneratorBase base) -> {
                        GameRegistry.registerWorldGenerator(base, 3 + base.params.hashCode() + base.hashCode());
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        System.out.println("Generators Loaded");
        ModFluids.mainRegistry();
        ModBlocks.init();
        ModItems.init();
        Material.materials.forEach((metal) -> {
            ItemStack subItemStack = new ItemStack(itemOreChunk, 1, metal.ordinal());
            OreDictionary.registerOre("ore" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemOreChunk, 1, metal.ordinal() + NETHER);
            OreDictionary.registerOre("oreNether" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemOreChunk, 1, metal.ordinal() + END);
            OreDictionary.registerOre("oreEnd" + metal.getName(), subItemStack);

            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + INGOT);
            OreDictionary.registerOre("ingot" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + DUST);
            OreDictionary.registerOre("dust" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + GEAR);
            OreDictionary.registerOre("gear" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + NUGGET);
            OreDictionary.registerOre("nugget" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + PLATE);
            OreDictionary.registerOre("plate" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + ROD);
            OreDictionary.registerOre("rod" + metal.getName(), subItemStack);
            OreDictionary.registerOre("stick" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + CLUMP);
            OreDictionary.registerOre("clump" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + CRYSTAL);
            OreDictionary.registerOre("crystal" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + SHARD);
            OreDictionary.registerOre("shard" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + WIRE);
            OreDictionary.registerOre("wire" + metal.getName(), subItemStack);
            OreDictionary.registerOre("cable" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + DIRTY);
            OreDictionary.registerOre("dustDirty" + metal.getName(), subItemStack);
            subItemStack = new ItemStack(itemMaterialObject, 1, metal.ordinal() + FOIL);
            OreDictionary.registerOre("foil" + metal.getName(), subItemStack);
        });

    }

    public void init(FMLInitializationEvent e) {

        MinecraftForge.EVENT_BUS.register(new Events());
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
        Recipies.addRecipies();
    }

    public void registerItemRenderer(Item item, int meta, String id) {

    }

    private void initMaterials() {
        new Material("none", "0x000000", -2);
        new Material("Antimony", "0xFADA5E", 0);
        new Material("Bismuth", "0xed7d92", 0);
        new Material("Cadmium", "0xed872d", 0);
        new Material("Mercury", "0x751f27", 0);
        new Material("Copper", "0xb4713d", 1, _2_copper);
        new Material("Zinc", "0xbac4c8", 1);
        new Material("Coal", "0x060607", 1) {
            @Override
            public ItemStack getDrops(byte base) {
                return new ItemStack(Items.COAL, 1, 0);
            }
        };
        new Material("Monazit", "0x82c59c", 1);
        new Material("Iron", "0xd3ad90", 2, _3_iron);
        new Material("Apatite", "0xc3b89c", 2) {
            @Override
            public ItemStack getDrops(byte base) {
                if (OreDictionary.doesOreNameExist("gemApatite")) {
                    return OreDictionary.getOres("gemApatite").get(0);
                } else {
                    return super.getDrops(base);
                }
            }
        };
        new Material("Chromium", "0x18391e", 2);
        new Material("Aluminum", "0xe0d9cd", 2);
        new Material("Silver", "0xb5b5bd", 2, _1_flint);
        new Material("Tellurium", "0xb5b5bd", 2);
        new Material("Lapis", "0x000094", 2) {
            @Override
            public ItemStack getDrops(byte base) {
                return new ItemStack(Items.DYE, 5, 4);
            }
        };
        new Material("Tin", "0x726a78", 3);
        new Material("Gold", "0xcccc33", 3);
        new Material("Lead", "0x474c4d", 3, _1_flint);
        new Material("Redstone", "0xd43c2c", 3) {
            @Override
            public ItemStack getDrops(byte base) {
                return new ItemStack(Items.REDSTONE, 4, 0);
            }
        };
        new Material("Sulfur", "0xedff21", 3) {
            @Override
            public ItemStack getDrops(byte base) {
                return new ItemStack(ModItems.itemMaterialObject, 1, Material.getMaterial("Sulfur").ordinal() + DUST);
            }
        };
        new Material("Nickel", "0xccd3d8", 3);
        new Material("Osmium", "0x9090a3", 3);
        new Material("ColdIron", "0x5f6c81", 3);
        new Material("Diamond", "0xb9f2ff", 4) {
            @Override
            public ItemStack getDrops(byte base) {
                return new ItemStack(Items.DIAMOND, 1, 0);
            }
        };
        new Material("Emerald", "0x318957", 4) {
            @Override
            public ItemStack getDrops(byte base) {
                return new ItemStack(Items.EMERALD, 1, 0);
            }
        };
        new Material("Ruby", "0x9b111e", 4) {
            @Override
            public ItemStack getDrops(byte base) {
                if (OreDictionary.doesOreNameExist("gemRuby")) {
                    return OreDictionary.getOres("gemRuby").get(0);
                } else {
                    return super.getDrops(base);
                }
            }
        };
        new Material("Sapphire", "0x297bc1", 4) {
            @Override
            public ItemStack getDrops(byte base) {
                if (OreDictionary.doesOreNameExist("gemSapphire")) {
                    return OreDictionary.getOres("gemSapphire").get(0);
                } else {
                    return super.getDrops(base);
                }
            }
        };
        new Material("Amethyst", "0x642552", 4) {
            @Override
            public ItemStack getDrops(byte base) {
                if (OreDictionary.doesOreNameExist("gemAmethyst")) {
                    return OreDictionary.getOres("gemAmethyst").get(0);
                } else {
                    return super.getDrops(base);
                }
            }
        };
        new Material("Manganese", "0x242d36", 4);
        new Material("Certus", "0xe6d7df", 5) {
            @Override
            public ItemStack getDrops(byte base) {
                if (OreDictionary.doesOreNameExist("crystalCertusQuartz")) {
                    return OreDictionary.getOres("crystalCertusQuartz").get(0);
                } else {
                    return super.getDrops(base);
                }
            }
        };
        new Material("CertusCharged", "0xeadadf", 5);
        new Material("Ardite", "0xd94925", 6, _7_ardite);
        new Material("Uranium", "0x329832", 6);
        new Material("Platinum", "0xb8b7b2", 6);
        new Material("Yellorium", "0xffce00", 6);
        new Material("Draconium", "0xd2a8d4", 6);
        new Material("Cobalt", "0x0071b6", 7, _8_cobalt);
        new Material("Iridium", "0xe0e2dd", 7);
        new Material("Titanium", "0x323230", 7);
        new Material("Stone", "0x8B8D7A", -1, _0_stone);
        new Material("Basalt", "0x202A29", 2, _0_stone);
        new Material("Paper", "0xF0EEE1", -1, _0_stone);
        new Material("Sponge", "0xD8C060", -1, _0_stone);
        new Material("Firewood", "0x000000", -1, _0_stone);
        new Material("Slime", "0x000000", -1, _0_stone);
        new Material("BlueSlime", "0x000000", -1, _0_stone);
        new Material("MagmaSlime", "0x000000", -1, _0_stone);
        new Material("TreatedWood", "0x000000", -1, _0_stone);
        new Material("Cactus", "0x64F566", -1, _0_stone);
        new Material("Netherrack", "0x800000", -1, _0_stone);
        new Material("Wood", "0x000000", -1, _0_stone);
        new Material("Bone", "0xFFF9ED", -1, _1_flint);
        new Material("Flint", "0x6E6460", 0, _1_flint) {
            @Override
            public ItemStack getDrops(byte base) {
                return new ItemStack(Items.FLINT, 1, 0);
            }
        };
        new Material("Prismarine", "0x000000", -1, _1_flint);
        new Material("Electrum", "0x928729", 2, _1_flint);
        new Material("Xu_demonic_metal", "0x000000", -1, _1_flint);
        new Material("Brass", "0xE4AD5B", 2, _2_copper);
        new Material("Constantan", "0xE0A050", 3, _3_iron);
        new Material("Endstone", "0xDCDEA4", 1, _3_iron);
        new Material("Bronze", "0xE69E2F", 4, _4_bronze);
        new Material("Steel", "0xdfdfdf", 5, _5_diamond);
        new Material("Pigiron", "0xff9999", 5, _5_diamond);
        new Material("Obsidian", "0x0c0f04", 3, _3_iron) {
            @Override
            public ItemStack getDrops(byte base) {
                return new ItemStack(Blocks.OBSIDIAN, 1, 0);
            }
        };
        new Material("Manyullyn", "0xBA55D3", 9, _9_manyullym);
        new Material("Mithril", "0xAEBBDB", 5, _6_obsidian);
        new Material("NetherQuartz", "0xdddddd", 1) {
            @Override
            public ItemStack getDrops(byte base) {
                return new ItemStack(Items.QUARTZ, 1, 0);
            }
        };
        new Material("Adamantine", "0xb30000", 8, 9);
        new Material("Terrax", "0x000000", 4, 5);
        new Material("Karmesine", "0x000000", 4);
        new Material("Ovium", "0x000000", 4);
        new Material("Jauxum", "0x000000", 4);
        new Material("Aurorium", "0x000000", 4, 5);
        new Material("Tiberium", "0xD4FF00", 2, 3);
        new Material("Dilithium", "0x000000", 2);
        new Material("Fractum", "0x000000", 3, 4);
        new Material("Triberium", "0x000000", 3, 3);
        new Material("Abyssum", "0x000000", 4);
        new Material("Tritonite", "0x000000", 8, 8);
        new Material("Violium", "0x000000", 7, 8);
        new Material("Astrium", "0x000000", 5, 6);
        new Material("Nihilite", "0x000000", 7, 7);
        new Material("Vibranium", "0x000000", 7, 8);
        new Material("Solarium", "0x000000", 7, 7);
        new Material("Valyrium", "0x000000", 6, 7);
        new Material("Uru", "0x000000", 6, 7);
        new Material("Adamant", "0x000000", 7, 7);
        new Material("Nucleum", "0x000000", 7, 7);
        new Material("Iox", "0x000000", 10);
        new Material("Seismum", "0x000000", 5, 6);
        new Material("Imperomite", "0x000000", 6, 6);
        new Material("Eezo", "0x38596A", 4, 5);
        new Material("Duranite", "0x000000", 5, 6);
        new Material("Promethium", "0x000000", 5, 6);
        new Material("Ignitz", "0x000000", 7, 7);
        new Material("Proxii", "0x000000", 6, 6);
        new Material("Osram", "0x000000", 4);
        new Material("Palladium", "0x000000", 5, 9);
        new Material("Yrdeen", "0x000000", 7, 7);
        new Material("Niob", "0x000000", 6, 6);
        new Material("Obsidiorite", "0x000000", 4, 4);
        new Material("Lumix", "0x000000", 4, 4);
        new Material("Dyonite", "0x000000", 6, 6);
        new Material("AluBrass", "0xCAA585", 1);
        new Material("Nitronite", "0x00000", 4);
        new Material("Magma", "0x000000", -1);
        new Material("Resonating", "0xFF4500", 2);
        new Material("BlackQuartz", "0x1A1A1A", 2) {
            @Override
            public ItemStack getDrops(byte base) {
                if (OreDictionary.doesOreNameExist("gemQuartzBlack")) {
                    return OreDictionary.getOres("gemQuartzBlack").get(0);
                } else {
                    return super.getDrops(base);
                }
            }
        };
        new Material("Invar", "0xD0C0B3", 3);
        new Material("Nichrome", "0x858F80", 3);
    }
}
