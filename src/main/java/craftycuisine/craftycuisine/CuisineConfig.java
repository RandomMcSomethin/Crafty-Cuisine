package craftycuisine.craftycuisine;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "craftycuisine")
public class CuisineConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean piesEnabled = true;

    @ConfigEntry.Gui.Tooltip
    public boolean cookiesEnabled = true;

    @ConfigEntry.Gui.Tooltip
    public boolean baconEnabled = true;

    @ConfigEntry.Gui.Tooltip
    public boolean sushiEnabled = true;
}
