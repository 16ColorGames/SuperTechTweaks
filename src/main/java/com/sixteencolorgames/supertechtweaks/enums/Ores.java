package com.sixteencolorgames.supertechtweaks.enums;

import com.sixteencolorgames.supertechtweaks.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.oredict.OreDictionary;

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
    COPPER("Copper", "0xb4713d", 1),
    ZINC("Zinc", "0xbac4c8", 1),
    COAL("Coal", "0x060607", 1) {
        @Override
        public ItemStack getDrops() {
            return new ItemStack(Items.COAL, 1, 0);
        }
    },
    MONAZIT("Monazit", "0x82c59c", 1),
    IRON("Iron", "0xd3ad90", 2),
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
    SILVER("Silver", "0xb5b5bd", 2),
    TELLURIUM("Tellurium", "0xb5b5bd", 2),
    LAPIS("Lapis", "0x000094", 2) {
        @Override
        public ItemStack getDrops() {
            return new ItemStack(Items.DYE, 5, 4);
        }
    },
    TIN("Tin", "0x726a78", 3),
    GOLD("Gold", "0xcccc33", 3),
    LEAD("Lead", "0x474c4d", 3),
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
    MANGANESE("Manganese", "0x242d36", 4), CERTUS("Certus", "0xe6d7df", 5) {
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
    ARDITE("Ardite", "0xff7b00", 6),
    URANIUM("Uranium", "0x329832", 6),
    PLATINUM("Platinum", "0xb8b7b2", 6),
    YELLORIUM("Yellorium", "0xffce00", 6),
    DRACONIUM("Draconium", "0xd2a8d4", 6),
    COBALT("Cobalt", "0x0071b6", 7),
    IRIDIUM("Iridium", "0xe0e2dd", 7),
    TITANIUM("Titanium", "0x323230", 7);

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

    Ores(String name, String color, int harvest) {
        this.name = name;
        this.color = color;
        this.harvest = harvest;
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

    public ItemStack getDrops() {
        return new ItemStack(ModItems.itemOreChunk, 1, this.ordinal());
    }
}
