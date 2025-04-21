package com.invadermonky.omniwand.handlers;

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
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.ForgeSubscribe;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {
    @ForgeSubscribe
    public void onMouseEvent(MouseEvent event) {
        EntityPlayerSP playerSP = Minecraft.getMinecraft().thePlayer;
        ItemStack heldItem = playerSP.getHeldItem();

        if (WandHelper.isOmniwand(heldItem) && ConfigTags.shouldTransform(playerSP.isSneaking())) {
            ItemStack newStack = heldItem;
            MovingObjectPosition result = playerSP.rayTrace(playerSP.capabilities.isCreativeMode ? 5f : 4.5f, 1.0f);

            String lookMod = "";
            if (result != null && result.typeOfHit == EnumMovingObjectType.TILE) {
                int blockId = playerSP.worldObj.getBlockId(result.blockX, result.blockY, result.blockZ);
                Block lookBlock = Block.blocksList[blockId];
                lookMod = WandHelper.getModOrAlias(lookBlock);
                if (WandHelper.getAutoMode(newStack)) {
                    newStack = WandHelper.getTransformedStack(heldItem, lookMod, false);
                    WandHelper.setAutoMode(newStack, true);
                }
            }

            if (!lookMod.isEmpty() && newStack != heldItem && !ItemHelper.areItemsEqual(newStack, heldItem)) {
                int slot = playerSP.inventory.currentItem;
                playerSP.inventory.setInventorySlotContents(slot, newStack);
                PacketDispatcher.sendPacketToServer(new Packet101WandTransform(lookMod, true).build());
            }
        }
    }
}
