package com.invadermonky.omniwand.handlers;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.network.MessageWandTransform;
import com.invadermonky.omniwand.util.RayTracer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MouseEventOW {
    public static final MouseEventOW INSTANCE = new MouseEventOW();

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onMouseEvent(MouseEvent event) {
        EntityPlayerSP playerSP = Minecraft.getMinecraft().player;
        ItemStack heldItem = playerSP.getHeldItem(ConfigHandler.getConfiguredHand());

        if(TransformHandler.isOmniwand(heldItem)) {
            ItemStack newStack = heldItem;
            RayTraceResult result = RayTracer.retrace(playerSP);
            String modLook = "";

            //Look transform
            if(ConfigHandler.enableTransform && result != null) {
                IBlockState state = playerSP.world.getBlockState(result.getBlockPos());
                modLook = TransformHandler.getModFromState(state);
                if (TransformHandler.autoMode && event.getDwheel() == 0) {
                    newStack = TransformHandler.getTransformStackForMod(heldItem, modLook);
                }
            }

            //Updating item
            if(newStack != heldItem && !ItemStack.areItemsEqual(newStack, heldItem)) {
                playerSP.inventory.setInventorySlotContents(ConfigHandler.offhandTransform ? playerSP.inventory.getSizeInventory() - 1 : playerSP.inventory.currentItem, newStack);
                Omniwand.network.sendToServer(new MessageWandTransform(newStack, playerSP.inventory.currentItem));
                Omniwand.proxy.updateEquippedItem();
            }
        }
    }
}
