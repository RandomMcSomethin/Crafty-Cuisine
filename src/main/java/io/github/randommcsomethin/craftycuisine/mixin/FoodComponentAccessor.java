package io.github.randommcsomethin.craftycuisine.mixin;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FoodComponent.class)
public interface FoodComponentAccessor {
    @Accessor
    @Mutable
    void setEatSeconds(float s);
}
