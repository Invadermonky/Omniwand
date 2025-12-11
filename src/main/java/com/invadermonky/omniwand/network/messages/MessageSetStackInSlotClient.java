package com.invadermonky.omniwand.network.messages;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class MessageSetStackInSlotClient implements IMessage {
    private ItemStack stack;

    public MessageSetStackInSlotClient(ItemStack stack) {
        this.stack = stack;
    }

    public MessageSetStackInSlotClient() {
        this(null);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.stack);
    }

    public static class MsgHandler implements IMessageHandler<MessageSetStackInSlotClient, IMessage> {
        @SideOnly(Side.CLIENT)
        @Override
        public IMessage onMessage(MessageSetStackInSlotClient message, MessageContext ctx) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            player.setCurrentItemOrArmor(0, message.stack);
            return null;
        }
    }
}
