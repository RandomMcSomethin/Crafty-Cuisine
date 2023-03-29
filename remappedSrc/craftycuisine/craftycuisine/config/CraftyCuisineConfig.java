package craftycuisine.craftycuisine.config;

import craftycuisine.craftycuisine.CraftyCuisine;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Config(name = "craftycuisine")
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
            "craftycuisine:cooked_bacon"};

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public List<String> fastFood = Arrays.asList(defaultFastFoods);

    /* probably not going to use these anytime soon
       If you want to disable foods, you can use a datapack with blank recipes,
       at least until I figure out how to disable recipes from a config file
    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean piesEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean cookiesEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean baconEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean sushiEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean candiedEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean jamsAndBreadEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean friedEggEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean turtleEggEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean monsterFoodsEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean soupsAndStewsEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean cookedCarrotEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean shepherdsPieEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean sweetBerryCandyEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean pastasEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public boolean disableAllFoods = false;
    */
}
