package com.invadermonky.omniwand.network;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.registry.Registry;
import com.invadermonky.omniwand.util.ItemHelper;
import com.invadermonky.omniwand.util.WandHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class MessageRevertWand implements IMessage {

    public MessageRevertWand() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class MsgHandler implements IMessageHandler<MessageRevertWand, IMessage> {

        @Override
        public IMessage onMessage(MessageRevertWand message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            ItemStack stack = player.getHeldItem();

            if (!ItemHelper.isEmpty(stack) && WandHelper.isOmniwand(stack) && stack.getItem() != Registry.OMNIWAND) {
                ItemStack newStack = WandHelper.getTransformedStack(stack, Omniwand.MOD_ID, false);
                player.setCurrentItemOrArmor(player.inventory.currentItem, newStack);
            }
            return null;
        }
    }
}
