package com.invadermonky.omniwand.network;

import com.invadermonky.omniwand.Omniwand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import java.io.*;

public abstract class PacketOmniwand {
    public abstract void handle(World world, EntityPlayer player);

    public abstract void read(DataInputStream inputStream) throws IOException;

    public abstract void write(DataOutputStream outputStream) throws IOException;

    public Packet build() {
        Byte id = Byte.parseByte(getClass().getSimpleName().toLowerCase().substring(6, 9));
        ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
        DataOutputStream os = new DataOutputStream(bos);
        try {
            os.writeByte(id);
            write(os);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = Omniwand.MOD_ID;
        packet.data = bos.toByteArray();
        packet.length = bos.size();
        return packet;
    }

    public void handle(Packet250CustomPayload packet, EntityPlayer player) {
        DataInputStream os = new DataInputStream(new ByteArrayInputStream(packet.data));
        try {
            os.readByte();
            read(os);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        handle(player.worldObj, player);
    }

}
