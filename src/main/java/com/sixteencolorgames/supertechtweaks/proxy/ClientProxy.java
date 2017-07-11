package com.sixteencolorgames.supertechtweaks.proxy;

import static com.sixteencolorgames.supertechtweaks.ModRegistry.itemMaterialObject;
import static com.sixteencolorgames.supertechtweaks.ModRegistry.itemOreChunk;
import com.sixteencolorgames.supertechtweaks.ModRegistry;
import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import static com.sixteencolorgames.supertechtweaks.items.ItemMaterialObject.*;
import static com.sixteencolorgames.supertechtweaks.items.ItemOreChunk.*;
import com.sixteencolorgames.supertechtweaks.items.ItemTechComponent;
import com.sixteencolorgames.supertechtweaks.render.BakedModelLoader;
import com.sixteencolorgames.supertechtweaks.render.MetalColor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
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
    public static ModelResourceLocation blockLocation = new ModelResourceLocation("supertechtweaks:blockMaterial", "normal");
    public static ModelResourceLocation itemLocation = new ModelResourceLocation("supertechtweaks:itemBlockMaterial", "inventory");

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        for (int i = 0; i < 256; i++) {//setup models for 200 metal types, we only use 109, but the rest are for IMC and CT usage
            registerModels(i);
        }
        Material.materials.forEach((mat) -> {
            mat.clientPrep();
        });
        ((ItemTechComponent) ModRegistry.itemTechComponent).registerModels();
        //((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new ModelCache());
        ModelLoaderRegistry.registerLoader(new BakedModelLoader());
    }

    public static void registerModels(int ordinal) {
        ModelLoader.setCustomModelResourceLocation(itemOreChunk, ordinal, chunkLocation);
        ModelLoader.setCustomModelResourceLocation(itemOreChunk, ordinal + NETHER, chunkLocation);
        ModelLoader.setCustomModelResourceLocation(itemOreChunk, ordinal + END, chunkLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, ordinal + INGOT, ingotLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, ordinal + DUST, dustLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, ordinal + GEAR, gearLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, ordinal + NUGGET, nuggetLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, ordinal + PLATE, plateLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, ordinal + ROD, rodLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, ordinal + CLUMP, clumpLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, ordinal + CRYSTAL, crystalLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, ordinal + SHARD, shardLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, ordinal + WIRE, wireLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, ordinal + DIRTY, dustLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, ordinal + FOIL, foilLocation);
        ModelLoader.setCustomModelResourceLocation(itemMaterialObject, ordinal + TINY, tinyLocation);
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
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(MetalColor.INSTANCE, ModRegistry.itemOreChunk);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(MetalColor.INSTANCE, ModRegistry.itemMaterialObject);
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

    @Override
    public World getWorld(IBlockAccess world) {
        if (world != null && world instanceof World) {
            return (World) world;
        }
        return Minecraft.getMinecraft().theWorld;
    }
}
