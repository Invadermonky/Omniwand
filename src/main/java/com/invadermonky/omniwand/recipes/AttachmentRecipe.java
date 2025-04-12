package com.invadermonky.omniwand.recipes;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.config.ConfigTags;
import com.invadermonky.omniwand.registry.Registry;
import com.invadermonky.omniwand.util.ItemHelper;
import com.invadermonky.omniwand.util.WandHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class AttachmentRecipe implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean foundWand = false;
        boolean foundTarget = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!ItemHelper.isEmpty(stack)) {
                if (this.isTarget(stack) && !foundTarget) {
                    foundTarget = true;
                } else if (stack.getItem() == Registry.OMNIWAND && !foundWand) {
                    foundWand = true;
                } else {
                    return false;
                }
            }
        }
        return foundWand && foundTarget;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack wand = null;
        ItemStack target = null;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!ItemHelper.isEmpty(stack)) {
                if (stack.getItem() == Registry.OMNIWAND) {
                    wand = stack;
                } else {
                    target = stack;
                }
            }
        }

        if (!ItemHelper.isEmpty(wand) && !ItemHelper.isEmpty(target)) {
            ItemStack wandCopy = wand.copy();
            NBTTagCompound wandData = WandHelper.getWandData(wandCopy);
            String mod = WandHelper.getModOrAlias(target);
            String modClean = mod;
            int i = 0;
            if (!ConfigTags.isTransformItem(target)) {
                mod = modClean + i++;
            }

            while (wandData.hasKey(mod)) {
                mod = modClean + i++;
            }

            WandHelper.setIsTransforming(target, false);
            NBTTagCompound targetCmp = new NBTTagCompound();
            target.writeToNBT(targetCmp);
            wandData.setTag(mod, targetCmp);
            WandHelper.setWandData(wandCopy, wandData);
            WandHelper.setIsTransforming(wandCopy, false);
            WandHelper.setAutoMode(wandCopy, true);
            return wandCopy;
        }
        return null;
    }

    @Override
    public int getRecipeSize() {
        return 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    public boolean isTarget(ItemStack stack) {
        if (!ItemHelper.isEmpty(stack) && stack.stackSize == 1 && !WandHelper.isOmniwand(stack)) {
            String mod = WandHelper.getModOrAlias(stack);
            return !mod.equals(Omniwand.MOD_ID) && ConfigTags.canItemStackAttach(stack);
        }
        return false;
    }
}
