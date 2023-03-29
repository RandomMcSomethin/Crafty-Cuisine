package craftycuisine.craftycuisine.items;

import craftycuisine.craftycuisine.mixin.RecipeRemainderAccessor;
import net.minecraft.item.Item;

public class CookieCutterItem extends Item {
    public CookieCutterItem(final Settings settings) {
        super(settings);
        ((RecipeRemainderAccessor)this).setRecipeRemainder(this);
    }
}
