package com.invadermonky.omniwand.registry;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.items.ItemWand;
import com.invadermonky.omniwand.recipes.AttachmentRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class Registry {

    public static Item OMNIWAND = new ItemWand();

    public static void registerItems() {
        GameRegistry.registerItem(OMNIWAND, "wand", Omniwand.MOD_ID);
    }

    public static void registerRecipes() {
        GameRegistry.addRecipe(new AttachmentRecipe());
    }
}
