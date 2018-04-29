package com.sixteencolorgames.supertechtweaks;

import java.util.function.Consumer;
import java.util.function.Function;

import com.sixteencolorgames.supertechtweaks.blocks.BlockMultiWall;
import com.sixteencolorgames.supertechtweaks.blocks.BlockOre;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.enums.Material.MaterialBuilder;
import com.sixteencolorgames.supertechtweaks.enums.Research;
import com.sixteencolorgames.supertechtweaks.items.ItemTechComponent;
import com.sixteencolorgames.supertechtweaks.items.MaterialItem;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiWall;
import com.sixteencolorgames.supertechtweaks.tileentities.basicresearcher.BlockBasicResearcher;
import com.sixteencolorgames.supertechtweaks.tileentities.basicresearcher.TileBasicResearcher;
import com.sixteencolorgames.supertechtweaks.tileentities.boiler.BlockBoiler;
import com.sixteencolorgames.supertechtweaks.tileentities.boiler.TileBoiler;
import com.sixteencolorgames.supertechtweaks.tileentities.cable.BlockCable;
import com.sixteencolorgames.supertechtweaks.tileentities.cable.TileCable;
import com.sixteencolorgames.supertechtweaks.tileentities.multifluidinput.BlockMultiFluidInput;
import com.sixteencolorgames.supertechtweaks.tileentities.multifluidinput.TileMultiFluidInput;
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
import com.sixteencolorgames.supertechtweaks.tileentities.steamengine.BlockSteamEngine;
import com.sixteencolorgames.supertechtweaks.tileentities.steamengine.TileSteamEngine;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber()
public class ModRegistry {
	final static String fluidTexturePrefix = SuperTechTweaksMod.MODID + ":blocks/fluid_";

	public static final int BASIC_RESEARCHER = 1;
	public static final int RESEARCH_SELECTER = 2;
	public static final int BOILER = 3;
	public static BlockBasicResearcher basicResearcherBlock;
	public static BlockResearchSelector blockResearchViewer;
	public static BlockMultiWall blockMultiWall;
	public static BlockOre superore;
	public static BlockMultiPowerInput blockMultiPowerInput;
	public static BlockMultiPowerOutput blockMultiPowerOutput;
	public static BlockMultiFluidInput blockMultiFluidInput;
	public static BlockCable blockCable;
	public static BlockPipe blockPipe;
	public static BlockBoiler blockBoiler;
	public static BlockPressureTank blockPressureTank;
	public static BlockSteamEngine blockSteamEngine;

	public static ItemTechComponent itemTechComponent;

