package com.sixteencolorgames.supertechtweaks.proxy;

import com.sixteencolorgames.supertechtweaks.ModItems;
import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.enums.Ores;
import com.sixteencolorgames.supertechtweaks.render.MetalColor;
import com.sixteencolorgames.supertechtweaks.render.TESRBlockOre;
import com.sixteencolorgames.supertechtweaks.tileentities.TileEntityOre;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("supertechtweaks:itemOreChunk",
                "inventory");
        for (int i = 0; i < Ores.values().length; i++) {// set all of the chunks to use the same model; MetalColors handles the color differences
            ModelLoader.setCustomModelResourceLocation(ModItems.itemOreChunk, i, itemModelResourceLocation);
        }
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        BlockColors colors = minecraft.getBlockColors();

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOre.class, new TESRBlockOre());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        MetalColor color = new MetalColor();
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(color, ModItems.itemOreChunk);
        //Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(color, ModBlocks.blockOre);
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(SuperTechTweaksMod.MODID + ":" + id, "inventory"));
    }
}
