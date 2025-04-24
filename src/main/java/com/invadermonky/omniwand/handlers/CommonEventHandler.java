package com.invadermonky.omniwand.handlers;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.registry.Registry;
import com.invadermonky.omniwand.util.WandHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Omniwand.MOD_ID)
public class CommonEventHandler {
    @SubscribeEvent
    public static void onItemBroken(PlayerDestroyItemEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if(WandHelper.isOmniwand(event.getOriginal())) {
            ItemStack wandStack = WandHelper.removeItemFromWand(event.getOriginal(), true, stack -> {
            });
            if (player != null && !player.addItemStackToInventory(wandStack)) {
                player.dropItem(wandStack, true);
            }
        }
    }

    @SubscribeEvent
    public static void onItemDropped(ItemTossEvent event) {
        EntityPlayer player = event.getPlayer();
        if (player.isSneaking()) {
            EntityItem entityItem = event.getEntityItem();
            ItemStack stack = entityItem.getItem();
            if (WandHelper.isOmniwand(stack) && stack.getItem() != Registry.OMNIWAND) {
                ItemStack wandStack = WandHelper.removeItemFromWand(stack, false, entityItem::setItem);
                player.inventory.setInventorySlotContents(player.inventory.currentItem, wandStack);
            }
        }
    }
}
