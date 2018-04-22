package com.sixteencolorgames.supertechtweaks.blocks;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiWall;
import com.sixteencolorgames.supertechtweaks.tileentities.basicresearcher.TileBasicResearcher;
import com.sixteencolorgames.supertechtweaks.tileentities.researchselector.TileResearchSelector;

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

public class BlockMultiWall extends BlockMulti {
	public BlockMultiWall() {
		super(Material.ROCK);
		setUnlocalizedName(SuperTechTweaksMod.MODID + ".multiwall");
		setRegistryName("blockmultiwall");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileMultiWall();
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
				if (worldIn.getTileEntity(pos) instanceof TileMultiWall) {
					TileMultiWall te = (TileMultiWall) worldIn.getTileEntity(pos);
					if (te.hasMaster()) {
						TileBasicResearcher master = (TileBasicResearcher) te.getMaster();
						TileResearchSelector selector = (TileResearchSelector) worldIn
								.getTileEntity(master.getSelectorpos());
						playerIn.sendMessage(new TextComponentString("Selected: " + selector.getSelected().toString()));
					}
				}
			}
		}
		return true;

	}
}
