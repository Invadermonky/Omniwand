package com.invadermonky.omniwand.util;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.ItemData;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ItemHelper {
    private static Field tagMapField;
    private static Field idMapField;

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
        return tag.getTags().isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static ItemData getItemData(ItemStack stack) {
        try {
            if (idMapField == null) {
                idMapField = GameData.class.getDeclaredFields()[0];
            }
            if (!idMapField.isAccessible()) {
                idMapField.setAccessible(true);
            }
            Map<Integer, ItemData> idMap = (Map<Integer, ItemData>) idMapField.get(null);
            return idMap.get(stack.getItem().itemID);
        } catch (Exception ignored) {
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static ItemData getBlockData(Block block) {
        try {
            if (idMapField == null) {
                idMapField = GameData.class.getDeclaredFields()[0];
            }
            if (!idMapField.isAccessible()) {
                idMapField.setAccessible(true);
            }
            Map<Integer, ItemData> idMap = (Map<Integer, ItemData>) idMapField.get(null);
            return idMap.get(block.blockID);
        } catch (Exception ignored) {
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Set<String> getItemTagKeys(NBTTagCompound tag) {
        try {
            if (tagMapField == null) {
                tagMapField = tag.getClass().getDeclaredFields()[0];
            }
            if (!tagMapField.isAccessible()) {
                tagMapField.setAccessible(true);
            }
            return ((Map<String, ?>) tagMapField.get(tag)).keySet();
        } catch (Exception ignored) {
        }
        return new HashSet<>();
    }

    public static String getOwnerMod(ItemStack stack) {
        ItemData data = getItemData(stack);
        return data != null ? data.getModId() : "";
    }

    public static String getOwnerMod(Block block) {
        ItemData data = getBlockData(block);
        return data != null ? data.getModId() : "";
    }

    public static String getItemId(ItemStack stack) {
        ItemData data = getItemData(stack);
        return data != null ? String.valueOf(data.getItemId()) : "";
    }

    public static String getItemId(Block block) {
        ItemData data = getBlockData(block);
        return data != null ? data.getItemType() : "";
    }

    public static String getRegistryName(ItemStack stack) {
        return stack.getUnlocalizedName();
    }
}
