package com.invadermonky.omniwand.proxy;

import com.invadermonky.omniwand.handlers.CommonEventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public void openWandGui(EntityPlayer player, ItemStack stack) {
    }
}
