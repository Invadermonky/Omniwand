package com.invadermonky.omniwand.config;

import com.invadermonky.omniwand.util.LogHelper;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigTags {
    private static final THashMap<String, String> MOD_ALIASES = new THashMap<>();
    private static final THashSet<String> TRANSFORM_ITEMS = new THashSet<>(3);
    private static final THashMap<String, THashSet<String>> BLACKLIST = new THashMap<>();
    private static final THashMap<String, THashSet<String>> WHITELIST = new THashMap<>();

    private static final String MOD = "mod";
    private static final String ITEM = "item";
    private static final String NAME = "name";

    /**
     * Returns the mod alias for the passed mod. If no alias is found, returns the passed string.
     *
     * @param modId The mod id to check
     * @return Any registered alias for the passed mod id
     */
    public static String getModAlias(String modId) {

        return MOD_ALIASES.getOrDefault(modId, modId);
    }

    public static boolean isTransformItem(ItemStack stack) {
        if (stack.isEmpty())
            return false;

        String itemId = stack.getItem().getRegistryName().toString();
        return TRANSFORM_ITEMS.contains(itemId) || TRANSFORM_ITEMS.contains(itemId + ":" + stack.getMetadata());
    }

    /**
     * Compares the item against all configured filter lists. Returns true if the item can be attached, false otherwise.
     *
     * @param stack The ItemStack to query
     * @return true if the item is configured to be attached to the omniwand
     */
    @SuppressWarnings("ConstantConditions")
    public static boolean canItemStackAttach(ItemStack stack) {
        String itemMod = stack.getItem().getCreatorModId(stack);
        String itemId = stack.getItem().getRegistryName().toString();
        String metaId = itemId + ":" + stack.getMetadata();
        String itemName = stack.getItem().getRegistryName().getPath();

        //If Transform Item
        if (TRANSFORM_ITEMS.contains(itemId) || TRANSFORM_ITEMS.contains(metaId)) {
            return true;
        }
        //If Whitelisted Item
        if (WHITELIST.get(ITEM).contains(itemId) || WHITELIST.get(ITEM).contains(metaId)) {
            return true;
        }
        //If Blacklisted Item
        if (BLACKLIST.get(ITEM).contains(itemId) || BLACKLIST.get(ITEM).contains(itemId)) {
            return false;
        }
        //If Blacklisted Mod
        if (BLACKLIST.get(MOD).contains(itemMod)) {
            return false;
        }
        //If Whitelisted Mod
        if (WHITELIST.get(MOD).contains(itemMod)) {
            return true;
        }
        //If Blacklisted Name
        for (String name : BLACKLIST.get(NAME)) {
            if (itemName.contains(name)) {
                return false;
            }
        }
        //If Whitelisted Name
        for (String name : WHITELIST.get(NAME)) {
            if (itemName.contains(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the hand that the omniwand will transform in. Holding in the opposite hand will block auto-transforming.
     */
    public static EnumHand getConfiguredHand() {
        return ConfigHandler.offhandTransform ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
    }


    private static void syncAliases() {
        MOD_ALIASES.clear();
        Pattern pattern = Pattern.compile("^([^=]+)=([^=]+)$");
        for (String alias : ConfigHandler.modAliases) {
            Matcher matcher = pattern.matcher(alias);
            if (matcher.find()) {
                MOD_ALIASES.put(matcher.group(1), matcher.group(2));
            } else {
                LogHelper.error("Invalid mod alias string: " + alias);
            }
        }
    }

    private static void syncTransformItems() {
        TRANSFORM_ITEMS.clear();
        Pattern pattern = Pattern.compile("^([^:\\s]+:[^:\\s]+:?\\d*)$");
        for (String item : ConfigHandler.transformItems) {
            item = item.replace(" ", "").trim();
            Matcher matcher = pattern.matcher(item);
            if (matcher.find()) {
                TRANSFORM_ITEMS.add(item);
            } else {
                LogHelper.error("Invalid transform item string: " + item);
            }
        }
    }

    private static void syncFilterList(THashMap<String, THashSet<String>> filterMap, String[] filterConfig) {
        filterMap.clear();
        filterMap.put(MOD, new THashSet<>());
        filterMap.put(ITEM, new THashSet<>());
        filterMap.put(NAME, new THashSet<>());

        Pattern pattern = Pattern.compile("^(mod|item|name)=(.+)$");
        for (String filter : filterConfig) {
            Matcher matcher = pattern.matcher(filter);
            if (matcher.find()) {
                switch (matcher.group(1).toLowerCase()) {
                    case MOD:
                        filterMap.putIfAbsent(MOD, new THashSet<>());
                        filterMap.get(MOD).add(matcher.group(2));
                        break;
                    case ITEM:
                        filterMap.putIfAbsent(ITEM, new THashSet<>());
                        filterMap.get(ITEM).add(matcher.group(2));
                        break;
                    case NAME:
                        filterMap.putIfAbsent(NAME, new THashSet<>());
                        filterMap.get(NAME).add(matcher.group(2));
                        break;
                }
            } else {
                LogHelper.error("Invalid filter string: " + filter);
            }
        }
    }

    public static void syncConfig() {
        syncAliases();
        syncTransformItems();
        syncFilterList(BLACKLIST, ConfigHandler.attachBlacklist);
        syncFilterList(WHITELIST, ConfigHandler.attachWhitelist);
    }

    static {
        syncConfig();
    }
}
