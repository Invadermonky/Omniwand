package com.invadermonky.omniwand.handlers;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.config.ConfigHandler;
import com.invadermonky.omniwand.config.ConfigTags;
import com.invadermonky.omniwand.network.MessageWandTransform;
import com.invadermonky.omniwand.registry.Registry;
import com.invadermonky.omniwand.util.WandHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
                if (WandHelper.getAutoMode(newStack)) {
                    newStack = WandHelper.getTransformedStack(heldItem, lookMod, false);
                    WandHelper.setAutoMode(newStack, true);
                }
            }

            if (newStack != heldItem && !ItemStack.areItemsEqual(newStack, heldItem)) {
                int slot = ConfigTags.getConfiguredHand() == EnumHand.OFF_HAND ? playerSP.inventory.getSizeInventory() - 1 : playerSP.inventory.currentItem;
                playerSP.inventory.setInventorySlotContents(slot, newStack);
                Omniwand.network.sendToServer(new MessageWandTransform(newStack, slot));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerSwing(PlayerInteractEvent.LeftClickEmpty event) {
        ItemStack stack = event.getItemStack();
        if (!ConfigHandler.crouchRevert || event.getEntityPlayer().isSneaking()) {
            if (WandHelper.isOmniwand(stack) && stack.getItem() != Registry.OMNIWAND) {
                ItemStack newStack = WandHelper.getTransformedStack(stack, Omniwand.MOD_ID, false);
                Omniwand.network.sendToServer(new MessageWandTransform(newStack, event.getEntityPlayer().inventory.currentItem));
            }
        }
    }
}
