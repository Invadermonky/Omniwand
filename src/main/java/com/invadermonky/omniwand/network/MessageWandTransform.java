package com.invadermonky.omniwand.network;

import com.invadermonky.omniwand.registry.Registry;
import com.invadermonky.omniwand.util.ItemHelper;
import com.invadermonky.omniwand.util.WandHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MessageWandTransform extends Packet {
    public String modId;
    public boolean auto;

    public MessageWandTransform(String modId, boolean auto) {
        this.modId = modId;
        this.auto = auto;
    }

    @Override
    public void readPacketData(DataInput input) throws IOException {
        this.modId = input.readUTF();
        this.auto = input.readBoolean();
    }

    @Override
    public void writePacketData(DataOutput output) throws IOException {
        output.writeUTF(this.modId);
        output.writeBoolean(this.auto);
    }

    @Override
    public void processPacket(NetHandler handler) {
        EntityPlayer player = handler.getPlayer();
        ItemStack stack = player.getHeldItem();
        boolean hasWand = !ItemHelper.isEmpty(stack) && stack.getItem() == Registry.OMNIWAND;

        if (hasWand) {
            ItemStack newStack = WandHelper.getTransformedStack(stack, this.modId, false);
            WandHelper.setAutoMode(newStack, this.auto);
            player.inventory.setInventorySlotContents(player.inventory.currentItem, newStack);
        }
    }

    @Override
    public int getPacketSize() {
        return ByteBuffer.wrap(this.modId.getBytes()).getShort() + 2;
    }
}
