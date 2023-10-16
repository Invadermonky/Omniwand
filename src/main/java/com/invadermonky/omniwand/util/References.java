package com.invadermonky.omniwand.util;

import net.minecraftforge.common.config.Property;

public class References {
    public static final String MINECRAFT = "minecraft";

    public static final String TAG_IS_TRANSFORMING = "omniwand:is_transforming";
    public static final String TAG_AUTO_TRANSFORM = "omniwand:auto_transform";
    public static final String TAG_WAND_DATA = "omniwand:data";
    public static final String TAG_WAND_DISPLAY_NAME = "omniwand:displayName";
    public static final String TAG_ITEM_DEFINED_MOD = "omniwand:definedMod";

    public static final Property ENABLE_TRANSFORM = new Property("enableTransforms", "true", Property.Type.BOOLEAN);
    public static final Property RESTRICT_TOOLTIP = new Property("restrictTooltip", "true", Property.Type.BOOLEAN);
    public static final Property OFFHAND_TRANSFORM = new Property("offhandTransformOnly", "false", Property.Type.BOOLEAN);
    public static final Property CROUCH_REVERT = new Property("crouchRevert", "false", Property.Type.BOOLEAN);
    public static final Property ALT_APPEARANCE = new Property("alternateAppearance", "false", Property.Type.BOOLEAN);
    public static final Property TRANSFORM_ITEMS = new Property("transformItems", "", Property.Type.STRING);
    public static final Property MOD_ALIASES = new Property("modAliases", "", Property.Type.STRING);
    public static final Property BLACKLIST_MODS = new Property("blacklistedMods", "", Property.Type.STRING);
    public static final Property WHITELIST_ITEMS = new Property("whitelistedItems", "", Property.Type.STRING);
    public static final Property WHITELIST_NAMES = new Property("whitelistedNames", "", Property.Type.STRING);

    //public static void initProps() {
    static {
        ENABLE_TRANSFORM.setComment("Enables Omniwand auto-transform.");
        ENABLE_TRANSFORM.setDefaultValue(true);

        RESTRICT_TOOLTIP.setComment("Restricts the Omniwand tooltip to only show items that used for transforms.");
        RESTRICT_TOOLTIP.setDefaultValue(false);

        OFFHAND_TRANSFORM.setComment("Omniwand will only transform when held in the offhand.");
        OFFHAND_TRANSFORM.setDefaultValue(false);

        CROUCH_REVERT.setComment("Omniwand requires crouch + swing to revert from a transformed item.");
        CROUCH_REVERT.setDefaultValue(false);

        ALT_APPEARANCE.setComment("If for some reason you hate the fact the Omniwand is a staff you can set this to true. REQUIRES RESTART.");
        ALT_APPEARANCE.setDefaultValue(false);
        ALT_APPEARANCE.setRequiresMcRestart(true);

        TRANSFORM_ITEMS.setComment( "List of items that will be associated with Omniwand auto-transform. This must be set before\n" +
                                    "items are crafted into the wand. Only one transform item per mod can be stored in the Omniwand.\n\n" +
                                    "This will override the \"blacklistedMods\" setting.\n\n");
        TRANSFORM_ITEMS.setDefaultValues(new String[]{
                "appliedenergistics2:certus_quartz_wrench",
                "appliedenergistics2:nether_quartz_wrench",
                "appliedenergistics2:network_tool",
                "astralsorcery:itemwand",
                "botania:twigwand",
                "draconicevolution:crystal_binder",
                "embers:tinker_hammer",
                "enderio:item_yeta_wrench",
                "immersiveengineering:tool:0",
                "mekanism:configurator",
                "rftools:smartwrench",
                "teslacore:wrench",
                "thermalfoundation:wrench"
        });

        MOD_ALIASES.setComment("List of mod aliases used for Omniwand transforming.\n");
        MOD_ALIASES.setDefaultValues(new String[]{
                "ae2stuff=appliedenergistics2",
                "threng=appliedenergistics2",
                "animus=bloodmagic",
                "bloodarsenal=bloodmagic",
                "nautralpledge=botania",
                "immersivetech=immersiveengineering",
                "immersivepetrolium=immersiveengineering",
                "industrialforegoing=teslacore",
                "redstonearsenal=thermalfoundation",
                "thermalcultivation=thermalfoundation",
                "thermaldynamics=thermalfoundation",
                "thermalexpansion=thermalfoundation",
                "rftoolsdim=rftools",
                "rftoolspower=rftools",
                "rftoolscontrol=rftools",
                "integrateddynamics=integratedtunnels",
                "mekanismgenerators=mekanism",
                "mekanismtools=mekanism",
                "deepresonance=rftools",
                "xnet=rftools",
                "buildcrafttransport=buildcraft",
                "buildcraftfactory=buildcraft",
                "buildcraftsilicon=buildcraft"
        });

        BLACKLIST_MODS.setComment("List of mods to deny attachment to the Omniwand.\n");
        BLACKLIST_MODS.setDefaultValues(new String[]{
                "intangible",
                "immersiveengineering",
                "tconstruct"
        });

        WHITELIST_ITEMS.setComment("List of items that can be attached to the Omniwand.\nThis will override the \"blacklistedMods\" setting.\n");
        WHITELIST_ITEMS.setDefaultValues(new String[]{
                "immersiveengineering:tool:1",
                "immersiveengineering:tool:2"
        });

        WHITELIST_NAMES.setComment("List of names contained within item ids that can be attached to the Omniwand.\n");
        WHITELIST_NAMES.setDefaultValues(new String[]{
                "configurator",
                "crowbar",
                "hammer",
                "rod",
                "rotator",
                "screwdriver",
                "wand",
                "wrench"
        });
    }
}
