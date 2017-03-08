package com.sixteencolorgames.supertechtweaks.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.ModItems;
import com.sixteencolorgames.supertechtweaks.compat.waila.WailaInfoProvider;
import com.sixteencolorgames.supertechtweaks.enums.Metals;
import com.sixteencolorgames.supertechtweaks.tileentities.TileEntityOre;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

/**
 * The ore block for world generation. Can hold up to 7 ores.
 * 
 * @author oa10712
 *
 */
public class BlockOre extends BlockTileEntity implements WailaInfoProvider {
	public BlockOre() {
		super(Material.ROCK, "blockOre");
		this.setHardness(3.0f);
	}

	/**
	 * Adds a metal to the ores contents
	 * 
	 * @param worldIn
	 *            The world this block is located in
	 * @param pos
	 *            The position of the block
	 * @param metal
	 *            The metal to add
	 * @return True if the metal was successfully added
	 */
	public boolean addMetal(World worldIn, BlockPos pos, Metals metal) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof TileEntityOre) {
			TileEntityOre ore = (TileEntityOre) tileEntity;
			return ore.addMetal(metal);
		}
		return false;
	}

	/**
	 * Ensures that this block does not drop any items, this is now handled in
	 * breakBlock.
	 * 
	 * @param meta
	 * @param random
	 * @param fortune
	 * @return
	 */
	@Override
	public Item getItemDropped(IBlockState meta, Random random, int fortune) {
		return null;
	}

	/**
	 * Method called after the player mines a block. Causes oreChunks to spawn
	 * that match the contained ores.
	 * 
	 * @param worldIn
	 * @param pos
	 * @param state
	 */
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof TileEntityOre) {
			TileEntityOre ore = (TileEntityOre) tileEntity;
			int[] ores = ore.getOres();
			for (int i = 0; i < 7; i++) {
				if (ores[i] != Metals.NONE.getIndex()) {
					worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
							new ItemStack(ModItems.itemOreChunk, 1, ores[i])));
				}
			}
			// handle dropping of metal chunks
		}
		super.breakBlock(worldIn, pos, state);
	}

	public void initModel() {
		// TODO Auto-generated method stub
	}

	@Override
	public Class getTileEntityClass() {
		return TileEntityOre.class;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		TileEntityOre ore = new TileEntityOre();
		return ore;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		TileEntity te = accessor.getTileEntity();
		if (te instanceof TileEntityOre) {
			TileEntityOre dataTileEntity = (TileEntityOre) te;
			currenttip.add(TextFormatting.GRAY + "Ores: " + Arrays.toString(dataTileEntity.getOres()));
		}
		return currenttip;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			@Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntityOre tile = (TileEntityOre) getTileEntity(world, pos);
			player.addChatMessage(new TextComponentString("Ores: " + Arrays.toString(tile.getOres())));
		}
		return true;
	}
}
