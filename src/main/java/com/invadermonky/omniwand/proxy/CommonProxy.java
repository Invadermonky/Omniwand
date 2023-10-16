package com.invadermonky.omniwand.proxy;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.handlers.TransformHandler;
import com.invadermonky.omniwand.network.MessageGuiTransform;
import com.invadermonky.omniwand.network.MessageRevertWand;
import com.invadermonky.omniwand.network.MessageWandTransform;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(TransformHandler.INSTANCE);

        Omniwand.network = NetworkRegistry.INSTANCE.newSimpleChannel(Omniwand.MOD_ID);
        Omniwand.network.registerMessage(MessageGuiTransform.MsgHandler.class, MessageGuiTransform.class, 0, Side.SERVER);
        Omniwand.network.registerMessage(MessageRevertWand.MsgHandler.class, MessageRevertWand.class, 1, Side.SERVER);
        Omniwand.network.registerMessage(MessageWandTransform.MsgHandler.class, MessageWandTransform.class, 2, Side.SERVER);
    }
    public void init(FMLInitializationEvent event) {}
    public void postInit(FMLPostInitializationEvent event) {}

    public void updateEquippedItem() {}
    public void openWandGui(EntityPlayer player, ItemStack stack) {}
}
