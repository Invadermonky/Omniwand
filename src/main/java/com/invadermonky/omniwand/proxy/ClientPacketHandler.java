package com.invadermonky.omniwand.proxy;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class ClientPacketHandler implements IPacketHandler {
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload payload, Player player) {

    }
}
