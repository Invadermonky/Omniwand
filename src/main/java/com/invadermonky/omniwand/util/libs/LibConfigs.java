package com.invadermonky.omniwand.util.libs;

public class LibConfigs {

    public static String autoTransformName = "Auto Transform";
    public static String autoTransformComment = "Enables Omniwand auto-transform when looking at blocks.";

    public static String offhandTransformName = "Offhand Transform";
    public static String offhandTransformComment = "Omniwand will transform when held in offhand and lock in mainhand";

    public static String restrictTooltipName = "Restrict Tooltip";
    public static String restrictTooltipComment = "Restricts the Omniwand tooltip to only show items that used for auto-transforms.";

    public static String crouchRevertName = "Sneak Revert";
    public static String crouchRevertComment = "The Omniwand will only revert if it is swung while sneaking.";

    public static String modAliasesName = "Mod Aliases";
    public static String modAliasesComment =
            "List of mod aliases used for Omniwand transforming.\n" +
                    "  Format: modid=aliasmodid";
    public static String[] modAliasesDefault = new String[]{
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
            "redstonearsenal=thermalfoundation",
            "rftoolsdim=rftools",
            "rftoolspower=rftools",
            "rftoolscontrol=rftools",
            "thermalcultivation=thermalfoundation",
            "thermaldynamics=thermalfoundation",
            "thermalexpansion=thermalfoundation",
            "threng=appliedenergistics2",
            "xnet=rftools"};

    public static String transformItemsName = "Transform Items";
    public static String transformItemsComment =
            "List of items that will be associated with Omniwand auto-transform. This must be set before items\n" +
                    "are crafted into the wand. Only one transform item per mod can be stored in the Omniwand.\n" +
                    "This option will override all blacklist settings.\n" +
                    "  Format: modid:item_id";
    public static String[] transformItemsDefault = new String[]{
            "appliedenergistics2:certus_quartz_wrench",
            "appliedenergistics2:nether_quartz_wrench",
            "appliedenergistics2:network_tool",
            "astralsorcery:ItemWand",
            "botania:twigWand",
            "draconicevolution:crystal_binder",
            "embers:tinker_hammer",
            "environmentaltech:tool_multiblock_assembler",
            "immersiveengineering:tool:0",
            "enderio:item_yeta_wrench",
            "mekanism:configurator",
            "naturesaura:range_visualizer",
            "rftools:smartWrenchItem",
            "teslacorelib:wrench",
            "thermalfoundation:wrench"};

    public static String attachBlacklistName = "Item Blacklist";
    public static String attachBlacklistComment =
            "List of mods, items and names that will be blacklisted and blocked from being attached to the Omniwand.\n" +
                    "  Format:\n" +
                    "    mod=modid - all items from this mod will be blacklisted\n" +
                    "    item=modid:itemid:metadata - this item will be blacklisted, metadata is optional\n" +
                    "    name=wrench - any item with the word 'wrench' in its item id will be blacklisted\n" +
                    "\n" +
                    "  Examples:\n" +
                    "    mod=tconstruct\n" +
                    "    item=botania:twigwand\n" +
                    "    item=immersiveengineering:tool:0\n" +
                    "    name=wrench\n" +
                    "\n" +
                    "  Filter Priority:\n" +
                    "    1. Transform Items (will always attach)\n" +
                    "    2. Whitelisted Items\n" +
                    "    3. Blacklisted Items\n" +
                    "    4. Blacklisted Mods\n" +
                    "    5. Whitelisted Mods\n" +
                    "    6. Blacklisted Names\n" +
                    "    7. Whitelisted Names\n" +
                    "\n";
    public static String[] attachBlacklistDefault = new String[]{
            "mod=intangible",
            "mod=immersiveengineering",
            "mod=tconstruct"
    };

    public static String attachWhitelistName = "Item Whitelist";
    public static String attachWhitelistComment =
            "List of mods, items and names that will be blacklisted and blocked from being attached to the Omniwand.\n" +
                    "  Format:\n" +
                    "    mod=modid - all items from this mod will be blacklisted\n" +
                    "    item=modid:itemid:metadata - this item will be blacklisted, metadata is optional\n" +
                    "    name=wrench - any item with the word 'wrench' in its item id will be blacklisted\n" +
                    "\n" +
                    "  Examples:\n" +
                    "    mod=tconstruct\n" +
                    "    item=botania:twigwand\n" +
                    "    item=immersiveengineering:tool:0\n" +
                    "    name=wrench\n" +
                    "\n" +
                    "  Filter Priority:\n" +
                    "    1. Transform Items (will always attach)\n" +
                    "    2. Whitelisted Items\n" +
                    "    3. Blacklisted Items\n" +
                    "    4. Blacklisted Mods\n" +
                    "    5. Whitelisted Mods\n" +
                    "    6. Blacklisted Names\n" +
                    "    7. Whitelisted Names\n" +
                    "\n";
    public static String[] attachWhitelistDefault = new String[]{
            "name=configurator",
            "name=crowbar",
            "name=hammer",
            "name=rod",
            "name=rotator",
            "name=screwdriver",
            "name=wand",
            "name=wrench"
    };

    public static String nameOverridesName = "Name Overrides";
    public static String nameOverridesComment = "A list of name associations used for Omniwand tooltip display. This is used\n" +
            "when a mod name does not match its item namespace.";
    public static String[] nameOverridesDefault = new String[]{
            "botania=Botania"
    };
}
