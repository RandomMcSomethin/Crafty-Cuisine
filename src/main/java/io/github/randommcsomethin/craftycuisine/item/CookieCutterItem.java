package io.github.randommcsomethin.craftycuisine.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class CookieCutterItem extends SelfRemainderItem {
    private final String type;
    public CookieCutterItem(Settings settings, String type) {
        super(settings);
        this.type = type;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(Text.translatable("item.craftycuisine.cookie_cutter_".concat(this.type).concat("_tooltip")).formatted(Formatting.GRAY));
    }
}
