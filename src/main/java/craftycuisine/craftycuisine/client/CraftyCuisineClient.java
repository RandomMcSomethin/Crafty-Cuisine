package craftycuisine.craftycuisine.client;

import craftycuisine.craftycuisine.CraftyCuisine;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Items;

@Environment(EnvType.CLIENT)
public class CraftyCuisineClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 :
                ((DyeableItem)stack.getItem()).getColor(stack), CraftyCuisine.FROSTED_SUGAR_COOKIE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 :
                ((DyeableItem)stack.getItem()).getColor(stack), CraftyCuisine.FROSTED_SUGAR_COOKIE_SQUARE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 :
                ((DyeableItem)stack.getItem()).getColor(stack), CraftyCuisine.FROSTED_SUGAR_COOKIE_STAR);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 :
                ((DyeableItem)stack.getItem()).getColor(stack), CraftyCuisine.FROSTED_SUGAR_COOKIE_TREE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 :
                ((DyeableItem)stack.getItem()).getColor(stack), CraftyCuisine.FROSTED_SUGAR_COOKIE_CREEPER);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 :
                ((DyeableItem)stack.getItem()).getColor(stack), CraftyCuisine.FROSTED_PUMPKIN_COOKIE);
    }
}
