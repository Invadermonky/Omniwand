package com.invadermonky.omniwand.handlers;

import com.invadermonky.omniwand.config.ConfigHandler;
import com.invadermonky.omniwand.config.ConfigTags;
import com.invadermonky.omniwand.network.Packet101WandTransform;
import com.invadermonky.omniwand.util.ItemHelper;
import com.invadermonky.omniwand.util.WandHelper;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {
    @ForgeSubscribe
    public void onMouseEvent(MouseEvent event) {
        EntityPlayerSP playerSP = Minecraft.getMinecraft().thePlayer;
        ItemStack heldItem = playerSP.getHeldItem();

        if (ConfigHandler.autoTransform && WandHelper.isOmniwand(heldItem) && ConfigTags.shouldTransform(playerSP.isSneaking())) {
            ItemStack newStack = heldItem;
            MovingObjectPosition result = playerSP.rayTrace(playerSP.capabilities.isCreativeMode ? 5f : 4.5f, 1.0f);

            String lookMod = "";
            if (result != null && result.typeOfHit == EnumMovingObjectType.TILE) {
                int blockId = playerSP.worldObj.getBlockId(result.blockX, result.blockY, result.blockZ);
                Block lookBlock = Block.blocksList[blockId];
                lookMod = WandHelper.getModOrAlias(lookBlock);
                if (WandHelper.isAutoMode(newStack)) {
                    newStack = WandHelper.getTransformedStack(heldItem, lookMod, false);
                    WandHelper.setAutoMode(newStack, true);
                }
            }

            if (!lookMod.isEmpty() && newStack != heldItem && !ItemHelper.areItemsEqual(newStack, heldItem)) {
                playerSP.inventory.setInventorySlotContents(playerSP.inventory.currentItem, newStack);
                PacketDispatcher.sendPacketToServer(new Packet101WandTransform(lookMod, true).build());
            }
        }
    }

    @ForgeSubscribe
    public void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.itemStack;
        if(WandHelper.isTransformedWand(stack)) {
            List<String> tooltips = event.toolTip;
            String displayName = stack.getDisplayName();
            for(int i = 0; i < tooltips.size(); i++) {
                String tooltip = tooltips.get(i);
                //Using contains rather than equals to account for rarity formatting.
                if(tooltip.contains(displayName)) {
                    tooltips.set(i, I18n.getStringParams("omniwand:sudo_name", displayName));
                    break;
                }
            }
        }
    }
}
