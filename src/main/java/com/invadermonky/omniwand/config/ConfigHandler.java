package com.invadermonky.omniwand.config;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.util.LogHelper;
import com.invadermonky.omniwand.util.simplejson.JSONArray;
import com.invadermonky.omniwand.util.simplejson.JSONObject;
import com.invadermonky.omniwand.util.simplejson.parser.JSONParser;
import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.Configuration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.invadermonky.omniwand.util.libs.LibConfigs.*;

public class ConfigHandler {
    public static int omniwandId = 7987;
    public static boolean autoTransform = true;
    public static boolean enableDebug = false;
    public static boolean sneakLocking = true;
    public static boolean restrictTooltip = false;
    public static String[] modAliases = new String[0];
    public static String[] transformItems = new String[0];
    public static String[] attachBlacklist = new String[0];
    public static String[] attachWhitelist = new String[0];

    public static void init() {
        File configFile = new File(Paths.get(Loader.instance().getConfigDir().getAbsolutePath(), Omniwand.MOD_ID + ".json").toString());
        if (!configFile.exists()) {
            writeConfig(configFile);
        }
        if (configFile.exists()) {
            readConfig(configFile);
            ConfigTags.syncConfig();
        } else {
            LogHelper.error("Failed to load configuration.");
        }
    }

    private static void writeConfig(File configFile) {
        try {
            InputStream in = Omniwand.class.getResourceAsStream("/assets/" + Omniwand.MOD_ID + "/data/omniwand.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readConfig(File configFile) {
        try {
            JSONObject configJson = (JSONObject) new JSONParser().parse(new FileReader(configFile));

            JSONArray item = (JSONArray) configJson.get(Configuration.CATEGORY_ITEM);
            for (Object object : item) {
                JSONObject obj = (JSONObject) object;
                if (obj.containsKey(omniwandItemIdName)) {
                    omniwandId = ((Long) obj.get(omniwandItemIdName)).intValue();
                }
            }

            JSONArray general = (JSONArray) configJson.get(Configuration.CATEGORY_GENERAL);
            for (Object object : general) {
                JSONObject obj = (JSONObject) object;
                if (obj.containsKey(enableDebugName)) {
                    enableDebug = (Boolean) ((JSONObject) object).get(enableDebugName);
                }
                if (obj.containsKey(autoTransformName)) {
                    autoTransform = (Boolean) ((JSONObject) object).get(autoTransformName);
                } else if (obj.containsKey(sneakLockingName)) {
                    sneakLocking = (Boolean) ((JSONObject) object).get(sneakLockingName);
                } else if (obj.containsKey(restrictTooltipName)) {
                    restrictTooltip = (Boolean) ((JSONObject) object).get(restrictTooltipName);
                } else if (obj.containsKey(modAliasesName)) {
                    modAliases = getStringArray(modAliasesName, obj);
                } else if (obj.containsKey(transformItemsName)) {
                    transformItems = getStringArray(transformItemsName, obj);
                } else if (obj.containsKey(attachBlacklistName)) {
                    attachBlacklist = getStringArray(attachBlacklistName, obj);
                } else if (obj.containsKey(attachWhitelistName)) {
                    attachWhitelist = getStringArray(attachWhitelistName, obj);
                }
            }
        } catch (Exception e) {
            LogHelper.error("Malformed config json detected. Regenerating configuration.");
            try {
                Files.delete(configFile.toPath());
                init();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private static String[] getStringArray(String key, JSONObject jsonObject) throws Exception {
        if (jsonObject.containsKey(key)) {
            JSONArray jsonArray = (JSONArray) jsonObject.get(key);
            String[] array = new String[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                array[i] = (String) jsonArray.get(i);
            }
            return array;
        } else {
            return new String[0];
        }
    }
}
