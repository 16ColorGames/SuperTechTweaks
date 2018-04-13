package com.sixteencolorgames.supertechtweaks.proxy;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.render.BakedModelLoader;
import com.sixteencolorgames.supertechtweaks.render.BlockColor;
import com.sixteencolorgames.supertechtweaks.render.MetalColor;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;

import com.sixteencolorgames.supertechtweaks.items.MaterialItem;
import com.sixteencolorgames.supertechtweaks.items.MaterialItem.*;

/**
 * Proxy for clients only.
 *
 * @author oa10712
 *
 */
@Mod.EventBusSubscriber(value = Side.CLIENT)
public class ClientProxy extends CommonProxy {

	private static final Minecraft minecraft = Minecraft.getMinecraft();

	static ModelResourceLocation chunkLocation = new ModelResourceLocation("supertechtweaks:itemOreChunk", "inventory");
	static ModelResourceLocation ingotLocation = new ModelResourceLocation("supertechtweaks:itemIngot", "inventory");
	static ModelResourceLocation dustLocation = new ModelResourceLocation("supertechtweaks:itemDust", "inventory");
	static ModelResourceLocation foilLocation = new ModelResourceLocation("supertechtweaks:itemFoil", "inventory");
	static ModelResourceLocation gearLocation = new ModelResourceLocation("supertechtweaks:itemGear", "inventory");
	static ModelResourceLocation nuggetLocation = new ModelResourceLocation("supertechtweaks:itemNugget", "inventory");
	static ModelResourceLocation plateLocation = new ModelResourceLocation("supertechtweaks:itemPlate", "inventory");
	static ModelResourceLocation rodLocation = new ModelResourceLocation("supertechtweaks:itemRod", "inventory");
	static ModelResourceLocation clumpLocation = new ModelResourceLocation("supertechtweaks:itemClump", "inventory");
	static ModelResourceLocation shardLocation = new ModelResourceLocation("supertechtweaks:itemShard", "inventory");
	static ModelResourceLocation crystalLocation = new ModelResourceLocation("supertechtweaks:itemCrystal",
			"inventory");
	static ModelResourceLocation wireLocation = new ModelResourceLocation("supertechtweaks:itemWire", "inventory");
	static ModelResourceLocation tinyLocation = new ModelResourceLocation("supertechtweaks:itemTinyDust", "inventory");
	public static ModelResourceLocation blockLocation = new ModelResourceLocation("supertechtweaks:blockMaterial",
			"normal");
	public static ModelResourceLocation itemLocation = new ModelResourceLocation("supertechtweaks:itemBlockMaterial",
			"inventory");

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		// ((ItemTechComponent) ModRegistry.itemTechComponent).registerModels();
		ModelLoaderRegistry.registerLoader(new BakedModelLoader());
	}

	@Override
	public void registerModels(Material mat) {
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.ORE, chunkLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.NETHER_ORE, chunkLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.END_ORE, chunkLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.INGOT, ingotLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.DUST, dustLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.GEAR, gearLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.NUGGET, nuggetLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.PLATE, plateLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.ROD, rodLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.CLUMP, clumpLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.CRYSTAL, crystalLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.SHARD, shardLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.WIRE, wireLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.DIRTY, dustLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.FOIL, foilLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.TINY, tinyLocation);
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		BlockColors colors = minecraft.getBlockColors();

		// ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOre.class, new
		// TESRBlockOre());
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}

	/**
	 * Register the {@link IBlockColor} handlers
	 *
	 * @param event
	 *            The event
	 */
	@SubscribeEvent
	public static void registerBlockColourHandlers(final ColorHandlerEvent.Block event) {
		GameRegistry.findRegistry(Material.class).forEach((m) -> {
			event.getBlockColors().registerBlockColorHandler(BlockColor.INSTANCE, m.getBlock());
		});
	}

	/**
	 * Register the {@link IItemColor} handlers
	 *
	 * @param event
	 *            The event
	 */
	@SubscribeEvent
	public static void registerItemColourHandlers(final ColorHandlerEvent.Item event) {
		GameRegistry.findRegistry(Material.class).forEach((m) -> {
			event.getItemColors().registerItemColorHandler(MetalColor.INSTANCE, m.getMaterialItem());
			event.getItemColors().registerItemColorHandler(BlockColor.INSTANCE, m.getItemBlock());
		});
	}

	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		NonNullList<ItemStack> subItems = NonNullList.create();
		item.getSubItems(CreativeTabs.MISC, subItems);
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
		return Minecraft.getMinecraft().world;
	}
}
