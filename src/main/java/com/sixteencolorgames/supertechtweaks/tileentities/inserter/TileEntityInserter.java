package com.sixteencolorgames.supertechtweaks.tileentities.inserter;

import javax.annotation.Nonnull;

import com.sixteencolorgames.supertechtweaks.tileentities.conveyor.BlockConveyorBase;
import com.sixteencolorgames.supertechtweaks.tileentities.conveyor.TileEntityConveyor;
import com.sixteencolorgames.supertechtweaks.tileentities.conveyor.TileEntityConveyorBase;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityInserter extends TileEntityConveyorBase {
	private static final String TAG_SLOPE_UP = "slope_up";
	private static final String TAG_SLOPE_DOWN = "slope_down";
	private static final String TAG_TURN_LEFT = "turn_left";
	private static final String TAG_TURN_RIGHT = "turn_right";
	private static final String TAG_POWERED = "powered";

	public TileEntityInserter() {
		super();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		if (compound.hasKey(TAG_POWERED))
			getTileData().setBoolean(TAG_POWERED, compound.getBoolean(TAG_POWERED));
	}

	@Override
	@Nonnull
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean(TAG_POWERED, isPowered());

		return compound;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);

		NBTTagCompound tag = pkt.getNbtCompound();

		NBTBase powered = tag.getTag(TAG_POWERED);

		getTileData().setTag(TAG_POWERED, powered);

		readFromNBT(tag);
	}

	public boolean isPowered() {
		return getTileData().getBoolean(TAG_POWERED);
	}

	public void setPowered(boolean powered) {
		getTileData().setBoolean(TAG_POWERED, powered);
	}

	@Override
	public void update() {
		if (!world.isRemote && world.getTotalWorldTime() % 20 == 0) {
			EnumFacing facing = getFacing();
			BlockPos handlerPos = getPos().offset(facing.getOpposite());
			TileEntity ht = world.getTileEntity(handlerPos);
			if (ht != null && ht.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
				IItemHandler inv = ht.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
				if (inv != null) {
					ItemStack ex = ItemStack.EMPTY;
					for (int i = 0; i < inv.getSlots(); i++) {
						ex = inv.extractItem(i, 2, false);
						if (!ex.isEmpty())
							break;
					}
					if (!ex.isEmpty()) {
						Vec3d posSpawn = new Vec3d(pos.getX() + 0.5D - facing.getFrontOffsetX() * .35,
								pos.getY() + 0.4D, pos.getZ() + 0.5D - facing.getFrontOffsetZ() * .35);
						EntityItem ei = new EntityItem(world, posSpawn.x, posSpawn.y, posSpawn.z, ex);
						ei.motionY = 0.1;
						world.spawnEntity(ei);
					}
				}
			}
		}
	}
}