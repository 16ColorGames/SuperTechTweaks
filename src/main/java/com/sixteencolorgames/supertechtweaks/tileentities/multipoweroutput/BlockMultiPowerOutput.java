package com.sixteencolorgames.supertechtweaks.tileentities.multipoweroutput;

import java.util.Random;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;
import com.sixteencolorgames.supertechtweaks.util.ItemHelper;

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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMultiPowerOutput extends Block implements ITileEntityProvider {

	public BlockMultiPowerOutput() {
		super(net.minecraft.block.material.Material.ROCK);
		setUnlocalizedName(SuperTechTweaksMod.MODID + ".multipoweroutput");
		setRegistryName("multipoweroutputblock");
		setHardness(2);
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
	 * Called when the block is right clicked by a player.
	 */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		// Only execute on the server
		if (world.isRemote) {
			return true;
		}
		TileMultiBlock te = (TileMultiBlock) world.getTileEntity(pos);
		if (te.hasMaster()) {
			IEnergyStorage capability = te.getMaster().getCapability(CapabilityEnergy.ENERGY, side);

			if (capability != null) {
				player.sendMessage(new TextComponentString("Controller power: " + capability.getEnergyStored() + "/"
						+ capability.getMaxEnergyStored() + " FE"));
			} else {
				player.sendMessage(new TextComponentString("Controller does not support energy"));
			}
		} else {
			return false;
		}
		return true;
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
		cable.setMaterial(ItemHelper.getItemMaterial(stack));
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
			ItemHelper.setItemMaterial(drop, cable.getMaterial());

			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
		return true;
	}

}
