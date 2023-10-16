package com.invadermonky.omniwand.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {
    public static boolean detectNBT(ItemStack stack) {
        return stack.hasTagCompound();
    }

    public static void initNBT(ItemStack stack) {
        if(!detectNBT(stack))
            injectNBT(stack, new NBTTagCompound());
    }

    public static void injectNBT(ItemStack stack, NBTTagCompound tagCompound) {
        stack.setTagCompound(tagCompound);
    }

    public static NBTTagCompound getNBT(ItemStack stack) {
        initNBT(stack);
        return stack.getTagCompound();
    }

    public static boolean verifyExistence(ItemStack stack, String tag) {
        return !stack.isEmpty() && detectNBT(stack) && getNBT(stack).hasKey(tag);
    }

    public static void setString(ItemStack stack, String tag, String str) {
        getNBT(stack).setString(tag, str);
    }

    public static NBTTagCompound getTagCompound(ItemStack stack, String tag) {
        return verifyExistence(stack, tag) ? getNBT(stack).getCompoundTag(tag) : new NBTTagCompound();
    }

    public static String getString(ItemStack stack, String tag, String defaultExpected) {
        return verifyExistence(stack, tag) ? getNBT(stack).getString(tag) : defaultExpected;
    }
}
