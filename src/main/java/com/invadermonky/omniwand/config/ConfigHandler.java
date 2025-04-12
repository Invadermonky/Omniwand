package com.invadermonky.omniwand.config;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.util.libs.LibConfigs;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.nio.file.Paths;

public class ConfigHandler {
    public static boolean autoTransform = true;
    public static boolean offhandTransform = true;
    public static boolean restrictTooltip = true;
    public static boolean crouchRevert = false;

    public static String[] modAliases;
    public static String[] transformItems;
    public static String[] attachBlacklist;
    public static String[] attachWhitelist;
    public static String[] nameOverrides;

    public static Configuration config;

    public static void init() {
        File configFile = new File(Paths.get(Loader.instance().getConfigDir().toString(), Omniwand.MOD_ID + ".cfg").toString());
        config = new Configuration(configFile);
        config.load();
        syncConfig();
    }

    public static void syncConfig() {
        autoTransform = config.getBoolean(LibConfigs.autoTransformName, Configuration.CATEGORY_GENERAL, true, LibConfigs.autoTransformComment);
        offhandTransform = config.getBoolean(LibConfigs.offhandTransformName, Configuration.CATEGORY_GENERAL, false, LibConfigs.offhandTransformComment);
        restrictTooltip = config.getBoolean(LibConfigs.restrictTooltipName, Configuration.CATEGORY_GENERAL, true, LibConfigs.restrictTooltipComment);
        crouchRevert = config.getBoolean(LibConfigs.crouchRevertName, Configuration.CATEGORY_GENERAL, false, LibConfigs.crouchRevertComment);

        modAliases = config.getStringList(LibConfigs.modAliasesName, Configuration.CATEGORY_GENERAL, LibConfigs.modAliasesDefault, LibConfigs.modAliasesComment);
        transformItems = config.getStringList(LibConfigs.transformItemsName, Configuration.CATEGORY_GENERAL, LibConfigs.transformItemsDefault, LibConfigs.transformItemsComment);
        attachBlacklist = config.getStringList(LibConfigs.attachBlacklistName, Configuration.CATEGORY_GENERAL, LibConfigs.attachBlacklistDefault, LibConfigs.attachBlacklistComment);
        attachWhitelist = config.getStringList(LibConfigs.attachWhitelistName, Configuration.CATEGORY_GENERAL, LibConfigs.attachWhitelistDefault, LibConfigs.attachWhitelistComment);
        nameOverrides = config.getStringList(LibConfigs.nameOverridesName, Configuration.CATEGORY_GENERAL, LibConfigs.nameOverridesDefault, LibConfigs.nameOverridesComment);

        if (config.hasChanged()) {
            config.save();
        }
    }


    @Mod.EventBusSubscriber
    public static class ConfigChangeListener {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Omniwand.MOD_ID)) {
                syncConfig();
                ConfigTags.syncConfig();
            }
        }
    }
}
