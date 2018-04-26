package com.sixteencolorgames.supertechtweaks.tileentities.multipoweroutput;

import java.util.Random;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.enums.Material;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMultiPowerOutput extends Block implements ITileEntityProvider {

	public BlockMultiPowerOutput() {
		super(net.minecraft.block.material.Material.ROCK);
		setUnlocalizedName(SuperTechTweaksMod.MODID + ".multipoweroutput");
		setRegistryName("multipoweroutputblock");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileMultiPowerOutput();
	}

	@Override
	public Item getItemDropped(IBlockState meta, Random random, int fortune) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks
	 * for render
	 */
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow
	 * post-place logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		TileMultiPowerOutput cable = (TileMultiPowerOutput) worldIn.getTileEntity(pos);
		if (!stack.hasTagCompound()) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("sttMaterial", "supertechtweaks:iron");
			stack.setTagCompound(tag);
		}
		String mat = stack.getTagCompound().getString("sttMaterial");
		cable.setMaterial(GameRegistry.findRegistry(Material.class).getValue(new ResourceLocation(mat)));
		cable.markDirty();
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player,
			boolean willHarvest) {
		if (!worldIn.isRemote) {
			if (player.isCreative()) {// If the player is in creative...
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
				return true;
			}
			ItemStack drop = new ItemStack(this);
			TileMultiPowerOutput cable = (TileMultiPowerOutput) worldIn.getTileEntity(pos);
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("sttMaterial", cable.getMaterial().getRegistryName().toString());
			drop.setTagCompound(tag);

			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
		return true;
	}

}
