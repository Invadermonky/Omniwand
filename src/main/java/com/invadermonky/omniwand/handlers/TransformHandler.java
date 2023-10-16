package com.invadermonky.omniwand.handlers;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.init.RegistryOW;
import com.invadermonky.omniwand.network.MessageRevertWand;
import com.invadermonky.omniwand.util.NBTHelper;
import com.invadermonky.omniwand.util.References;
import gnu.trove.map.hash.THashMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

public class TransformHandler {
    public static final TransformHandler INSTANCE = new TransformHandler();
    public static final THashMap<String,String> modNames = new THashMap<>();

    public static boolean autoMode = true;

    @SubscribeEvent
    public void onItemDropped(ItemTossEvent event) {
        if(event.getPlayer().isSneaking()) {
            EntityItem e = event.getEntityItem();
            ItemStack stack = e.getItem();
            TransformHandler.removeItemFromWand(e, stack, false, e::setItem);
            autoMode = true;
        }
    }

    @SubscribeEvent
    public void onItemBroken(PlayerDestroyItemEvent event) {
        TransformHandler.removeItemFromWand(event.getEntityPlayer(), event.getOriginal(), true, (transform) -> {
            event.getEntityPlayer().setHeldItem(event.getHand(), transform);
            autoMode = true;
        });
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerSwing(PlayerInteractEvent.LeftClickEmpty event) {
        ItemStack stack = event.getItemStack();
        if(!ConfigHandler.crouchRevert || event.getEntityPlayer().isSneaking()) {
            if (stack != null && TransformHandler.isOmniwand(stack) && stack.getItem() != RegistryOW.OMNIWAND) {
                Omniwand.network.sendToServer(new MessageRevertWand());
                autoMode = true;
            }
        }
    }

    public static NBTTagCompound getWandNBTData(ItemStack stack) {
        return stack.getTagCompound().getCompoundTag(References.TAG_WAND_DATA);
    }

    public static String getModFromState(IBlockState state) {
        return getModOrAlias(state.getBlock().getRegistryName().getNamespace());
    }

    public static String getModFromStack(ItemStack stack) {
        return getModOrAlias(stack.getItem().getCreatorModId(stack));
    }

    public static String getModOrAlias(String mod) {
        return ConfigHandler.modAliases.getOrDefault(mod, mod);
    }

    public static ItemStack getTransformStackForMod(ItemStack stack, String mod) {
        if(!stack.hasTagCompound()) {
            return stack;
        } else {
            String currentMod = getModFromStack(stack);
            if(mod.equals(currentMod)) {
                return stack;
            } else {
                NBTTagCompound transformData = getWandNBTData(stack);
                return makeTransformedStack(stack, mod, transformData);
            }
        }
    }

    public static ItemStack makeTransformedStack(ItemStack currStack, String targetMod, NBTTagCompound transformData) {
        String currMod = NBTHelper.getString(currStack, References.TAG_ITEM_DEFINED_MOD, getModFromStack(currStack));
        NBTTagCompound currTag = new NBTTagCompound();
        currStack.writeToNBT(currTag);
        currTag = currTag.copy();

        if(currTag.hasKey("tag")) {
            currTag.getCompoundTag("tag").removeTag(References.TAG_WAND_DATA);
        }

        if(!currMod.equalsIgnoreCase(References.MINECRAFT) && !currMod.equalsIgnoreCase(Omniwand.MOD_ID)) {
            transformData.setTag(currMod, currTag);
        }

        ItemStack stack;
        NBTTagCompound stackTag;

        if(targetMod.equals(References.MINECRAFT)) {
            stack = new ItemStack(RegistryOW.OMNIWAND);
        } else {
            stackTag = transformData.getCompoundTag(targetMod);
            transformData.removeTag(targetMod);
            stack = new ItemStack(stackTag);

            if(stack.isEmpty())
                stack = new ItemStack(RegistryOW.OMNIWAND);
        }

        if(!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());

        stackTag = stack.getTagCompound();
        stackTag.setTag(References.TAG_WAND_DATA, transformData);
        stackTag.setBoolean(References.TAG_IS_TRANSFORMING, true);

        if(stack.getItem() != RegistryOW.OMNIWAND) {
            String displayName = stack.getDisplayName();
            if(stackTag.hasKey(References.TAG_WAND_DISPLAY_NAME))
                displayName = stackTag.getString(References.TAG_WAND_DISPLAY_NAME);
            else
                stackTag.setString(References.TAG_WAND_DISPLAY_NAME, displayName);

            stack.setStackDisplayName(TextFormatting.RESET + net.minecraft.util.text.translation.I18n.translateToLocalFormatted("omniwand:sudo_name", TextFormatting.GREEN + displayName + TextFormatting.RESET));
            //stack.setStackDisplayName(TextFormatting.RESET + I18n.format("omniwand:sudo_name", TextFormatting.GREEN + displayName + TextFormatting.RESET));
        }
        stack.setCount(1);
        return stack;
    }

    public static void removeItemFromWand(Entity entity, ItemStack stack, boolean isBroken, Consumer<ItemStack> consumer) {
        if(!stack.isEmpty() && isOmniwand(stack) && stack.getItem() != RegistryOW.OMNIWAND) {

            NBTTagCompound transformData = getWandNBTData(stack).copy();
            ItemStack transform = makeTransformedStack(stack, References.MINECRAFT, transformData);
            NBTTagCompound newTransformData = getWandNBTData(transform);

            String currMod = NBTHelper.getString(stack, References.TAG_ITEM_DEFINED_MOD, getModFromStack(stack));
            newTransformData.removeTag(currMod);

            if (!isBroken) {
                if (!entity.getEntityWorld().isRemote) {
                    EntityItem newItem = new EntityItem(entity.getEntityWorld(), entity.posX, entity.posY, entity.posZ, transform);
                    entity.getEntityWorld().spawnEntity(newItem);
                }

                ItemStack copy = stack.copy();
                NBTTagCompound copyTag = copy.getTagCompound();
                if (copyTag == null) {
                    copyTag = new NBTTagCompound();
                    copy.setTagCompound(copyTag);
                }

                copyTag.removeTag("display");
                String displayName = copyTag.getString(References.TAG_WAND_DISPLAY_NAME);

                if (!displayName.isEmpty() && !displayName.equals(copy.getDisplayName()))
                    copy.setStackDisplayName(displayName);

                copyTag.removeTag(References.TAG_IS_TRANSFORMING);
                copyTag.removeTag(References.TAG_AUTO_TRANSFORM);
                copyTag.removeTag(References.TAG_WAND_DISPLAY_NAME);
                copyTag.removeTag(References.TAG_WAND_DATA);
                copyTag.removeTag(References.TAG_ITEM_DEFINED_MOD);

                consumer.accept(copy);
            } else {
                consumer.accept(transform);
            }
        }
    }

    public static String getModNameForId(String modId) {
        modId = modId.toLowerCase(Locale.ENGLISH);
        return modNames.getOrDefault(modId, modId);
    }

    public static boolean isOmniwand(ItemStack stack) {
        if(stack.isEmpty())
            return false;
        else if(stack.getItem() == RegistryOW.OMNIWAND)
            return true;
        else
            return stack.hasTagCompound() && stack.getTagCompound().getBoolean(References.TAG_IS_TRANSFORMING);
    }

    static {
        for(Map.Entry<String, ModContainer> entry : Loader.instance().getIndexedModList().entrySet()) {
            modNames.put(entry.getKey().toLowerCase().toLowerCase(Locale.ENGLISH), entry.getValue().getName());
        }
    }
}
