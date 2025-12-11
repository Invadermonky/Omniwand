package com.invadermonky.omniwand.handlers;

import com.invadermonky.omniwand.config.ConfigHandler;
import com.invadermonky.omniwand.config.ConfigTags;
import com.invadermonky.omniwand.network.NetworkHandler;
import com.invadermonky.omniwand.network.messages.MessageTransformWand;
import com.invadermonky.omniwand.util.WandHelper;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onMouseEvent(MouseEvent event) {
        EntityPlayerSP playerSP = Minecraft.getMinecraft().thePlayer;
        ItemStack heldItem = playerSP.getHeldItem();

        if (ConfigHandler.autoTransform && WandHelper.isOmniwand(heldItem) && ConfigTags.shouldTransform(playerSP.isSneaking())) {
            MovingObjectPosition result = playerSP.rayTrace(playerSP.capabilities.isCreativeMode ? 5f : 4.5f, 1.0f);

            if (result != null && result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                Block lookBlock = playerSP.worldObj.getBlock(result.blockX, result.blockY, result.blockZ);
                String lookMod = WandHelper.getModOrAlias(lookBlock);
                if (WandHelper.isAutoMode(heldItem)) {
                    NetworkHandler.INSTANCE.sendToServer(new MessageTransformWand(lookMod, true));
                }
            }
        }
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.itemStack;
        if(WandHelper.isTransformedWand(stack)) {
            List<String> tooltips = event.toolTip;
            String displayName = stack.getDisplayName();
            for(int i = 0; i < tooltips.size(); i++) {
                String tooltip = tooltips.get(i);
                //Using contains rather than equals to account for rarity formatting.
                if(tooltip.contains(displayName)) {
                    tooltips.set(i, I18n.format("omniwand:sudo_name", displayName));
                    break;
                }
            }
        }
    }
}
