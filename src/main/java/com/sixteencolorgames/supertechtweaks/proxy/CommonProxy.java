package com.sixteencolorgames.supertechtweaks.proxy;

import java.io.File;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import java.util.ArrayList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.items.MaterialItem;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import org.apache.http.config.RegistryBuilder;

/**
 * Proxy functions common to both the client and server side
 *
 * @author oa10712
 *
 */
public abstract class CommonProxy {

	public RegistryBuilder registryInit(RegistryEvent.NewRegistry e) {
		RegistryBuilder<Material> created = RegistryBuilder.create();
		return created;
	}

	public void preInit(FMLPreInitializationEvent e) {

	}

	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(SuperTechTweaksMod.instance, new GuiProxy());
		FMLInterModComms.sendMessage("chiselsandbits", "ignoreblocklogic", "supertechtweaks:superore");
	}

	public void postInit(FMLPostInitializationEvent e) {

	}

	public void registerItemRenderer(Item item, int meta, String id) {

	}

	public abstract World getWorld(IBlockAccess world);

	public World getWorld() {
		return getWorld(null);
	}

	public void registerModels(Material mat) {
		// TODO Auto-generated method stub

	}

}
