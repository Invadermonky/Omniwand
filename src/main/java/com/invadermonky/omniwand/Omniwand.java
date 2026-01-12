package com.invadermonky.omniwand;

import com.invadermonky.omniwand.command.CommandReloadConfig;
import com.invadermonky.omniwand.config.ConfigHandler;
import com.invadermonky.omniwand.network.PacketHandler;
import com.invadermonky.omniwand.proxy.CommonProxy;
import com.invadermonky.omniwand.registry.Registry;
import com.invadermonky.omniwand.util.LogHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = Omniwand.MOD_ID, name = Omniwand.MOD_NAME, version = Omniwand.MOD_VERSION, acceptedMinecraftVersions = Omniwand.MC_VERSION)
@NetworkMod(clientSideRequired = true, channels = {Omniwand.MOD_ID}, packetHandler = PacketHandler.class)
public class Omniwand {
    public static final String MOD_ID = "omniwand";
    public static final String MOD_NAME = "Omniwand";
    public static final String MOD_VERSION = "1.0.1";
    public static final String MC_VERSION = "[1.6.4]";

    public static final String ProxyClientClass = "com.invadermonky." + MOD_ID + ".proxy.ClientProxy";
    public static final String ProxyServerClass = "com.invadermonky." + MOD_ID + ".proxy.CommonProxy";

    @Mod.Instance(MOD_ID)
    public static Omniwand INSTANCE;

    @SidedProxy(clientSide = ProxyClientClass, serverSide = ProxyServerClass)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogHelper.info("Starting Omniwand.");
        ConfigHandler.init();
        PacketHandler.init();
        proxy.preInit(event);
        Registry.registerItems();
        LogHelper.debug("Finished preInit phase.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        Registry.registerRecipes();
        LogHelper.debug("Finished init phase.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        LogHelper.debug("Finished postInit phase.");
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandReloadConfig());
    }

}
