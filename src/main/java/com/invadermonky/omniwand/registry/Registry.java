package com.invadermonky.omniwand.registry;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.items.ItemWand;
import com.invadermonky.omniwand.recipes.AttachmentRecipe;
import com.invadermonky.omniwand.util.LogHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Omniwand.MOD_ID)
public class Registry {
    public static Item OMNIWAND;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(OMNIWAND = new ItemWand());
    }

    @SubscribeEvent
    public static void registerItemRenders(ModelRegistryEvent event) {
        LogHelper.debug("Registering model of item: " + OMNIWAND.delegate.name());
        ModelLoader.setCustomModelResourceLocation(OMNIWAND, 0, new ModelResourceLocation(OMNIWAND.delegate.name(), "inventory"));
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        registry.register(new AttachmentRecipe());
    }
}
