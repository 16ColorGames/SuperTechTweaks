package com.sixteencolorgames.supertechtweaks.proxy;

import java.io.File;
import java.util.ArrayList;

import org.apache.http.config.RegistryBuilder;

import com.sixteencolorgames.supertechtweaks.Config;
import com.sixteencolorgames.supertechtweaks.ServerEvents;
import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.enums.Ore;
import com.sixteencolorgames.supertechtweaks.enums.Research;
import com.sixteencolorgames.supertechtweaks.network.PacketHandler;
import com.sixteencolorgames.supertechtweaks.network.ReceiveResearchUpdate;
import com.sixteencolorgames.supertechtweaks.network.ResearchUpdatePacket;
import com.sixteencolorgames.supertechtweaks.world.GenerationParser;
import com.sixteencolorgames.supertechtweaks.world.WorldGeneratorBase;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

/**
 * Proxy functions common to both the client and server side
 *
 * @author oa10712
 *
 */
public abstract class CommonProxy {

	public static Configuration config;
	public static SimpleNetworkWrapper simpleNetworkWrapper;
	public static final byte RESEARCH_MESSAGE_ID = 35;
	public static ArrayList<WorldGeneratorBase> parsed;
	private File configFolder;
	private ServerEvents serverEvents;

	public abstract Side getSide();

	public World getWorld() {
		return getWorld(null);
	}

	public abstract World getWorld(IBlockAccess world);

	public void init(FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(SuperTechTweaksMod.instance, new GuiProxy());
		FMLInterModComms.sendMessage("chiselsandbits", "ignoreblocklogic", "supertechtweaks:superore");
	}

	public void postInit(FMLPostInitializationEvent e) {
		IForgeRegistryModifiable recipies = (IForgeRegistryModifiable) ForgeRegistries.RECIPES;
		recipies.remove(new ResourceLocation("minecraft:diamond_pickaxe"));
		recipies.remove(new ResourceLocation("minecraft:iron_pickaxe"));
		recipies.remove(new ResourceLocation("minecraft:stone_pickaxe"));
		recipies.remove(new ResourceLocation("minecraft:wooden_pickaxe"));
		recipies.remove(new ResourceLocation("minecraft:golden_pickaxe"));
		for (File gen : configFolder.listFiles()) {
			if (gen.getName().contains(".json")) {
				try {
					parsed = GenerationParser.parseScripts(gen);
					parsed.forEach((WorldGeneratorBase base) -> {
						GameRegistry.registerWorldGenerator(base, 3 + base.params.hashCode() + base.hashCode());
					});
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		System.out.println("Generators Loaded");
		IForgeRegistry<Research> research = GameRegistry.findRegistry(Research.class);
		research.forEach((res) -> {
			res.getRequirements().forEach((req) -> {
				Research req2 = research.getValue(req);
				if (req2 != null) {
					req2.addDependent(res);
				} else {
					System.out.println("Missing research registration: " + req);
				}
			});
		});
	}

	public void preInit(FMLPreInitializationEvent e) {

		PacketHandler.registerMessages(SuperTechTweaksMod.MODID + "Chan");
		simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("MBEchannel");
		simpleNetworkWrapper.registerMessage(ReceiveResearchUpdate.class, ResearchUpdatePacket.class,
				RESEARCH_MESSAGE_ID, Side.SERVER);

		configFolder = new File(e.getModConfigurationDirectory().toString() + "/supertechtweaks/");
		config = new Configuration(new File(configFolder.getPath(), "config.cfg"));
		Config.readConfig(configFolder);
		serverEvents = new ServerEvents();
		MinecraftForge.EVENT_BUS.register(serverEvents);
		MinecraftForge.ORE_GEN_BUS.register(serverEvents);
	}

	public void registerItemRenderer(Item item, int meta, String id) {
	}

	public void registerModels(Material mat) {
	}

	public void registerModels(Ore ore) {
	}

	public RegistryBuilder registryInit(RegistryEvent.NewRegistry e) {
		RegistryBuilder<Material> created = RegistryBuilder.create();
		return created;
	}

}
