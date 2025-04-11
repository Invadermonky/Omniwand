package com.invadermonky.omniwand.config;

import com.invadermonky.omniwand.Omniwand;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Omniwand.MOD_ID)
public class ConfigHandler {
    @Config.Name("Auto Transform")
    @Config.Comment("Enables Omniwand auto-transform when looking at blocks.")
    public static boolean autoTransform = true;

    @Config.Name("Offhand Transform")
    @Config.Comment("Omniwand will only transform when held in the offhand.")
    public static boolean offhandTransform = false;

    @Config.Name("Restrict Tooltip")
    @Config.Comment("Restricts the Omniwand tooltip to only show items that used for auto-transforms.")
    public static boolean restrictTooltip = true;

    @Config.Name("Revert Requires Crouch")
    @Config.Comment("Omniwand requires crouch + swing to revert from a transformed item.")
    public static boolean crouchRevert = false;

    @Config.Name("Mod Aliases")
    @Config.Comment
            ({
                    "List of mod aliases used for Omniwand transforming.",
                    "  Format: modid=aliasmodid"
            })
    public static String[] modAliases = new String[]{
            "ae2stuff=appliedenergistics2",
            "animus=bloodmagic",
            "bloodarsenal=bloodmagic",
            "buildcrafttransport=buildcraft",
            "buildcraftfactory=buildcraft",
            "buildcraftsilicon=buildcraft",
            "deepresonance=rftools",
            "immersivetech=immersiveengineering",
            "immersivepetrolium=immersiveengineering",
            "industrialforegoing=teslacorelib",
            "integrateddynamics=integratedtunnels",
            "mekanismgenerators=mekanism",
            "mekanismtools=mekanism",
            "nautralpledge=botania",
            "redstonearsenal=thermalfoundation",
            "rftoolsdim=rftools",
            "rftoolspower=rftools",
            "rftoolscontrol=rftools",
            "thermalcultivation=thermalfoundation",
            "thermaldynamics=thermalfoundation",
            "thermalexpansion=thermalfoundation",
            "threng=appliedenergistics2",
            "xnet=rftools"
    };

    @Config.Name("Transform Items")
    @Config.Comment
            ({
                    "List of items that will be associated with Omniwand auto-transform. This must be set before items",
                    "are crafted into the wand. Only one transform item per mod can be stored in the Omniwand.",
                    "This option will override all blacklist settings.",
                    "  Format: modid:item_id"
            })
    public static String[] transformItems = new String[]{
            "appliedenergistics2:certus_quartz_wrench",
            "appliedenergistics2:nether_quartz_wrench",
            "appliedenergistics2:network_tool",
            "astralsorcery:itemwand",
            "botania:twigwand",
            "draconicevolution:crystal_binder",
            "embers:tinker_hammer",
            "environmentaltech:tool_multiblock_assembler",
            "immersiveengineering:tool:0",
            "enderio:item_yeta_wrench",
            "mekanism:configurator",
            "naturesaura:range_visualizer",
            "rftools:smartwrench",
            "teslacorelib:wrench",
            "thermalfoundation:wrench"
    };

    @Config.Name("Item Blacklist")
    @Config.Comment
            ({
                    "List of mods, items and names that will be blacklisted and blocked from being attached to the Omniwand.",
                    "  Format:",
                    "    mod=modid - all items from this mod will be blacklisted",
                    "    item=modid:itemid:metadata - this item will be blacklisted, metadata is optional",
                    "    name=wrench - any item with the word 'wrench' in its item id will be blacklisted",
                    "",
                    "  Examples:",
                    "    mod=tconstruct",
                    "    item=botania:twigwand",
                    "    item=immersiveengineering:tool:0",
                    "    name=wrench",
                    "",
                    "  Filter Priority:",
                    "    1. Transform Items (will always attach)",
                    "    2. Whitelisted Items",
                    "    3. Blacklisted Items",
                    "    4. Blacklisted Mods",
                    "    5. Whitelisted Mods",
                    "    6. Blacklisted Names",
                    "    7. Whitelisted Names",
                    ""
            })
    public static String[] attachBlacklist = new String[]{
            "mod=intangible",
            "mod=immersiveengineering",
            "mod=tconstruct"
    };

    @Config.Name("Item Whitelist")
    @Config.Comment
            ({
                    "List of mods, items and names that will be whitelisted and allowed to be attached to the Omniwand.",
                    "  Format:",
                    "    mod=modid - all items from this mod will be whitelisted",
                    "    item=modid:itemid:metadata - this item will be whitelisted, metadata is optional",
                    "    name=wrench - any item with the word 'wrench' in its item id will be whitelisted",
                    "",
                    "  Examples:",
                    "    mod=tconstruct",
                    "    item=botania:twigwand",
                    "    item=immersiveengineering:tool:0",
                    "    name=wrench",
                    "",
                    "  Filter Priority:",
                    "    1. Transform Items (will always attach)",
                    "    2. Whitelisted Items",
                    "    3. Blacklisted Items",
                    "    4. Blacklisted Mods",
                    "    5. Whitelisted Mods",
                    "    6. Blacklisted Names",
                    "    7. Whitelisted Names",
                    ""
            })
    public static String[] attachWhitelist = new String[]{
            "name=configurator",
            "name=crowbar",
            "name=hammer",
            "name=rod",
            "name=rotator",
            "name=screwdriver",
            "name=wand",
            "name=wrench"
    };


    @Mod.EventBusSubscriber(modid = Omniwand.MOD_ID)
    public static class ConfigChangeListener {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Omniwand.MOD_ID)) {
                ConfigManager.sync(Omniwand.MOD_ID, Config.Type.INSTANCE);
                ConfigTags.syncConfig();
            }
        }
    }
}
