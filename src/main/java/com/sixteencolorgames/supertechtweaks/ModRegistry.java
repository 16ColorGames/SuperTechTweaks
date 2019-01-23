package com.sixteencolorgames.supertechtweaks;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.blocks.BlockMultiWall;
import com.sixteencolorgames.supertechtweaks.blocks.BlockOre;
import com.sixteencolorgames.supertechtweaks.blocks.BlockRock;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.enums.Material.MaterialBuilder;
import com.sixteencolorgames.supertechtweaks.enums.Ore;
import com.sixteencolorgames.supertechtweaks.enums.Research;
import com.sixteencolorgames.supertechtweaks.items.ItemConstructor;
import com.sixteencolorgames.supertechtweaks.items.ItemTechComponent;
import com.sixteencolorgames.supertechtweaks.items.MaterialItem;
import com.sixteencolorgames.supertechtweaks.proxy.CommonProxy;
import com.sixteencolorgames.supertechtweaks.render.StateMapperRock;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiWall;
import com.sixteencolorgames.supertechtweaks.tileentities.basicresearcher.BlockBasicResearcher;
import com.sixteencolorgames.supertechtweaks.tileentities.basicresearcher.TileBasicResearcher;
import com.sixteencolorgames.supertechtweaks.tileentities.boiler.BlockBoiler;
import com.sixteencolorgames.supertechtweaks.tileentities.boiler.TileBoiler;
import com.sixteencolorgames.supertechtweaks.tileentities.cable.BlockCable;
import com.sixteencolorgames.supertechtweaks.tileentities.cable.TileCable;
import com.sixteencolorgames.supertechtweaks.tileentities.conveyor.BlockConveyor;
import com.sixteencolorgames.supertechtweaks.tileentities.conveyor.TileEntityConveyor;
import com.sixteencolorgames.supertechtweaks.tileentities.crusher.BlockCrusher;
import com.sixteencolorgames.supertechtweaks.tileentities.crusher.TileCrusher;
import com.sixteencolorgames.supertechtweaks.tileentities.extractor.BlockExtractor;
import com.sixteencolorgames.supertechtweaks.tileentities.extractor.TileEntityExtractor;
import com.sixteencolorgames.supertechtweaks.tileentities.inserter.BlockInserter;
import com.sixteencolorgames.supertechtweaks.tileentities.inserter.TileEntityInserter;
import com.sixteencolorgames.supertechtweaks.tileentities.multifluidinput.BlockMultiFluidInput;
import com.sixteencolorgames.supertechtweaks.tileentities.multifluidinput.TileMultiFluidInput;
import com.sixteencolorgames.supertechtweaks.tileentities.multiiteminterface.BlockMultiItemInterface;
import com.sixteencolorgames.supertechtweaks.tileentities.multiiteminterface.TileMultiItemInterface;
import com.sixteencolorgames.supertechtweaks.tileentities.multipowerinput.BlockMultiPowerInput;
import com.sixteencolorgames.supertechtweaks.tileentities.multipowerinput.TileMultiPowerInput;
import com.sixteencolorgames.supertechtweaks.tileentities.multipoweroutput.BlockMultiPowerOutput;
import com.sixteencolorgames.supertechtweaks.tileentities.multipoweroutput.TileMultiPowerOutput;
import com.sixteencolorgames.supertechtweaks.tileentities.pipe.BlockPipe;
import com.sixteencolorgames.supertechtweaks.tileentities.pipe.TilePipe;
import com.sixteencolorgames.supertechtweaks.tileentities.pressuretank.BlockPressureTank;
import com.sixteencolorgames.supertechtweaks.tileentities.pressuretank.TilePressureTank;
import com.sixteencolorgames.supertechtweaks.tileentities.researchselector.BlockResearchSelector;
import com.sixteencolorgames.supertechtweaks.tileentities.researchselector.TileResearchSelector;
import com.sixteencolorgames.supertechtweaks.tileentities.solarpanel.BlockSolarPanel;
import com.sixteencolorgames.supertechtweaks.tileentities.solarpanel.TileSolarPanel;
import com.sixteencolorgames.supertechtweaks.tileentities.steamengine.BlockSteamEngine;
import com.sixteencolorgames.supertechtweaks.tileentities.steamengine.TileSteamEngine;
import com.sixteencolorgames.supertechtweaks.util.ItemHelper;
import com.sixteencolorgames.supertechtweaks.world.WorldGeneratorBase;
import com.sixteencolorgames.supertechtweaks.world.WorldGeneratorCluster;
import com.sixteencolorgames.supertechtweaks.world.WorldGeneratorPlate;
import com.sixteencolorgames.supertechtweaks.world.WorldGeneratorVein;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber()
public class ModRegistry {
	final static String fluidTexturePrefix = SuperTechTweaksMod.MODID + ":blocks/fluid_";

	public static final int BASIC_RESEARCHER = 1;
	public static final int RESEARCH_SELECTER = 2;
	public static final int BOILER = 3;

	public static final int CRUSHER = 4;
	public static BlockBasicResearcher basicResearcherBlock;
	public static BlockResearchSelector blockResearchViewer;
	public static BlockMultiWall blockMultiWall;
	public static BlockOre superore;
	public static BlockMultiPowerInput blockMultiPowerInput;
	public static BlockMultiPowerOutput blockMultiPowerOutput;
	public static BlockMultiFluidInput blockMultiFluidInput;
	public static BlockMultiItemInterface blockMultiItemInterface;
	public static BlockCable blockCable;
	public static BlockPipe blockPipe;
	public static BlockBoiler blockBoiler;
	public static BlockPressureTank blockPressureTank;
	public static BlockSteamEngine blockSteamEngine;
	public static BlockCrusher blockCrusher;
	public static BlockConveyor blockConveyor;
	public static BlockExtractor blockExtractor;
	public static BlockInserter blockInserter;
	public static BlockSolarPanel blockSolarPanel;

	public static ItemConstructor itemConstructor;

	public static ItemTechComponent itemTechComponent;

