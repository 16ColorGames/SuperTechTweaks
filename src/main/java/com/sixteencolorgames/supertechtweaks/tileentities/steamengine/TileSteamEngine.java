package com.sixteencolorgames.supertechtweaks.tileentities.steamengine;

import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlock;
import com.sixteencolorgames.supertechtweaks.tileentities.TileMultiBlockController;
import com.sixteencolorgames.supertechtweaks.tileentities.pressuretank.TilePressureTank;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileSteamEngine extends TileMultiBlockController implements IEnergyStorage {
	public FluidTank steamInternal = new FluidTank(10000);
	protected int energy;
	protected int capacity;
	protected int maxExtract;
	private EnumFacing facing = EnumFacing.DOWN;

	public TileSteamEngine() {
		energy = 0;
		capacity = 10000;
		maxExtract = 1000;
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return false;
	}

	@Override
	public boolean checkMultiBlockForm() {
		for (EnumFacing face : EnumFacing.HORIZONTALS) {
			TileEntity behind1 = world.getTileEntity(getPos().offset(face, 1));
			TileEntity behind2 = world.getTileEntity(getPos().offset(face, 2));
			TileEntity behind3 = world.getTileEntity(getPos().offset(face, 3));
			if (!(behind1 instanceof TilePressureTank)) {
				continue;
			}
			if (!(behind2 instanceof TilePressureTank)) {
				continue;
			}
			if (!(behind3 instanceof TileMultiBlock)) {
				continue;
			}
			for (int i = -1; i < 2; i++) {
				for (int y = -1; y < 2; y++) {
					for (int k = 0; k < 4; k++) {
						TileEntity tile;
						switch (face) {
						case EAST:
							tile = world.getTileEntity(getPos().add(k, y, i));
							break;
						case WEST:
							tile = world.getTileEntity(getPos().add(-k, y, i));
							break;
						case SOUTH:
							tile = world.getTileEntity(getPos().add(i, y, k));
							break;
						default:
							tile = world.getTileEntity(getPos().add(i, y, -k));
							break;
						}
						if (tile == null) {
							return false;
						}
						if (!(tile instanceof TileMultiBlock)) {
							return false;
						}
					}
				}
			}
			facing = face;
			return true;
		}
		return false;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int energySent = Math.min(energy, Math.min(this.maxExtract, maxExtract));
		if (!simulate) {
			energy -= energySent;
		}
		return energySent;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY && hasMaster()) {
			return (T) this;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public int getEnergyStored() {
		return energy;
	}

	public EnumFacing getFacing() {
		return facing;
	}

	@Override
	public int getMaxEnergyStored() {
		return capacity;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY && hasMaster()) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public void masterTick() {
		if (steamInternal.getFluidAmount() > 0 && energy < capacity) {
			FluidStack drain = steamInternal.drainInternal(20, true);
			energy += Math.min(drain.amount * 5, (capacity - energy));
			double motionX = world.rand.nextGaussian() * 0.02D;
			double motionY = world.rand.nextGaussian() * 0.02D + .05;
			double motionZ = world.rand.nextGaussian() * 0.02D;
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, getPos().getX(), getPos().getY() + 1, getPos().getZ(),
					motionX, motionY, motionZ);
		}

	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		maxExtract = compound.getInteger("maxExport");
		energy = compound.getInteger("energy");
		capacity = compound.getInteger("capacity");
		facing = EnumFacing.values()[compound.getInteger("facing")];
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public void resetStructure() {
		TileEntity behind1 = world.getTileEntity(getPos().offset(facing));
		TileEntity behind2 = world.getTileEntity(getPos().offset(facing, 2));
		TileEntity behind3 = world.getTileEntity(getPos().offset(facing, 3));

		if ((behind1 instanceof TilePressureTank)) {
			((TileMultiBlock) behind1).reset();
		}
		if ((behind2 instanceof TilePressureTank)) {
			((TileMultiBlock) behind2).reset();
		}
		if ((behind3 instanceof TileMultiBlock)) {
			((TileMultiBlock) behind3).reset();
		}
		for (int i = -1; i < 2; i++) {
			for (int y = -1; y < 2; y++) {
				for (int k = 0; k < 4; k++) {
					TileEntity tile;
					switch (facing) {
					case EAST:
						tile = world.getTileEntity(getPos().add(k, y, i));
						break;
					case WEST:
						tile = world.getTileEntity(getPos().add(-k, y, i));
						break;
					case SOUTH:
						tile = world.getTileEntity(getPos().add(i, y, k));
						break;
					default:
						tile = world.getTileEntity(getPos().add(i, y, -k));
						break;
					}
					if ((tile instanceof TileMultiBlock)) {
						TileMultiBlock te = (TileMultiBlock) tile;
						te.reset();
					}
				}
			}
		}
		facing = EnumFacing.DOWN;
	}

	@Override
	public void setupStructure() {
		facingLoop: for (EnumFacing face : EnumFacing.HORIZONTALS) {
			int steamCap = 0;
			TileEntity behind1 = world.getTileEntity(getPos().offset(face));
			TileEntity behind2 = world.getTileEntity(getPos().offset(face, 2));
			TileEntity behind3 = world.getTileEntity(getPos().offset(face, 3));

			if (!(behind1 instanceof TilePressureTank)) {
				continue;
			}
			if (!(behind2 instanceof TilePressureTank)) {
				continue;
			}
			if (!(behind3 instanceof TileMultiBlock)) {
				continue;
			}
			NonNullList<TileMultiBlock> contained = NonNullList.create();
			for (int i = -1; i < 2; i++) {
				for (int y = -1; y < 2; y++) {
					for (int k = 0; k < 4; k++) {
						TileEntity tile;
						switch (face) {
						case EAST:
							tile = world.getTileEntity(getPos().add(k, y, i));
							break;
						case WEST:
							tile = world.getTileEntity(getPos().add(-k, y, i));
							break;
						case SOUTH:
							tile = world.getTileEntity(getPos().add(i, y, k));
							break;
						default:
							tile = world.getTileEntity(getPos().add(i, y, -k));
							break;
						}
						if (!(tile instanceof TileMultiBlock)) {
							contained.clear();
							continue facingLoop;
						}
						contained.add((TileMultiBlock) tile);

					}
				}
			}
			contained.add((TileMultiBlock) behind1);
			contained.add((TileMultiBlock) behind2);
			contained.add((TileMultiBlock) behind3);
			contained.forEach((multi) -> {
				multi.setIsMaster(false);
				multi.setMasterCoords(getPos().getX(), getPos().getY(), getPos().getZ());
			});
			steamCap += TilePressureTank.getMaxCapacity(((TilePressureTank) behind1).getMaterial());
			steamCap += TilePressureTank.getMaxCapacity(((TilePressureTank) behind2).getMaterial());
			steamInternal.setCapacity(steamCap);
			setIsMaster(true);
			setMasterCoords(getPos().getX(), getPos().getY(), getPos().getZ());
			System.out.println("Structure formed in " + face);
			facing = face;
			return;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("energy", energy);
		compound.setInteger("capacity", capacity);
		compound.setInteger("maxExport", maxExtract);
		compound.setInteger("facing", facing.ordinal());
		return compound;
	}

}
