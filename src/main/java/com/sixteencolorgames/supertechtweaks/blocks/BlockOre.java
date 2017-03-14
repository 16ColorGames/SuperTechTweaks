package com.sixteencolorgames.supertechtweaks.blocks;

import java.util.List;
import java.util.Random;

import com.sixteencolorgames.supertechtweaks.ModItems;
import com.sixteencolorgames.supertechtweaks.compat.waila.WailaInfoProvider;
import com.sixteencolorgames.supertechtweaks.enums.Ores;
import com.sixteencolorgames.supertechtweaks.tileentities.TileEntityOre;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The ore block for world generation. Can hold up to 7 ores.
 * 
 * @author oa10712
 *
 */
public class BlockOre extends BlockTileEntity<TileEntityOre> implements WailaInfoProvider {

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
	public boolean addMetal(World worldIn, BlockPos pos, Ores metal) {
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
	 * 
	 * @return True if the block is destroyed
	 */
	@Override
	public boolean removedByPlayer(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player,
			boolean willHarvest) {
		if (!worldIn.isRemote) {
			boolean metalLeft = false;
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof TileEntityOre) {
				TileEntityOre ore = (TileEntityOre) tileEntity;
				int[] ores = ore.getOres();
				for (int i = 0; i < 7; i++) {
					if (ores[i] != Ores.NONE.ordinal()) {
						if (Ores.values()[ores[i]].getHarvest() <= player.getHeldItem(player.getActiveHand()).getItem()
								.getHarvestLevel(null, "pickaxe")) {
							worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY(),
									pos.getZ() + 0.5, new ItemStack(ModItems.itemOreChunk, 1, ores[i])));
							ore.setMetal(i, Ores.NONE);
						} else {
							metalLeft = true;
						}
					}
				}
				// handle dropping of metal chunks
			}
			willHarvest = metalLeft;
			if (!metalLeft) {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
			}
			return !metalLeft;
		}
		return true;
	}

	public void initModel() {
		// TODO Auto-generated method stub
	}

	@Override
	public Class getTileEntityClass() {
		return TileEntityOre.class;
	}

	@Override
	public TileEntityOre createTileEntity(World world, IBlockState state) {
		TileEntityOre ore = new TileEntityOre();
		return ore;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		TileEntityOre te = getTileEntity(accessor.getWorld(), accessor.getPosition());
		EntityPlayer player = accessor.getPlayer();
		int harvest = player.getHeldItemMainhand() != null
				? player.getHeldItemMainhand().getItem().getHarvestLevel(null, "pickaxe") : -1;
		for (int metal : te.getOres()) {
			if (metal != Ores.NONE.ordinal()) {
				Ores ore = Ores.values()[metal];
				TextFormatting color = TextFormatting.RED;
				if (harvest >= ore.getHarvest()) {
					color = TextFormatting.GREEN;
				}
				currenttip.add(color + Ores.values()[metal].getName());
			}
		}

		return currenttip;
	}

	@Override
	public void registerItemModel(Item item) {
		// void since we shouldn't have this in inventory
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.SOLID;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState iBlockState) {
		return EnumBlockRenderType.MODEL;
	}
}
