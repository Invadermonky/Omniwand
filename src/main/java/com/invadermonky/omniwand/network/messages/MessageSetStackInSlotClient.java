package com.invadermonky.omniwand.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSetStackInSlotClient implements IMessage {
    private ItemStack stack;
    private EnumHand hand;

    public MessageSetStackInSlotClient(ItemStack stack, EnumHand hand) {
        this.stack = stack;
        this.hand = hand;
    }

    public MessageSetStackInSlotClient() {
        this(ItemStack.EMPTY, EnumHand.MAIN_HAND);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.stack = ByteBufUtils.readItemStack(buf);
        this.hand = EnumHand.values()[buf.readShort()];
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.stack);
        buf.writeShort(this.hand.ordinal());
    }

    public static class MsgHandler implements IMessageHandler<MessageSetStackInSlotClient, IMessage> {
        @SideOnly(Side.CLIENT)
        @Override
        public IMessage onMessage(MessageSetStackInSlotClient message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                EntityPlayer player = Minecraft.getMinecraft().player;
                player.setHeldItem(message.hand, message.stack);
            });
            return null;
        }
    }
}
