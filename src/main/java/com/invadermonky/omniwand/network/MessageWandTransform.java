package com.invadermonky.omniwand.network;

import com.invadermonky.omniwand.config.ConfigTags;
import com.invadermonky.omniwand.util.WandHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageWandTransform implements IMessage {
    public ItemStack stack;
    public int slot;

    public MessageWandTransform() {
    }

    public MessageWandTransform(ItemStack stack, int slot) {
        this.stack = stack;
        this.slot = slot;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        stack = ByteBufUtils.readItemStack(buf);
        slot = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, stack);
        buf.writeInt(slot);
    }

    public static class MsgHandler implements IMessageHandler<MessageWandTransform, IMessage> {
        @Override
        public IMessage onMessage(MessageWandTransform message, MessageContext ctx) {
            ctx.getServerHandler().player.server.addScheduledTask(() -> {
                EntityPlayer player = ctx.getServerHandler().player;
                ItemStack heldItem = player.getHeldItem(ConfigTags.getConfiguredHand());

                if (WandHelper.isOmniwand(heldItem) && message.stack != heldItem && !ItemStack.areItemsEqual(message.stack, heldItem))
                    player.setHeldItem(ConfigTags.getConfiguredHand(), message.stack);
            });
            return null;
        }
    }
}
