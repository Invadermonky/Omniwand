package com.invadermonky.omniwand.items;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.config.ConfigHandler;
import com.invadermonky.omniwand.config.ConfigTags;
import com.invadermonky.omniwand.util.ItemHelper;
import com.invadermonky.omniwand.util.WandHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemWand extends Item {
    public ItemWand() {
        this.setRegistryName(new ResourceLocation(Omniwand.MOD_ID, "wand"));
        this.setUnlocalizedName(this.getRegistryName().toString());
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxStackSize(1);
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull ItemStack stack, @NotNull World worldIn, @NotNull EntityPlayer playerIn, @NotNull EnumHand hand) {
        Omniwand.proxy.openWandGui(playerIn, stack);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void addInformation(@NotNull ItemStack wandStack, @NotNull EntityPlayer playerIn, @NotNull List<String> tooltip, boolean advanced) {
        NBTTagCompound wandData = WandHelper.getWandData(wandStack);
        if (!wandData.getKeySet().isEmpty() && GuiScreen.isShiftKeyDown()) {
            List<String> keys = new ArrayList<>(wandData.getKeySet());
            Collections.sort(keys);
            String currentMod = "";

            for (String key : keys) {
                ItemStack storedItem = ItemStack.loadItemStackFromNBT(wandData.getCompoundTag(key));
                if (!ItemHelper.isEmpty(storedItem)) {
                    String name = WandHelper.getDisplayNameCache(storedItem);
                    String mod = WandHelper.getModOrAlias(storedItem);
                    if (ConfigHandler.restrictTooltip) {
                        if (mod.equals(key) && ConfigTags.isTransformItem(storedItem)) {
                            name = TextFormatting.GREEN + WandHelper.getModName(mod) + TextFormatting.AQUA + " ┠> " + name;
                            tooltip.add(name);
                        }
                    } else {
                        //If current mod is not equal to new mod, adds a new header
                        if (!currentMod.equals(mod)) {
                            currentMod = mod;
                            tooltip.add(TextFormatting.GREEN + WandHelper.getModName(currentMod));
                        }

                        name = (key.equals(mod) ? TextFormatting.AQUA + " ┠>" : " ┠ ") + name;
                        tooltip.add(name);
                    }
                }
            }
        } else {
            tooltip.add(I18n.format("tooltip.omniwand:shiftinfo"));
        }
    }
}
