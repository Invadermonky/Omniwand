package com.invadermonky.omniwand.proxy;

import com.invadermonky.omniwand.client.GuiWand;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void openWandGui(EntityPlayer player, ItemStack stack) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == player)
            mc.displayGuiScreen(new GuiWand(stack));
    }
}
