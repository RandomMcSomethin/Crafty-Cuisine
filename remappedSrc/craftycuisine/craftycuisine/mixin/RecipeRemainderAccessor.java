package craftycuisine.craftycuisine.mixin;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.class)
public interface RecipeRemainderAccessor {
    @Accessor @Mutable
    void setRecipeRemainder(final Item remainder);
}