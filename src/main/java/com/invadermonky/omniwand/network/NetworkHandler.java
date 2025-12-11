package com.invadermonky.omniwand.network;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.network.messages.MessageSetStackInSlotClient;
import com.invadermonky.omniwand.network.messages.MessageTransformWand;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkHandler {
    public static final SimpleNetworkWrapper INSTANCE;

    public static void init() {
        int id = 0;
        INSTANCE.registerMessage(MessageTransformWand.MsgHandler.class, MessageTransformWand.class, id++, Side.SERVER);
        INSTANCE.registerMessage(MessageSetStackInSlotClient.MsgHandler.class, MessageSetStackInSlotClient.class, id++, Side.CLIENT);
    }

    static {
        INSTANCE = new SimpleNetworkWrapper(Omniwand.MOD_ID);
    }
}
