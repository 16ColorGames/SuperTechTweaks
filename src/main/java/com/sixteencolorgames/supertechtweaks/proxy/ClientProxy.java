package com.sixteencolorgames.supertechtweaks.proxy;

import com.sixteencolorgames.supertechtweaks.ModRegistry;
import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.enums.Ore;
import com.sixteencolorgames.supertechtweaks.items.MaterialItem;
import com.sixteencolorgames.supertechtweaks.items.OreItem;
import com.sixteencolorgames.supertechtweaks.render.BakedModelLoader;
import com.sixteencolorgames.supertechtweaks.render.BlockColor;
import com.sixteencolorgames.supertechtweaks.render.MetalColor;
import com.sixteencolorgames.supertechtweaks.render.OreColor;
import com.sixteencolorgames.supertechtweaks.tileentities.cable.ModelLoaderCable;
import com.sixteencolorgames.supertechtweaks.tileentities.pipe.ModelLoaderPipe;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

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
	static ModelResourceLocation crushedLocation = new ModelResourceLocation("supertechtweaks:itemOreCrushed",
			"inventory");
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
	static ModelResourceLocation coinLocation = new ModelResourceLocation("supertechtweaks:itemCoin", "inventory");
	static ModelResourceLocation bladeLocation = new ModelResourceLocation("supertechtweaks:itemBlade", "inventory");
	static ModelResourceLocation hammerLocation = new ModelResourceLocation("supertechtweaks:itemHammer", "inventory");
	static ModelResourceLocation pickaxeLocation = new ModelResourceLocation("supertechtweaks:itemPickaxe",
			"inventory");
	static ModelResourceLocation pliersLocation = new ModelResourceLocation("supertechtweaks:itemPliers", "inventory");
	static ModelResourceLocation drawPlateLocation = new ModelResourceLocation("supertechtweaks:itemDrawPlate",
			"inventory");
	public static ModelResourceLocation blockLocation = new ModelResourceLocation("supertechtweaks:blockMaterial",
			"normal");
	public static ModelResourceLocation itemLocation = new ModelResourceLocation("supertechtweaks:itemBlockMaterial",
			"inventory");

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		ModRegistry.initModels();
	}

	@Override
	public Side getSide() {
		return Side.CLIENT;
	}

	@Override
	public World getWorld(IBlockAccess world) {
		if (world != null && world instanceof World) {
			return (World) world;
		}
		return Minecraft.getMinecraft().world;
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
		Material.REGISTRY.getValuesCollection().forEach((m) -> {
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(MetalColor.INSTANCE, m.getMaterialItem());
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(BlockColor.INSTANCE, m.getItemBlock());
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(BlockColor.INSTANCE, m.getBlock());
		});
		Ore.REGISTRY.getValuesCollection().forEach((o) -> {
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(OreColor.INSTANCE, o.getItemOre());
		});
		ModRegistry.initItemModels();
	}

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		// ((ItemTechComponent) ModRegistry.itemTechComponent).registerModels();
		ModelLoaderRegistry.registerLoader(new BakedModelLoader());
		ModelLoaderRegistry.registerLoader(new ModelLoaderCable());
		ModelLoaderRegistry.registerLoader(new ModelLoaderPipe());
		OBJLoader.INSTANCE.addDomain(SuperTechTweaksMod.MODID);
		// ModelLoaderRegistry.registerLoader(ModelLoaderRock.INSTANCE);
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
	public void registerModels(Material mat) {
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
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.COIN, coinLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.BLADE, bladeLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.HAMMER, hammerLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.PICKAXE, pickaxeLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.PLIERS, pliersLocation);
		ModelLoader.setCustomModelResourceLocation(mat.getMaterialItem(), MaterialItem.DRAW_PLATE, drawPlateLocation);
	}

	@Override
	public void registerModels(Ore ore) {
		ModelLoader.setCustomModelResourceLocation(ore.getItemOre(), OreItem.ORE, chunkLocation);
		ModelLoader.setCustomModelResourceLocation(ore.getItemOre(), OreItem.NETHER_ORE, chunkLocation);
		ModelLoader.setCustomModelResourceLocation(ore.getItemOre(), OreItem.END_ORE, chunkLocation);
		ModelLoader.setCustomModelResourceLocation(ore.getItemOre(), OreItem.CRUSHED, crushedLocation);

	}
}
