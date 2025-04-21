package com.invadermonky.omniwand.network;

import com.invadermonky.omniwand.util.ItemHelper;
import com.invadermonky.omniwand.util.WandHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet101WandTransform extends PacketOmniwand {
    public String modId;
    public boolean auto;

    public Packet101WandTransform(String modId, boolean auto) {
        this.modId = modId;
        this.auto = auto;
    }

    public Packet101WandTransform() {
    }

    @Override
    public void handle(World world, EntityPlayer player) {
        ItemStack stack = player.getHeldItem();
        boolean hasWand = !ItemHelper.isEmpty(stack) && WandHelper.isOmniwand(stack);

        if (hasWand) {
            ItemStack newStack = WandHelper.getTransformedStack(stack, this.modId, false);
            WandHelper.setAutoMode(newStack, this.auto);
            player.inventory.setInventorySlotContents(player.inventory.currentItem, newStack);
        }
    }

    @Override
    public void read(DataInputStream inputStream) throws IOException {
        this.modId = inputStream.readUTF();
        this.auto = inputStream.readBoolean();
    }

    @Override
    public void write(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF(this.modId);
        outputStream.writeBoolean(this.auto);
    }

}
