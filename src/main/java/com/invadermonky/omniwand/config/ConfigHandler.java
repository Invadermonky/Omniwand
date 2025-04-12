package com.invadermonky.omniwand.config;

import java.io.File;
import java.nio.file.Paths;

import net.minecraftforge.common.config.Configuration;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.util.libs.LibConfigs;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {
    public static boolean autoTransform = true;
    public static boolean sneakTransform = false;
    public static boolean restrictTooltip = true;
    public static boolean crouchRevert = false;
    public static String[] modAliases;
    public static String[] transformItems;
    public static String[] attachBlacklist;
    public static String[] attachWhitelist;

    public static Configuration config;

    public static void init() {
        File configFile = new File(Paths.get(Loader.instance().getConfigDir().toString(), Omniwand.MOD_ID + ".cfg").toString());
        config = new Configuration(configFile);
        config.load();
        syncConfig();
    }

    public static void syncConfig() {
        autoTransform = config.getBoolean(LibConfigs.autoTransformName, Configuration.CATEGORY_GENERAL, true, LibConfigs.autoTransformComment);
        crouchRevert = config.getBoolean(LibConfigs.crouchRevertName, Configuration.CATEGORY_GENERAL, false, LibConfigs.crouchRevertComment);
        restrictTooltip = config.getBoolean(LibConfigs.restrictTooltipName, Configuration.CATEGORY_GENERAL, true, LibConfigs.restrictTooltipComment);
        sneakTransform = config.getBoolean(LibConfigs.sneakTransformName, Configuration.CATEGORY_GENERAL, false, LibConfigs.sneakTransformComment);

        attachBlacklist = config.getStringList(LibConfigs.attachBlacklistName, Configuration.CATEGORY_GENERAL, LibConfigs.attachBlacklistDefault, LibConfigs.attachBlacklistComment);
        attachWhitelist = config.getStringList(LibConfigs.attachWhitelistName, Configuration.CATEGORY_GENERAL, LibConfigs.attachWhitelistDefault, LibConfigs.attachWhitelistComment);
        modAliases = config.getStringList(LibConfigs.modAliasesName, Configuration.CATEGORY_GENERAL, LibConfigs.modAliasesDefault, LibConfigs.modAliasesComment);
        transformItems = config.getStringList(LibConfigs.transformItemsName, Configuration.CATEGORY_GENERAL, LibConfigs.transformItemsDefault, LibConfigs.transformItemsComment);

        if(config.hasChanged())
            config.save();
    }

    public static class ConfigChangeListener {

        @SubscribeEvent
        public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.modID.equals(Omniwand.MOD_ID)) {
                syncConfig();
                ConfigTags.syncConfig();
            }
        }
    }
}
