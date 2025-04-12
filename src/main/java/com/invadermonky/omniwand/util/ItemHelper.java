package com.invadermonky.omniwand.util;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemHelper {

    public static boolean areItemsEqual(ItemStack stack1, ItemStack stack2) {
        if (isEmpty(stack1) || isEmpty(stack2)) {
            return false;
        } else {
            return stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage();
        }
    }

    public static boolean isEmpty(ItemStack stack) {
        return stack == null || stack.getItem() == null;
    }

    public static boolean isEmpty(NBTTagCompound tag) {
        return tag.func_150296_c()
            .isEmpty();
    }

    public static String getOwnerMod(ItemStack stack) {
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(stack.getItem());
        return id != null ? id.modId : "";
    }

    public static String getOwnerMod(Block block) {
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(block);
        return id != null ? id.modId : "";
    }

    public static String getItemId(ItemStack stack) {
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(stack.getItem());
        return id != null ? id.name : "";
    }

    public static String getItemId(Block block) {
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(block);
        return id != null ? id.name : "";
    }

    public static String getRegistryName(ItemStack stack) {
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(stack.getItem());
        return id != null ? id.toString() : "";
    }
}