	public static Fluid steam = createFluid("steam", true,
			fluid -> fluid.setLuminosity(10).setDensity(-1600).setViscosity(100).setGaseous(true),
			fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.CLAY)));

	static ModelResourceLocation fluidLocation = new ModelResourceLocation("supertechtweaks:blockFluid", "inventory");

	/**
	 * Create a {@link Fluid} and its {@link IFluidBlock}, or use the existing
	 * ones if a fluid has already been registered with the same name.
	 *
	 * @param name
	 *            The name of the fluid
	 * @param hasFlowIcon
	 *            Does the fluid have a flow icon?
	 * @param fluidPropertyApplier
	 *            A function that sets the properties of the {@link Fluid}
	 * @param blockFactory
	 *            A function that creates the {@link IFluidBlock}
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
		blockSteamEngine.initModel();
		blockResearchViewer.initModel();
		blockMultiWall.initModel();
		blockMultiFluidInput.initModel();
		blockMultiPowerInput.initModel();
		blockMultiPowerOutput.initModel();
		blockCable.initModel();
		blockPipe.initModel();
		itemTechComponent.registerModels();
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		System.out.println("Attempting block registry");

		basicResearcherBlock = new BlockBasicResearcher();
		event.getRegistry().register(basicResearcherBlock);
		GameRegistry.registerTileEntity(TileBasicResearcher.class, SuperTechTweaksMod.MODID + "_basicresearcherblock");

		blockBoiler = new BlockBoiler();
		event.getRegistry().register(blockBoiler);
		GameRegistry.registerTileEntity(TileBoiler.class, SuperTechTweaksMod.MODID + "_tileboiler");

		blockSteamEngine = new BlockSteamEngine();
		event.getRegistry().register(blockSteamEngine);
		GameRegistry.registerTileEntity(TileSteamEngine.class, SuperTechTweaksMod.MODID + "_tilesteamengine");

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

		blockMultiPowerOutput = new BlockMultiPowerOutput();
		event.getRegistry().register(blockMultiPowerOutput);
		GameRegistry.registerTileEntity(TileMultiPowerOutput.class, SuperTechTweaksMod.MODID + "_tilemultipoweroutput");

		blockCable = new BlockCable();
		event.getRegistry().register(blockCable);
		GameRegistry.registerTileEntity(TileCable.class, SuperTechTweaksMod.MODID + "_tilecable");

		blockPipe = new BlockPipe();
		event.getRegistry().register(blockPipe);
		GameRegistry.registerTileEntity(TilePipe.class, SuperTechTweaksMod.MODID + "_tilepipe");

	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry()
				.register(new ItemBlock(basicResearcherBlock).setRegistryName(basicResearcherBlock.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockPressureTank) {
			@Override
			public String getItemStackDisplayName(ItemStack stack) {
				try {
					Material material = Material.REGISTRY
							.getValue(new ResourceLocation(stack.getTagCompound().getString("sttMaterial")));
					if (I18n.canTranslate(getUnlocalizedNameInefficiently(stack) + '.' + material.getName())) {
						return I18n.translateToLocal(getUnlocalizedNameInefficiently(stack) + '.' + material.getName());
					}
					return String.format(super.getItemStackDisplayName(stack),
							I18n.canTranslate("supertechtweaks.entry." + material.getName())
									? I18n.translateToLocal("supertechtweaks.entry." + material.getName())
									: material.getName());
				} catch (Exception e) {
					return super.getItemStackDisplayName(stack);
				}
			}
		}.setRegistryName(blockPressureTank.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockBoiler) {
			@Override
			public String getItemStackDisplayName(ItemStack stack) {
				try {
					Material material = Material.REGISTRY
							.getValue(new ResourceLocation(stack.getTagCompound().getString("sttMaterial")));
					if (I18n.canTranslate(getUnlocalizedNameInefficiently(stack) + '.' + material.getName())) {
						return I18n.translateToLocal(getUnlocalizedNameInefficiently(stack) + '.' + material.getName());
					}
					return String.format(super.getItemStackDisplayName(stack),
							I18n.canTranslate("supertechtweaks.entry." + material.getName())
									? I18n.translateToLocal("supertechtweaks.entry." + material.getName())
									: material.getName());
				} catch (Exception e) {
					return super.getItemStackDisplayName(stack);
				}
			}
		}.setRegistryName(blockBoiler.getRegistryName()));
		event.getRegistry()
				.register(new ItemBlock(blockSteamEngine).setRegistryName(blockSteamEngine.getRegistryName()));
		event.getRegistry()
				.register(new ItemBlock(blockResearchViewer).setRegistryName(blockResearchViewer.getRegistryName()));
		event.getRegistry().register(new ItemBlock(superore).setRegistryName(superore.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockMultiWall).setRegistryName(blockMultiWall.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockMultiPowerInput) {
			@Override
			public String getItemStackDisplayName(ItemStack stack) {
				try {
					Material material = Material.REGISTRY
							.getValue(new ResourceLocation(stack.getTagCompound().getString("sttMaterial")));
					if (I18n.canTranslate(getUnlocalizedNameInefficiently(stack) + '.' + material.getName())) {
						return I18n.translateToLocal(getUnlocalizedNameInefficiently(stack) + '.' + material.getName());
					}
					return String.format(super.getItemStackDisplayName(stack),
							I18n.canTranslate("supertechtweaks.entry." + material.getName())
									? I18n.translateToLocal("supertechtweaks.entry." + material.getName())
									: material.getName());
				} catch (Exception e) {
					return super.getItemStackDisplayName(stack);
				}
			}
		}.setRegistryName(blockMultiPowerInput.getRegistryName()));
		event.getRegistry()
				.register(new ItemBlock(blockMultiFluidInput).setRegistryName(blockMultiFluidInput.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockMultiPowerOutput) {
			@Override
			public String getItemStackDisplayName(ItemStack stack) {
				try {
					Material material = Material.REGISTRY
							.getValue(new ResourceLocation(stack.getTagCompound().getString("sttMaterial")));
					if (I18n.canTranslate(getUnlocalizedNameInefficiently(stack) + '.' + material.getName())) {
						return I18n.translateToLocal(getUnlocalizedNameInefficiently(stack) + '.' + material.getName());
					}
					return String.format(super.getItemStackDisplayName(stack),
							I18n.canTranslate("supertechtweaks.entry." + material.getName())
									? I18n.translateToLocal("supertechtweaks.entry." + material.getName())
									: material.getName());
				} catch (Exception e) {
					return super.getItemStackDisplayName(stack);
				}
			}
		}.setRegistryName(blockMultiPowerOutput.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockCable) {
			@Override
			public String getItemStackDisplayName(ItemStack stack) {
				try {
					Material material = Material.REGISTRY
							.getValue(new ResourceLocation(stack.getTagCompound().getString("sttMaterial")));
					if (I18n.canTranslate(getUnlocalizedNameInefficiently(stack) + '.' + material.getName())) {
						return I18n.translateToLocal(getUnlocalizedNameInefficiently(stack) + '.' + material.getName());
					}
					return String.format(super.getItemStackDisplayName(stack),
							I18n.canTranslate("supertechtweaks.entry." + material.getName())
									? I18n.translateToLocal("supertechtweaks.entry." + material.getName())
									: material.getName());
				} catch (Exception e) {
					return super.getItemStackDisplayName(stack);
				}
			}
		}.setRegistryName(blockCable.getRegistryName()));
		event.getRegistry().register(new ItemBlock(blockPipe) {
			@Override
			public String getItemStackDisplayName(ItemStack stack) {
				try {
					Material material = Material.REGISTRY
							.getValue(new ResourceLocation(stack.getTagCompound().getString("sttMaterial")));
					if (I18n.canTranslate(getUnlocalizedNameInefficiently(stack) + '.' + material.getName())) {
						return I18n.translateToLocal(getUnlocalizedNameInefficiently(stack) + '.' + material.getName());
					}
					return String.format(super.getItemStackDisplayName(stack),
							I18n.canTranslate("supertechtweaks.entry." + material.getName())
									? I18n.translateToLocal("supertechtweaks.entry." + material.getName())
									: material.getName());
				} catch (Exception e) {
					return super.getItemStackDisplayName(stack);
				}
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
				.setBulkModulus(42).setHarvestLevel(3).build().registerMaterial();
		new MaterialBuilder("Bismuth").setColor(0xed7d92).setDensity(9.78).setThermalExpansion(13.4)
				.setThermalConductivity(7.97).setElectricalResistance(1290).setYoungsModulus(32).setShearModulus(12)
				.setBulkModulus(31).setHarvestLevel(0).build().registerMaterial();
		new MaterialBuilder("Cadmium").setColor(0xed872d).setDensity(8.65).setThermalExpansion(32.8)
				.setThermalConductivity(96.6).setElectricalResistance(72.7).setYoungsModulus(50).setShearModulus(19)
				.setBulkModulus(42).setHarvestLevel(0).build().registerMaterial();

		new MaterialBuilder("Mercury").setColor(0x751f27).setHarvestLevel(0).setDensity(13.546)
				.setElectricalResistance(961).setThermalExpansion(60.4).setShearModulus(0).setThermalConductivity(8.3)
				.setBulkModulus(0).setYoungsModulus(0).build().registerMaterial();
		Material copper = new MaterialBuilder("Copper").setColor(0xb4713d).setToolLevel(2).setDensity(8.96)
				.setThermalExpansion(16.5).setThermalConductivity(401).setElectricalResistance(16.78)
				.setYoungsModulus(119).setShearModulus(48).setBulkModulus(140).setHarvestLevel(1).build();
		copper.registerMaterial();
		copper.addBasicSmelting();
		new MaterialBuilder("Zinc").setColor(0xbac4cb).setDensity(7.14).setThermalExpansion(30.2)
				.setThermalConductivity(116).setElectricalResistance(59.0).setYoungsModulus(108).setShearModulus(43)
				.setBulkModulus(70).setHarvestLevel(1).build().registerMaterial();

		new MaterialBuilder("Coal").setColor(0x060607).setHarvestLevel(1).setDensity(2.08)
				.setCustomDrops(new ItemStack(Items.COAL)).setElectricalResistance(30000).setThermalExpansion(6)
				.setShearModulus(6).setThermalConductivity(293).setBulkModulus(12).setYoungsModulus(14).build()
				.registerMaterial();

		Material iron = new MaterialBuilder("Iron").setColor(0xd3ad90).setToolLevel(3).setHarvestLevel(2).setThermalExpansion(11.8)
				.setThermalConductivity(80.4).setElectricalResistance(96.1).setYoungsModulus(211).setShearModulus(82)
				.setBulkModulus(170).setDensity(7.874).build();
		iron.registerMaterial();
		iron.addBasicSmelting();
		new MaterialBuilder("Chromium").setColor(0x18391e).setDensity(7.19).setThermalExpansion(4.9)
				.setThermalConductivity(93.9).setElectricalResistance(125).setYoungsModulus(279).setShearModulus(115)
				.setBulkModulus(160).setHarvestLevel(2).build().registerMaterial();
		new MaterialBuilder("Aluminum").setColor(0xe0d9cd).setDensity(2.7).setThermalExpansion(23.1)
				.setThermalConductivity(237).setElectricalResistance(28.2).setYoungsModulus(70).setShearModulus(26)
				.setBulkModulus(79).setHarvestLevel(2).build().registerMaterial();

		Material silver = new MaterialBuilder("Silver").setColor(0xb5b5bd).setDensity(10.49).setThermalExpansion(18.9)
				.setThermalConductivity(429).setElectricalResistance(15.87).setYoungsModulus(83).setShearModulus(30)
				.setBulkModulus(100).setHarvestLevel(2).build();
		silver.registerMaterial();
		silver.addBasicSmelting();
		new MaterialBuilder("Tellurium").setColor(0xb5b5bd).setDensity(6.24).setThermalExpansion(18)
				.setThermalConductivity(2.5).setElectricalResistance(10000).setYoungsModulus(43).setShearModulus(16)
				.setBulkModulus(65).setHarvestLevel(2).build().registerMaterial();

		new MaterialBuilder("Lapis").setColor(0x000094).setHarvestLevel(2).setDensity(2.8)
				.setCustomDrops(new ItemStack(Items.DYE, 5, 4)).setElectricalResistance(999999).setThermalExpansion(6)
				.setShearModulus(200).setThermalConductivity(293).setBulkModulus(170).setYoungsModulus(137).build()
				.registerMaterial();

		Material tin = new MaterialBuilder("Tin").setColor(0x726a78).setDensity(7.265).setThermalExpansion(22)
				.setThermalConductivity(66.8).setElectricalResistance(115).setYoungsModulus(50).setShearModulus(18)
				.setBulkModulus(58).setHarvestLevel(3).build();
		tin.registerMaterial();
		tin.addBasicSmelting();
		Material gold = new MaterialBuilder("Gold").setColor(0xccccc33).setDensity(19.3).setThermalExpansion(14.2)
				.setThermalConductivity(318).setElectricalResistance(22.14).setYoungsModulus(79).setShearModulus(27)
				.setBulkModulus(180).setHarvestLevel(3).build();
		gold.registerMaterial();
		gold.addBasicSmelting();
		Material lead = new MaterialBuilder("Lead").setColor(0x474c4d).setDensity(11.34).setThermalExpansion(28.9)
				.setThermalConductivity(35.3).setElectricalResistance(208).setYoungsModulus(16).setShearModulus(6)
				.setBulkModulus(46).setHarvestLevel(3).build();
		lead.registerMaterial();
		lead.addBasicSmelting();
		new MaterialBuilder("Palladium").setColor(0xced0dd).setHarvestLevel(4).setDensity(12.023)
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
				.setThermalConductivity(90.9).setElectricalResistance(69.3).setYoungsModulus(200).setShearModulus(76)
				.setBulkModulus(180).setHarvestLevel(3).build();
		nickel.registerMaterial();
		nickel.addBasicSmelting();
		new MaterialBuilder("Osmium").setColor(0x9090a3).setDensity(22.59).setThermalExpansion(5.1)
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
				.setThermalConductivity(7.81).setElectricalResistance(1440).setYoungsModulus(198).setBulkModulus(120)
				.setShearModulus(74).setHarvestLevel(4).build().registerMaterial();

		new MaterialBuilder("Uranium").setColor(0x329832).setDensity(19.1).setThermalExpansion(13.9)
				.setThermalConductivity(27.5).setElectricalResistance(280).setYoungsModulus(208).setShearModulus(111)
				.setBulkModulus(100).setHarvestLevel(6).build().registerMaterial();
		new MaterialBuilder("Platinum").setColor(0xb8b7b2).setDensity(21.45).setThermalExpansion(8.8)
				.setElectricalResistance(105).setThermalConductivity(71.6).setYoungsModulus(168).setShearModulus(61)
				.setBulkModulus(230).setHarvestLevel(3).build().registerMaterial();
		new MaterialBuilder("Iridium").setColor(0xe0e2dd).setDensity(22.56).setThermalExpansion(6.4)
				.setThermalConductivity(147).setElectricalResistance(47.1).setYoungsModulus(528).setShearModulus(210)
				.setBulkModulus(320).setHarvestLevel(7).build().registerMaterial();
		new MaterialBuilder("Titanium").setColor(0x323230).setDensity(4.506).setThermalExpansion(8.6)
				.setThermalConductivity(21.9).setElectricalResistance(420).setYoungsModulus(116).setShearModulus(44)
				.setBulkModulus(110).setHarvestLevel(7).build().registerMaterial();
		new MaterialBuilder("Stone").setColor(0x8b8d7a).setToolLevel(1).setHarvestLevel(0).setDensity(1.6)
				.setElectricalResistance(999999).setThermalExpansion(2.7).setShearModulus(30)
				.setThermalConductivity(12.83).setBulkModulus(60).setYoungsModulus(47).build().registerMaterial();
		new MaterialBuilder("Netherrack").setColor(0x800000).setToolLevel(1).setHarvestLevel(0).setDensity(1.24)
				.setElectricalResistance(999999).setThermalExpansion(2.7).setShearModulus(30)
				.setThermalConductivity(14.87).setBulkModulus(54).setYoungsModulus(31).build().registerMaterial();
		new MaterialBuilder("Wood").setColor(0x4f2412).setHarvestLevel(0).setDensity(.75).setElectricalResistance(10000)
				.setThermalExpansion(30).setToolLevel(0).setShearModulus(13).setThermalConductivity(0.15)
				.setBulkModulus(17).setYoungsModulus(11).build().registerMaterial();
		new MaterialBuilder("Electrum").setColor(0x928729).setHarvestLevel(3).setDensity(14.2)
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

		new MaterialBuilder("Mithril").setColor(0xaebbdb).setHarvestLevel(5).setDensity(2.3)
				.setElectricalResistance(14.3).setThermalExpansion(20).setShearModulus(200).setThermalConductivity(521)
				.setBulkModulus(400).setYoungsModulus(387).build().registerMaterial();

		new MaterialBuilder("Adamantine").setColor(0xb30000).setHarvestLevel(8).setDensity(24)
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
	}

	@SubscribeEvent
	public static void registerResearch(RegistryEvent.Register<Research> event) {
		event.getRegistry()
				.register(new Research("automation").setEnergyRequired(1000)
						.setDisplay(new ItemStack(ModRegistry.itemTechComponent, 1, ItemTechComponent.SMALL_POWER_UNIT))
						.setTitle("Basic Automation"));
		event.getRegistry()
				.register(
						new Research("electronics")
								.setDisplay(new ItemStack(ModRegistry.itemTechComponent, 1,
										ItemTechComponent.BASIC_CIRCUIT))
								.setTitle("Basic Electrinics").setEnergyRequired(1000)
								.addRequirement(new ResourceLocation("supertechtweaks:automation")));
		event.getRegistry().register(new Research("optics").setTitle("Optics").setDisplay(new ItemStack(Blocks.TORCH)));
		event.getRegistry()
				.register(new Research("combustion").setTitle("Combustion").setDisplay(new ItemStack(Items.GUNPOWDER)));
		event.getRegistry()
				.register(new Research("logistics").setTitle("Logistics").setDisplay(new ItemStack(Blocks.HOPPER)));
		event.getRegistry().register(
				new Research("metallurgy").setTitle("Metallurgy").setDisplay(new ItemStack(Items.BLAZE_POWDER)));
		event.getRegistry().register(new Research("advancediron1").setTitle("Advanced Iron Processing 1")
				.setDisplay(new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:iron")).getMaterialItem(), 1,
						MaterialItem.INGOT))
				.addRequirement(new ResourceLocation("supertechtweaks:metallurgy")));
		event.getRegistry().register(new Research("advancediron2").setTitle("Advanced Iron Processing 2")
				.setDisplay(new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:iron")).getMaterialItem(), 1,
						MaterialItem.DUST))
				.addRequirement(new ResourceLocation("supertechtweaks:advancediron1")));
		event.getRegistry().register(new Research("advancediron3").setTitle("Advanced Iron Processing 3")
				.setDisplay(new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:iron")).getMaterialItem(), 1,
						MaterialItem.CLUMP))
				.addRequirement(new ResourceLocation("supertechtweaks:advancediron2")));
		event.getRegistry().register(new Research("advancedcopper2").setTitle("Advanced Copper Processing 2")
				.setDisplay(new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:copper")).getMaterialItem(), 1,
						MaterialItem.DUST))
				.addRequirement(new ResourceLocation("supertechtweaks:advancedcopper1")));
		event.getRegistry().register(new Research("advancedcopper3").setTitle("Advanced Copper Processing 3")
				.setDisplay(new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:copper")).getMaterialItem(), 1,
						MaterialItem.CLUMP))
				.addRequirement(new ResourceLocation("supertechtweaks:advancedcopper2")));
		event.getRegistry().register(new Research("advancedcopper1").setTitle("Advanced Copper Processing 1")
				.setDisplay(new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:copper")).getMaterialItem(), 1,
						MaterialItem.INGOT))
				.addRequirement(new ResourceLocation("supertechtweaks:metallurgy")));
		event.getRegistry().register(new Research("steel").setTitle("Steel")
				.setDisplay(new ItemStack(
						Material.REGISTRY.getValue(new ResourceLocation("supertechtweaks:steel")).getMaterialItem(), 1,
						MaterialItem.INGOT))
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
}
