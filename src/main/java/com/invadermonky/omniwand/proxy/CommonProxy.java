package com.invadermonky.omniwand.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.handlers.CommonEventHandler;
import com.invadermonky.omniwand.network.MessageGuiTransform;
import com.invadermonky.omniwand.network.MessageRevertWand;
import com.invadermonky.omniwand.network.MessageWandTransform;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        int i = 0;
        Omniwand.network = NetworkRegistry.INSTANCE.newSimpleChannel(Omniwand.MOD_ID);
        Omniwand.network
            .registerMessage(MessageGuiTransform.MsgHandler.class, MessageGuiTransform.class, i++, Side.SERVER);
        Omniwand.network.registerMessage(MessageRevertWand.MsgHandler.class, MessageRevertWand.class, i++, Side.SERVER);
        Omniwand.network
            .registerMessage(MessageWandTransform.MsgHandler.class, MessageWandTransform.class, i++, Side.SERVER);

        MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
    }

    public void init(FMLInitializationEvent event) {}

    public void postInit(FMLPostInitializationEvent event) {}

    public void openWandGui(EntityPlayer player, ItemStack stack) {}
}
