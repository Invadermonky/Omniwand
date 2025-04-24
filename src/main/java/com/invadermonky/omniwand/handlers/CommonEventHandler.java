package com.invadermonky.omniwand.handlers;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.registry.Registry;
import com.invadermonky.omniwand.util.WandHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

public class CommonEventHandler {
    @ForgeSubscribe
    public void onItemBroken(PlayerDestroyItemEvent event) {
        EntityPlayer player = event.entityPlayer;
        if(WandHelper.isOmniwand(event.original)) {
            ItemStack wandStack = WandHelper.removeItemFromWand(event.original, true, null);
            if (player != null && !player.inventory.addItemStackToInventory(wandStack)) {
                player.entityDropItem(wandStack, player.eyeHeight / 2.0f);
            }
        }
    }

    @ForgeSubscribe
    public void onItemDropped(ItemTossEvent event) {
        EntityPlayer player = event.player;
        EntityItem entityItem = event.entityItem;
        ItemStack stack = entityItem.getEntityItem();
        if (WandHelper.isOmniwand(stack) && stack.getItem() != Registry.OMNIWAND) {
            if (player.isSneaking()) {
                ItemStack wandStack = WandHelper.removeItemFromWand(stack, false, entityItem);
                player.inventory.setInventorySlotContents(player.inventory.currentItem, wandStack);
            } else {
                ItemStack wandStack = WandHelper.getTransformedStack(stack, Omniwand.MOD_ID, false);
                player.inventory.setInventorySlotContents(player.inventory.currentItem, wandStack);
                event.setCanceled(true);
            }
        }
    }

}
