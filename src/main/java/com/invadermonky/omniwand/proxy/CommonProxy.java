package com.invadermonky.omniwand.proxy;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.network.MessageGuiTransform;
import com.invadermonky.omniwand.network.MessageRevertWand;
import com.invadermonky.omniwand.network.MessageWandTransform;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        int i = 0;
        Omniwand.network = NetworkRegistry.INSTANCE.newSimpleChannel(Omniwand.MOD_ID);
        Omniwand.network.registerMessage(MessageGuiTransform.MsgHandler.class, MessageGuiTransform.class, i++, Side.SERVER);
        Omniwand.network.registerMessage(MessageRevertWand.MsgHandler.class, MessageRevertWand.class, i++, Side.SERVER);
        Omniwand.network.registerMessage(MessageWandTransform.MsgHandler.class, MessageWandTransform.class, i++, Side.SERVER);
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public void openWandGui(EntityPlayer player, ItemStack stack) {
    }
}
