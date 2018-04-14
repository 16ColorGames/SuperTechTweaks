package com.sixteencolorgames.supertechtweaks.proxy;

import org.apache.http.config.RegistryBuilder;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.enums.Material;

import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

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
