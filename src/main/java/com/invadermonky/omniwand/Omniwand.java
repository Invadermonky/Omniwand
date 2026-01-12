package com.invadermonky.omniwand;

import com.invadermonky.omniwand.config.ConfigHandler;
import com.invadermonky.omniwand.network.NetworkHandler;
import com.invadermonky.omniwand.proxy.CommonProxy;
import com.invadermonky.omniwand.util.LogHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(
        modid = Omniwand.MOD_ID,
        name = Omniwand.MOD_NAME,
        version = Omniwand.MOD_VERSION,
        acceptedMinecraftVersions = Omniwand.MC_VERSION,
        guiFactory = Omniwand.GUI_FACTORY
)
public class Omniwand {
    public static final String MOD_ID = "omniwand";
    public static final String MOD_NAME = "Omniwand";
    public static final String MOD_VERSION = "1.0.1";
    public static final String MC_VERSION = "[1.10.2]";
    public static final String GUI_FACTORY = "com.invadermonky." + MOD_ID + ".client.GuiFactory";

    public static final String ProxyClientClass = "com.invadermonky." + MOD_ID + ".proxy.ClientProxy";
    public static final String ProxyServerClass = "com.invadermonky." + MOD_ID + ".proxy.CommonProxy";

    public static SimpleNetworkWrapper network;

    @Mod.Instance(MOD_ID)
    public static Omniwand INSTANCE;

    @SidedProxy(clientSide = ProxyClientClass, serverSide = ProxyServerClass)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogHelper.info("Starting Omniwand.");
        ConfigHandler.init();
        NetworkHandler.init();
        proxy.preInit(event);
        LogHelper.debug("Finished preInit phase.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        LogHelper.debug("Finished init phase.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        LogHelper.debug("Finished postInit phase.");
    }
}
