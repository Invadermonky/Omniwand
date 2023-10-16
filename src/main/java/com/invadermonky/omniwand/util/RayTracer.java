package com.invadermonky.omniwand.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RayTracer {
    public static RayTraceResult retrace(EntityPlayer player) {
        Vec3d startVec = getStartVec(player);
        Vec3d endVec = getEndVec(player, getBlockReachDistance(player));
        return player.world.rayTraceBlocks(startVec, endVec, true, false, true);
    }

    public static Vec3d getStartVec(EntityPlayer player) {
        return new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
    }

    public static Vec3d getEndVec(EntityPlayer player, double reach) {
        Vec3d headVec = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3d lookVec = player.getLook(1.0F);
        return headVec.add(lookVec.x * reach, lookVec.y * reach, lookVec.z * reach);
    }

    public static double getBlockReachDistance(EntityPlayer player) {
        return player.world.isRemote ? getBlockReachDistanceClient() : player instanceof EntityPlayerMP ? getBlockReachDistanceServer((EntityPlayerMP) player) : 5D;
    }

    private static double getBlockReachDistanceServer(EntityPlayerMP player) {
        return player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
    }

    @SideOnly(Side.CLIENT)
    private static double getBlockReachDistanceClient() {
        return Minecraft.getMinecraft().playerController.getBlockReachDistance();
    }
}
