package com.invadermonky.omniwand.network.messages;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.util.ItemHelper;
import com.invadermonky.omniwand.util.WandHelper;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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

    public static class MsgHandler implements IMessageHandler<MessageTransformWand, MessageSetStackInSlotClient> {
        @Override
        public MessageSetStackInSlotClient onMessage(MessageTransformWand message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            ItemStack stack = player.getHeldItem();
            ItemStack newStack = null;
            boolean hasWand = WandHelper.isOmniwand(stack);
            if(hasWand) {
                ItemStack temp = WandHelper.getTransformedStack(stack, message.modKey, false);
                if(!stack.equals(temp) && !ItemStack.areItemStacksEqual(stack, temp)) {
                    WandHelper.setAutoMode(temp, message.autoMode);
                    newStack = temp;
                    player.setCurrentItemOrArmor(0, newStack);
                }
            }
            return !ItemHelper.isEmpty(newStack) ? new MessageSetStackInSlotClient(newStack) : null;
        }
    }
}
