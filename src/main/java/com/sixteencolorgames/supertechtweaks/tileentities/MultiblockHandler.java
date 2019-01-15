/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is originally taken from Immersive Engineering (https://minecraft.curseforge.com/projects/immersive-engineering?gameCategorySlug=mc-mods&projectID=231951)
 */

package com.sixteencolorgames.supertechtweaks.tileentities;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author BluSunrize - 27.04.2015 <br>
 *         The handler for IE multiblocks. TO handle custom structures, create a
 *         class implementing IMultiblock and register it
 */
public class MultiblockHandler {
	static ArrayList<IMultiblock> multiblocks = new ArrayList<IMultiblock>();

	public static void registerMultiblock(IMultiblock multiblock) {
		multiblocks.add(multiblock);
	}

	public static ArrayList<IMultiblock> getMultiblocks() {
		return multiblocks;
	}

	public interface IMultiblock {
		/**
		 * returns name of the Multiblock. This is used for the interdiction NBT system
		 * on the hammer, so this name /must/ be unique.
		 */
		String getUniqueName();

		/**
		 * Check whether the given block can be used to trigger the structure creation
		 * of the multiblock.<br>
		 * Basically, a less resource-intensive preliminary check to avoid checking
		 * every structure.
		 */
		boolean isBlockTrigger(IBlockState state);

		/**
		 * This method checks the structure .
		 *
		 * @return if the structure was valid.
		 */
		default boolean checkStructure(World world, BlockPos pos, EnumFacing side, EntityPlayer player) {
			//TODO make a rotate CCW method/store the other rotations somewhere
			BlockPos fll = pos.add(getTriggerOffset());
			IBlockState[][][] check = this.getStructureManual();
			switch (side) {
			case EAST:
				check = rotateCW(check);
				break;
			case NORTH:
				break;
			case SOUTH:
				check = rotateCW(rotateCW(check));
				break;
			case WEST:
				check = rotateCW(rotateCW(rotateCW(check)));
				break;
			default:
				break;

			}
			for (int i = 0; i < getStructureManual().length; i++) {
				for (int j = 0; j < getStructureManual()[0].length; j++) {
					for (int k = 0; k < getStructureManual()[0][0].length; k++) {
						if (world.getBlockState(fll.add(i, j, k)).equals(getStructureManual()[i][j][k])) {
							return false;
						}
					}
				}
			}
			return true;
		}

		static IBlockState[][][] rotateCW(IBlockState[][][] mat) {
			final int M = mat[0].length;
			final int N = mat[0][0].length;
			IBlockState[][][] ret = new IBlockState[mat.length][N][M];
			for (int h = 0; h < mat.length; h++) {
				for (int r = 0; r < M; r++) {
					for (int c = 0; c < N; c++) {
						ret[h][c][M - 1 - r] = mat[h][r][c];
					}
				}
			}
			return ret;
		}

		/**
		 * This method sets up the structure.
		 *
		 * @return if the structure was transformed.
		 */
		boolean createStructure(World world, BlockPos pos, EnumFacing side, EntityPlayer player);

		/**
		 * A three-dimensional array (height, width, length) of the structure to be
		 * rendered in the Engineers Manual
		 */
		IBlockState[][][] getStructureManual();

		/**
		 * Returns the blockpos offset
		 */
		Vec3i getTriggerOffset();

		default IBlockState getBlockstateFromStack(int index, ItemStack stack) {
			if (!stack.isEmpty() && stack.getItem() instanceof ItemBlock)
				return ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getItemDamage());
			return null;
		}

		/**
		 * Use this to overwrite the rendering of a Multiblock's Component
		 */
		@SideOnly(Side.CLIENT)
		boolean overwriteBlockRender(ItemStack stack, int iterator);

		/**
		 * returns true to add a button that will switch between the assembly of
		 * multiblocks and the finished render
		 */
		@SideOnly(Side.CLIENT)
		boolean canRenderFormedStructure();

		/**
		 * use this function to render the complete multiblock
		 */
		@SideOnly(Side.CLIENT)
		void renderFormedStructure();
	}

	public static MultiblockFormEvent postMultiblockFormationEvent(EntityPlayer player, IMultiblock multiblock,
			BlockPos clickedBlock, ItemStack hammer) {
		MultiblockFormEvent event = new MultiblockFormEvent(player, multiblock, clickedBlock, hammer);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}

	/**
	 * This event is fired BEFORE the multiblock is attempted to be formed.<br>
	 * No checks of the structure have been made. The event simply exists to cancel
	 * the formation of the multiblock before it ever happens.
	 */
	@Cancelable
	public static class MultiblockFormEvent extends PlayerEvent {
		private final IMultiblock multiblock;
		private final BlockPos clickedBlock;
		private final ItemStack hammer;

		public MultiblockFormEvent(EntityPlayer player, IMultiblock multiblock, BlockPos clickedBlock,
				ItemStack hammer) {
			super(player);
			this.multiblock = multiblock;
			this.clickedBlock = clickedBlock;
			this.hammer = hammer;
		}

		public IMultiblock getMultiblock() {
			return multiblock;
		}

		public BlockPos getClickedBlock() {
			return clickedBlock;
		}

		public ItemStack getHammer() {
			return hammer;
		}
	}
}