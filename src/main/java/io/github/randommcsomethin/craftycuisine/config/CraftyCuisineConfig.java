package io.github.randommcsomethin.craftycuisine.config;

import io.github.randommcsomethin.craftycuisine.CraftyCuisine;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.Arrays;
import java.util.List;

@Config(name = CraftyCuisine.MOD_ID)
public class CraftyCuisineConfig implements ConfigData {
    @ConfigEntry.Gui.Excluded
    @ConfigEntry.Category("craftycuisine.general")
    public transient static CraftyCuisineConfig instance;

    @ConfigEntry.Gui.Excluded
    @ConfigEntry.Category("craftycuisine.general")
    public String[] defaultFastFoods = {
            "minecraft:cookie",
            "craftycuisine:sugar_cookie",
            "craftycuisine:frosted_sugar_cookie",
            "craftycuisine:pumpkin_cookie",
            "craftycuisine:frosted_pumpkin_cookie",
            "craftycuisine:bacon",
            "craftycuisine:cooked_bacon",
            "craftycuisine:cooked_brown_mushroom",
            "craftycuisine:cooked_red_mushroom"
    };

    @ConfigEntry.Gui.Excluded
    @ConfigEntry.Category("craftycuisine.general")
    public String[] defaultStews = {
            "minecraft:mushroom_stew",
            "minecraft:beetroot_soup",
            "minecraft:rabbit_stew",
            "craftycuisine:chocolate_pudding",
            "craftycuisine:monster_meatballs",
            "craftycuisine:cactus_soup",
            "craftycuisine:fish_soup",
            "craftycuisine:crimson_fungus_stew",
            "craftycuisine:warped_fungus_stew",
            "craftycuisine:glow_ramen",
            "craftycuisine:root_risotto",
            "craftycuisine:seafoam_pudding",
            "craftycuisine:cod_surprise",
            "craftycuisine:chutney",
            "craftycuisine:breakfast_platter",
            "craftycuisine:glazed_carrots",
            "craftycuisine:honey_flan",
    };

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public List<String> fastFood = Arrays.asList(defaultFastFoods);

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public List<String> stews = Arrays.asList(defaultStews);

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public int stewStackSize = 1;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean fishSoupTrade = true;
}
