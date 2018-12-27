package com.sixteencolorgames.supertechtweaks.tileentities.pressuretank;

import static mcjty.theoneprobe.api.IProbeInfo.ENDLOC;
import static mcjty.theoneprobe.api.IProbeInfo.STARTLOC;

import java.util.Random;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.blocks.BlockMulti;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;
import com.sixteencolorgames.supertechtweaks.tileentities.boiler.TileBoiler;
import com.sixteencolorgames.supertechtweaks.util.ItemHelper;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPressureTank extends BlockMulti {

	public static final PropertyBool PART = PropertyBool.create("part");

	public BlockPressureTank() {
		super(net.minecraft.block.material.Material.ROCK);
		setUnlocalizedName(SuperTechTweaksMod.MODID + ".pressuretank");
		setRegistryName("blockpressuretank");
		setDefaultState(blockState.getBaseState().withProperty(PART, false));
		setHardness(2);

	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { PART });
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TilePressureTank();
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies
	 * properties not visible in the metadata, such as fence connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity te = worldIn.getTileEntity(pos);
		boolean part = false;
		if (te != null && te instanceof TileMultiBlock) {
			part = ((TileMultiBlock) te).hasMaster();
		}
		return state.withProperty(PART, part);
	}

	@Override
	public Item getItemDropped(IBlockState meta, Random random, int fortune) {
		return null;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
				new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for
	 * render
	 */
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place
	 * logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		TilePressureTank tank = (TilePressureTank) worldIn.getTileEntity(pos);
		tank.setMaterial(ItemHelper.getItemMaterial(stack));
		tank.markDirty();
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
			TilePressureTank cable = (TilePressureTank) worldIn.getTileEntity(pos);
			ItemHelper.setItemMaterial(drop, cable.getMaterial());

			worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
		return true;
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world,
			IBlockState blockState, IProbeHitData data) {
		super.addProbeInfo(mode, probeInfo, player, world, blockState, data);

		TileEntity te = world.getTileEntity(data.getPos());
		if (te instanceof TilePressureTank) {
			TilePressureTank dataTileEntity = (TilePressureTank) te;
			probeInfo.horizontal().text(TextFormatting.GREEN + STARTLOC + "Material:" + ENDLOC + " "
					+ dataTileEntity.getMaterial().getName());
		}
	}
}
