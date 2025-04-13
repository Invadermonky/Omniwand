package com.invadermonky.omniwand.config;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.util.ItemHelper;
import com.invadermonky.omniwand.util.LogHelper;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigTags {

    private static final HashMap<String, String> MOD_ALIASES = new HashMap<>();
    private static final HashSet<String> TRANSFORM_ITEMS = new HashSet<>(3);
    private static final HashMap<String, HashSet<String>> BLACKLIST = new HashMap<>();
    private static final HashMap<String, HashSet<String>> WHITELIST = new HashMap<>();

    private static final String MOD = "mod";
    private static final String ITEM = "item";
    private static final String NAME = "name";

    public static boolean shouldTransform(boolean isSneaking) {
        return isSneaking != ConfigHandler.sneakLocking;
    }

    /**
     * Returns the mod alias for the passed mod. If no alias is found, returns the passed string.
     *
     * @param modId The mod id to check
     * @return Any registered alias for the passed mod id
     */
    public static String getModAlias(String modId) {
        if (MOD_ALIASES.containsKey(modId)) {
            return MOD_ALIASES.get(modId);
        }
        return !modId.isEmpty() ? modId : Omniwand.MOD_ID;
    }

    public static boolean isTransformItem(ItemStack stack) {
        if (ItemHelper.isEmpty(stack)) return false;

        String itemName = ItemHelper.getRegistryName(stack);
        String itemId = ItemHelper.getItemId(stack);
        return TRANSFORM_ITEMS.contains(itemName) || TRANSFORM_ITEMS.contains(itemId);
    }

    /**
     * Compares the item against all configured filter lists. Returns true if the item can be attached, false otherwise.
     *
     * @param stack The ItemStack to query
     * @return true if the item is configured to be attached to the omniwand
     */
    @SuppressWarnings("ConstantConditions")
    public static boolean canItemStackAttach(ItemStack stack) {
        String itemMod = ItemHelper.getOwnerMod(stack);
        String itemName = ItemHelper.getRegistryName(stack);
        String itemId = ItemHelper.getItemId(stack);

        // If Transform Item
        if (TRANSFORM_ITEMS.contains(itemId) || TRANSFORM_ITEMS.contains(itemName)) {
            return true;
        }
        // If Whitelisted Item
        if (WHITELIST.get(ITEM).contains(itemId) || WHITELIST.containsKey(itemName)) {
            return true;
        }
        // If Blacklisted Item
        if (BLACKLIST.get(ITEM).contains(itemId) || BLACKLIST.get(ITEM).contains(itemName)) {
            return false;
        }
        // If Blacklisted Mod
        if (BLACKLIST.get(MOD).contains(itemMod)) {
            return false;
        }
        // If Whitelisted Mod
        if (WHITELIST.get(MOD).contains(itemMod)) {
            return true;
        }
        // If Blacklisted Name
        for (String name : BLACKLIST.get(NAME)) {
            if (itemName.toLowerCase().contains(name)) {
                return false;
            }
        }
        // If Whitelisted Name
        for (String name : WHITELIST.get(NAME)) {
            if (itemName.toLowerCase().contains(name)) {
                return true;
            }
        }
        return false;
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
        for (String item : ConfigHandler.transformItems) {
            item = item.replace(" ", "").trim();
            TRANSFORM_ITEMS.add(item);
        }
    }

    private static void syncFilterList(Map<String, HashSet<String>> filterMap, String[] filterConfig) {
        filterMap.clear();
        filterMap.put(MOD, new HashSet<String>());
        filterMap.put(ITEM, new HashSet<String>());
        filterMap.put(NAME, new HashSet<String>());

        Pattern pattern = Pattern.compile("^(mod|item|name)=(.+)$");
        for (String filter : filterConfig) {
            Matcher matcher = pattern.matcher(filter);
            if (matcher.find()) {
                switch (matcher.group(1).toLowerCase()) {
                    case MOD:
                        filterMap.get(MOD).add(matcher.group(2).replace(" ", "").trim());
                        break;
                    case ITEM:
                        filterMap.get(ITEM).add(matcher.group(2).replace(" ", "").trim());
                        break;
                    case NAME:
                        filterMap.get(NAME).add(matcher.group(2).replace(" ", "").trim());
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
