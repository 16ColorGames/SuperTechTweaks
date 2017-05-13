package com.sixteencolorgames.supertechtweaks.blocks;

import com.sixteencolorgames.supertechtweaks.blocks.properties.PropertyByte;
import com.sixteencolorgames.supertechtweaks.blocks.properties.PropertyOres;
import java.util.List;
import java.util.Random;

import com.sixteencolorgames.supertechtweaks.compat.waila.WailaInfoProvider;
import com.sixteencolorgames.supertechtweaks.enums.Material;
import com.sixteencolorgames.supertechtweaks.render.OreBakedModel;
import com.sixteencolorgames.supertechtweaks.world.ExampleWorldSavedData;
import java.util.ArrayList;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The ore block for world generation. Can hold up to 7 ores.
 *
 * @author oa10712
 *
 */
public class BlockOre extends BlockBase implements WailaInfoProvider {

    public static final PropertyByte BASE = new PropertyByte("base");
    public static final PropertyOres ORES = new PropertyOres("ores");

    public BlockOre() {
        super(net.minecraft.block.material.Material.ROCK, "superore");
        this.setHardness(3.0f);
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

    /**
     * This returns a complete list of items dropped from this block.
     *
     * @param world The current world
     * @param pos Block position in world
     * @param state Current state
     * @param fortune Breakers fortune level
     * @return A ArrayList containing all items this block drops
     */
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

        List<ItemStack> ret = new ArrayList<>();
        int[] ores = ExampleWorldSavedData.get(Minecraft.getMinecraft().theWorld).getOres(pos);
        int base = ExampleWorldSavedData.get(Minecraft.getMinecraft().theWorld).getBase(pos);
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        for (int i = 0; i < 7; i++) {
            if (ores[i] != 0) {
                Material material = Material.materials.get(ores[i]);
                ret.add(material.getDrops((byte) base));
                for (int j = 0; j < fortune; j++) {
                    if (rand.nextDouble() < .25) {
                        ret.add(material.getDrops((byte) base));
                    }
                }
            }
        }

        return ret;
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
            if (player.isCreative()) {//If the player is in creative...
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());//...remove the block itself
                return true;
            }
            boolean metalLeft = false;
            int[] ores = ExampleWorldSavedData.get(worldIn).getOres(pos);
            int base = ExampleWorldSavedData.get(worldIn).getBase(pos);
            int tagCount;
            tagCount = player.getHeldItemMainhand().getEnchantmentTagList() != null ? player.getHeldItemMainhand().getEnchantmentTagList().tagCount() : 0;
            int fortune = 0;
            for (int i = 0; i < tagCount; i++) {
                NBTTagCompound compound = (NBTTagCompound) player.getHeldItemMainhand().getEnchantmentTagList().get(i);
                if (compound.getShort("id") == 35) {//if the enchantment is Fortune
                    fortune = compound.getShort("lvl");
                }
            }
            for (int i = 0; i < 7; i++) {
                if (ores[i] != 0) {
                    Material material = Material.materials.get(ores[i]);
                    if (material.getHarvest() <= player.getHeldItemMainhand().getItem()
                            .getHarvestLevel(player.getHeldItemMainhand(), "pickaxe")) {
                        worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY(),
                                pos.getZ() + 0.5, material.getDrops((byte) base)));//this is what actually drops the item. Note this calls the getDrops function, which can be overridden
                        for (int j = 0; j < fortune; j++) {
                            if (RANDOM.nextDouble() < .25) {//25% chance of an extra drop
                                worldIn.spawnEntityInWorld(new EntityItem(worldIn, pos.getX() + 0.5, pos.getY(),
                                        pos.getZ() + 0.5, material.getDrops((byte) base)));//this is what actually drops the item. Note this calls the getDrops function, which can be overridden
                            }
                        }
                        ores[i] = 0;
                    } else {
                        metalLeft = true;
                    }
                }
            }
            ExampleWorldSavedData.get(worldIn).setOres(pos, ores);
            willHarvest = metalLeft;
            if (!metalLeft) {//When we have removel all of the ore from the block...
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());//...remove the block itself
            }
            worldIn.notifyBlockUpdate(pos, state, state, 2);
            return !metalLeft;
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        int[] ores = ExampleWorldSavedData.get(accessor.getWorld()).getOres(accessor.getPosition());
        int base = ExampleWorldSavedData.get(accessor.getWorld()).getBase(accessor.getPosition());
        currenttip.add("Base: " + base);
        EntityPlayer player = accessor.getPlayer();
        int harvest = player.getHeldItemMainhand() != null
                ? player.getHeldItemMainhand().getItem().getHarvestLevel(player.getHeldItemMainhand(), "pickaxe") : -1;
        for (int metal : ores) {
            if (metal != 0) {
                Material ore = Material.materials.get(metal);
                TextFormatting color = TextFormatting.RED;
                if (harvest >= ore.getHarvest()) {
                    color = TextFormatting.GREEN;
                }
                currenttip.add(color + ore.getName() + "(" + ore.getHarvest() + ")");
            }
        }

        return currenttip;
    }

    @Override
    public void registerItemModel(Item item) {
        // void since we shouldn't have this in inventory
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState iBlockState) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        IProperty[] listedProperties = new IProperty[0]; // no listed properties
        IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[]{BASE, ORES};
        return new ExtendedBlockState(this, listedProperties, unlistedProperties);
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;
        int[] ores = ExampleWorldSavedData.get(Minecraft.getMinecraft().theWorld).getOres(pos);
        int base = ExampleWorldSavedData.get(Minecraft.getMinecraft().theWorld).getBase(pos);
        ArrayList<Integer> oreList = new ArrayList();
        for (int i : ores) {
            if (i != 0) {
                oreList.add(i);
            }
        }
        return extendedBlockState
                .withProperty(BASE, (byte) base)
                .withProperty(ORES, oreList.toArray(new Integer[0]));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        // To make sure that our baked model model is chosen for all states we use this custom state mapper:
        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return OreBakedModel.BAKED_MODEL;
            }
        };
        ModelLoader.setCustomStateMapper(this, ignoreState);
    }
}
