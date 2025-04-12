package com.invadermonky.omniwand.network;

import com.invadermonky.omniwand.registry.Registry;
import com.invadermonky.omniwand.util.ItemHelper;
import com.invadermonky.omniwand.util.WandHelper;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class MessageGuiTransform implements IMessage {

    public String modId;

    public MessageGuiTransform() {
    }

    public MessageGuiTransform(String modId) {
        this.modId = modId;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        modId = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, modId);
    }

    public static class MsgHandler implements IMessageHandler<MessageGuiTransform, IMessage> {

        @Override
        public IMessage onMessage(MessageGuiTransform message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            ItemStack stack = player.getHeldItem();

            boolean hasWand = !ItemHelper.isEmpty(stack) && stack.getItem() == Registry.OMNIWAND;

            if (hasWand) {
                ItemStack newStack = WandHelper.getTransformedStack(stack, message.modId, false);
                player.inventory.setInventorySlotContents(player.inventory.currentItem, newStack);
            }
            return null;
        }
    }
}
