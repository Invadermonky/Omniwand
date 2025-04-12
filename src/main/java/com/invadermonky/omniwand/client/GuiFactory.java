package com.invadermonky.omniwand.client;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

import java.util.Set;

public class GuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {
    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return OmniwandConfig.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Deprecated
    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

    public static class OmniwandConfig extends GuiConfig {
        public OmniwandConfig(GuiScreen parentScreen) {
            super(
                    parentScreen,
                    new ConfigElement(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                    Omniwand.MOD_ID,
                    false,
                    false,
                    OmniwandConfig.getAbridgedConfigPath(ConfigHandler.config.toString()));
        }
    }
}
