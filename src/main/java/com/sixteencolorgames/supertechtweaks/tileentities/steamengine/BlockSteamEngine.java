package com.sixteencolorgames.supertechtweaks.tileentities.steamengine;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSteamEngine extends Block implements ITileEntityProvider {

	public BlockSteamEngine() {
		super(Material.ROCK);
		setUnlocalizedName(SuperTechTweaksMod.MODID + "blocksteamengine");
		this.setRegistryName("blocksteamengine");
	}

	/**
	 * Called serverside after this block is replaced with another in Chunk, but
	 * before the Tile Entity is updated
	 */
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileSteamEngine tile = (TileSteamEngine) worldIn.getTileEntity(pos);
		tile.resetStructure();
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileSteamEngine();
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking()) {
			return false;
		} else {
			if (!worldIn.isRemote) {
				if (worldIn.getTileEntity(pos) instanceof TileSteamEngine) {
					TileSteamEngine te = (TileSteamEngine) worldIn.getTileEntity(pos);
					playerIn.sendMessage(new TextComponentString(
							"current steam: " + te.steamInternal.getFluidAmount() + ", current power: " + te.energy));
				}
			}
		}
		return true;

	}

}
