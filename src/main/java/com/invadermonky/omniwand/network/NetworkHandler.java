package com.invadermonky.omniwand.network;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.network.messages.MessageGuiTransform;
import com.invadermonky.omniwand.network.messages.MessageRevertWand;
import com.invadermonky.omniwand.network.messages.MessageWandTransform;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
    public static final SimpleNetworkWrapper INSTANCE;

    public static void init() {
        int id = 0;
        INSTANCE.registerMessage(MessageGuiTransform.MsgHandler.class, MessageGuiTransform.class, id++, Side.SERVER);
        INSTANCE.registerMessage(MessageRevertWand.MsgHandler.class, MessageRevertWand.class, id++, Side.SERVER);
        INSTANCE.registerMessage(MessageWandTransform.MsgHandler.class, MessageWandTransform.class, id++, Side.SERVER);
    }

    static {
        INSTANCE = new SimpleNetworkWrapper(Omniwand.MOD_ID);
    }
}