	public static Fluid steam = createFluid("steam", true,
			fluid -> fluid.setLuminosity(10).setDensity(-1600).setViscosity(100).setGaseous(true),
			fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.CLAY)));

	static ModelResourceLocation fluidLocation = new ModelResourceLocation("supertechtweaks:blockFluid", "inventory");

	/**
	 * Create a {@link Fluid} and its {@link IFluidBlock}, or use the existing ones
	 * if a fluid has already been registered with the same name.
	 *
	 * @param name                 The name of the fluid
	 * @param hasFlowIcon          Does the fluid have a flow icon?
	 * @param fluidPropertyApplier A function that sets the properties of the
	 *                             {@link Fluid}
	 * @param blockFactory         A function that creates the {@link IFluidBlock}
	 * @return The fluid and block
	 */
	private static <T extends Block & IFluidBlock> Fluid createFluid(String name, boolean hasFlowIcon,
			Consumer<Fluid> fluidPropertyApplier, Function<Fluid, T> blockFactory) {

		final ResourceLocation still = new ResourceLocation(fluidTexturePrefix + name + "_still");
		final ResourceLocation flowing = hasFlowIcon ? new ResourceLocation(fluidTexturePrefix + name + "_flow")
				: still;

		Fluid fluid = new Fluid(name, still, flowing);
		final boolean useOwnFluid = FluidRegistry.registerFluid(fluid);
		if (useOwnFluid) {
			fluidPropertyApplier.accept(fluid);
		} else {
			fluid = FluidRegistry.getFluid(name);
		}

		return fluid;
	}

	@SideOnly(Side.CLIENT)
	public static void initItemModels() {
		blockCable.initItemModel();
		blockPipe.initItemModel();
	}

	@SideOnly(Side.CLIENT)
	public static void initModels() {
		basicResearcherBlock.initModel();
		blockBoiler.initModel();
		blockPressureTank.initModel();
		blockCrusher.initModel();
		blockSteamEngine.initModel();
		blockResearchViewer.initModel();
		blockMultiWall.initModel();
		blockMultiFluidInput.initModel();
		blockMultiItemInterface.initModel();
		blockMultiPowerInput.initModel();
		blockMultiPowerOutput.initModel();
		blockCable.initModel();
		blockPipe.initModel();
		itemTechComponent.registerModels();

	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		System.out.println("Attempting block registry");

		blockSolarPanel = new BlockSolarPanel();
		event.getRegistry().register(blockSolarPanel);
		GameRegistry.registerTileEntity(TileSolarPanel.class, SuperTechTweaksMod.MODID + "_solarpanel");

		blockConveyor = new BlockConveyor();
		event.getRegistry().register(blockConveyor);
		GameRegistry.registerTileEntity(TileEntityConveyor.class, SuperTechTweaksMod.MODID + "_conveyor");

		blockExtractor = new BlockExtractor();
		event.getRegistry().register(blockExtractor);
		GameRegistry.registerTileEntity(TileEntityExtractor.class, SuperTechTweaksMod.MODID + "_extractor");

		blockInserter = new BlockInserter();
		event.getRegistry().register(blockInserter);
		GameRegistry.registerTileEntity(TileEntityInserter.class, SuperTechTweaksMod.MODID + "_inserter");

		basicResearcherBlock = new BlockBasicResearcher();
		event.getRegistry().register(basicResearcherBlock);
		GameRegistry.registerTileEntity(TileBasicResearcher.class, SuperTechTweaksMod.MODID + "_basicresearcherblock");

		blockBoiler = new BlockBoiler();
		event.getRegistry().register(blockBoiler);
		GameRegistry.registerTileEntity(TileBoiler.class, SuperTechTweaksMod.MODID + "_tileboiler");

		blockSteamEngine = new BlockSteamEngine();
		event.getRegistry().register(blockSteamEngine);
		GameRegistry.registerTileEntity(TileSteamEngine.class, SuperTechTweaksMod.MODID + "_tilesteamengine");

		blockCrusher = new BlockCrusher();
		event.getRegistry().register(blockCrusher);
		GameRegistry.registerTileEntity(TileCrusher.class, SuperTechTweaksMod.MODID + "_tilecrusher");

		blockResearchViewer = new BlockResearchSelector();
		event.getRegistry().register(blockResearchViewer);
		GameRegistry.registerTileEntity(TileResearchSelector.class,
				SuperTechTweaksMod.MODID + "_researchselectorblock");

		superore = new BlockOre();
		event.getRegistry().register(superore);

		blockMultiWall = new BlockMultiWall();
		event.getRegistry().register(blockMultiWall);
		GameRegistry.registerTileEntity(TileMultiWall.class, SuperTechTweaksMod.MODID + "_tilemultiwall");

		blockPressureTank = new BlockPressureTank();
		event.getRegistry().register(blockPressureTank);
		GameRegistry.registerTileEntity(TilePressureTank.class, SuperTechTweaksMod.MODID + "_tilepressuretank");

		blockMultiPowerInput = new BlockMultiPowerInput();
		event.getRegistry().register(blockMultiPowerInput);
		GameRegistry.registerTileEntity(TileMultiPowerInput.class, SuperTechTweaksMod.MODID + "_tilemultipowerinput");

		blockMultiFluidInput = new BlockMultiFluidInput();
		event.getRegistry().register(blockMultiFluidInput);
		GameRegistry.registerTileEntity(TileMultiFluidInput.class, SuperTechTweaksMod.MODID + "_tilemultifluidinput");

		blockMultiItemInterface = new BlockMultiItemInterface();
		event.getRegistry().register(blockMultiItemInterface);
		GameRegistry.registerTileEntity(TileMultiItemInterface.class,
				SuperTechTweaksMod.MODID + "_tilemultiiteminterface");

		blockMultiPowerOutput = new BlockMultiPowerOutput();
		event.getRegistry().register(blockMultiPowerOutput);
		GameRegistry.registerTileEntity(TileMultiPowerOutput.class, SuperTechTweaksMod.MODID + "_tilemultipoweroutput");

		blockCable = new BlockCable();
		event.getRegistry().register(blockCable);
		GameRegistry.registerTileEntity(TileCable.class, SuperTechTweaksMod.MODID + "_tilecable");

		blockPipe = new BlockPipe();
		event.getRegistry().register(blockPipe);
		GameRegistry.registerTileEntity(TilePipe.class, SuperTechTweaksMod.MODID + "_tilepipe");

		// Rocks

		RockManager.addRockTypes(
				Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE),
				"igneous", "andesite", "vanilla", "extrusive", "intermediate");
		RockManager.addTextureOverride(
				Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE),
				new ResourceLocation("minecraft:blocks/andesite"));
		createStoneType("basalt", 5, 100, 2, event, "igneous", "extrusive");
		RockManager.addRockTypes(Blocks.SANDSTONE.getDefaultState(), "sedimentary", "sandstone", "vanilla", "clastic");
		RockManager.addRockTypes(
				Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), "igneous",
				"diorite", "vanilla", "intrusive", "intermediate");
		RockManager.addTextureOverride(
				Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE),
				new ResourceLocation("minecraft:blocks/diorite"));
		RockManager.addRockTypes(
				Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), "igneous",
				"granite", "vanilla", "felsic", "intrusive");
		RockManager.addTextureOverride(
				Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE),
				new ResourceLocation("minecraft:blocks/granite"));
		createStoneType("rhyolite", 1.5, 10, 0, event, "igneous", "felsic", "extrusive");
		createStoneType("gabbro", 1.5, 10, 0, event, "igneous", "mafic", "intrusive");
		createStoneType("scoria", 1.5, 10, 0, event, "igneous", "mafic", "extrusive");
		createStoneType("pegmatite", 1.5, 10, 0, event, "igneous", "felsic", "intrusive");

		// RockManager.addRockTypes(Blocks.GRAVEL.getDefaultState(), "sedimentary");
		createStoneType("shale", 1.5, 10, 0, event, "sedimentary", "clastic");
		createStoneType("chert", 1.5, 10, 0, event, "sedimentary");
		createStoneType("conglomerate", 1.5, 10, 0, event, "sedimentary", "clastic");
		createStoneType("dolomite", 3, 15, 1, event, "sedimentary");
		createStoneType("limestone", 1.5, 10, 0, event, "sedimentary");
		createStoneType("chalk", 1.5, 10, 0, event, "sedimentary");

		createStoneType("schist", 3, 15, 1, event, "metamorphic");
		createStoneType("gneiss", 3, 15, 1, event, "metamorphic");
		createStoneType("marble", 1.5, 10, 0, event, "sedimentary");
		createStoneType("phyllite", 1.5, 10, 0, event, "metamorphic");
		createStoneType("amphibolite", 3, 15, 1, event, "metamorphic");
		createStoneType("slate", 1.5, 10, 0, event, "metamorphic");

		createStoneType("kimberlite", 2.0, 14, 3, event, "gabbro");

	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		itemConstructor = new ItemConstructor();
		// event.getRegistry().register(itemConstructor);

		event.getRegistry().register(new ItemBlock(blockSolarPanel).setRegistryName(blockSolarPanel.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockConveyor).setRegistryName(blockConveyor.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockExtractor).setRegistryName(blockExtractor.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockInserter).setRegistryName(blockInserter.getRegistryName()));
		event.getRegistry()
				.register(new ItemBlock(basicResearcherBlock).setRegistryName(basicResearcherBlock.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockPressureTank) {
			@Override
			@SideOnly(Side.CLIENT)
			public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
					ITooltipFlag flagIn) {
				Material material = ItemHelper.getItemMaterial(stack);
				tooltip.add("Material: " + material.getName());
				tooltip.add("Internal Capacity: " + material.getFluidCapacity() + " mb");
			}
		}.setRegistryName(blockPressureTank.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockBoiler) {
			@Override
			@SideOnly(Side.CLIENT)
			public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
					ITooltipFlag flagIn) {
				Material material = ItemHelper.getItemMaterial(stack);
				tooltip.add("Material: " + material.getName());
				tooltip.add("Boilrate: " + TileBoiler.calcBoilRate(material) + " mb/t");
				tooltip.add("Water Capacity: " + material.getFluidCapacity() + " mb");
				tooltip.add("Steam Capacity: Based on Pressure Tank");
			}
		}.setRegistryName(blockBoiler.getRegistryName()));
		event.getRegistry()
				.register(new ItemBlock(blockSteamEngine).setRegistryName(blockSteamEngine.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockCrusher).setRegistryName(blockCrusher.getRegistryName()));
		event.getRegistry()
				.register(new ItemBlock(blockResearchViewer).setRegistryName(blockResearchViewer.getRegistryName()));
		event.getRegistry().register(new ItemBlock(superore).setRegistryName(superore.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockMultiWall).setRegistryName(blockMultiWall.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockMultiPowerInput) {
			@Override
			@SideOnly(Side.CLIENT)
			public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
					ITooltipFlag flagIn) {
				Material material = ItemHelper.getItemMaterial(stack);
				tooltip.add("Material: " + material.getName());
				tooltip.add("Max Power Input: " + material.getTransferRate() + " FE/t");

			}
		}.setRegistryName(blockMultiPowerInput.getRegistryName()));
		event.getRegistry()
				.register(new ItemBlock(blockMultiFluidInput).setRegistryName(blockMultiFluidInput.getRegistryName()));
		event.getRegistry().register(
				new ItemBlock(blockMultiItemInterface).setRegistryName(blockMultiItemInterface.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockMultiPowerOutput) {
			@Override
			@SideOnly(Side.CLIENT)
			public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
					ITooltipFlag flagIn) {
				Material material = ItemHelper.getItemMaterial(stack);
				tooltip.add("Material: " + material.getName());
				tooltip.add("Max Power Output: " + material.getTransferRate() + " FE/t");

			}
		}.setRegistryName(blockMultiPowerOutput.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockCable) {
			@Override
			@SideOnly(Side.CLIENT)
			public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
					ITooltipFlag flagIn) {
				Material material = ItemHelper.getItemMaterial(stack);
				tooltip.add("Material: " + material.getName());
				tooltip.add("Max Power Transfer: " + material.getTransferRate() + " FE/t");

			}
		}.setRegistryName(blockCable.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockPipe) {
			@Override
			@SideOnly(Side.CLIENT)
			public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
					ITooltipFlag flagIn) {
				Material material = ItemHelper.getItemMaterial(stack);
				tooltip.add("Material: " + material.getName());
				tooltip.add("Max Fluid Transfer: " + material.getFluidTransferRate() + " mb/t");
			}
		}.setRegistryName(blockPipe.getRegistryName()));

		itemTechComponent = new ItemTechComponent();
		event.getRegistry().register(itemTechComponent);
		itemTechComponent.setupDictionary();
	}

	@SubscribeEvent
	public static void registerMaterials(RegistryEvent.Register<Material> event) {
		new MaterialBuilder("Antimony").setColor(0xfada5e).setDensity(6.697).setThermalExpansion(11)
				.setThermalConductivity(24.4).setElectricalResistance(417).setYoungsModulus(55).setShearModulus(20)
				.setBulkModulus(42).setHarvestLevel(3).setNativeHarvest(3).build().registerMaterial();
		new MaterialBuilder("Bismuth").setColor(0xed7d92).setDensity(9.78).setThermalExpansion(13.4)
				.setThermalConductivity(7.97).setElectricalResistance(1290).setNativeHarvest(0).setYoungsModulus(32)
				.setShearModulus(12).setBulkModulus(31).setHarvestLevel(0).build().registerMaterial();
		new MaterialBuilder("Cadmium").setColor(0xed872d).setDensity(8.65).setNativeHarvest(0).setThermalExpansion(32.8)
				.setThermalConductivity(96.6).setElectricalResistance(72.7).setYoungsModulus(50).setShearModulus(19)
				.setBulkModulus(42).setHarvestLevel(0).build().registerMaterial();

		new MaterialBuilder("Mercury").setColor(0x751f27).setHarvestLevel(0).setDensity(13.546)
				.setElectricalResistance(961).setThermalExpansion(60.4).setShearModulus(0).setThermalConductivity(8.3)
				.setBulkModulus(0).setYoungsModulus(0).build().registerMaterial();
		Material copper = new MaterialBuilder("Copper").setColor(0xb4713d).setToolLevel(2).setNativeHarvest(1)
				.setDensity(8.96).setThermalExpansion(16.5).setThermalConductivity(401).setElectricalResistance(16.78)
				.setYoungsModulus(119).setShearModulus(48).setBulkModulus(140).setHarvestLevel(1).build();
		copper.registerMaterial();
		copper.addBasicProcessing();
		new MaterialBuilder("Zinc").setColor(0xbac4cb).setDensity(7.14).setThermalExpansion(30.2).setNativeHarvest(1)
				.setThermalConductivity(116).setElectricalResistance(59.0).setYoungsModulus(108).setShearModulus(43)
				.setBulkModulus(70).setHarvestLevel(1).build().registerMaterial();

		new MaterialBuilder("Coal").setColor(0x060607).setHarvestLevel(1).setDensity(2.08)
				.setCustomDrops(new ItemStack(Items.COAL)).setElectricalResistance(30000).setThermalExpansion(6)
				.setShearModulus(6).setThermalConductivity(293).setBulkModulus(12).setYoungsModulus(14).build()
				.registerMaterial();

		Material iron = new MaterialBuilder("Iron").setColor(0xd3ad90).setToolLevel(3).setHarvestLevel(2)
				.setNativeHarvest(2).setThermalExpansion(11.8).setThermalConductivity(80.4)
				.setElectricalResistance(96.1).setYoungsModulus(211).setShearModulus(82).setBulkModulus(170)
				.setDensity(7.874).build();
		iron.registerMaterial();
		iron.addBasicProcessing();
		new MaterialBuilder("Chromium").setColor(0x18391e).setDensity(7.19).setThermalExpansion(4.9).setNativeHarvest(2)
				.setThermalConductivity(93.9).setElectricalResistance(125).setYoungsModulus(279).setShearModulus(115)
				.setBulkModulus(160).setHarvestLevel(2).build().registerMaterial();
		new MaterialBuilder("Aluminum").setColor(0xe0d9cd).setDensity(2.7).setThermalExpansion(23.1).setNativeHarvest(2)
				.setThermalConductivity(237).setElectricalResistance(28.2).setYoungsModulus(70).setShearModulus(26)
				.setBulkModulus(79).setHarvestLevel(2).build().registerMaterial();

		Material silver = new MaterialBuilder("Silver").setColor(0xb5b5bd).setDensity(10.49).setThermalExpansion(18.9)
				.setNativeHarvest(2).setThermalConductivity(429).setElectricalResistance(15.87).setYoungsModulus(83)
				.setShearModulus(30).setBulkModulus(100).setHarvestLevel(2).build();
		silver.registerMaterial();
		silver.addBasicProcessing();
		new MaterialBuilder("Tellurium").setColor(0xb5b5bd).setDensity(6.24).setThermalExpansion(18).setNativeHarvest(2)
				.setThermalConductivity(2.5).setElectricalResistance(10000).setYoungsModulus(43).setShearModulus(16)
				.setBulkModulus(65).setHarvestLevel(2).build().registerMaterial();

		new MaterialBuilder("Lapis").setColor(0x000094).setHarvestLevel(2).setDensity(2.8)
				.setCustomDrops(new ItemStack(Items.DYE, 5, 4)).setElectricalResistance(999999).setThermalExpansion(6)
				.setShearModulus(200).setThermalConductivity(293).setBulkModulus(170).setYoungsModulus(137).build()
				.registerMaterial();

		Material tin = new MaterialBuilder("Tin").setColor(0x726a78).setDensity(7.265).setThermalExpansion(22)
				.setNativeHarvest(1).setThermalConductivity(66.8).setElectricalResistance(115).setYoungsModulus(50)
				.setShearModulus(18).setBulkModulus(58).setHarvestLevel(3).build();
		tin.registerMaterial();
		tin.addBasicProcessing();
		Material gold = new MaterialBuilder("Gold").setColor(0xccccc33).setDensity(19.3).setThermalExpansion(14.2)
				.setNativeHarvest(3).setThermalConductivity(318).setElectricalResistance(22.14).setYoungsModulus(79)
				.setShearModulus(27).setBulkModulus(180).setHarvestLevel(3).build();
		gold.registerMaterial();
		gold.addBasicProcessing();
		Material lead = new MaterialBuilder("Lead").setColor(0x474c4d).setDensity(11.34).setThermalExpansion(28.9)
				.setNativeHarvest(3).setThermalConductivity(35.3).setElectricalResistance(208).setYoungsModulus(16)
				.setShearModulus(6).setBulkModulus(46).setHarvestLevel(3).build();
		lead.registerMaterial();
		lead.addBasicProcessing();
		new MaterialBuilder("Palladium").setColor(0xced0dd).setHarvestLevel(4).setDensity(12.023).setNativeHarvest(4)
				.setThermalExpansion(11.8).setThermalConductivity(71.8).setElectricalResistance(105.4)
				.setYoungsModulus(121).setShearModulus(44).setBulkModulus(180).build().registerMaterial();
		new MaterialBuilder("Redstone").setColor(0xd43c2c).setDensity(8.96).setThermalExpansion(16.5)
				.setThermalConductivity(401).setElectricalResistance(16.78).setYoungsModulus(119).setShearModulus(48)
				.setBulkModulus(140).setHarvestLevel(2).setCustomDrops(new ItemStack(Items.REDSTONE, 4, 0)).build()
				.registerMaterial();
		// TODO make sulfur drop sulfur
		new MaterialBuilder("Sulfur").setColor(0xedff21).setHarvestLevel(3)
				.setCustomDrops(new ItemStack(Items.GUNPOWDER)).setDensity(20.7).setElectricalResistance(999999)
				.setThermalExpansion(0).setShearModulus(0).setThermalConductivity(.205).setBulkModulus(0)
				.setYoungsModulus(0).build().registerMaterial();
		Material nickel = new MaterialBuilder("Nickel").setColor(0xccd3d8).setDensity(8.908).setThermalExpansion(13.4)
				.setNativeHarvest(3).setThermalConductivity(90.9).setElectricalResistance(69.3).setYoungsModulus(200)
				.setShearModulus(76).setBulkModulus(180).setHarvestLevel(3).build();
		nickel.registerMaterial();
		nickel.addBasicProcessing();
		new MaterialBuilder("Osmium").setColor(0x9090a3).setDensity(22.59).setThermalExpansion(5.1).setNativeHarvest(5)
				.setThermalConductivity(87.6).setElectricalResistance(81.2).setShearModulus(222).setBulkModulus(462)
				.setYoungsModulus(565).setHarvestLevel(3).build().registerMaterial();
		new MaterialBuilder("Diamond").setColor(0xb9f2ff).setHarvestLevel(3)
				.setCustomDrops(new ItemStack(Items.DIAMOND)).setDensity(3.52).setElectricalResistance(999999)
				.setThermalExpansion(1).setShearModulus(455).setThermalConductivity(1800).setBulkModulus(530)
				.setYoungsModulus(1100).build().registerMaterial();
		new MaterialBuilder("Emerald").setColor(0x50c878).setHarvestLevel(4)
				.setCustomDrops(new ItemStack(Items.EMERALD)).setDensity(2.52).setElectricalResistance(999999)
				.setThermalExpansion(1).setShearModulus(150).setThermalConductivity(1800).setBulkModulus(1)
				.setYoungsModulus(287).build().registerMaterial();

		new MaterialBuilder("Manganese").setColor(0x242d36).setDensity(7.21).setThermalExpansion(21.7)
				.setNativeHarvest(4).setThermalConductivity(7.81).setElectricalResistance(1440).setYoungsModulus(198)
				.setBulkModulus(120).setShearModulus(74).setHarvestLevel(4).build().registerMaterial();

		new MaterialBuilder("Uranium").setColor(0x329832).setDensity(19.1).setThermalExpansion(13.9).setNativeHarvest(6)
				.setThermalConductivity(27.5).setElectricalResistance(280).setYoungsModulus(208).setShearModulus(111)
				.setBulkModulus(100).setHarvestLevel(6).build().registerMaterial();
		new MaterialBuilder("Platinum").setColor(0xb8b7b2).setDensity(21.45).setThermalExpansion(8.8)
				.setNativeHarvest(3).setElectricalResistance(105).setThermalConductivity(71.6).setYoungsModulus(168)
				.setShearModulus(61).setBulkModulus(230).setHarvestLevel(3).build().registerMaterial();
		new MaterialBuilder("Iridium").setColor(0xe0e2dd).setDensity(22.56).setThermalExpansion(6.4).setNativeHarvest(7)
				.setThermalConductivity(147).setElectricalResistance(47.1).setYoungsModulus(528).setShearModulus(210)
				.setBulkModulus(320).setHarvestLevel(7).build().registerMaterial();
		new MaterialBuilder("Titanium").setColor(0x323230).setDensity(4.506).setThermalExpansion(8.6)
				.setNativeHarvest(7).setThermalConductivity(21.9).setElectricalResistance(420).setYoungsModulus(116)
				.setShearModulus(44).setBulkModulus(110).setHarvestLevel(7).build().registerMaterial();
		new MaterialBuilder("Stone").setColor(0x8b8d7a).setToolLevel(1).setHarvestLevel(0).setDensity(1.6)
				.setElectricalResistance(999999).setThermalExpansion(2.7).setShearModulus(30)
				.setThermalConductivity(12.83).setBulkModulus(60).setYoungsModulus(47).build().registerMaterial();
		new MaterialBuilder("Netherrack").setColor(0x800000).setToolLevel(1).setHarvestLevel(0).setDensity(1.24)
				.setElectricalResistance(999999).setThermalExpansion(2.7).setShearModulus(30)
				.setThermalConductivity(14.87).setBulkModulus(54).setYoungsModulus(31).build().registerMaterial();
		new MaterialBuilder("Wood").setColor(0x4f2412).setHarvestLevel(0).setDensity(.75).setElectricalResistance(10000)
				.setThermalExpansion(30).setToolLevel(0).setShearModulus(13).setThermalConductivity(0.15)
				.setBulkModulus(17).setYoungsModulus(11).build().registerMaterial();
		new MaterialBuilder("Electrum").setColor(0x928729).setHarvestLevel(3).setDensity(14.2).setNativeHarvest(3)
				.setElectricalResistance(24.67).setThermalExpansion(20).setShearModulus(35).setThermalConductivity(400)
				.setYoungsModulus(80).setBulkModulus(140).build().registerMaterial();
		new MaterialBuilder("Brass").setColor(0xe4ad5b).setHarvestLevel(2).setDensity(8.78)
				.setElectricalResistance(59.92).setThermalExpansion(20.5).setShearModulus(40)
				.setElectricalResistance(109).setYoungsModulus(112).setBulkModulus(108).build().registerMaterial();
		new MaterialBuilder("Constantan").setColor(0xe0a050).setHarvestLevel(3).setDensity(8.885)
				.setElectricalResistance(490).setThermalExpansion(14.9).setShearModulus(62).setThermalConductivity(21.2)
				.setYoungsModulus(162).setBulkModulus(130).build().registerMaterial();
		new MaterialBuilder("Bronze").setColor(0xe69e2f).setHarvestLevel(4).setToolLevel(4).setDensity(8.73)
				.setElectricalResistance(111.86).setThermalExpansion(18.2).setShearModulus(44)
				.setThermalConductivity(26).setYoungsModulus(107).setBulkModulus(112).build().registerMaterial();
		new MaterialBuilder("Steel").setColor(0xdfdfdf).setToolLevel(5).setHarvestLevel(5).setDensity(7.9)
				.setElectricalResistance(169).setThermalExpansion(72).setShearModulus(79).setThermalConductivity(50.2)
				.setBulkModulus(139).setYoungsModulus(200).build().registerMaterial();
		new MaterialBuilder("Obsidian").setColor(0x3d354b).setHarvestLevel(3).setDensity(2.4)
				.setElectricalResistance(999999).setThermalExpansion(2.9).setShearModulus(12)
				.setThermalConductivity(0.8).setBulkModulus(40).setYoungsModulus(73)
				.setCustomDrops(new ItemStack(Blocks.OBSIDIAN)).build().registerMaterial();

		new MaterialBuilder("Mithril").setColor(0xaebbdb).setHarvestLevel(5).setDensity(2.3).setNativeHarvest(5)
				.setElectricalResistance(14.3).setThermalExpansion(20).setShearModulus(200).setThermalConductivity(521)
				.setBulkModulus(400).setYoungsModulus(387).build().registerMaterial();

		new MaterialBuilder("Adamantine").setColor(0xb30000).setHarvestLevel(8).setDensity(24).setNativeHarvest(8)
				.setElectricalResistance(400).setThermalExpansion(1).setShearModulus(1000).setThermalConductivity(50)
				.setBulkModulus(1000).setYoungsModulus(1000).build().registerMaterial();
		new MaterialBuilder("AluBrass").setColor(0xcaa585).setHarvestLevel(1).setDensity(7.78)
				.setElectricalResistance(115).setThermalExpansion(17.8).setShearModulus(30).setThermalConductivity(65)
				.setYoungsModulus(120).setBulkModulus(70).build().registerMaterial();
		new MaterialBuilder("Invar").setColor(0xd0c0b3).setHarvestLevel(3).setDensity(8.1).setElectricalResistance(80)
				.setThermalExpansion(7.3).setShearModulus(326).setThermalConductivity(13).setBulkModulus(109)
				.setYoungsModulus(141).build().registerMaterial();
		new MaterialBuilder("Nichrome").setColor(0x858f80).setHarvestLevel(3).setDensity(8.4)
				.setElectricalResistance(125).setThermalExpansion(14).setShearModulus(100).setThermalConductivity(11.3)
				.setBulkModulus(160).setYoungsModulus(200).build().registerMaterial();
		new MaterialBuilder("Quartz").setColor(0xdddddd).setHarvestLevel(2).setDensity(2.65)
				.setElectricalResistance(10000).setThermalConductivity(1.3).setThermalExpansion(12.3)
				.setShearModulus(55).setBulkModulus(2).setCustomDrops(new ItemStack(Items.QUARTZ)).build()
				.registerMaterial();
		new MaterialBuilder("StainlessSteel").setColor(0xe0dfdb).setHarvestLevel(2).setDensity(8)
				.setElectricalResistance(7200).setThermalExpansion(17.3).setShearModulus(86)
				.setThermalConductivity(16.2).setYoungsModulus(198).setBulkModulus(143).build().registerMaterial();
		Material thorium = new MaterialBuilder("Thorium").setBulkModulus(54).setColor(0xD3D3D3).setDensity(11.7)
				.setElectricalResistance(157).setNativeHarvest(3).setHarvestLevel(3).setShearModulus(31)
				.setThermalConductivity(54).setThermalExpansion(11).setYoungsModulus(79).build();
		thorium.registerMaterial();
		thorium.addBasicProcessing();
		Material lithium = new MaterialBuilder("Lithium").setBulkModulus(11).setColor(0xD3D3C0).setDensity(0.534)
				.setElectricalResistance(92.8).setHarvestLevel(1).setNativeHarvest(1).setShearModulus(4)
				.setThermalConductivity(84.8).setThermalExpansion(46).setYoungsModulus(5).build();
		lithium.registerMaterial();
		lithium.addBasicProcessing();
		Material boron = new MaterialBuilder("Boron").setColor(0xBC8F8).setDensity(2.37)
				.setElectricalResistance(1000000).setHarvestLevel(0).setNativeHarvest(0).setThermalConductivity(27.4)
				.setThermalExpansion(6).build();
		boron.registerMaterial();
		boron.addBasicProcessing();
		Material magnesium = new MaterialBuilder("Magnesium").setBulkModulus(35).setColor(0xd3d3e5).setDensity(1.738)
				.setElectricalResistance(43.9).setHarvestLevel(1).setNativeHarvest(1).setShearModulus(17)
				.setThermalConductivity(156).setThermalExpansion(24.8).setYoungsModulus(45).build();
		magnesium.registerMaterial();
		magnesium.addBasicProcessing();
	}

	@SubscribeEvent
	public static void registerOres(RegistryEvent.Register<Ore> event) {
		new Ore("Bauxite", 2, 2, 0x7CFC00).registerOre();
		new Ore("Bornite", 2, 2.5, 0x8B4513).registerOre();
		new Ore("Magnetite", 2, 2.5, 0x494d51).registerOre();
		new Ore("Limonite", 2, 2.5, 0xFFF44F).registerOre();
		new Ore("Chalcocite", 2, 2.75, 0x2F4F4F).registerOre();
		new Ore("Cassiterite", 1, 1.5, 0x654321).registerOre();
		new Ore("Chromite", 2, 1.5, 0xC0C0CC).registerOre();
		new Ore("Cinnabar", 1, 1.0, 0x8b0017).registerOre();
		new Ore("Cobaltite", 5, 1.0, 0xd2b48c).registerOre();
		new Ore("Galena", 4, 5.0, 0xbeb2b2).registerOre();
		new Ore("Hematite", 3, 2.0, 0x101c1f).registerOre();
		new Ore("Ilmenite", 6, 6.0, 0x323230).registerOre();
		new Ore("Sphalerite", 2, 1.7, 0x323230).registerOre();
		new Ore("Coal", 1, 1.04, 0x060607) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.COAL);
			}
		}.registerOre();
		new Ore("Emerald", 4, 1.25, 0x50c878) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.EMERALD);
			}
		}.registerOre();
		new Ore("Lapis", 2, 1.4, 0x000094) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.DYE, (int) (Math.random() * 4 + 2), 4);
			}
		}.registerOre();
		new Ore("Redstone", 2, 1.4, 0xa00010) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.REDSTONE, (int) (Math.random() * 4 + 2));
			}
		}.registerOre();

		new Ore("Quartz", 1, 1.3, 0xdddddd) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.QUARTZ);
			}
		}.registerOre();
		new Ore("Diamond", 3, 1.73, 0xb9f2ff) {
			@Override
			public ItemStack getDrops(byte base) {
				return new ItemStack(Items.DIAMOND);
			}
		}.registerOre();

		CommonProxy.parsed.add(new WorldGeneratorPlate(
				WorldGeneratorBase.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:galena"))),
				"galena", new int[] { 0 }, 10, 10, "extrusive", "metamorphic", "granite", "sandstone"));

		CommonProxy.parsed.add(new WorldGeneratorCluster(
				WorldGeneratorBase
						.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:nativeAluminum"))),
				"nativeAluminum", new int[] { 0 }, 15, 1, 1, 5, "extrusive"));
		CommonProxy.parsed
				.add(new WorldGeneratorCluster(
						WorldGeneratorBase.singleOre(
								Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:bauxite"))),
						"bauxite", new int[] { 0 }, 20, 1, 1, 5, "limestone", "dolomite", "granite", "gneiss", "basalt",
						"shale"));
		CommonProxy.parsed.add(new WorldGeneratorCluster(
				WorldGeneratorBase.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:bornite"))),
				"bornite", new int[] { 0 }, 20, 1, 2, 5, "mafic", "pegmatite", "shale"));
		CommonProxy.parsed.add(new WorldGeneratorCluster(
				WorldGeneratorBase.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:chalcocite"))),
				"chalcocite", new int[] { 0 }, 20, 1, 3, 5, "sedimentary", ""));
		CommonProxy.parsed.add(new WorldGeneratorCluster(
				WorldGeneratorBase
						.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:cassiterite"))),
				"cassiterite", new int[] { 0 }, 10, 1, 3, 5, "sedimentary"));
		CommonProxy.parsed.add(new WorldGeneratorCluster(
				WorldGeneratorBase.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:chromite"))),
				"chromite", new int[] { 0 }, 10, 1, 4, 5, "intrusive", "metamorphic"));
		CommonProxy.parsed.add(new WorldGeneratorCluster(
				WorldGeneratorBase.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:magnetite"))),
				"magnetite", new int[] { 0 }, 10, 1, 6, 5, "sedimentary"));
		CommonProxy.parsed.add(new WorldGeneratorVein(
				WorldGeneratorBase.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:cinnabar"))),
				"cinnabar", new int[] { 0 }, 2, 1, 1, 2, "extrusive", "shale"));
		CommonProxy.parsed.add(new WorldGeneratorVein(
				WorldGeneratorBase.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:limonite"))),
				"limonite", new int[] { 0 }, 2, 1, 2, 8, "sedimentary"));
		CommonProxy.parsed.add(new WorldGeneratorVein(
				WorldGeneratorBase.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:cobaltite"))),
				"cobaltite", new int[] { 0 }, 2, 1, 1, 2, "igneous", "metamorphic"));
		CommonProxy.parsed.add(new WorldGeneratorVein(
				WorldGeneratorBase.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:hematite"))),
				"hematite", new int[] { 0 }, 2, 1, 2, 4, "extrusive", "sedimentary"));
		CommonProxy.parsed.add(new WorldGeneratorCluster(
				WorldGeneratorBase.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:ilmenite"))),
				"ilmenite", new int[] { 0 }, 10, 1, 8, 5, "gabbro"));
		CommonProxy.parsed.add(new WorldGeneratorVein(
				WorldGeneratorBase.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:sphalerite"))),
				"sphalerite", new int[] { 0 }, 2, 1, 1, 3, "metamorphic", "dolomite"));
		CommonProxy.parsed.add(new WorldGeneratorVein(
				WorldGeneratorBase.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:coal"))),
				"lignite", new int[] { 0 }, 2, 3, 1, 8, "sedimentary"));
		CommonProxy.parsed.add(new WorldGeneratorCluster(
				WorldGeneratorBase.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:coal"))),
				"bitumin", new int[] { 0 }, 10, 1, 15, 2, "sedimentary"));
		CommonProxy.parsed.add(new WorldGeneratorCluster(
				WorldGeneratorBase.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:redstone"))),
				"redstone", new int[] { 0 }, 10, 1, 7, 2, "igneous"));
		CommonProxy.parsed.add(new WorldGeneratorCluster(
				WorldGeneratorBase.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:emerald"))),
				"emerald", new int[] { 0 }, 5, 1, 7, 2, "granite", "schist", "marble"));
		CommonProxy.parsed.add(new WorldGeneratorCluster(
				WorldGeneratorBase.singleOre(Ore.REGISTRY.getValue(new ResourceLocation("supertechtweaks:lapis"))),
				"lapis", new int[] { 0 }, 5, 1, 7, 2, "intrusive", "marble"));
	}

	@SubscribeEvent
	public static void registerRegistry(RegistryEvent.NewRegistry event) {
		new RegistryBuilder().setType(Material.class)
				.setName(new ResourceLocation(SuperTechTweaksMod.MODID, "MaterialRegistry")).setIDRange(0, 256)
				.create();
		Material.REGISTRY = GameRegistry.findRegistry(Material.class);
		new RegistryBuilder().setType(Research.class)
				.setName(new ResourceLocation(SuperTechTweaksMod.MODID, "ResearchRegistry")).setIDRange(0, 512)
				.create();
		new RegistryBuilder().setType(Ore.class).setName(new ResourceLocation(SuperTechTweaksMod.MODID, "OreRegistry"))
				.setIDRange(0, 512).create();
		Ore.REGISTRY = GameRegistry.findRegistry(Ore.class);
	}

	@SubscribeEvent
	public static void registerResearch(RegistryEvent.Register<Research> event) {
		event.getRegistry()
				.register(new Research("automation").setEnergyRequired(1000)
						.setDisplay(new ItemStack(ModRegistry.itemTechComponent, 1, ItemTechComponent.SMALL_POWER_UNIT))
						.setTitle("Basic Automation").addItemRequirement(new ItemStack(Items.REDSTONE, 4)));
		event.getRegistry()
				.register(new Research("electronics")
						.setDisplay(new ItemStack(ModRegistry.itemTechComponent, 1, ItemTechComponent.BASIC_CIRCUIT))
						.setTitle("Basic Electrinics").setEnergyRequired(1000)
						.addRequirement(new ResourceLocation("supertechtweaks:automation")));
		event.getRegistry().register(new Research("optics").setTitle("Optics").setDisplay(new ItemStack(Blocks.TORCH)));
		event.getRegistry()
				.register(new Research("combustion").setTitle("Combustion").setDisplay(new ItemStack(Items.GUNPOWDER)));
		event.getRegistry()
				.register(new Research("logistics").setTitle("Logistics").setDisplay(new ItemStack(Blocks.HOPPER))
						.setEnergyRequired(1000).addItemRequirement(new ItemStack(Blocks.DIRT, 4)));
		event.getRegistry().register(
				new Research("metallurgy").setTitle("Metallurgy").setDisplay(new ItemStack(Items.BLAZE_POWDER)));
		event.getRegistry().register(new Research("advancediron1").setTitle("Advanced Iron Processing 1")
				.setDisplay(new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:iron")).getMaterialItem(), 1,
						MaterialItem.DUST))
				.addRequirement(new ResourceLocation("supertechtweaks:metallurgy")));
		event.getRegistry().register(new Research("bronze1").setTitle("Bronze 1")
				.setDisplay(new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:bronze")).getMaterialItem(), 1,
						MaterialItem.DUST))
				.addRequirement(new ResourceLocation("supertechtweaks:advancedcopper1"))
				.addRequirement(new ResourceLocation("supertechtweaks:advancedtin1")));
		event.getRegistry().register(new Research("advancediron2").setTitle("Advanced Iron Processing 2")
				.setDisplay(new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:iron")).getMaterialItem(), 1,
						MaterialItem.CLUMP))
				.addRequirement(new ResourceLocation("supertechtweaks:advancediron1")));
		event.getRegistry().register(new Research("advancediron3").setTitle("Advanced Iron Processing 3")
				.setDisplay(new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:iron")).getMaterialItem(), 1,
						MaterialItem.CRYSTAL))
				.addRequirement(new ResourceLocation("supertechtweaks:advancediron2")));
		event.getRegistry().register(new Research("advancedcopper2").setTitle("Advanced Copper Processing 2")
				.setDisplay(new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:copper")).getMaterialItem(), 1,
						MaterialItem.CLUMP))
				.addRequirement(new ResourceLocation("supertechtweaks:advancedcopper1")));
		event.getRegistry().register(new Research("advancedcopper3").setTitle("Advanced Copper Processing 3")
				.setDisplay(new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:copper")).getMaterialItem(), 1,
						MaterialItem.CRYSTAL))
				.addRequirement(new ResourceLocation("supertechtweaks:advancedcopper2")));
		event.getRegistry().register(new Research("advancedcopper1").setTitle("Advanced Copper Processing 1")
				.setDisplay(new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:copper")).getMaterialItem(), 1,
						MaterialItem.DUST))
				.addRequirement(new ResourceLocation("supertechtweaks:metallurgy")));
		event.getRegistry().register(new Research("advancedtin1").setTitle("Advanced Tin Processing 1")
				.setDisplay(new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:tin")).getMaterialItem(), 1,
						MaterialItem.DUST))
				.addRequirement(new ResourceLocation("supertechtweaks:metallurgy")));
		event.getRegistry().register(new Research("steel").setTitle("Steel")
				.setDisplay(new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:steel")).getMaterialItem(), 1,
						MaterialItem.DUST))
				.addRequirement(new ResourceLocation("supertechtweaks:advancediron1")));
		event.getRegistry()
				.register(new Research("solarenergy").setTitle("Solar Energy").setDisplay(new ItemStack(Blocks.TORCH))
						.addRequirement(new ResourceLocation("supertechtweaks:optics"))
						.addRequirement(new ResourceLocation("supertechtweaks:electronics")));
		event.getRegistry()
				.register(new Research("engines").setTitle("Engines").setDisplay(new ItemStack(Blocks.LIT_FURNACE))
						.addRequirement(new ResourceLocation("supertechtweaks:steel"))
						.addRequirement(new ResourceLocation("supertechtweaks:combustion"))
						.addRequirement(new ResourceLocation("supertechtweaks:automation2")));
		event.getRegistry()
				.register(new Research("automation2").setTitle("Automation 2").setDisplay(new ItemStack(Blocks.TORCH))
						.addRequirement(new ResourceLocation("supertechtweaks:electronics")));
		event.getRegistry().register(new Research("oilprocessing").setTitle("Oil Processing")
				.setDisplay(new ItemStack(Blocks.WATER)).addRequirement(new ResourceLocation("supertechtweaks:steel")));
		event.getRegistry()
				.register(new Research("advancedstorage").setTitle("Advanced Storage")
						.setDisplay(new ItemStack(Blocks.ENDER_CHEST))
						.addRequirement(new ResourceLocation("supertechtweaks:steel")));
		event.getRegistry()
				.register(new Research("plastics").setTitle("Plastics")
						.setDisplay(new ItemStack(Items.COMMAND_BLOCK_MINECART))
						.addRequirement(new ResourceLocation("supertechtweaks:oilprocessing")));
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		// add smelting, since there's no json for that
	}

	/**
	 * 
	 * @param type              Igneous, sedimentary, or metamorphic
	 * @param name              id-name of the block
	 * @param hardness          How hard (time duration) the block is to pick. For
	 *                          reference, dirt is 0.5, stone is 1.5, ores are 3,
	 *                          and obsidian is 50
	 * @param blastResistance   how resistant the block is to explosions. For
	 *                          reference, dirt is 0, stone is 10, and blast-proof
	 *                          materials are 2000
	 * @param toolHardnessLevel 0 for wood tools, 1 for stone, 2 for iron, 3 for
	 *                          diamond
	 */
	private static void createStoneType(String name, double hardness, double blastResistance, int toolHardnessLevel,
			RegistryEvent.Register<Block> event, String... types) {
		final Block rock, rockStairs, rockSlab, rockSlabDouble, rockCobble;
		// rockCobble = new BlockRock(name + "cobble", true, (float) hardness, (float)
		// blastResistance, toolHardnessLevel,SoundType.STONE);

		ItemBlock itemBlock /*
							 * = (ItemBlock) new
							 * ItemBlock(rockCobble).setRegistryName(rockCobble.getRegistryName())
							 */;
		// ForgeRegistries.ITEMS.register(itemBlock);
		rock = new BlockRock(name, true, (float) hardness, (float) blastResistance, toolHardnessLevel,
				SoundType.STONE) {
			/*
			 * @Override public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
			 * IBlockState state, int fortune) { return Arrays.asList(new
			 * ItemStack(Item.getItemFromBlock(rockCobble)));
			 * 
			 * }
			 */
		};

		String[] newArray = new String[types.length + 1];
		System.arraycopy(types, 0, newArray, 0, types.length);
		newArray[newArray.length - 1] = name;
		RockManager.addRockTypes(rock.getDefaultState(), newArray);

		/*
		 * rockStairs = new BlockRockStairs(name + "_stairs", rock, (float) hardness,
		 * (float) blastResistance, toolHardnessLevel, SoundType.STONE); rockSlab = new
		 * BlockRockSlab.Half(name + "_slab", (float) hardness, (float) blastResistance,
		 * toolHardnessLevel, SoundType.STONE); rockSlabDouble = new
		 * BlockRockSlab.Double(name + "_slab_double", (float) hardness, (float)
		 * blastResistance, toolHardnessLevel, SoundType.STONE);
		 */

		itemBlock = (ItemBlock) new ItemBlock(rock).setRegistryName(rock.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlock);

		// GameRegistry.addSmelting(rock, new ItemStack(Blocks.STONE), 0.1F);
		event.getRegistry().registerAll(rock/* , rockCobble , rockStairs, rockSlab, rockSlabDouble */);

		/*
		 * GameRegistry.findRegistry(IRecipe.class).register(new ShapedOreRecipe(new
		 * ResourceLocation("stairs"), new ItemStack(rockStairs, 4), "x  ", "xx ",
		 * "xxx", 'x', rock)); GameRegistry.findRegistry(IRecipe.class).register( new
		 * ShapedOreRecipe(new ResourceLocation("slabs"), new ItemStack(rockSlab, 6),
		 * "xxx", 'x', rock));
		 * 
		 * GameRegistry.findRegistry(IRecipe.class).register( new ShapedOreRecipe(new
		 * ResourceLocation("blocks"), new ItemStack(brick, 4), "xx", "xx", 'x', rock));
		 * GameRegistry.findRegistry(IRecipe.class).register(new ShapedOreRecipe(new
		 * ResourceLocation("stairs"), new ItemStack(brickStairs, 4), "x  ", "xx ",
		 * "xxx", 'x', brick)); GameRegistry.findRegistry(IRecipe.class).register( new
		 * ShapedOreRecipe(new ResourceLocation("slabs"), new ItemStack(brickSlab, 6),
		 * "xxx", 'x', brick));
		 * 
		 * GameRegistry.findRegistry(IRecipe.class) .register(new ShapedOreRecipe(new
		 * ResourceLocation("blocks"), new ItemStack(smooth, 1), rock, "sand"));
		 * GameRegistry.findRegistry(IRecipe.class).register(new ShapedOreRecipe(new
		 * ResourceLocation("stairs"), new ItemStack(smoothStairs, 4), "x  ", "xx ",
		 * "xxx", 'x', smooth)); GameRegistry.findRegistry(IRecipe.class).register( new
		 * ShapedOreRecipe(new ResourceLocation("slabs"), new ItemStack(smoothSlab, 6),
		 * "xxx", 'x', smooth)); GameRegistry.findRegistry(IRecipe.class).register(new
		 * ShapedOreRecipe(new ResourceLocation("blocks"), new ItemStack(smoothBrick,
		 * 4), "xx", "xx", 'x', smooth));
		 * GameRegistry.findRegistry(IRecipe.class).register(new ShapedOreRecipe(new
		 * ResourceLocation("stairs"), new ItemStack(smoothBrickStairs, 4), "x  ",
		 * "xx ", "xxx", 'x', smoothBrick));
		 * GameRegistry.findRegistry(IRecipe.class).register(new ShapedOreRecipe(new
		 * ResourceLocation("slabs"), new ItemStack(smoothBrickSlab, 6), "xxx", 'x',
		 * smoothBrick));
		 */

		OreDictionary.registerOre("stone", rock);
		// OreDictionary.registerOre("cobblestone", rockCobble);

		final Item item = Item.getItemFromBlock(rock);
		ModelLoader.setCustomModelResourceLocation(item, 0,
				new ModelResourceLocation(new ResourceLocation(rock.getRegistryName().getResourceDomain(), "rock"),
						rock.getRegistryName().getResourcePath()));
		ModelLoader.setCustomStateMapper(rock, new StateMapperRock());
	}

	/**
	 * Register a single model for the {@link Block}'s {@link Item}.
	 * <p>
	 * Uses the registry name as the domain/path and the {@link IBlockState} as the
	 * variant.
	 *
	 * @param state The state to use as the variant
	 */
	private static void registerBlockItemModel(final IBlockState state) {
		final Block block = state.getBlock();
		final Item item = Item.getItemFromBlock(block);

		if (item != Items.AIR) {
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(block.getRegistryName(), "normal"));
		}
	}

}
