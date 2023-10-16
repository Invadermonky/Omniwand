package com.invadermonky.omniwand.proxy;

import com.invadermonky.omniwand.client.GuiWand;
import com.invadermonky.omniwand.handlers.ConfigHandler;
import com.invadermonky.omniwand.handlers.MouseEventOW;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        MinecraftForge.EVENT_BUS.register(MouseEventOW.INSTANCE);
    }

    @Override
    public void updateEquippedItem() {
        Minecraft.getMinecraft().entityRenderer.itemRenderer.resetEquippedProgress(ConfigHandler.getConfiguredHand());
    }

    @Override
    public void openWandGui(EntityPlayer player, ItemStack stack) {
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.player == player)
            mc.displayGuiScreen(new GuiWand(stack));
    }
}
