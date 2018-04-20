package com.sixteencolorgames.supertechtweaks.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sixteencolorgames.supertechtweaks.SuperTechTweaksMod;
import com.sixteencolorgames.supertechtweaks.blocks.properties.PropertyBase;
import com.sixteencolorgames.supertechtweaks.blocks.properties.PropertyOres;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.network.PacketHandler;
import com.sixteencolorgames.supertechtweaks.network.UpdateOresPacket;
import com.sixteencolorgames.supertechtweaks.world.OreSavedData;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The ore block for world generation. Can hold many ores
 *
 * @author oa10712
 *
 */
public class BlockOre extends BlockBase {

	public static final PropertyBase BASE = new PropertyBase("base");
	public static final PropertyOres ORES = new PropertyOres("ores");

	public BlockOre() {
		super(net.minecraft.block.material.Material.ROCK, "superore");
		this.setHardness(3.0f);
	}

	// @SideOnly(Side.CLIENT)
	// public void initModel() {
	// // To make sure that our baked model model is chosen for all states we
	// use this custom state mapper:
	// StateMapperBase ignoreState = new StateMapperBase() {
	// @Override
	// protected ModelResourceLocation getModelResourceLocation(IBlockState
	// iBlockState) {
	// return OreBakedModel.BAKED_MODEL;
	// }
	// };
	// ModelLoader.setCustomStateMapper(this, ignoreState);
	// }
	@Override
	@Deprecated // Forge: State sensitive version
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		IProperty[] listedProperties = new IProperty[0]; // no listed properties
		IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { BASE, ORES };
		return new ExtendedBlockState(this, listedProperties, unlistedProperties);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	/**
	 * This returns a complete list of items dropped from this block.
	 *
	 * @param world
	 *            The current world
	 * @param pos
	 *            Block position in world
	 * @param state
	 *            Current state
	 * @param fortune
	 *            Breakers fortune level
	 * @return A ArrayList containing all items this block drops
	 */
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		World worldObject = SuperTechTweaksMod.proxy.getWorld(world);
		List<ItemStack> ret = new ArrayList<>();
		ResourceLocation[] ores = OreSavedData.get(worldObject).getOres(pos);
		ResourceLocation base = OreSavedData.get(worldObject).getBase(pos);
		byte type = 0;
		if (base.getResourcePath().contains("nether")) {
			type = -1;
		} else if (base.getResourcePath().contains("end")) {
			type = 1;
		}
		Random rand = worldObject.rand;
		for (int i = 0; i < ores.length; i++) {
			Material material = GameRegistry.findRegistry(Material.class).getValue(ores[i]);
			ret.add(material.getDrops(type));
			for (int j = 0; j < fortune; j++) {
				if (rand.nextDouble() < .25) {
					ret.add(material.getDrops(type));
				}
			}
		}

		return ret;
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;

		ResourceLocation[] ores = OreSavedData.get(SuperTechTweaksMod.proxy.getWorld(world)).getOres(pos);
		ResourceLocation base = OreSavedData.get(SuperTechTweaksMod.proxy.getWorld(world)).getBase(pos);
		ArrayList<ResourceLocation> oreList = new ArrayList();
		for (ResourceLocation i : ores) {
			oreList.add(i);
		}
		return extendedBlockState.withProperty(BASE, base).withProperty(ORES, oreList.toArray(new ResourceLocation[0]));
	}

	/**
	 * Ensures that this block does not drop any items, this is now handled in
	 * removedByPlayer.
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

	@Override
	public EnumBlockRenderType getRenderType(IBlockState iBlockState) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void registerItemModel(Item item) {
		// void since we shouldn't have this in inventory
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
			if (player.isCreative()) {// If the player is in creative...
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
				return true;
			}
			boolean metalLeft = false;
			ResourceLocation[] ores = OreSavedData.get(worldIn).getOres(pos);
			ResourceLocation base = OreSavedData.get(worldIn).getBase(pos);
			int tagCount;
			tagCount = player.getHeldItemMainhand().getEnchantmentTagList() != null
					? player.getHeldItemMainhand().getEnchantmentTagList().tagCount() : 0;
			int fortune = 0;
			for (int i = 0; i < tagCount; i++) {
				NBTTagCompound compound = (NBTTagCompound) player.getHeldItemMainhand().getEnchantmentTagList().get(i);
				if (compound.getShort("id") == 35) {// if the enchantment is
													// Fortune
					fortune = compound.getShort("lvl");
				}
			}
			byte type = 0;
			if (base.getResourcePath().contains("nether")) {
				type = -1;
			} else if (base.getResourcePath().contains("end")) {
				type = 1;
			}
			for (int i = 0; i < ores.length; i++) {
				Material material = GameRegistry.findRegistry(Material.class).getValue(ores[i]);
				if (material.getHarvest() <= player.getHeldItemMainhand().getItem()
						.getHarvestLevel(player.getHeldItemMainhand(), "pickaxe", player, state)) {
					worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
							material.getDrops(type)));
					for (int j = 0; j < fortune; j++) {
						if (RANDOM.nextDouble() < .25) {
							worldIn.spawnEntity(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
									material.getDrops(type)));
						}
					}
					ores[i] = null;
				} else {
					metalLeft = true;
				}

			}
			ores = removeNulls(ores);
			OreSavedData.get(worldIn).setOres(pos, ores);
			willHarvest = metalLeft;
			if (!metalLeft) {// When we have removel all of the ore from the
								// block...
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());// ...remove
																			// the
																			// block
																			// itself
			}
			worldIn.notifyBlockUpdate(pos, state, state, 2);

			UpdateOresPacket packet = new UpdateOresPacket(OreSavedData.get(player.world), pos);
			PacketHandler.INSTANCE.sendTo(packet, (EntityPlayerMP) player);

			return !metalLeft;
		}
		return true;
	}

	private ResourceLocation[] removeNulls(ResourceLocation[] ores) {
		ArrayList<ResourceLocation> temp = new ArrayList();
		for (int i = 0; i < ores.length; i++) {
			if (ores[i] != null) {
				temp.add(ores[i]);
			}
		}
		return temp.toArray(new ResourceLocation[0]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		return true;
	}
}
