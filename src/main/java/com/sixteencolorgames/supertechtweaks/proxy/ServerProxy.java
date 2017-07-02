package com.sixteencolorgames.supertechtweaks.proxy;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Serverside only functions
 *
 * @author oa10712
 *
 */
public class ServerProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        DimensionManager.init();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }

    @Override
    public World getWorld(IBlockAccess world) {
        if (world instanceof World) {
            return (World) world;
        }
        return null;
    }
}
