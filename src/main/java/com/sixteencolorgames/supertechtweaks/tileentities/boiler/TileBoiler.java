package com.sixteencolorgames.supertechtweaks.tileentities.boiler;

import javax.annotation.Nonnull;

import com.sixteencolorgames.supertechtweaks.ModRegistry;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlockController;
import com.sixteencolorgames.supertechtweaks.tileentities.pressuretank.TilePressureTank;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileBoiler extends TileMultiBlockController {

	public static final int SIZE = 1;

	protected FluidTank water = new FluidTank(5000) {
		@Override
		public boolean canFillFluidType(FluidStack fluid) {
			return fluid.getFluid().equals(FluidRegistry.WATER);
		}
	};
	private FluidTank steam = new FluidTank(5000);
	/**
	 * how many ticks are left for this burn
	 */
	public int burnTime = 0;
	/**
	 * how many ticks this burn started with
	 */
	public int totalBurnTime; // This item handler will hold our nine inventory
								// slots
	private ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE) {
		@Override
		protected void onContentsChanged(int slot) {
			// We need to tell the tile entity that something has changed so
			// that the chest contents is persisted
			TileBoiler.this.markDirty();
		}
	};

	public TileBoiler() {
		getSteam().setCanFill(false);
		water.setCanDrain(false);
	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		// If we are too far away from this tile entity you cannot use it
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	@Override
	public boolean checkMultiBlockForm() {
		TileEntity tile = world.getTileEntity(getPos().add(0, 1, 0));
		if (tile != null && (tile instanceof TilePressureTank)) {
			return true;
		}
		return false;
	}

	public int getBurnTime(@Nonnull ItemStack item) {
		return (int) Math.round(TileEntityFurnace.getItemBurnTime(item) * getBurnTimeMultiplier());
	}

	private double getBurnTimeMultiplier() {
		return 1.0;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(water);
		}
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
		}
		return super.getCapability(capability, facing);
	}

	public FluidTank getSteam() {
		return steam;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		}
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public void masterTick() {
		if (!world.isRemote) {
			if (getSteam().getFluidAmount() < getSteam().getCapacity() && water.getFluidAmount() > 0) {
				if (burnTime > 0) {
					// heat at a rate of 10 mb per tick

					FluidStack drain = water.drainInternal(10, true);
					FluidStack fill = new FluidStack(ModRegistry.steam, drain.amount);
					getSteam().fillInternal(fill, true);
					burnTime--;

				} else {
					final ItemStack fuelStack = itemStackHandler.getStackInSlot(0);
					if (fuelStack != null && !fuelStack.isEmpty()) {
						burnTime = getBurnTime(fuelStack);
						if (burnTime > 0) {
							totalBurnTime = burnTime;
							ItemStack containedItem = fuelStack.getItem().getContainerItem(fuelStack.splitStack(1));
							if (!containedItem.isEmpty()) {
								if (fuelStack.isEmpty()) {
									itemStackHandler.setStackInSlot(0, containedItem);
								} else {
									world.spawnEntity(new EntityItem(world, pos.getX() + .5, pos.getY() + .5,
											pos.getZ() + .5, containedItem));
								}
							}
							markDirty();
						}
					}
				}
			}
		}

	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if (tag.hasKey("items")) {
			itemStackHandler.deserializeNBT((NBTTagCompound) tag.getTag("items"));
		}
		if (tag.hasKey("water")) {
			water.readFromNBT(tag.getCompoundTag("water"));
		}
		if (tag.hasKey("steam")) {
			getSteam().readFromNBT(tag.getCompoundTag("steam"));
		}
		burnTime = tag.getInteger("burnTime");
		totalBurnTime = tag.getInteger("totalBurnTime");
	}

	@Override
	public void resetStructure() {
		TileEntity tile = world.getTileEntity(getPos().add(0, 1, 0));
		if (tile != null && (tile instanceof TileMultiBlock)) {
			((TileMultiBlock) tile).reset();
		}
		reset();
	}

	@Override
	public void setupStructure() {
		TileEntity tile = world.getTileEntity(getPos().add(0, 1, 0));
		if (tile != null && (tile instanceof TileMultiBlock)) {
			((TileMultiBlock) tile).setMasterCoords(getPos().getX(), getPos().getY(), getPos().getZ());
			((TileMultiBlock) tile).setHasMaster(true);
			((TileMultiBlock) tile).setIsMaster(false);
		}
		setHasMaster(true);
		setIsMaster(true);
		setMasterCoords(getPos().getX(), getPos().getY(), getPos().getZ());

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag = super.writeToNBT(tag);
		NBTTagCompound waterTag = new NBTTagCompound();
		water.writeToNBT(waterTag);
		tag.setTag("water", waterTag);
		NBTTagCompound steamTag = new NBTTagCompound();
		getSteam().writeToNBT(steamTag);
		tag.setTag("steam", steamTag);
		tag.setTag("items", itemStackHandler.serializeNBT());

		tag.setInteger("burnTime", burnTime);
		tag.setInteger("totalBurnTime", totalBurnTime);
		return tag;
	}
}
