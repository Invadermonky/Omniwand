package com.invadermonky.omniwand.init;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.handlers.ConfigHandler;
import com.invadermonky.omniwand.items.ItemWand;
import com.invadermonky.omniwand.util.LogHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Omniwand.MOD_ID)
public class RegistryOW {
    public static Item OMNIWAND;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        OMNIWAND = new ItemWand("wand");
        registry.register(OMNIWAND);
    }

    @SubscribeEvent
    public static void registerItemRenders(ModelRegistryEvent event) {
        LogHelper.debug("Registering model of item: " + OMNIWAND.delegate.name());
        ModelResourceLocation loc;
        
        if(ConfigHandler.alternateAppearance)
            loc = new ModelResourceLocation(new ResourceLocation(Omniwand.MOD_ID, "wand_alt"), "inventory");
        else
            loc = new ModelResourceLocation(OMNIWAND.delegate.name(), "inventory");

        ModelLoader.setCustomModelResourceLocation(OMNIWAND, 0, loc);
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();
        registry.register(((ItemWand) OMNIWAND).getAttachmentRecipe());
    }
}
