package com.invadermonky.omniwand.items;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.handlers.ConfigHandler;
import com.invadermonky.omniwand.handlers.TransformHandler;
import com.invadermonky.omniwand.recipes.AttachmentRecipe;
import com.invadermonky.omniwand.util.References;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemWand extends Item {
    protected AttachmentRecipe attachmentRecipe;

    public ItemWand(String unlocName) {
        setRegistryName(unlocName);
        setCreativeTab(CreativeTabs.TOOLS);
        setTranslationKey(new ResourceLocation(Omniwand.MOD_ID, unlocName).toString());
        setMaxStackSize(1);
        this.attachmentRecipe = new AttachmentRecipe();
    }

    public IRecipe getAttachmentRecipe() {
        return this.attachmentRecipe;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        Omniwand.proxy.openWandGui(playerIn, stack);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey(References.TAG_WAND_DATA)) {
            NBTTagCompound data = stack.getTagCompound().getCompoundTag(References.TAG_WAND_DATA);

            if(data.getKeySet().size() != 0 && GuiScreen.isShiftKeyDown()) {

                List<String> keys = new ArrayList<>(data.getKeySet());
                Collections.sort(keys);
                String currentMod = "";

                for(String key : keys) {
                    NBTTagCompound compoundTag = data.getCompoundTag(key);

                    if(compoundTag != null) {
                        ItemStack modStack = new ItemStack(compoundTag);

                        if(!modStack.isEmpty()) {
                            String name = modStack.getDisplayName();
                            String mod = TransformHandler.getModFromStack(modStack);
                            String registryName = modStack.getItem().getRegistryName().toString();

                            if(modStack.hasTagCompound() && modStack.getTagCompound().hasKey(References.TAG_WAND_DISPLAY_NAME))
                                name = modStack.getTagCompound().getString(References.TAG_WAND_DISPLAY_NAME);

                            if(ConfigHandler.restrictTooltip) {
                                if(!currentMod.equals(mod) && TransformHandler.modNames.containsKey(key)) {
                                    if (ConfigHandler.transformItems.contains(registryName) || ConfigHandler.transformItems.contains(registryName + ":" + modStack.getItemDamage())) {
                                        name = TextFormatting.GREEN + TransformHandler.getModNameForId(mod) + TextFormatting.AQUA + " ┠> " + name;
                                        tooltip.add(name);
                                    }
                                }
                            } else {
                                if (!currentMod.equals(mod))
                                    tooltip.add(TextFormatting.GREEN + TransformHandler.getModNameForId(mod));

                                if (TransformHandler.modNames.containsKey(key)) {
                                    String modStackRegistryName = modStack.getItem().getRegistryName().toString();
                                    if (ConfigHandler.transformItems.contains(modStackRegistryName) || ConfigHandler.transformItems.contains(modStackRegistryName + ":" + modStack.getItemDamage()))
                                        name = TextFormatting.AQUA + " ┠>" + name;
                                } else {
                                    name = " ┠ " + name;
                                }
                                tooltip.add(name);
                            }
                            currentMod = mod;
                        }
                    }
                }
            } else {
                tooltip.add(I18n.format("tooltip.omniwand:shiftinfo"));
            }
        }
    }
}
