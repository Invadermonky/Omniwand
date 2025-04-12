package com.invadermonky.omniwand.handlers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.config.ConfigTags;
import com.invadermonky.omniwand.network.MessageWandTransform;
import com.invadermonky.omniwand.util.ItemHelper;
import com.invadermonky.omniwand.util.WandHelper;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onMouseEvent(MouseEvent event) {
        EntityPlayerSP playerSP = Minecraft.getMinecraft().thePlayer;
        ItemStack heldItem = playerSP.getHeldItem();

        if (WandHelper.isOmniwand(heldItem) && ConfigTags.shouldTransform(playerSP.isSneaking())) {
            ItemStack newStack = heldItem;
            MovingObjectPosition result = playerSP.rayTrace(playerSP.capabilities.isCreativeMode ? 5f : 4.5f, 1.0f);

            if (result != null && result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                Block lookBlock = playerSP.worldObj.getBlock(result.blockX, result.blockY, result.blockZ);
                String lookMod = WandHelper.getModOrAlias(lookBlock);
                if (WandHelper.getAutoMode(newStack)) {
                    newStack = WandHelper.getTransformedStack(heldItem, lookMod, false);
                    WandHelper.setAutoMode(newStack, true);
                }
            }

            if (newStack != heldItem && !ItemHelper.areItemsEqual(newStack, heldItem)) {
                int slot = playerSP.inventory.currentItem;
                playerSP.inventory.setInventorySlotContents(slot, newStack);
                Omniwand.network.sendToServer(new MessageWandTransform(newStack, slot));
            }
        }
    }

    /*
     * TODO: Figure this out
     * @SubscribeEvent
     * public void onPlayerSwing(PlayerInteractEvent.LeftClickEmpty event) {
     * ItemStack stack = event.getItemStack();
     * if (!ConfigHandler.crouchRevert || event.getEntityPlayer().isSneaking()) {
     * if (WandHelper.isOmniwand(stack) && stack.getItem() != Registry.OMNIWAND) {
     * ItemStack newStack = WandHelper.getTransformedStack(stack, Omniwand.MOD_ID, false);
     * Omniwand.network.sendToServer(new MessageWandTransform(newStack, event.getEntityPlayer().inventory.currentItem));
     * }
     * }
     * }
     */
}
