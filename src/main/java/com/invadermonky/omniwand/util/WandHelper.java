package com.invadermonky.omniwand.util;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.config.ConfigHandler;
import com.invadermonky.omniwand.config.ConfigTags;
import com.invadermonky.omniwand.registry.Registry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;

import java.util.function.Consumer;

import static com.invadermonky.omniwand.util.libs.LibTags.*;

public class WandHelper {
    public static String getModName(String mod) {
        return Loader.instance().getIndexedModList().containsKey(mod) ? Loader.instance().getIndexedModList().get(mod).getName() : "";
    }

    public static String getModOrAlias(String modId) {
        return ConfigTags.getModAlias(modId);
    }

    public static String getModOrAlias(ItemStack stack) {
        return getModOrAlias(stack.getItem().getCreatorModId(stack));
    }

    public static String getModOrAlias(IBlockState state) {
        return getModOrAlias(state.getBlock().getRegistryName().getNamespace());
    }

    public static boolean isOmniwand(ItemStack stack) {
        return stack.getItem() == Registry.OMNIWAND || isTransformedWand(stack);
    }

    public static boolean isTransformedWand(ItemStack stack) {
        return stack.getItem() != Registry.OMNIWAND && getIsTransforming(stack) && !getWandData(stack).isEmpty();
    }

    /**
     * Gets the transform stack for the passed mod key value. If the mod value does not refer to an item stored in
     * the Omniwand, it will attempt to revert back to the Omniwand item.
     *
     * @param stack       The Omniwand stack
     * @param mod         The Omniwand item key value or desired transform mod
     * @param removeStack If the passed ItemStack will be removed from the Omniwand
     * @return The newly transformed wand ItemStack
     */
    public static ItemStack getTransformedStack(ItemStack stack, String mod, boolean removeStack) {
        //Retrieving the item mod string, used for the wand data key
        String wandSlot = getModSlot(stack);

        if (stack.isEmpty() || wandSlot.equals(mod) || !isOmniwand(stack))
            return stack;

        //Retrieving and copying the wand "inventory" data
        NBTTagCompound wandData = getWandData(stack).copy();

        //If no transform item is found on wand, resets it to default "omniwand"
        if (!wandData.hasKey(mod))
            mod = Omniwand.MOD_ID;


        if (!removeStack) {
            //Cleaning Omniwand data off the transforming stack
            cleanStackTags(stack);

            //Adding the current item back into the wand data tag
            NBTTagCompound currentStackTag = new NBTTagCompound();
            stack.writeToNBT(currentStackTag);
            wandData.setTag(wandSlot, currentStackTag);
        }

        //Creating or pulling new item from the wand
        ItemStack newStack;
        if (wandData.hasKey(mod) && !mod.equals(Omniwand.MOD_ID)) {
            newStack = new ItemStack(wandData.getCompoundTag(mod));
            wandData.removeTag(mod);
        } else {
            newStack = new ItemStack(Registry.OMNIWAND);
            wandData.removeTag(Omniwand.MOD_ID);
        }

        //Setting all the Omniwand required tag info
        setWandData(newStack, wandData);
        setModSlot(newStack, mod);
        setAutoMode(newStack, newStack.getItem() == Registry.OMNIWAND);
        setIsTransforming(newStack, newStack.getItem() != Registry.OMNIWAND);

        return newStack;
    }

    /**
     * Removes the current item from the Omniwand and returns the reverted Omniwand tool.
     *
     * @param stack    The Omniwand stack
     * @param isBroken Whether the item is broken or destroyed
     * @param consumer If the item is not broken, the removed item will be added to this consumer
     * @return The reverted Omniwand item with the passed ItemStack removed.
     */
    public static ItemStack removeItemFromWand(ItemStack stack, boolean isBroken, Consumer<ItemStack> consumer) {
        if (stack.isEmpty() || !isOmniwand(stack) || stack.getItem() == Registry.OMNIWAND)
            return stack;

        //Getting removed stack
        ItemStack original = stack.copy();
        ItemStack wandStack = getTransformedStack(stack, Omniwand.MOD_ID, true);

        if (!isBroken) {
            cleanStackTags(original);
            consumer.accept(original);
        }

        return wandStack;
    }

    /**
     * Removes all Omniwand tags from the passed stack and purges Item tag if the tag is empty.
     * <p>
     * Use this when removing items from the Omniwand to ensure the item is clean after being removed.
     * <p>
     * Does nothing if the item is the Omniwand.
     */
    public static void cleanStackTags(ItemStack stack) {
        if (stack.getItem() == Registry.OMNIWAND)
            return;

        NBTTagCompound tag = getStackTag(stack);
        tag.removeTag(TAG_WAND_DATA);
        tag.removeTag(TAG_MOD_SLOT);
        tag.removeTag(TAG_IS_TRANSFORMING);
        tag.removeTag(TAG_AUTO_TRANSFORM);
        if (tag.isEmpty()) {
            stack.setTagCompound(null);
        } else {
            stack.setTagCompound(tag);
        }
    }














    public static NBTTagCompound getWandData(ItemStack stack) {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey(TAG_WAND_DATA) ? getStackTag(stack).getCompoundTag(TAG_WAND_DATA) : new NBTTagCompound();
    }

    public static void setWandData(ItemStack stack, NBTTagCompound tag) {
        getStackTag(stack).setTag(TAG_WAND_DATA, tag);
    }

    /**
     * Gets the current Omniwand auto transform mode. This will cause the wand to shift based on what the player is
     * looking at.
     */
    public static boolean isAutoMode(ItemStack stack) {
        if (!getStackTag(stack).hasKey(TAG_AUTO_TRANSFORM) && stack.getItem() == Registry.OMNIWAND)
            getStackTag(stack).setBoolean(TAG_AUTO_TRANSFORM, ConfigHandler.autoTransform);
        return getStackTag(stack).getBoolean(TAG_AUTO_TRANSFORM);
    }

    /**
     * Sets the auto-transform mod for the wand. This automatically detects if auto-transform is disabled in the
     * configs and adjusts accordingly.
     */
    public static void setAutoMode(ItemStack stack, boolean autoMode) {
        getStackTag(stack).setBoolean(TAG_AUTO_TRANSFORM, autoMode && ConfigHandler.autoTransform);
    }


    public static boolean getIsTransforming(ItemStack stack) {
        return stack.getTagCompound() != null && stack.getTagCompound().getBoolean(TAG_IS_TRANSFORMING);
    }

    public static void setIsTransforming(ItemStack stack, boolean isTransforming) {
        getStackTag(stack).setBoolean(TAG_IS_TRANSFORMING, isTransforming);
    }

    public static String getModSlot(ItemStack stack) {
        NBTTagCompound tag = getStackTag(stack);
        return tag.hasKey(TAG_MOD_SLOT) ? tag.getString(TAG_MOD_SLOT) : Omniwand.MOD_ID;
    }

    public static void setModSlot(ItemStack stack, String modName) {
        getStackTag(stack).setString(TAG_MOD_SLOT, modName);
    }

    public static NBTTagCompound getStackTag(ItemStack stack) {
        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        return stack.getTagCompound();
    }
}
