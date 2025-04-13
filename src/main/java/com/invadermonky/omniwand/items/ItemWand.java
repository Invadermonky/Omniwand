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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemWand extends Item {
    public ItemWand() {
        super(ConfigHandler.omniwandId);
        this.setUnlocalizedName("omniwand:wand");
        this.setTextureName("omniwand:wand");
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player) {
        ItemStack stack = player.getHeldItem();
        Omniwand.proxy.openWandGui(player, stack);
        return stack;
    }

    @Override
    public void addInformation(ItemStack wandStack, EntityPlayer par2EntityPlayer, List tooltip, boolean par4) {
        NBTTagCompound wandData = WandHelper.getWandData(wandStack);
        if (!ItemHelper.isEmpty(wandData) && GuiScreen.isShiftKeyDown()) {
            List<String> keys = new ArrayList<>(ItemHelper.getItemTagKeys(wandData));
            Collections.sort(keys);
            String currentMod = "";

            for (String key : keys) {
                ItemStack storedItem = ItemStack.loadItemStackFromNBT(wandData.getCompoundTag(key));
                if (!ItemHelper.isEmpty(storedItem)) {
                    String name = WandHelper.getDisplayNameCache(storedItem);
                    String mod = WandHelper.getModOrAlias(storedItem);
                    if (ConfigHandler.restrictTooltip) {
                        if (mod.equals(key) && ConfigTags.isTransformItem(storedItem)) {
                            name = EnumChatFormatting.GREEN + WandHelper.getModName(mod) + EnumChatFormatting.AQUA + " ┠> " + name;
                            tooltip.add(name);
                        }
                    } else {
                        // If current mod is not equal to new mod, adds a new header
                        if (!currentMod.equals(mod)) {
                            currentMod = mod;
                            tooltip.add(EnumChatFormatting.GREEN + WandHelper.getModName(currentMod));
                        }

                        name = (key.equals(mod) ? EnumChatFormatting.AQUA + " ┠>" : " ┠ ") + name;
                        tooltip.add(name);
                    }
                }
            }
        } else if (GuiScreen.isCtrlKeyDown()) {
            tooltip.add(I18n.getString("tooltip.omniwand:wand.ctrl.1"));
            tooltip.add(I18n.getString("tooltip.omniwand:wand.ctrl.2"));
            tooltip.add(I18n.getString("tooltip.omniwand:wand.ctrl.3"));
            tooltip.add(I18n.getString("tooltip.omniwand:wand.ctrl.4"));
        } else {
            tooltip.add(I18n.getString("tooltip.omniwand:wand.shift"));
            tooltip.add(I18n.getString("tooltip.omniwand:wand.ctrl"));
        }
    }
}
