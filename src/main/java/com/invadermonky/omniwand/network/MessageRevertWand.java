package com.invadermonky.omniwand.network;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.registry.Registry;
import com.invadermonky.omniwand.util.WandHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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
            ctx.getServerHandler().player.server.addScheduledTask(() -> {
                EntityPlayer player = ctx.getServerHandler().player;
                ItemStack stack = player.getHeldItemMainhand();

                if (!stack.isEmpty() && WandHelper.isOmniwand(stack) && stack.getItem() != Registry.OMNIWAND) {
                    ItemStack newStack = WandHelper.getTransformedStack(stack, Omniwand.MOD_ID, false);
                    player.setHeldItem(EnumHand.MAIN_HAND, newStack);
                }
            });
            return null;
        }
    }
}
