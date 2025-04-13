package com.invadermonky.omniwand.registry;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.items.ItemWand;
import com.invadermonky.omniwand.recipes.AttachmentRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class Registry {

    public static Item OMNIWAND = new ItemWand();

    public static void registerItems() {
        GameRegistry.registerItem(OMNIWAND, "wand", Omniwand.MOD_ID);
    }

    public static void registerRecipes() {
        GameRegistry.addRecipe(new AttachmentRecipe());
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(
                new ItemStack(OMNIWAND),
                " DE", " OD", "O  ",
                'D', "gemDiamond",
                'E', new ItemStack(Item.enderPearl),
                'O', new ItemStack(Block.obsidian)
        ));
    }
}
