package com.invadermonky.omniwand.network;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.handlers.TransformHandler;
import com.invadermonky.omniwand.init.RegistryOW;
import com.invadermonky.omniwand.util.References;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRevertWand implements IMessage {
    public MessageRevertWand() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class MsgHandler implements IMessageHandler<MessageRevertWand,IMessage> {
        @Override
        public IMessage onMessage(MessageRevertWand message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;
            ItemStack stack = player.getHeldItemMainhand();

            if(stack != null && TransformHandler.isOmniwand(stack) && stack.getItem() != RegistryOW.OMNIWAND) {
                ItemStack newStack = TransformHandler.getTransformStackForMod(stack, References.MINECRAFT);
                player.setHeldItem(EnumHand.MAIN_HAND, newStack);
                Omniwand.proxy.updateEquippedItem();
            }
            return null;
        }
    }
}
