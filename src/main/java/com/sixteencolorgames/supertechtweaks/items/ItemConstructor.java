package com.sixteencolorgames.supertechtweaks.items;

import javax.annotation.Nonnull;

import com.sixteencolorgames.supertechtweaks.tileentities.MultiblockHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemConstructor extends ItemBase {

	public ItemConstructor() {
		super("itemConstructor");
	}

	@Nonnull
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		for (MultiblockHandler.IMultiblock mb : MultiblockHandler.getMultiblocks()) {
			if (mb.isBlockTrigger(world.getBlockState(pos))) {

				player.sendMessage(new TextComponentString("Block hit trigger"));
				if (MultiblockHandler.postMultiblockFormationEvent(player, mb, pos, stack).isCanceled()) {
					continue;
				}
				if (side.getAxis() == Axis.Y) {
					side = EnumFacing.fromAngle(player.rotationYaw);
				} else {
					side = side.getOpposite();
				}
				if (!mb.checkStructure(world, pos, side, player)) {
					continue;
				}
				if (mb.createStructure(world, pos, side, player)) {
					return EnumActionResult.SUCCESS;
				}
			}
		}

		return EnumActionResult.PASS;
	}

}
