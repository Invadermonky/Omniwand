package com.invadermonky.omniwand.util.libs;

public class LibConfigs {

    public static String autoTransformName = "Auto Transform";
    public static String autoTransformComment = "Enables Omniwand auto-transform when looking at blocks.";

    public static String sneakLockingName = "Sneak Locking";
    public static String sneakLockingComment = "Omniwand will not transform when sneaking. Set to true to invert this behavior.";

    public static String restrictTooltipName = "Restrict Tooltip";
    public static String restrictTooltipComment = "Restricts the Omniwand tooltip to only show items that used for auto-transforms.";

    public static String modAliasesName = "Mod Aliases";
    public static String modAliasesComment = """
            "List of mod aliases used for Omniwand transforming.",
            "  Format: modid=aliasmodid"
        """;
    public static String[] modAliasesDefault = new String[]{
        "ae2stuff=appliedenergistics2",
        "animus=bloodmagic",
        "bloodarsenal=bloodmagic",
        "BuildCraft|Builders=BuildCraft|Core",
        "BuildCraft|Factory=BuildCraft|Core",
        "BuildCraft|Robotics=BuildCraft|Core",
        "BuildCraft|Silicon=BuildCraft|Core",
        "BuildCraft|Transport=BuildCraft|Core",
        "deepresonance=rftools",
        "mekanismgenerators=mekanism",
        "mekanismtools=mekanism",
        "RedstoneArsenal=ThermalExpansion",
        "rftoolsdim=rftools",
        "rftoolspower=rftools",
        "rftoolscontrol=rftools",
        "ThermalDynamics=ThermalExpansion",
        "ThermalFoundation=ThermalExpansion",
        "threng=appliedenergistics2",
        "xnet=rftools"
    };

    public static String transformItemsName = "Transform Items";
    public static String transformItemsComment = """
                List of items that will be associated with Omniwand auto-transform. This must be set before items
                are crafted into the wand. Only one transform item per mod can be stored in the Omniwand.
                This option will override all blacklist settings.
                  Format: modid:item_id
        """;
    public static String[] transformItemsDefault = new String[]{
        "appliedenergistics2:item.ToolNetherQuartzWrench",
        "appliedenergistics2:item.ToolNetherCertusWrench",
        "appliedenergistics2:item.ToolNetworkTool",
        "Botania:twigWand",
        "BuildCraft|Core:wrenchItem",
        "DraconicEvolution:wrench",
        "EnderIO:itemYetaWrench",
        "ImmersiveEngineering:tool:0",
        "mekanism:configurator",
        "MineFactoryReloaded:hammer",
        "RedstoneArsenal:tool.wrenchFlux",
        "RedstoneArsenal:tool.battleWrenchFlux",
        "rftools:smartWrenchItem",
        "ThermalExpansion:wrench",
        "ThermalExpansion:tool.battleWrenchInvar"
    };

    public static String attachBlacklistName = "Item Blacklist";
    public static String attachBlacklistComment = """
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
        """;
    public static String[] attachBlacklistDefault = new String[]{
        "mod=immersiveengineering",
        "mod=tconstruct"
    };

    public static String attachWhitelistName = "Item Whitelist";
    public static String attachWhitelistComment = """
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
        """;
    public static String[] attachWhitelistDefault = new String[]{
        "item=EnderIO:itemConduitProbe",
        "item=Botania:obedienceStick",
        "name=configurator",
        "name=crowbar",
        "name=hammer",
        "name=rod",
        "name=rotator",
        "name=screwdriver",
        "name=wand",
        "name=wrench"
    };
}
