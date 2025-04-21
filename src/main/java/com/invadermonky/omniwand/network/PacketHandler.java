package com.invadermonky.omniwand.network;

import com.invadermonky.omniwand.Omniwand;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;

public class PacketHandler implements IPacketHandler {
    public static Map<Byte, PacketOmniwand> packets = new HashMap<>();

    public static void register(PacketOmniwand packet) {
        Byte id = Byte.parseByte(packet.getClass().getSimpleName().toLowerCase().substring(6, 9));
        packets.put(id, packet);
    }

    public static PacketOmniwand getPacket(byte bite) {
        return packets.get(bite);
    }

    public static void init() {
        register(new Packet101WandTransform());
    }

    @Override
    public void onPacketData(INetworkManager iNetworkManager, Packet250CustomPayload packet, Player player) {
        DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
        if (packet.channel.equals(Omniwand.MOD_ID) && packet.data.length > 1) {
            try {
                getPacket(data.readByte()).handle(packet, (EntityPlayer) player);
            } catch (Exception ignored) {
            }
        }
    }
}
