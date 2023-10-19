package com.invadermonky.omniwand.handlers;

import com.google.common.collect.Sets;
import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.util.References;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {
    public static Configuration config = null;

    public static boolean enableTransform;
    public static boolean offhandTransform;
    public static boolean restrictTooltip;
    public static boolean crouchRevert;
    public static boolean alternateAppearance;
    public static THashSet<String> transformItems;
    public static THashMap<String,String> modAliases;
    public static THashSet<String> blacklistedMods;
    public static THashSet<String> whitelistedItems;
    public static THashSet<String> whiteListedNames;

    public static void preInit() {
        File configFile = new File(Paths.get(Loader.instance().getConfigDir().toString(), Omniwand.MOD_ID + ".cfg").toString());
        config = new Configuration(configFile);
        config.load();
        setPropertyOrder();
        loadConfig();
    }

    private static void setPropertyOrder() {
        List<String> propOrder = new ArrayList<>();

        propOrder.add(References.ENABLE_TRANSFORM.getName());
        propOrder.add(References.RESTRICT_TOOLTIP.getName());
        propOrder.add(References.OFFHAND_TRANSFORM.getName());
        propOrder.add(References.CROUCH_REVERT.getName());
        propOrder.add(References.ALT_APPEARANCE.getName());
        propOrder.add(References.TRANSFORM_ITEMS.getName());
        propOrder.add(References.MOD_ALIASES.getName());
        propOrder.add(References.BLACKLIST_MODS.getName());
        propOrder.add(References.WHITELIST_ITEMS.getName());
        propOrder.add(References.WHITELIST_NAMES.getName());

        config.setCategoryPropertyOrder(Configuration.CATEGORY_GENERAL, propOrder);
    }

    public static void loadConfig() {
        enableTransform = config.getBoolean(
                References.ENABLE_TRANSFORM.getName(),
                Configuration.CATEGORY_GENERAL,
                References.ENABLE_TRANSFORM.getBoolean(),
                References.ENABLE_TRANSFORM.getComment()
        );

        restrictTooltip = config.getBoolean(
                References.RESTRICT_TOOLTIP.getName(),
                Configuration.CATEGORY_GENERAL,
                References.RESTRICT_TOOLTIP.getBoolean(),
                References.RESTRICT_TOOLTIP.getComment()
        );

        offhandTransform = config.getBoolean(
                References.OFFHAND_TRANSFORM.getName(),
                Configuration.CATEGORY_GENERAL,
                References.OFFHAND_TRANSFORM.getBoolean(),
                References.OFFHAND_TRANSFORM.getComment()
        );

        crouchRevert = config.getBoolean(
                References.CROUCH_REVERT.getName(),
                Configuration.CATEGORY_GENERAL,
                References.CROUCH_REVERT.getBoolean(),
                References.CROUCH_REVERT.getComment()
        );

        alternateAppearance = config.getBoolean(
                References.ALT_APPEARANCE.getName(),
                Configuration.CATEGORY_GENERAL,
                References.ALT_APPEARANCE.getBoolean(),
                References.ALT_APPEARANCE.getComment()
        );

        String[] a_transformItems = config.getStringList(
                References.TRANSFORM_ITEMS.getName(),
                Configuration.CATEGORY_GENERAL,
                References.TRANSFORM_ITEMS.getDefaults(),
                References.TRANSFORM_ITEMS.getComment()
        );

        String[] a_modAliases = config.getStringList(
                References.MOD_ALIASES.getName(),
                Configuration.CATEGORY_GENERAL,
                References.MOD_ALIASES.getDefaults(),
                References.MOD_ALIASES.getComment()
        );

        String[] a_blacklistedMods = config.getStringList(
                References.BLACKLIST_MODS.getName(),
                Configuration.CATEGORY_GENERAL,
                References.BLACKLIST_MODS.getDefaults(),
                References.BLACKLIST_MODS.getComment()
        );

        String[] a_whitelistedItems = config.getStringList(
                References.WHITELIST_ITEMS.getName(),
                Configuration.CATEGORY_GENERAL,
                References.WHITELIST_ITEMS.getDefaults(),
                References.WHITELIST_ITEMS.getComment()
        );

        String[] a_whitelistedNames = config.getStringList(
                References.WHITELIST_NAMES.getName(),
                Configuration.CATEGORY_GENERAL,
                References.WHITELIST_NAMES.getDefaults(),
                References.WHITELIST_NAMES.getComment()
        );

        //Converting Arrays to HashSets/HashMaps
        transformItems = getStringHashSet(a_transformItems);
        modAliases = getStringHashMap(a_modAliases);
        blacklistedMods = getStringHashSet(a_blacklistedMods);
        whitelistedItems = getStringHashSet(a_whitelistedItems);
        whiteListedNames = getStringHashSet(a_whitelistedNames);

        if(config.hasChanged())
            config.save();
    }

    private static THashMap<String,String> getStringHashMap(String[] array) {
        THashMap<String,String> map = new THashMap<>();
        for(String entry : array) {
            String[] split = entry.split("=");
            if(split.length != 2)
                continue;
            map.put(split[0], split[1]);
        }
        return map;
    }

    private static THashSet<String> getStringHashSet(String[] array) {
        return new THashSet<>(Sets.newHashSet(array));
    }

    public static EnumHand getConfiguredHand() {
        return offhandTransform ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
        if(event.getModID().equals(Omniwand.MOD_ID))
            loadConfig();
    }
}
