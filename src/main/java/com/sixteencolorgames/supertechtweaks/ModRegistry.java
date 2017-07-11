/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks;

import com.sixteencolorgames.supertechtweaks.blocks.BlockBase;
import com.sixteencolorgames.supertechtweaks.blocks.BlockOre;
import com.sixteencolorgames.supertechtweaks.blocks.BlockTileEntity;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels.*;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.items.ItemBase;
import com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.*;
import com.sixteencolorgames.supertechtweaks.items.ItemModelProvider;
import com.sixteencolorgames.supertechtweaks.items.ItemOreChunk;
import com.sixteencolorgames.supertechtweaks.items.ItemTechComponent;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.common.registry.RegistryBuilder;
import net.minecraftforge.oredict.OreDictionary;

/**
 * This class is where all registration will take place
 *
 * @author oa10712
 */
@ObjectHolder("supertechtweaks")
@Mod.EventBusSubscriber()
public class ModRegistry {

    static ModelResourceLocation fluidLocation = new ModelResourceLocation("supertechtweaks:blockFluid",
            "inventory");

    public static final Block superore = null;
    public static final Item itemTechComponent = null;

    public static final Item itemOreChunk = null;
    public static final Item itemMaterialObject = null;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        System.out.println("Attempting block registry");
        register(new BlockOre());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerItems(RegistryEvent.Register<Item> event) {
        System.out.println("Attempting item registry");
        ItemTechComponent tech = register(new ItemTechComponent());
        tech.setupDictionary();
        register(new ItemOreChunk());
        register(new ItemMaterialObject());
    }

    @SubscribeEvent
    public static void registerMaterials(RegistryEvent.Register<Material> event) {
        System.out.println("Attempting material registry");
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
                return new ItemStack(ModRegistry.itemMaterialObject, 1, Material.getMaterial("Sulfur").ordinal() + DUST);
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
        new Material("Firewood", "0x8B0000", -1, _0_stone);
        new Material("Slime", 0x32CD32, -1, _0_stone);
        new Material("BlueSlime", 0x20B2AA, -1, _0_stone);
        new Material("MagmaSlime", 0xFF4500, -1, _0_stone);
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
        new Material("Terrax", "0xA9A9A9", 4, 5);
        new Material("Karmesine", "0xC71585", 4);
        new Material("Ovium", "0x8A2BE2", 4);
        new Material("Jauxum", "0x7FFF00", 4);
        new Material("Aurorium", "0xFFC0CB", 4, 5);
        new Material("Tiberium", "0xD4FF00", 2, 3);
        new Material("Dilithium", "0xC0C0CC", 2);
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
        new Material("Obsidiorite", "0x4B0082", 4, 4);
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
        new Material("Osmiridium", "0x191970", 7);
        new Material("Signalum", "0x800000", 7);
        new Material("StainlessSteel", "0xE0DFDB", 2);
    }

    @SubscribeEvent
    public static void registerRegistry(RegistryEvent.NewRegistry event) {
        IForgeRegistry builder = new RegistryBuilder()
                .setType(Material.class)
                .setName(new ResourceLocation(SuperTechTweaksMod.MODID, "MaterialRegistry"))
                .setIDRange(0, 256)
                .create();
    }

    /**
     * Registers a block and associated requirements, such as model and tile
     * entity
     *
     * @param block The block to register
     * @param itemBlock the item block to register
     * @return the block registered
     */
    public static <T extends Block> T register(T block, ItemBlock itemBlock) {
        GameRegistry.register(block);
        if (itemBlock != null) {
            GameRegistry.register(itemBlock);

            if (block instanceof ItemModelProvider) {
                ((ItemModelProvider) block).registerItemModel(itemBlock);
            }
            if (block instanceof BlockBase) {
                ((BlockBase) block).setItemBlock(itemBlock);
            }
        }

        if (block instanceof BlockTileEntity) {
            GameRegistry.registerTileEntity(((BlockTileEntity<?>) block).getTileEntityClass(),
                    block.getRegistryName().toString());
        }

        return block;
    }

    /**
     * Registers a block and associated requirements
     *
     * @param block the block to register
     * @return the block registered
     */
    public static <T extends Block> T register(T block) {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return register(block, itemBlock);
    }

    /**
     * Registers an item and its model with forge
     *
     * @param item The Item to register
     * @return The Item registered
     */
    public static <T extends Item> T register(T item) {
        GameRegistry.register(item);

        if (item instanceof ItemBase) {
            ((ItemBase) item).registerItemModel();
        }

        return item;
    }

    public static <T extends Block & IFluidBlock> Fluid createFluid(String name, boolean hasFlowIcon, Consumer<Fluid> fluidPropertyApplier, Function<Fluid, T> blockFactory, Material ore) {
        final String texturePrefix = SuperTechTweaksMod.MODID + ":" + "blocks/fluid_";

        final ResourceLocation still = new ResourceLocation(texturePrefix + "still");
        final ResourceLocation flowing = hasFlowIcon ? new ResourceLocation(texturePrefix + "flow") : still;

        Fluid fluid = new Fluid(name, still, flowing) {
            @Override
            public int getColor() {
                return ore.getColor();
            }
        };
        final boolean useOwnFluid = FluidRegistry.registerFluid(fluid);

        if (useOwnFluid) {
            fluidPropertyApplier.accept(fluid);
            registerBucket(fluid);
            //registerFluidBlock(blockFactory.apply(fluid));
        } else {
            fluid = FluidRegistry.getFluid(name);
        }

        return fluid;
    }

    private static <T extends Block & IFluidBlock> T registerFluidBlock(T block) {
        block.setRegistryName("fluid" + block.getFluid().getName());
        block.setUnlocalizedName(SuperTechTweaksMod.MODID + ":" + block.getFluid().getUnlocalizedName());

//        ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
//            @Override
//            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
//                return new ModelResourceLocation("supertechtweaks:fluid", "fluid");
//            }
//        });
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        GameRegistry.register(block);
        GameRegistry.register(itemBlock);
        ModelLoader.setCustomModelResourceLocation(itemBlock, 0, fluidLocation);
        return block;
    }

    private static void registerBucket(Fluid fluid) {
        FluidRegistry.addBucketForFluid(fluid);
    }
}
