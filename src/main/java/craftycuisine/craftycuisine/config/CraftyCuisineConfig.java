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
            "craftycuisine:pumpkin_cookie",
            "craftycuisine:bacon",
            "craftycuisine:cooked_bacon"};

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Gui.Tooltip
    public List<String> fastFood = Arrays.asList(defaultFastFoods);

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.Tooltip
    public boolean piesEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.Tooltip
    public boolean cookiesEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.Tooltip
    public boolean baconEnabled = true;

    @ConfigEntry.Category("craftycuisine.general")
    @ConfigEntry.Gui.Tooltip
    public boolean sushiEnabled = true;
}
