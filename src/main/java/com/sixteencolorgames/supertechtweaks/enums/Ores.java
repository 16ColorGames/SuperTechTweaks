package com.sixteencolorgames.supertechtweaks.enums;

import com.sixteencolorgames.supertechtweaks.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.oredict.OreDictionary;
import static com.sixteencolorgames.supertechtweaks.enums.HarvestLevels.*;
import net.minecraft.init.Blocks;

/**
 * Enumeration of ore types used in this mod.
 *
 * @author oa10712
 *
 */
public enum Ores implements IStringSerializable {

    NONE("none", "0x000000", 0),
    ANTIMONY("Antimony", "0xFADA5E", 0),
    BISMUTH("Bismuth", "0xed7d92", 0),
    CADMIUM("Cadmium", "0xed872d", 0),
    MERCURY("Mercury", "0x751f27", 0),
    COPPER("Copper", "0xb4713d", 1, _2_copper),
    ZINC("Zinc", "0xbac4c8", 1),
    COAL("Coal", "0x060607", 1) {
        @Override
        public ItemStack getDrops() {
            return new ItemStack(Items.COAL, 1, 0);
        }
    },
    MONAZIT("Monazit", "0x82c59c", 1),
    IRON("Iron", "0xd3ad90", 2, _3_iron),
    APATITE("Apatite", "0xc3b89c", 2) {
        @Override
        public ItemStack getDrops() {
            if (OreDictionary.doesOreNameExist("gemApatite")) {
                return OreDictionary.getOres("gemApatite").get(0);
            } else {
                return super.getDrops();
            }
        }
    },
    CHROMIUM("Chromium", "0x18391e", 2),
    ALUMINUM("Aluminum", "0xe0d9cd", 2),
    SILVER("Silver", "0xb5b5bd", 2, _1_flint),
    TELLURIUM("Tellurium", "0xb5b5bd", 2),
    LAPIS("Lapis", "0x000094", 2) {
        @Override
        public ItemStack getDrops() {
            return new ItemStack(Items.DYE, 5, 4);
        }
    },
    TIN("Tin", "0x726a78", 3),
    GOLD("Gold", "0xcccc33", 3),
    LEAD("Lead", "0x474c4d", 3, _1_flint),
    REDSTONE("Redstone", "0xd43c2c", 3) {
        @Override
        public ItemStack getDrops() {
            return new ItemStack(Items.REDSTONE, 4, 0);
        }
    },
    SULFUR("Sulfur", "0xedff21", 3) {
        @Override
        public ItemStack getDrops() {
            if (OreDictionary.doesOreNameExist("dustSulfur")) {
                return OreDictionary.getOres("dustSulfur").get(0);
            } else {
                return super.getDrops();
            }
        }
    },
    NICKEL("Nickel", "0xccd3d8", 3),
    OSMIUM("Osmium", "0x9090a3", 3),
    COLD_IRON("Cold Iron", "0x5f6c81", 3),
    DIAMOND("Diamond", "0xb9f2ff", 4) {
        @Override
        public ItemStack getDrops() {
            return new ItemStack(Items.DIAMOND, 1, 0);
        }
    },
    EMERALD("Emerald", "0x318957", 4) {
        @Override
        public ItemStack getDrops() {
            return new ItemStack(Items.EMERALD, 1, 0);
        }
    },
    RUBY("Ruby", "0x9b111e", 4) {
        @Override
        public ItemStack getDrops() {
            if (OreDictionary.doesOreNameExist("gemRuby")) {
                return OreDictionary.getOres("gemRuby").get(0);
            } else {
                return super.getDrops();
            }
        }
    },
    SAPPHIRE("Sapphire", "0x297bc1", 4) {
        @Override
        public ItemStack getDrops() {
            if (OreDictionary.doesOreNameExist("gemSapphire")) {
                return OreDictionary.getOres("gemSapphire").get(0);
            } else {
                return super.getDrops();
            }
        }
    },
    AMETHYST("Amethyst", "0x642552", 4) {
        @Override
        public ItemStack getDrops() {
            if (OreDictionary.doesOreNameExist("gemAmethyst")) {
                return OreDictionary.getOres("gemAmethyst").get(0);
            } else {
                return super.getDrops();
            }
        }
    },
    MANGANESE("Manganese", "0x242d36", 4),
    CERTUS("Certus", "0xe6d7df", 5) {
        @Override
        public ItemStack getDrops() {
            if (OreDictionary.doesOreNameExist("crystalCertusQuartz")) {
                return OreDictionary.getOres("crystalCertusQuartz").get(0);
            } else {
                return super.getDrops();
            }
        }
    },
    CHARGED_CERTUS("Certus_Charged", "0xeadadf", 5),
    ARDITE("Ardite", "0xd94925", 6, _7_ardite),
    URANIUM("Uranium", "0x329832", 6),
    PLATINUM("Platinum", "0xb8b7b2", 6),
    YELLORIUM("Yellorium", "0xffce00", 6),
    DRACONIUM("Draconium", "0xd2a8d4", 6),
    COBALT("Cobalt", "0x0071b6", 7, _8_cobalt),
    IRIDIUM("Iridium", "0xe0e2dd", 7),
    TITANIUM("Titanium", "0x323230", 7),
    STONE("Stone", "0x8B8D7A", -1, _0_stone),
    BASALT("Basalt", "0x202A29", 2, _0_stone),
    PAPER("Paper", "0xF0EEE1", -1, _0_stone),
    SPONGE("Sponge", "0xD8C060", -1, _0_stone),
    FIREWOOD("Firewood", "0x000000", -1, _0_stone),
    SLIME("Slime", "0x000000", -1, _0_stone),
    BLUESLIME("BlueSlime", "0x000000", -1, _0_stone),
    MAGMASLIME("MagmaSlime", "0x000000", -1, _0_stone),
    TREATEDWOOD("TreatedWood", "0x000000", -1, _0_stone),
    CACTUS("Cactus", "0x64F566", -1, _0_stone),
    NETHERRACK("Netherrack", "0x800000", -1, _0_stone),
    WOOD("Wood", "0x000000", -1, _0_stone),
    BONE("Bone", "0xFFF9ED", -1, _1_flint),
    FLINT("Flint", "0x6E6460", 0, _1_flint) {
        @Override
        public ItemStack getDrops() {
            return new ItemStack(Items.FLINT, 1, 0);
        }
    },
    PRISMARINE("Prismarine", "0x000000", -1, _1_flint),
    ELECTRUM("Electrum", "0x928729", 2, _1_flint),
    XU_DEMONIC_METAL("Xu_demonic_metal", "0x000000", -1, _1_flint),
    BRASS("Brass", "0xE4AD5B", 2, _2_copper),
    CONSTANTAN("Constantan", "0xE0A050", 3, _3_iron),
    ENDSTONE("Endstone", "0xDCDEA4", 1, _3_iron),
    BRONZE("Bronze", "0xE69E2F", 4, _4_bronze),
    STEEL("Steel", "0xdfdfdf", 5, _5_diamond),
    PIGIRON("Pigiron", "0xff9999", 5, _5_diamond),
    OBSIDIAN("Obsidian", "0x0c0f04", 3, _3_iron) {
        @Override
        public ItemStack getDrops() {
            return new ItemStack(Blocks.OBSIDIAN, 1, 0);
        }
    },
    MANYULLYN("Manyullyn", "0xBA55D3", 9, _9_manyullym),
    MITHRIL("Mithril", "0xAEBBDB", 5, _6_obsidian),
    NETHER_QUARTZ("NetherQuartz", "0xdddddd", 1) {
        @Override
        public ItemStack getDrops() {
            return new ItemStack(Items.QUARTZ, 1, 0);
        }
    },
    ADAMANTINE("Adamantine", "0xb30000", 8, 9),
    TERRAX("Terrax", "0x000000", 3, 4),
    KARMESINE("Karmesine", "0x000000", 4),
    OVIUM("Ovium", "0x000000", 4),
    JAUXUM("Jauxum", "0x000000", 4),
    AURORIUM("Aurorium", "0x000000", 4, 4),
    TIBERIUM("Tiberium", "0x000000", 2, 3),
    DILITHIUM("Dilithium", "0x000000", 2),
    FRACTUM("Fractum", "0x000000", 2, 2),
    TRIBERIUM("Triberium", "0x000000", 3, 3),
    ABYSSUM("Abyssum", "0x000000", 4),
    TRITONITE("Tritonite", "0x000000", 4, 4),
    VIOLIUM("Violium", "0x000000", 4, 4),
    ASTRIUM("Astrium", "0x000000", 4, 4),
    NIHILITE("Nihilite", "0x000000", 6, 6),
    VIBRANIUM("Vibranium", "0x000000", 7, 7),
    SOLARIUM("SOLARIUM", "0x000000", 7, 7),
    VALYRIUM("Valyrium", "0x000000", 6, 6),
    URU("Uru", "0x000000", 6, 6),
    ADAMANT("Adamant", "0x000000", 7, 7),
    NUCLEUM("Nucleum", "0x000000", 6, 6),
    IOX("Iox", "0x000000", 10),
    SEISMUM("Seismum", "0x000000", 4, 4),
    IMPEROMITE("Imperomite", "0x000000", 5, 5),
    EEZO("Eezo", "0x000000", 4, 4),
    DURANITE("Duranite", "0x000000", 5, 5),
    PROMETHIUM("Promethium", "0x000000", 5, 5),
    IGNITZ("Ignitz", "0x000000", 4, 4),
    PROXII("Proxii", "0x000000", 5, 5),
    OSRAM("Osram", "0x000000", 4),
    PALLADIUM("Palladium", "0x000000", 5, 5),
    YRDEEN("Yrdeen", "0x000000", 4, 4),
    NIOB("Niob", "0x000000", 4, 4),
    OBSIDIORITE("Obsidiorite", "0x000000", 4, 4),
    LUMIX("Lumix", "0x000000", 4, 4),
    DYONITE("Dyonite", "0x000000", 5, 5),
    ALUMINUMBRASS("Alubrass", "0x000000", 1),
    NITRONITE("Nitronite", "0x00000", 4),
    MAGMA("Magma", "0x000000", -1);

    /**
     * The name of the metal
     */
    private final String name;
    /**
     * The RGB code for the color of this metal.
     */
    private String color;
    /**
     * The harvest level of this metal.
     */
    private int harvest;
    /**
     * Level that a tool made of this can mine
     */
    private int mine;
    private String liquid = null;

    /**
     *
     * @param name
     * @param color
     * @param harvest
     */
    Ores(String name, String color, int harvest) {
        this(name, color, harvest, -1);
    }

    Ores(String name, String color, int harvest, int mine) {
        this.name = name;
        this.color = color;
        this.harvest = harvest;
        this.mine = mine;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getHarvest() {
        return harvest;
    }

    public int getMine() {
        return mine;
    }

    public ItemStack getDrops() {
        return new ItemStack(ModItems.itemOreChunk, 1, this.ordinal());
    }

    public String getLiquid() {
        return liquid;
    }
}
