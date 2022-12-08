package craftycuisine.craftycuisine.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TooltipItem extends Item {
    public String tooltip;
    public TooltipItem(Settings settings, String tooltip) {
        super(settings);
        this.tooltip = tooltip;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (!this.tooltip.equals(""))
            tooltip.add(Text.translatable(this.tooltip));
    }
}
