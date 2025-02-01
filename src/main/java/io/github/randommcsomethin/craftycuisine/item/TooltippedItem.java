package io.github.randommcsomethin.craftycuisine.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class TooltippedItem extends Item {
    private final List<Text> tooltips;

    public TooltippedItem(Settings settings, List<Text> tooltips) {
        super(settings);
        this.tooltips = tooltips;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.addAll(tooltips);
    }
}
