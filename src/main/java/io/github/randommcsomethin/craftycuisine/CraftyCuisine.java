package io.github.randommcsomethin.craftycuisine;

import io.github.randommcsomethin.craftycuisine.item.DrinkableItem;
import io.github.randommcsomethin.craftycuisine.item.PoisonCureItem;
import io.github.randommcsomethin.craftycuisine.item.SelfRemainderItem;
import io.github.randommcsomethin.craftycuisine.item.TooltippedItem;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ChorusFruitItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class CraftyCuisine implements ModInitializer {
	public static final String MOD_ID = "craftycuisine";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// items
	// cooked foods
	public static final Item COOKED_CARROT = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(4).saturationModifier(1).build())), "cooked_carrot");
	public static final Item BAKED_APPLE = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(5).saturationModifier(1).build())), "baked_apple");
	public static final Item COOKED_BROWN_MUSHROOM = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().snack().nutrition(1).saturationModifier(1).build())), "cooked_brown_mushroom");
	public static final Item COOKED_RED_MUSHROOM = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().snack().nutrition(2).saturationModifier(0.5F).build())), "cooked_red_mushroom");
	public static final Item COOKED_EGG = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().snack().nutrition(1).saturationModifier(0.3F).build())), "cooked_egg");
	// bacon
	public static final Item RAW_BACON = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(1).saturationModifier(0.2F).snack().build())), "bacon");
	public static final Item COOKED_BACON = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.8F).snack().build())), "cooked_bacon");
	// monster foods
	public static final Item COOKED_SPIDER_EYE = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(3).saturationModifier(1.2F).build())), "cooked_spider_eye");
	public static final Item MONSTER_MEATBALLS = registerItem(new Item(new Item.Settings()
			.food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6F).usingConvertsTo(Items.BOWL).build()).maxCount(1)), "monster_meatballs");
	public static final Item MONSTER_MANICOTTI = registerItem(new TooltippedItem(new Item.Settings()
			.food(new FoodComponent.Builder().nutrition(3).saturationModifier(1.2F)
			.statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300), 1.0F)
			.statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 300), 1.0F)
			.statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 300), 1.0F).build()), List.of(Text.translatable("item.craftycuisine.monster_manicotti_tooltip").formatted(Formatting.WHITE))), "monster_manicotti");

	// fish
	public static final Item SUSHI = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(10).saturationModifier(0.5F).build())), "sushi");
	public static final Item SALMON_CAKES = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(10).saturationModifier(0.5F)
					.statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600), 1.0F).build())), "salmon_cakes");
	// candy
	public static final Item CANDIED_APPLE = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(1)
			.usingConvertsTo(Items.STICK).build())), "candied_apple");
	public static final Item CANDIED_CHORUS_FRUIT = registerItem(new ChorusFruitItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(1)
			.usingConvertsTo(Items.STICK).build())), "candied_chorus_fruit");
	public static final Item CANDIED_MELON_SLICE = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(1)
			.usingConvertsTo(Items.STICK).build())), "candied_melon_slice");
	public static final Item SWEET_BERRY_CANDY = registerItem(new DrinkableItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(10).saturationModifier(1.2F)
			.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300), 1.0F)
			.usingConvertsTo(Items.GLASS_BOTTLE).build()).recipeRemainder(Items.GLASS_BOTTLE)), "sweet_berry_candy");
	// jams and breads
	public static final Item SWEET_BERRY_JAM = registerItem(new DrinkableItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.15F)
			.usingConvertsTo(Items.GLASS_BOTTLE).build())
			.maxCount(16)
			.recipeRemainder(Items.GLASS_BOTTLE)), "sweet_berry_jam");
	public static final Item GLOW_BERRY_JAM = registerItem(new DrinkableItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.15F)
			.usingConvertsTo(Items.GLASS_BOTTLE)
			.statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 300), 1.0F).build())
			.maxCount(16)
			.recipeRemainder(Items.GLASS_BOTTLE)), "glow_berry_jam");
	public static final Item SWEET_BERRY_BREAD = registerItem(new PoisonCureItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6F).build())), "sweet_berry_bread");
	public static final Item GLOW_BERRY_BREAD = registerItem(new PoisonCureItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6F)
			.statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600), 1.0F).build())), "glow_berry_bread");
	public static final Item HONEY_BREAD = registerItem(new PoisonCureItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6F).build())), "honey_bread");
	// cookies
	public static final Item SUGAR_COOKIE = registerItem(new Item(new Item.Settings().food(FoodComponents.COOKIE)), "sugar_cookie");
	public static final Item FROSTED_SUGAR_COOKIE = registerItem(new Item(new Item.Settings().food(FoodComponents.COOKIE)), "frosted_sugar_cookie");
	public static final Item PUMPKIN_COOKIE = registerItem(new Item(new Item.Settings().food(FoodComponents.COOKIE)), "pumpkin_cookie");
	public static final Item FROSTED_PUMPKIN_COOKIE = registerItem(new Item(new Item.Settings().food(FoodComponents.COOKIE)), "frosted_pumpkin_cookie");
	public static final Item SUGAR_COOKIE_SQUARE = registerSugarCookie("square", false);
	public static final Item FROSTED_SUGAR_COOKIE_SQUARE = registerSugarCookie("square", true);
	public static final Item SUGAR_COOKIE_STAR = registerSugarCookie("star", false);
	public static final Item FROSTED_SUGAR_COOKIE_STAR = registerSugarCookie("star", true);
	public static final Item SUGAR_COOKIE_TREE = registerSugarCookie("tree", false);
	public static final Item FROSTED_SUGAR_COOKIE_TREE = registerSugarCookie("tree", true);
	public static final Item SUGAR_COOKIE_CREEPER = registerSugarCookie("creeper", false);
	public static final Item FROSTED_SUGAR_COOKIE_CREEPER = registerSugarCookie("creeper", true);
	public static final Item SUGAR_COOKIE_HEART = registerSugarCookie("heart", false);
	public static final Item FROSTED_SUGAR_COOKIE_HEART = registerSugarCookie("heart", true);
	public static final Item SUGAR_COOKIE_SHAMROCK = registerSugarCookie("shamrock", false);
	public static final Item FROSTED_SUGAR_COOKIE_SHAMROCK = registerSugarCookie("shamrock", true);
	public static final Item SUGAR_COOKIE_EGG = registerSugarCookie("egg", false);
	public static final Item FROSTED_SUGAR_COOKIE_EGG = registerSugarCookie("egg", true);
	// cookie cutters
	public static final Item COOKIE_CUTTER_SQUARE = registerItem(new SelfRemainderItem(new Item.Settings()), "cookie_cutter_square");
	public static final Item COOKIE_CUTTER_STAR = registerItem(new SelfRemainderItem(new Item.Settings()), "cookie_cutter_star");
	public static final Item COOKIE_CUTTER_TREE = registerItem(new SelfRemainderItem(new Item.Settings()), "cookie_cutter_tree");
	public static final Item COOKIE_CUTTER_CREEPER = registerItem(new SelfRemainderItem(new Item.Settings()), "cookie_cutter_creeper");
	public static final Item COOKIE_CUTTER_HEART = registerItem(new SelfRemainderItem(new Item.Settings()), "cookie_cutter_heart");
	public static final Item COOKIE_CUTTER_SHAMROCK = registerItem(new SelfRemainderItem(new Item.Settings()), "cookie_cutter_shamrock");
	public static final Item COOKIE_CUTTER_EGG = registerItem(new SelfRemainderItem(new Item.Settings()), "cookie_cutter_egg");
	// pies
	public static final Item APPLE_PIE = registerItem(new Item(new Item.Settings().food(FoodComponents.PUMPKIN_PIE)), "apple_pie");
	public static final Item CHOCOLATE_PIE = registerItem(new Item(new Item.Settings().food(FoodComponents.PUMPKIN_PIE)), "chocolate_pie");
	public static final Item CHORUS_PIE = registerItem(new ChorusFruitItem(new Item.Settings().food(FoodComponents.PUMPKIN_PIE)), "chorus_pie");
	public static final Item MELON_PIE = registerItem(new Item(new Item.Settings().food(FoodComponents.PUMPKIN_PIE)), "melon_pie");
	public static final Item SWEET_BERRY_PIE = registerItem(new Item(new Item.Settings().food(FoodComponents.PUMPKIN_PIE)), "sweet_berry_pie");
	public static final Item GLOW_BERRY_PIE = registerItem(new Item(new Item.Settings().food(FoodComponents.PUMPKIN_PIE)), "glow_berry_pie");
	public static final Item SHEPHERDS_PIE = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(9).saturationModifier(0.75F).build())), "shepherds_pie");
	// ice creams
	public static final Item ICE_CREAM = registerItem(new Item(new Item.Settings()
			.food(createIceCream().build()).maxCount(1)), "ice_cream");
	public static final Item APPLE_ICE_CREAM = registerItem(new Item(new Item.Settings()
			.food(createIceCream(StatusEffects.HASTE, 1200).build()).maxCount(1)), "apple_ice_cream");
	public static final Item CHOCOLATE_ICE_CREAM = registerItem(new Item(new Item.Settings()
			.food(createIceCream(StatusEffects.REGENERATION, 1200).build()).maxCount(1)), "chocolate_ice_cream");
	public static final Item MELON_ICE_CREAM = registerItem(new Item(new Item.Settings()
			.food(createIceCream(StatusEffects.INSTANT_HEALTH).build()).maxCount(1)), "melon_ice_cream");
	public static final Item CHORUS_ICE_CREAM = registerItem(new ChorusFruitItem(new Item.Settings()
			.food(createIceCream(true).build()).maxCount(1)), "chorus_ice_cream");
	public static final Item PUMPKIN_ICE_CREAM = registerItem(new Item(new Item.Settings()
			.food(createIceCream(StatusEffects.RESISTANCE, 1200).build()).maxCount(1)), "pumpkin_ice_cream");
	public static final Item SWEET_BERRY_ICE_CREAM = registerItem(new Item(new Item.Settings()
			.food(createIceCream(StatusEffects.SPEED, 1200).build()).maxCount(1)), "sweet_berry_ice_cream");
	public static final Item GLOW_BERRY_ICE_CREAM = registerItem(new Item(new Item.Settings()
			.food(createIceCream(StatusEffects.GLOWING, 1200).build()).maxCount(1)), "glow_berry_ice_cream");
	public static final Item HONEY_ICE_CREAM = registerItem(new PoisonCureItem(new Item.Settings()
			.food(createIceCream(true).build()).maxCount(1)), "honey_ice_cream");
	// soups and stews
	public static final Item CHOCOLATE_PUDDING = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(5).saturationModifier(0.6F)
			.usingConvertsTo(Items.BOWL)
			.statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600), 1.0F).build())
			.maxCount(1)), "chocolate_pudding");
	public static final Item CACTUS_SOUP = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.3F)
					.usingConvertsTo(Items.BOWL)
					.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600), 1.0F).build())
			.maxCount(1)), "cactus_soup");
	public static final Item FISH_SOUP = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(5).saturationModifier(0.6F)
					.usingConvertsTo(Items.BOWL).build())
			.maxCount(1)), "fish_soup");
	public static final Item CRIMSON_FUNGUS_STEW = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(5).saturationModifier(0.6F)
					.usingConvertsTo(Items.BOWL).build())
			.maxCount(1)), "crimson_fungus_stew");
	public static final Item WARPED_FUNGUS_STEW = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(5).saturationModifier(0.6F)
					.usingConvertsTo(Items.BOWL).build())
			.maxCount(1)), "warped_fungus_stew");
    public static final Item GLOW_RAMEN = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(10).saturationModifier(0.6F)
                    .usingConvertsTo(Items.BOWL)
                    .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600), 1.0F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 600), 1.0F).build())
            .maxCount(1)), "glow_ramen");
    public static final Item ROOT_RISOTTO = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6F)
                    .usingConvertsTo(Items.BOWL)
                    .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600), 1.0F).build())
            .maxCount(1)), "root_risotto");
	public static final Item SEAFOAM_PUDDING = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(8).saturationModifier(0.6F)
                    .usingConvertsTo(Items.BOWL)
                    .statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 3600), 1.0F).build())
            .maxCount(1)), "seafoam_pudding");
	public static final Item COD_SURPRISE = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6F)
					.usingConvertsTo(Items.BOWL).build())
			.maxCount(1)), "cod_surprise");

	// prepared meals
	public static final Item BREAKFAST_PLATTER = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(10).saturationModifier(0.6F)
			.usingConvertsTo(Items.BOWL).build())
			.maxCount(1)), "breakfast_platter");
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		// tem groups
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register((itemGroup -> {
			itemGroup.addAfter(Items.CARROT, COOKED_CARROT);
			itemGroup.addAfter(Items.APPLE, BAKED_APPLE);
			itemGroup.addAfter(Items.BEETROOT, COOKED_EGG, COOKED_BROWN_MUSHROOM, COOKED_RED_MUSHROOM);
			itemGroup.addAfter(Items.HONEY_BOTTLE, SWEET_BERRY_JAM, GLOW_BERRY_JAM);
			itemGroup.addAfter(Items.BREAD, SWEET_BERRY_BREAD, GLOW_BERRY_BREAD, HONEY_BREAD);
			itemGroup.addAfter(Items.COOKED_MUTTON, SHEPHERDS_PIE);
			itemGroup.addBefore(Items.PUMPKIN_PIE,
					ICE_CREAM, APPLE_ICE_CREAM, CHOCOLATE_ICE_CREAM, CHORUS_ICE_CREAM, MELON_ICE_CREAM, PUMPKIN_ICE_CREAM, SWEET_BERRY_ICE_CREAM, GLOW_BERRY_ICE_CREAM, HONEY_ICE_CREAM,
					APPLE_PIE, CHOCOLATE_PIE, CHORUS_PIE, MELON_PIE);
			itemGroup.addAfter(Items.PUMPKIN_PIE, SWEET_BERRY_PIE, GLOW_BERRY_PIE);
			itemGroup.addAfter(Items.COOKED_PORKCHOP, RAW_BACON, COOKED_BACON);
			itemGroup.addBefore(Items.MILK_BUCKET, BREAKFAST_PLATTER);
			itemGroup.addAfter(Items.MUSHROOM_STEW, CRIMSON_FUNGUS_STEW, WARPED_FUNGUS_STEW, CHOCOLATE_PUDDING, CACTUS_SOUP, ROOT_RISOTTO);
			itemGroup.addBefore(Items.RABBIT_STEW, FISH_SOUP, SEAFOAM_PUDDING, COD_SURPRISE, GLOW_RAMEN);
			itemGroup.addAfter(Items.PUFFERFISH, SUSHI, SALMON_CAKES);
			itemGroup.addBefore(Items.COOKIE, CANDIED_APPLE, CANDIED_CHORUS_FRUIT, CANDIED_MELON_SLICE, SWEET_BERRY_CANDY);
			itemGroup.addAfter(Items.COOKIE, SUGAR_COOKIE, FROSTED_SUGAR_COOKIE,
											 SUGAR_COOKIE_SQUARE, FROSTED_SUGAR_COOKIE_SQUARE,
											 SUGAR_COOKIE_STAR, FROSTED_SUGAR_COOKIE_STAR,
											 SUGAR_COOKIE_TREE, FROSTED_SUGAR_COOKIE_TREE,
											 SUGAR_COOKIE_CREEPER, FROSTED_SUGAR_COOKIE_CREEPER,
											 SUGAR_COOKIE_HEART, FROSTED_SUGAR_COOKIE_HEART,
											 SUGAR_COOKIE_SHAMROCK, FROSTED_SUGAR_COOKIE_SHAMROCK,
											 SUGAR_COOKIE_EGG, FROSTED_SUGAR_COOKIE_EGG,
											 PUMPKIN_COOKIE, FROSTED_PUMPKIN_COOKIE);
			itemGroup.addAfter(Items.SPIDER_EYE, COOKED_SPIDER_EYE, MONSTER_MEATBALLS, MONSTER_MANICOTTI);
		}));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup -> {
			itemGroup.addBefore(Items.EXPERIENCE_BOTTLE,
					COOKIE_CUTTER_SQUARE,
					COOKIE_CUTTER_STAR,
					COOKIE_CUTTER_TREE,
					COOKIE_CUTTER_CREEPER,
					COOKIE_CUTTER_HEART,
					COOKIE_CUTTER_SHAMROCK,
					COOKIE_CUTTER_EGG);
		}));
		LOGGER.info("Hello Fabric world!");
	}

	public static Item registerItem(Item item, String id) {
		return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, id), item);
	}

	public static FoodComponent.Builder createIceCream() {
		return new FoodComponent.Builder().nutrition(3).saturationModifier(0.3F);
	}
	public static FoodComponent.Builder createIceCream(boolean flavored) {
		return new FoodComponent.Builder().nutrition(5).saturationModifier(0.6F);
	}
	public static FoodComponent.Builder createIceCream(RegistryEntry<StatusEffect> effect) {
		return new FoodComponent.Builder().nutrition(5).saturationModifier(0.6F).statusEffect(new StatusEffectInstance(effect, 0), 1.0F);
	}
	public static FoodComponent.Builder createIceCream(RegistryEntry<StatusEffect> effect, int duration) {
		return new FoodComponent.Builder().nutrition(5).saturationModifier(0.6F).statusEffect(new StatusEffectInstance(effect, duration), 1.0F);
	}

	public static Item registerSugarCookie(String type, boolean frosted) {
		String cookieName = "sugar_cookie_".concat(type);
		FoodComponent food = FoodComponents.COOKIE;
		if (frosted) {
			cookieName = "frosted_".concat(cookieName);
			food = FoodComponents.COOKIE;
		}

		return registerItem(new TooltippedItem(new Item.Settings().food(food), List.of(Text.translatable("item.craftycuisine.".concat(cookieName).concat("_tooltip")).formatted(Formatting.GRAY))), cookieName);
	}
}