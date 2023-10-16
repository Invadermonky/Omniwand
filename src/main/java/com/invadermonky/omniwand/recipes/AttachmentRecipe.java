package com.invadermonky.omniwand.recipes;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.handlers.ConfigHandler;
import com.invadermonky.omniwand.handlers.TransformHandler;
import com.invadermonky.omniwand.init.RegistryOW;
import com.invadermonky.omniwand.util.NBTHelper;
import com.invadermonky.omniwand.util.References;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class AttachmentRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    public AttachmentRecipe() {
        setRegistryName(Omniwand.MOD_ID, "attachment");
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean foundTool = false;
        boolean foundTarget = false;

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if(!stack.isEmpty()) {
                if(this.isTarget(stack)) {

                    if(foundTarget)
                        return false;

                    foundTarget = true;
                } else {
                    if(stack.getItem() != RegistryOW.OMNIWAND || foundTool)
                        return false;

                    foundTool = true;
                }
            }
        }
        return foundTool && foundTarget;
    }

    public boolean isTarget(ItemStack stack) {
        if (!stack.isEmpty() && !TransformHandler.isOmniwand(stack)) {
            String mod = TransformHandler.getModFromStack(stack);
            ResourceLocation registryNameRL = stack.getItem().getRegistryName();
            String registryName = registryNameRL.toString();

            //If Minecraft
            if (mod.equals(References.MINECRAFT)) {
                return false;
            }
            //If Item is transform item
            else if(ConfigHandler.transformItems.contains(registryName) || ConfigHandler.transformItems.contains(registryName + ":" + stack.getItemDamage())) {
                return true;
            }
            //If Item whitelisted
            else if (ConfigHandler.whitelistedItems.contains(registryName) || ConfigHandler.whitelistedItems.contains(registryName + ":" + stack.getItemDamage())) {
                return true;
            }
            //If Mod blacklisted
            else if (ConfigHandler.blacklistedMods.contains(mod)) {
                return false;
            }
            //Name whitelist
            else {
                String itemName = registryNameRL.getPath().toLowerCase();
                for(String name : ConfigHandler.whiteListedNames) {
                    if(itemName.contains(name))
                        return true;
                }
                return false;
            }
        }
        return false;
    }

    private boolean isTransform(ItemStack stack) {
        if(stack.isEmpty())
            return false;

        String registryName = stack.getItem().getRegistryName().toString();
        return ConfigHandler.transformItems.contains(registryName) || ConfigHandler.transformItems.contains(registryName + ":" + stack.getItemDamage());
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack tool = ItemStack.EMPTY;
        ItemStack target = ItemStack.EMPTY;

        for(int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == RegistryOW.OMNIWAND)
                    tool = stack;
                else
                    target = stack;
            }
        }

        ItemStack copy = tool.copy();
        NBTTagCompound tagCompound = copy.getTagCompound();

        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            copy.setTagCompound(tagCompound);
        }

        if (!tagCompound.hasKey(References.TAG_WAND_DATA)) {
            tagCompound.setTag(References.TAG_WAND_DATA, new NBTTagCompound());
        }

        NBTTagCompound transformData = tagCompound.getCompoundTag(References.TAG_WAND_DATA);
        String mod = TransformHandler.getModFromStack(target);
        String modClean = mod;

        if(!isTransform(target) || transformData.hasKey(mod)) {
            mod = modClean + 0;
            for (int i = 1; transformData.hasKey(mod); i++) {
                mod = modClean + i;
            }
        }

        NBTHelper.setString(target, References.TAG_ITEM_DEFINED_MOD, mod);
        NBTTagCompound modCmp = new NBTTagCompound();
        target.writeToNBT(modCmp);
        transformData.setTag(mod, modCmp);
        return copy;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
    }
}
