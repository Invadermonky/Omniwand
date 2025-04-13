package com.invadermonky.omniwand.proxy;

import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PacketWrapper {
    public static Packet250CustomPayload createPacket(String paramString, int paramInt, Object[] objects) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        try {
            dataOutputStream.write(paramInt);
            if (objects != null)
                for (Object object : objects)
                    writeObjectToStream(object, dataOutputStream);
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = paramString;
        packet.data = outputStream.toByteArray();
        packet.length = packet.data.length;
        return packet;
    }

    public static Object[] readPacketData(DataInputStream inputStream, Class<?>[] clazzes) {
        ArrayList<Object> arrayList = new ArrayList<>();
        try {
            for (Class<?> clazz : clazzes)
                arrayList.add(readObjectFromStream(inputStream, clazz));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList.toArray();
    }

    private static void writeObjectToStream(Object object, DataOutputStream outputStream) throws IOException {
        Class<?> clazz = object.getClass();
        if (clazz.equals(Boolean.class)) {
            outputStream.writeBoolean((Boolean) object);
        } else if (clazz.equals(Byte.class)) {
            outputStream.writeByte((Byte) object);
        } else if (clazz.equals(Integer.class)) {
            outputStream.writeInt((Integer) object);
        } else if (clazz.equals(String.class)) {
            outputStream.writeUTF((String) object);
        } else if (clazz.equals(Double.class)) {
            outputStream.writeDouble((Double) object);
        } else if (clazz.equals(Float.class)) {
            outputStream.writeFloat((Float) object);
        } else if (clazz.equals(Long.class)) {
            outputStream.writeLong((Long) object);
        } else if (clazz.equals(Short.class)) {
            outputStream.writeShort((Short) object);
        }
    }

    private static Object readObjectFromStream(DataInputStream inputStream, Class<?> clazz) throws IOException {
        if (clazz.equals(Boolean.class))
            return inputStream.readBoolean();
        if (clazz.equals(Byte.class))
            return inputStream.readByte();
        if (clazz.equals(Integer.class))
            return inputStream.readInt();
        if (clazz.equals(String.class))
            return inputStream.readUTF();
        if (clazz.equals(Double.class))
            return inputStream.readDouble();
        if (clazz.equals(Float.class))
            return inputStream.readFloat();
        if (clazz.equals(Long.class))
            return inputStream.readLong();
        if (clazz.equals(Short.class))
            return inputStream.readShort();
        return null;
    }
}
