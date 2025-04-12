package com.invadermonky.omniwand.handlers;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

import com.invadermonky.omniwand.registry.Registry;
import com.invadermonky.omniwand.util.WandHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CommonEventHandler {

    @SubscribeEvent
    public void onItemBroken(PlayerDestroyItemEvent event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack wandStack = WandHelper.removeItemFromWand(event.original, true, stack -> {});
        if (player != null && !player.inventory.addItemStackToInventory(wandStack)) {
            player.entityDropItem(wandStack, player.eyeHeight / 2.0f);
        }
    }

    @SubscribeEvent
    public void onItemDropped(ItemTossEvent event) {
        EntityPlayer player = event.player;
        if (player.isSneaking()) {
            EntityItem entityItem = event.entityItem;
            ItemStack stack = entityItem.getEntityItem();
            if (WandHelper.isOmniwand(stack) && stack.getItem() != Registry.OMNIWAND) {
                ItemStack wandStack = WandHelper.removeItemFromWand(stack, false, entityItem::setEntityItemStack);
                player.inventory.setInventorySlotContents(player.inventory.currentItem, wandStack);
            }
        }
    }
}
