package io.github.randommcsomethin.craftycuisine;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.component.type.DyedColorComponent;

public class CraftyCuisineClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 :
				DyedColorComponent.getColor(stack, -0xF25),
				CraftyCuisine.FROSTED_SUGAR_COOKIE,
				CraftyCuisine.FROSTED_SUGAR_COOKIE_SQUARE,
				CraftyCuisine.FROSTED_SUGAR_COOKIE_STAR,
				CraftyCuisine.FROSTED_SUGAR_COOKIE_TREE,
				CraftyCuisine.FROSTED_SUGAR_COOKIE_CREEPER,
				CraftyCuisine.FROSTED_SUGAR_COOKIE_HEART,
				CraftyCuisine.FROSTED_SUGAR_COOKIE_SHAMROCK,
				CraftyCuisine.FROSTED_SUGAR_COOKIE_EGG);
	}
}