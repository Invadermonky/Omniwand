package com.invadermonky.omniwand.proxy;

import com.invadermonky.omniwand.registry.Registry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
    }

    public void init(FMLInitializationEvent event) {
        Registry.registerRecipes();
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public void openWandGui(EntityPlayer player, ItemStack stack) {
    }
}
