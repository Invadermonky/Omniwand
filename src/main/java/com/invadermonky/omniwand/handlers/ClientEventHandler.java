package com.invadermonky.omniwand.handlers;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.config.ConfigHandler;
import com.invadermonky.omniwand.config.ConfigTags;
import com.invadermonky.omniwand.network.NetworkHandler;
import com.invadermonky.omniwand.network.messages.MessageWandTransform;
import com.invadermonky.omniwand.registry.Registry;
import com.invadermonky.omniwand.util.WandHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Omniwand.MOD_ID)
public class ClientEventHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onMouseEvent(MouseEvent event) {
        EntityPlayerSP playerSP = Minecraft.getMinecraft().player;
        ItemStack heldItem = playerSP.getHeldItem(ConfigTags.getConfiguredHand());

        if (WandHelper.isOmniwand(heldItem)) {
            ItemStack newStack = heldItem;
            RayTraceResult result = playerSP.rayTrace(playerSP.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue(), 1.0f);

            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                IBlockState lookState = playerSP.world.getBlockState(result.getBlockPos());
                String lookMod = WandHelper.getModOrAlias(lookState);
                if (WandHelper.isAutoMode(newStack)) {
                    newStack = WandHelper.getTransformedStack(heldItem, lookMod, false);
                    WandHelper.setAutoMode(newStack, true);
                }
            }

            if (newStack != heldItem && !ItemStack.areItemsEqual(newStack, heldItem)) {
                int slot = ConfigTags.getConfiguredHand() == EnumHand.OFF_HAND ? playerSP.inventory.getSizeInventory() - 1 : playerSP.inventory.currentItem;
                playerSP.inventory.setInventorySlotContents(slot, newStack);
                NetworkHandler.INSTANCE.sendToServer(new MessageWandTransform(newStack, slot));
            }
        }
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if(WandHelper.isOmniwand(stack) && WandHelper.isTransformedWand(stack)) {
            List<String> tooltips = event.getToolTip();
            String displayName = stack.getDisplayName();
            for(int i = 0; i < tooltips.size(); i++) {
                String tooltip = tooltips.get(i);
                //Using contains rather than equals to account for rarity formatting.
                if(tooltip.contains(displayName)) {
                    tooltips.set(i, I18n.format("omniwand:sudo_name", displayName));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerSwing(PlayerInteractEvent.LeftClickEmpty event) {
        ItemStack stack = event.getItemStack();
        if (!ConfigHandler.crouchRevert || event.getEntityPlayer().isSneaking()) {
            if (WandHelper.isOmniwand(stack) && stack.getItem() != Registry.OMNIWAND) {
                ItemStack newStack = WandHelper.getTransformedStack(stack, Omniwand.MOD_ID, false);
                NetworkHandler.INSTANCE.sendToServer(new MessageWandTransform(newStack, event.getEntityPlayer().inventory.currentItem));
            }
        }
    }
}
