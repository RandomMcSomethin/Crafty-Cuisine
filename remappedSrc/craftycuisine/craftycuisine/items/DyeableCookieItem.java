package craftycuisine.craftycuisine.items;

import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class DyeableCookieItem extends TooltipItem implements DyeableItem {
    public int getColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt("display");
        return nbtCompound != null && nbtCompound.contains("color", 99) ? nbtCompound.getInt("color") : 0xFFF0DA;
    }

    public DyeableCookieItem(Settings settings, String tooltip) {
        super(settings, tooltip);
    }
}
