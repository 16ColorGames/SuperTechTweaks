package com.sixteencolorgames.supertechtweaks.tileentities.pipe;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.sixteencolorgames.supertechtweaks.enums.Material;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TilePipe extends TileEntity implements ITickable, IFluidHandler {

	private Material mat;

	private long network;

	protected FluidTank tank = new FluidTank(5000);

	public TilePipe() {
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return tank.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return tank.fill(resource, doFill);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return (T) this;
		}
		return super.getCapability(capability, facing);
	}

	public Material getMaterial() {
		return mat;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return tank.getTankProperties();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("TilePipe")) {
			tank.readFromNBT(compound.getCompoundTag("TilePipe"));
			mat = Material.REGISTRY
					.getValue(new ResourceLocation(compound.getCompoundTag("TilePipe").getString("material")));
			tank.setCapacity(mat.getFluidCapacity());
		}
	}

	public void setMaterial(Material material) {
		mat = material;
		tank.setCapacity(mat.getFluidCapacity());
		System.out.println(mat.getName());
	}

	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		if (tank.getFluidAmount() == 0 || tank.getFluid() == null) {
			return;
		}
		ArrayList<IFluidHandler> acceptors = new ArrayList<IFluidHandler>();

		for (EnumFacing face : EnumFacing.VALUES) {
			BlockPos offPos = getPos().offset(face);
			TileEntity tile = getWorld().getTileEntity(offPos);

			if (tile == null) {
				continue;
			} else if (tile instanceof TilePipe) {
				TilePipe pipeOther = (TilePipe) tile;
				if (tank.getFluidAmount() > pipeOther.tank.getFluidAmount() && (pipeOther.tank.getFluid() == null
						|| tank.getFluid().getFluid().equals(pipeOther.tank.getFluid().getFluid()))) {
					acceptors.add(pipeOther);
				}
			} else if (tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, face.getOpposite())) {
				IFluidHandler fluidTile = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
						face.getOpposite());
				if (fluidTile != null && fluidTile.fill(new FluidStack(tank.getFluid().getFluid(), 1), false) != 0) {
					acceptors.add(fluidTile);
				}

			}
		}

		if (acceptors.size() > 0) {
			int drain = Math.min(tank.getFluidAmount(), mat.getTransferRate());
			int fluidShare = (int) Math.ceil(((double) drain) / ((double) acceptors.size()));
			int remainingFluid = drain;

			if (fluidShare > 0) {
				for (IFluidHandler tile : acceptors) {
					// Push energy to connected tile acceptors
					int move = tile.fill(new FluidStack(tank.getFluid(), Math.min(fluidShare, remainingFluid)), true);
					if (move > 0) {
						remainingFluid -= move;
					}
				}
				this.drain(drain - remainingFluid, true);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagCompound data = new NBTTagCompound();
		tank.writeToNBT(data);
		data.setString("material", mat.getRegistryName().toString());
		compound.setTag("TilePipe", data);

		return compound;
	}

	public void setNetwork(long net) {
		network = net;
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}
}
