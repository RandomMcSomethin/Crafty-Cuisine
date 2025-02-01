package io.github.randommcsomethin.craftycuisine.item;

import io.github.randommcsomethin.craftycuisine.mixin.RecipeRemainderAccessor;
import net.minecraft.item.Item;

public class SelfRemainderItem extends Item {
    public SelfRemainderItem(Settings settings) {
        super(settings);
        ((RecipeRemainderAccessor)this).setRecipeRemainder(this);
    }
}
