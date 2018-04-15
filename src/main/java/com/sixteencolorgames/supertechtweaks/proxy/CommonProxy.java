package com.sixteencolorgames.supertechtweaks.proxy;

import org.apache.http.config.RegistryBuilder;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.network.ReceiveResearchUpdate;
import com.sixteencolorgames.supertechtweaks.network.ResearchUpdatePacket;

import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Proxy functions common to both the client and server side
 *
 * @author oa10712
 *
 */
public abstract class CommonProxy {

	public static SimpleNetworkWrapper simpleNetworkWrapper;
	public static final byte RESEARCH_MESSAGE_ID = 35;

	public World getWorld() {
		return getWorld(null);
	}

	public abstract World getWorld(IBlockAccess world);

	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(SuperTechTweaksMod.instance, new GuiProxy());
		FMLInterModComms.sendMessage("chiselsandbits", "ignoreblocklogic", "supertechtweaks:superore");
	}

	public void postInit(FMLPostInitializationEvent e) {

	}

	public void preInit(FMLPreInitializationEvent e) {

		simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("MBEchannel");
		simpleNetworkWrapper.registerMessage(ReceiveResearchUpdate.class, ResearchUpdatePacket.class,
				RESEARCH_MESSAGE_ID, Side.SERVER);
	}

	public void registerItemRenderer(Item item, int meta, String id) {

	}

	public void registerModels(Material mat) {
		// TODO Auto-generated method stub

	}

	public RegistryBuilder registryInit(RegistryEvent.NewRegistry e) {
		RegistryBuilder<Material> created = RegistryBuilder.create();
		return created;
	}

	public abstract Side getSide();

}
