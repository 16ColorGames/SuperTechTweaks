package com.sixteencolorgames.supertechtweaks.proxy;

import com.sixteencolorgames.supertechtweaks.ModBlocks;
import com.sixteencolorgames.supertechtweaks.ModItems;
import static com.sixteencolorgames.supertechtweaks.ModItems.itemMaterialObject;
import static com.sixteencolorgames.supertechtweaks.ModItems.itemOreChunk;
import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.*;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.*;
import com.sixteencolorgames.supertechtweaks.render.BakedModelLoader;
import com.sixteencolorgames.supertechtweaks.render.MetalColor;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.state.IBlockState;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Proxy for clients only.
 *
 * @author oa10712
 *
 */
public class ClientProxy extends CommonProxy {

    private static final Minecraft minecraft = Minecraft.getMinecraft();
    public static MetalColor color = new MetalColor();

    static ModelResourceLocation chunkLocation = new ModelResourceLocation("supertechtweaks:itemOreChunk",
            "inventory");
    static ModelResourceLocation ingotLocation = new ModelResourceLocation("supertechtweaks:itemIngot",
            "inventory");
    static ModelResourceLocation dustLocation = new ModelResourceLocation("supertechtweaks:itemDust",
            "inventory");
    static ModelResourceLocation foilLocation = new ModelResourceLocation("supertechtweaks:itemFoil",
            "inventory");
    static ModelResourceLocation gearLocation = new ModelResourceLocation("supertechtweaks:itemGear",
            "inventory");
    static ModelResourceLocation nuggetLocation = new ModelResourceLocation("supertechtweaks:itemNugget",
            "inventory");
    static ModelResourceLocation plateLocation = new ModelResourceLocation("supertechtweaks:itemPlate",
            "inventory");
    static ModelResourceLocation rodLocation = new ModelResourceLocation("supertechtweaks:itemRod",
            "inventory");
    static ModelResourceLocation clumpLocation = new ModelResourceLocation("supertechtweaks:itemClump",
            "inventory");
    static ModelResourceLocation shardLocation = new ModelResourceLocation("supertechtweaks:itemShard",
            "inventory");
    static ModelResourceLocation crystalLocation = new ModelResourceLocation("supertechtweaks:itemCrystal",
            "inventory");
    static ModelResourceLocation wireLocation = new ModelResourceLocation("supertechtweaks:itemWire",
            "inventory");
    static ModelResourceLocation tinyLocation = new ModelResourceLocation("supertechtweaks:itemTinyDust",
            "inventory");
    static ModelResourceLocation blockLocation = new ModelResourceLocation("supertechtweaks:itemBlock",
            "inventory");

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        for (Material metal : Material.materials) {
            registerModels(metal);
        }
        //((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new ModelCache());
        ModelLoaderRegistry.registerLoader(new BakedModelLoader());
    }

    public static void registerModels(Material metal) {
        ModelLoader.setCustomModelResourceLocation(itemOreChunk, metal.ordinal(), chunkLocation);
        ModelLoader.setCustomModelResourceLocation(itemOreChunk, metal.ordinal() + NETHER, chunkLocation);
        ModelLoader.setCustomModelResourceLocation(itemOreChunk, metal.ordinal() + END, chunkLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + INGOT, ingotLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + DUST, dustLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + GEAR, gearLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + NUGGET, nuggetLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + PLATE, plateLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + ROD, rodLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + CLUMP, clumpLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + CRYSTAL, crystalLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + SHARD, shardLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + WIRE, wireLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + DIRTY, dustLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + FOIL, foilLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, metal.ordinal() + TINY, tinyLocation);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        BlockColors colors = minecraft.getBlockColors();

        // ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOre.class, new TESRBlockOre());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(color, ModItems.itemOreChunk);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(color, ModItems.itemMaterialObject);
        //Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(color, ModBlocks.blockOre);
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        List<ItemStack> subItems = new ArrayList();
        item.getSubItems(item, CreativeTabs.MISC, subItems);
        subItems.forEach((item2) -> {
            ModelLoader.setCustomModelResourceLocation(item, item2.getMetadata(),
                    new ModelResourceLocation(SuperTechTweaksMod.MODID + ":" + id, "inventory"));
        });
    }
}
