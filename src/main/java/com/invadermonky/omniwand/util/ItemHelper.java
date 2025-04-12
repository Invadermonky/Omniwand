package com.invadermonky.omniwand.util;

import net.minecraft.item.ItemStack;

public class ItemHelper {
    public static boolean isEmpty(ItemStack stack) {
        return stack == null || stack.getItem() == null;
    }
}
