package com.sixteencolorgames.supertechtweaks.tileentities.boiler;

import javax.annotation.Nonnull;

import com.sixteencolorgames.supertechtweaks.ModRegistry;
import com.sixteencolorgames.supertechtweaks.enums.Material;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileBoiler extends TileMultiBlockController {

	public static final int SIZE = 1;

	public static int calcBoilRate(Material mat) {
		return (int) (2 * Math.log(mat.getConductivity()));
	}

	protected FluidTank water = new FluidTank(5000) {
		@Override
		public boolean canFillFluidType(FluidStack fluid) {
			return fluid.getFluid().equals(FluidRegistry.WATER);
		}
	};
	protected FluidTank steam = new FluidTank(0);
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

	private Material material;

	private int boilRate = 10;

	public TileBoiler() {
		steam.setCanFill(false);
		water.setCanDrain(false);
		steam.setFluid(new FluidStack(ModRegistry.steam, 0));
		water.setFluid(new FluidStack(FluidRegistry.WATER, 0));
	}

	private void attemptSteamPush() {
		for (EnumFacing face : EnumFacing.VALUES) {
			BlockPos offset = pos.add(0, 1, 0).offset(face);
			TileEntity tile = world.getTileEntity(offset);
			if (tile != null
					&& tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, face.getOpposite())) {
				int attempt = Math.min(getMaxExtract(), steam.getFluidAmount());
				IFluidHandler otherTank = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
						face.getOpposite());
				int receiveSteam = otherTank.fill(new FluidStack(ModRegistry.steam, attempt), true);
				steam.drain(receiveSteam, true);
			}
		}

	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		// If we are too far away from this tile entity you cannot use it
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	@Override
	public boolean checkMultiBlockForm() {
		TileEntity tile = world.getTileEntity(getPos().add(0, 1, 0));
		if (tile != null && (tile instanceof TilePressureTank) && ((TilePressureTank) tile).getMaterial() != null) {
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

	public int getField(int id) {
		switch (id) {
		case 0:
			return burnTime;
		case 1:
			return totalBurnTime;
		case 2:
			return water.getCapacity();
		case 3:
			return water.getFluidAmount();
		case 4:
			return steam.getCapacity();
		case 5:
			return steam.getFluidAmount();
		default:
			return 0;
		}
	}

	public Material getMaterial() {
		return material;
	}

	private int getMaxExtract() {
		return (int) Math.max(50,
				((double) steam.getFluidAmount()) / ((double) steam.getCapacity()) * (steam.getCapacity() / 10));
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

					FluidStack drain = water.drainInternal(boilRate, true);
					FluidStack fill = new FluidStack(ModRegistry.steam, drain.amount);
					getSteam().fillInternal(fill, true);
					burnTime--;

				} else {
					final ItemStack fuelStack = itemStackHandler.getStackInSlot(0);
					if (fuelStack != null && !fuelStack.isEmpty()) {
						burnTime = getBurnTime(fuelStack) / 5;
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
			attemptSteamPush();
		}

	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if (tag.hasKey("TileBoiler")) {
			water.readFromNBT(tag.getCompoundTag("TileBoiler").getCompoundTag("water"));
			steam.readFromNBT(tag.getCompoundTag("TileBoiler").getCompoundTag("steam"));
			itemStackHandler.deserializeNBT((NBTTagCompound) tag.getCompoundTag("TileBoiler").getTag("items"));
			water.readFromNBT(tag.getCompoundTag("TileBoiler").getCompoundTag("water"));
			burnTime = tag.getInteger("burnTime");
			totalBurnTime = tag.getInteger("totalBurnTime");
			setMaterial(Material.REGISTRY
					.getValue(new ResourceLocation(tag.getCompoundTag("TileBoiler").getString("material"))));
		}
	}

	@Override
	public void resetStructure() {
		TileEntity tile = world.getTileEntity(getPos().add(0, 1, 0));
		if (tile != null && (tile instanceof TileMultiBlock)) {
			((TileMultiBlock) tile).reset();
		}
		reset();
	}

	public void setField(int id, int value) {
		switch (id) {
		case 0:
			burnTime = value;
			break;
		case 1:
			totalBurnTime = value;
			break;
		case 2:
			water.setCapacity(value);
			break;
		case 3:
			water.setFluid(new FluidStack(FluidRegistry.WATER, value));
			break;
		case 4:
			steam.setCapacity(value);
			break;
		case 5:
			water.setFluid(new FluidStack(ModRegistry.steam, value));
			break;
		}
	}

	public void setMaterial(Material material) {
		this.material = material;
		water.setCapacity(material.getFluidCapacity());
		boilRate = calcBoilRate(material);
	}

	@Override
	public void setupStructure() {
		TileEntity tile = world.getTileEntity(getPos().add(0, 1, 0));
		if (tile != null && (tile instanceof TilePressureTank)) {
			TilePressureTank tank = ((TilePressureTank) tile);
			tank.setIsMaster(false);
			tank.setMasterCoords(getPos().getX(), getPos().getY(), getPos().getZ());
			Material tankMat = tank.getMaterial();
			water.setCapacity(material.getFluidCapacity());
			steam.setCapacity(tankMat.getFluidCapacity());
		}
		setIsMaster(true);
		setMasterCoords(getPos().getX(), getPos().getY(), getPos().getZ());

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag = super.writeToNBT(tag);
		NBTTagCompound data = new NBTTagCompound();
		NBTTagCompound waterTag = new NBTTagCompound();
		water.writeToNBT(waterTag);
		data.setTag("water", waterTag);
		NBTTagCompound steamTag = new NBTTagCompound();
		steam.writeToNBT(steamTag);
		data.setTag("steam", steamTag);
		data.setTag("items", itemStackHandler.serializeNBT());
		data.setInteger("burnTime", burnTime);
		data.setInteger("totalBurnTime", totalBurnTime);
		data.setString("material", Material.REGISTRY.getKey(material).toString());
		tag.setTag("TileBoiler", data);
		return tag;
	}
}
