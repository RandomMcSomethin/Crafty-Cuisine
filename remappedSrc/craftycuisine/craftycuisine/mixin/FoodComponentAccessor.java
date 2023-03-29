package craftycuisine.craftycuisine.mixin;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FoodComponent.class)
public interface FoodComponentAccessor {
    @Accessor
    @Mutable
    void setSnack(boolean isSnack);
}