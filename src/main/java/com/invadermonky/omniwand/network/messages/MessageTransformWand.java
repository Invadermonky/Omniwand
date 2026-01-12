package com.invadermonky.omniwand.network.messages;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.util.WandHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MessageTransformWand implements IMessage {
    private String modKey;
    private boolean autoMode;

    public MessageTransformWand(String modKey, boolean autoMode) {
        this.modKey = modKey;
        this.autoMode = autoMode;
    }

    public MessageTransformWand() {
        this(Omniwand.MOD_ID, true);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.modKey = ByteBufUtils.readUTF8String(buf);
        this.autoMode = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.modKey);
        buf.writeBoolean(this.autoMode);
    }

    public static class MsgHandler implements IMessageHandler<MessageTransformWand, IMessage> {
        @Override
        public IMessage onMessage(MessageTransformWand message, MessageContext ctx) {
            AtomicReference<ItemStack> newStack = new AtomicReference<>(ItemStack.EMPTY);
            AtomicReference<EnumHand> hand = new AtomicReference<>(EnumHand.MAIN_HAND);
            AtomicBoolean hasWand = new AtomicBoolean(false);
            ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
                EntityPlayer player = ctx.getServerHandler().player;
                ItemStack stack = player.getHeldItemMainhand();
                hasWand.set(WandHelper.isOmniwand(stack));

                if (!hasWand.get()) {
                    stack = player.getHeldItemOffhand();
                    hasWand.set(WandHelper.isOmniwand(stack));
                    hand.set(EnumHand.OFF_HAND);
                }

                if (hasWand.get()) {
                    ItemStack temp = WandHelper.getTransformedStack(stack, message.modKey, false);
                    if(!stack.equals(temp) && !ItemStack.areItemStacksEqual(stack, temp)) {
                        WandHelper.setAutoMode(temp, message.autoMode);
                        newStack.set(temp);
                        player.setHeldItem(hand.get(), newStack.get());
                    }
                }
            });
            return null;
        }
    }
}
