package com.invadermonky.omniwand.recipes;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.config.ConfigTags;
import com.invadermonky.omniwand.registry.Registry;
import com.invadermonky.omniwand.util.WandHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;

public class AttachmentRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    public AttachmentRecipe() {
        this.setRegistryName(new ResourceLocation(Omniwand.MOD_ID, "attachment"));
    }

    @Override
    public boolean matches(InventoryCrafting inv, @NotNull World worldIn) {
        boolean foundWand = false;
        boolean foundTarget = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
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
    public @NotNull ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack wand = ItemStack.EMPTY;
        ItemStack target = ItemStack.EMPTY;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Registry.OMNIWAND) {
                    wand = stack;
                } else {
                    target = stack;
                }
            }
        }

        ItemStack wandCopy = wand.copy();
        NBTTagCompound wandData = WandHelper.getWandData(wandCopy);
        String mod = WandHelper.getModOrAlias(target);
        String modClean = mod;
        int i = 0;
        if (!ConfigTags.isTransformItem(target))
            mod = modClean + i++;

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

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public @NotNull ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
    }

    public boolean isTarget(ItemStack stack) {
        if (!stack.isEmpty() && stack.getCount() == 1 && !WandHelper.isOmniwand(stack)) {
            String mod = WandHelper.getModOrAlias(stack);
            return !mod.equals(Omniwand.MOD_ID) && ConfigTags.canItemStackAttach(stack);
        }
        return false;
    }
}
