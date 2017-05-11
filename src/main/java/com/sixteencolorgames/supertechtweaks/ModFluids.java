/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sixteencolorgames.supertechtweaks;

import static com.sixteencolorgames.supertechtweaks.ModItems.itemOreChunk;
import com.sixteencolorgames.supertechtweaks.blocks.BlockBase;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.items.ItemModelProvider;
import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 *
 * @author oa10712
 */
public class ModFluids {

    static ModelResourceLocation fluidLocation = new ModelResourceLocation("supertechtweaks:blockFluid",
            "inventory");

    public static void mainRegistry() {
        Material.materials.forEach((ore) -> {
            createFluid(ore.getName().toLowerCase(), false,
                    fluid -> fluid.setLuminosity(10).setDensity(800).setViscosity(300),
                    fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.PURPLE)),
                    ore);
        });
    }

    private static <T extends Block & IFluidBlock> Fluid createFluid(String name, boolean hasFlowIcon, Consumer<Fluid> fluidPropertyApplier, Function<Fluid, T> blockFactory, Material ore) {
        System.out.println("Creating Fluid: " + name);
        final String texturePrefix = SuperTechTweaksMod.MODID + ":" + "blocks/fluid_";

        final ResourceLocation still = new ResourceLocation(texturePrefix + "still");
        final ResourceLocation flowing = hasFlowIcon ? new ResourceLocation(texturePrefix + "flow") : still;

        Fluid fluid = new Fluid(name, still, flowing) {
            @Override
            public int getColor() {
                return ore.getColor();
            }
        };
        final boolean useOwnFluid = FluidRegistry.registerFluid(fluid);

        if (useOwnFluid) {
            fluidPropertyApplier.accept(fluid);
            registerBucket(fluid);
            //registerFluidBlock(blockFactory.apply(fluid));
        } else {
            fluid = FluidRegistry.getFluid(name);
        }

        return fluid;
    }

    private static <T extends Block & IFluidBlock> T registerFluidBlock(T block) {
        block.setRegistryName("fluid" + block.getFluid().getName());
        block.setUnlocalizedName(SuperTechTweaksMod.MODID + ":" + block.getFluid().getUnlocalizedName());

        ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation("supertechtweaks:fluid", "fluid");
            }
        });
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        GameRegistry.register(block);
        GameRegistry.register(itemBlock);
        ModelLoader.setCustomModelResourceLocation(itemBlock, 0, fluidLocation);
        return block;
    }

    private static void registerBucket(Fluid fluid) {
        FluidRegistry.addBucketForFluid(fluid);
    }
}
