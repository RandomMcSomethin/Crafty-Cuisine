package craftycuisine.craftycuisine;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonListWidget;
//import net.minecraft.client.options.Option;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import javax.annotation.Nullable;
//import org.jetbrains.annotations.Nullable;

public class CraftyCuisineSettings extends Screen {

    /*
    private final CuisineConfig config;
    private final Screen parent;
    private final Option piesOption;
    private final Option cookiesOption;
    private final Option baconOption;
    private final Option sushiOption;
    private ButtonListWidget list;
    */
    protected CraftyCuisineSettings(@Nullable Screen parent) {
        super(new TranslatableText("craftycuisine.menu.title"));
        
    }

}
