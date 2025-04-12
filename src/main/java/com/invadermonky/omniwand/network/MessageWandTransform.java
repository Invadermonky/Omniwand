package com.invadermonky.omniwand.network;

import com.invadermonky.omniwand.util.ItemHelper;
import com.invadermonky.omniwand.util.WandHelper;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            ItemStack heldItem = player.getHeldItem();

            if (WandHelper.isOmniwand(heldItem) && message.stack != heldItem
                && !ItemHelper.areItemsEqual(message.stack, heldItem))
                player.inventory.setInventorySlotContents(player.inventory.currentItem, message.stack);
            return null;
        }
    }
}
