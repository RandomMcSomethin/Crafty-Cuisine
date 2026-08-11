package io.github.randommcsomethin.craftycuisine;

import io.github.randommcsomethin.craftycuisine.config.CraftyCuisineConfig;
import io.github.randommcsomethin.craftycuisine.effect.AntidoteEffect;
import io.github.randommcsomethin.craftycuisine.effect.MagnetismEffect;
import io.github.randommcsomethin.craftycuisine.effect.SweetToothEffect;
import io.github.randommcsomethin.craftycuisine.event.EntityAttackingEntityCallback;
import io.github.randommcsomethin.craftycuisine.item.*;
import io.github.randommcsomethin.craftycuisine.mixin.FoodComponentAccessor;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.block.Portal;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stat;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CraftyCuisine implements ModInitializer {
	public static final String MOD_ID = "craftycuisine";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// reused food components
	public static final FoodComponent FROSTED_COOKIE_FOOD = new FoodComponent.Builder().nutrition(2).saturationModifier(0.25F).build();

	// status effects
	public static final RegistryEntry<StatusEffect> SWEET_TOOTH_EFFECT = registerStatusEffect("sweet_tooth", new SweetToothEffect());
	public static final RegistryEntry<StatusEffect> MAGNETISM_EFFECT = registerStatusEffect("magnetism", new MagnetismEffect());
	public static final RegistryEntry<StatusEffect> ANTIDOTE_EFFECT = registerStatusEffect("antidote", new AntidoteEffect());

	// variables
	public static final StatusEffect[] COD_SURPRISE_STATUS_EFFECTS = {
			StatusEffects.HASTE.value(),
			StatusEffects.REGENERATION.value(),
			StatusEffects.JUMP_BOOST.value(),
			StatusEffects.RESISTANCE.value(),
			StatusEffects.STRENGTH.value(),
			StatusEffects.DOLPHINS_GRACE.value(),
			StatusEffects.INVISIBILITY.value(),
			StatusEffects.NIGHT_VISION.value(),
			StatusEffects.WATER_BREATHING.value()
	};
	public static final StatusEffect[] WARPED_FUNGUS_STEW_STATUS_EFFECTS = {
			StatusEffects.REGENERATION.value(),
			StatusEffects.FIRE_RESISTANCE.value(),
			StatusEffects.HASTE.value(),
			StatusEffects.POISON.value(),
			StatusEffects.BLINDNESS.value(),
			StatusEffects.WEAKNESS.value()
	};

	// config
	public static CraftyCuisineConfig config;

	// items
	// cooked foods
	public static final Item COOKED_CARROT = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(4).saturationModifier(1).build())), "cooked_carrot");
	public static final Item COOKED_BEETROOT = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(4).saturationModifier(1).build())), "cooked_beetroot");
	public static final Item BAKED_APPLE = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(5).saturationModifier(1).build())), "baked_apple");
	public static final Item COOKED_BROWN_MUSHROOM = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(1).saturationModifier(1).build())), "cooked_brown_mushroom");
	public static final Item COOKED_RED_MUSHROOM = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.5F).build())), "cooked_red_mushroom");
	public static final Item COOKED_EGG = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(1).saturationModifier(0.3F).build())), "cooked_egg");
	public static final Item COOKED_TURTLE_EGG = registerItem(new TooltippedItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(5).saturationModifier(0.3F).build()),
			List.of(Text.translatable("item.craftycuisine.cooked_turtle_egg_tooltip").formatted(Formatting.GRAY))), "cooked_turtle_egg");
	// bacon
	public static final Item RAW_BACON = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(1).saturationModifier(0.2F).build())), "bacon");
	public static final Item COOKED_BACON = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.8F).build())), "cooked_bacon");
	// monster foods
	public static final Item COOKED_SPIDER_EYE = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(3).saturationModifier(1.2F).build())), "cooked_spider_eye");
	public static final Item MONSTER_MEATBALLS = registerItem(new Item(new Item.Settings()
			.food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6F).usingConvertsTo(Items.BOWL).build()).maxCount(1)), "monster_meatballs");
	public static final Item MONSTER_MANICOTTI = registerItem(new TooltippedItem(new Item.Settings()
			.food(new FoodComponent.Builder().nutrition(3).saturationModifier(1.2F)
			.statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300), 1.0F)
			.statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 300), 1.0F)
			.statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 300), 1.0F).build()), List.of(Text.translatable("item.craftycuisine.monster_manicotti_tooltip").formatted(Formatting.GRAY))), "monster_manicotti");

	// fish
	public static final Item SUSHI = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(10).saturationModifier(0.5F).build())), "sushi");
	public static final Item SALMON_CAKES = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(10).saturationModifier(0.5F)
					.statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600), 1.0F).build())), "salmon_cakes");
	// candy
	public static final Item CANDIED_APPLE = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(1)
			.usingConvertsTo(Items.STICK).build())
			.maxCount(16)), "candied_apple");
	public static final Item CANDIED_CHORUS_FRUIT = registerItem(new ChorusFruitItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(1)
			.usingConvertsTo(Items.STICK).build())
			.maxCount(16)), "candied_chorus_fruit");
	public static final Item CANDIED_MELON_SLICE = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(1)
			.usingConvertsTo(Items.STICK).build())
			.maxCount(16)), "candied_melon_slice");
	public static final Item SWEET_BERRY_CANDY = registerItem(new DrinkableItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(10).saturationModifier(1.2F)
			.statusEffect(new StatusEffectInstance(SWEET_TOOTH_EFFECT, 600, 1), 1.0F)
			.usingConvertsTo(Items.GLASS_BOTTLE).build())
			.maxCount(16)
			.recipeRemainder(Items.GLASS_BOTTLE)), "sweet_berry_candy");
	// jams and breads
	public static final Item SWEET_BERRY_JAM = registerItem(new DrinkableItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.15F)
			.usingConvertsTo(Items.GLASS_BOTTLE)
			.statusEffect(new StatusEffectInstance(SWEET_TOOTH_EFFECT, 300), 1.0F).build())
			.maxCount(16)
			.recipeRemainder(Items.GLASS_BOTTLE)), "sweet_berry_jam");
	public static final Item GLOW_BERRY_JAM = registerItem(new DrinkableItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.15F)
			.usingConvertsTo(Items.GLASS_BOTTLE)
			.statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 300), 1.0F).build())
			.maxCount(16)
			.recipeRemainder(Items.GLASS_BOTTLE)), "glow_berry_jam");
	public static final Item SWEET_BERRY_BREAD = registerItem(new PoisonCureItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6F)
			.statusEffect(new StatusEffectInstance(SWEET_TOOTH_EFFECT, 600), 1.0F).build())), "sweet_berry_bread");
	public static final Item GLOW_BERRY_BREAD = registerItem(new PoisonCureItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6F)
			.statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600), 1.0F).build())), "glow_berry_bread");
	public static final Item HONEY_BREAD = registerItem(new PoisonCureItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6F).build())), "honey_bread");
	// cookies
	public static final Item SUGAR_COOKIE = registerItem(new Item(new Item.Settings().food(FoodComponents.COOKIE)), "sugar_cookie");
	public static final Item FROSTED_SUGAR_COOKIE = registerItem(new Item(new Item.Settings().food(FROSTED_COOKIE_FOOD)), "frosted_sugar_cookie");
	public static final Item PUMPKIN_COOKIE = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.4F).build())), "pumpkin_cookie");
	public static final Item FROSTED_PUMPKIN_COOKIE = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(4).saturationModifier(0.4F).build())), "frosted_pumpkin_cookie");
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
	public static final Item COOKIE_CUTTER_SQUARE = registerItem(new CookieCutterItem(new Item.Settings(), "square"), "cookie_cutter_square");
	public static final Item COOKIE_CUTTER_STAR = registerItem(new CookieCutterItem(new Item.Settings(), "star"), "cookie_cutter_star");
	public static final Item COOKIE_CUTTER_TREE = registerItem(new CookieCutterItem(new Item.Settings(), "tree"), "cookie_cutter_tree");
	public static final Item COOKIE_CUTTER_CREEPER = registerItem(new CookieCutterItem(new Item.Settings(), "creeper"), "cookie_cutter_creeper");
	public static final Item COOKIE_CUTTER_HEART = registerItem(new CookieCutterItem(new Item.Settings(), "heart"), "cookie_cutter_heart");
	public static final Item COOKIE_CUTTER_SHAMROCK = registerItem(new CookieCutterItem(new Item.Settings(), "shamrock"), "cookie_cutter_shamrock");
	public static final Item COOKIE_CUTTER_EGG = registerItem(new CookieCutterItem(new Item.Settings(), "egg"), "cookie_cutter_egg");
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
			.food(createIceCream(StatusEffects.HASTE, 1200)
			.statusEffect(new StatusEffectInstance(MAGNETISM_EFFECT, 1200), 1.0F).build()).maxCount(1)), "apple_ice_cream");
	public static final Item CHOCOLATE_ICE_CREAM = registerItem(new Item(new Item.Settings()
			.food(createIceCream(StatusEffects.REGENERATION, 1200).build()).maxCount(1)), "chocolate_ice_cream");
	public static final Item MELON_ICE_CREAM = registerItem(new Item(new Item.Settings()
			.food(createIceCream(StatusEffects.INSTANT_HEALTH).build()).maxCount(1)), "melon_ice_cream");
	public static final Item CHORUS_ICE_CREAM = registerItem(new ChorusFruitItem(new Item.Settings()
			.food(createIceCream(true).build()).maxCount(1)), "chorus_ice_cream");
	public static final Item PUMPKIN_ICE_CREAM = registerItem(new Item(new Item.Settings()
			.food(createIceCream(StatusEffects.RESISTANCE, 1200).build()).maxCount(1)), "pumpkin_ice_cream");
	public static final Item SWEET_BERRY_ICE_CREAM = registerItem(new Item(new Item.Settings()
			.food(createIceCream(SWEET_TOOTH_EFFECT, 1200).build()).maxCount(1)), "sweet_berry_ice_cream");
	public static final Item GLOW_BERRY_ICE_CREAM = registerItem(new Item(new Item.Settings()
			.food(createIceCream(StatusEffects.GLOWING, 1200).build()).maxCount(1)), "glow_berry_ice_cream");
	public static final Item HONEY_ICE_CREAM = registerItem(new PoisonCureItem(new Item.Settings()
			.food(createIceCream(ANTIDOTE_EFFECT, 1200).build()).maxCount(1)), "honey_ice_cream");
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
	public static final Item WARPED_FUNGUS_STEW = registerItem(new RandomPotionEffectItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(5).saturationModifier(0.6F)
					.usingConvertsTo(Items.BOWL).build())
			.maxCount(1),
			WARPED_FUNGUS_STEW_STATUS_EFFECTS), "warped_fungus_stew");
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
	public static final Item COD_SURPRISE = registerItem(new RandomPotionEffectItem(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6F)
					.usingConvertsTo(Items.BOWL).build())
			.maxCount(1), COD_SURPRISE_STATUS_EFFECTS), "cod_surprise");
	public static final Item CHUTNEY = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(8).saturationModifier(0.6F)
					.usingConvertsTo(Items.BOWL)
					.statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 1200), 1.0F)
					.statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600), 1.0F)
					.build())
			.maxCount(1)), "chutney");
	public static final Item MIXED_PICKLES = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(8).saturationModifier(0.6F)
					.usingConvertsTo(Items.GLASS_BOTTLE)
					.statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 2400), 1.0F)
					.statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600), 1.0F)
					.build())), "mixed_pickles");

	// prepared meals
	public static final Item BREAKFAST_PLATTER = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(10).saturationModifier(0.6F)
			.usingConvertsTo(Items.BOWL).build())
			.maxCount(1)), "breakfast_platter");
	public static final Item GLAZED_CARROTS = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(7).saturationModifier(0.6F)
			.usingConvertsTo(Items.BOWL)
			.statusEffect(new StatusEffectInstance(ANTIDOTE_EFFECT, 600), 1.0F).build())
			.maxCount(1)), "glazed_carrots");
	public static final Item HONEY_FLAN = registerItem(new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(8).saturationModifier(0.3F)
					.usingConvertsTo(Items.BOWL)
					.statusEffect(new StatusEffectInstance(ANTIDOTE_EFFECT, 2400, 1), 1.0F).build())
			.maxCount(1)), "honey_flan");

	// sounds
	public static final SoundEvent LIFESTEAL = Registry.register(Registries.SOUND_EVENT, Identifier.of(MOD_ID, "lifesteal"),
			SoundEvent.of(Identifier.of(MOD_ID, "lifesteal")));
	//

	@Override
	public void onInitialize() {
		// config
		AutoConfig.register(CraftyCuisineConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(CraftyCuisineConfig.class).getConfig();

		// sweet tooth logic
		EntityAttackingEntityCallback.EVENT.register((attacker, target) -> {
			// only from living entities
			if (!(attacker instanceof LivingEntity)) return ActionResult.PASS;
			StatusEffectInstance sweetTooth = ((LivingEntity) attacker).getStatusEffect(SWEET_TOOTH_EFFECT);
			if (sweetTooth != null && !attacker.getWorld().isClient()) {
				if (((LivingEntity) target).hurtTime > 0) return ActionResult.PASS;
				if (target instanceof LivingEntity && !target.getType().isIn(TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CraftyCuisine.MOD_ID, "cannot_activate_sweet_tooth")))) {
					((LivingEntity) attacker).heal(2.0F + sweetTooth.getAmplifier());
					attacker.getWorld().playSound(null, attacker.getBlockPos(), CraftyCuisine.LIFESTEAL, SoundCategory.PLAYERS, 0.75f, (float) (Math.random()/10f + 0.9f));
					((ServerWorld) attacker.getWorld()).spawnParticles(
							ParticleTypes.HEART,
							target.getX(), target.getRandomBodyY(), target.getZ(),
							(2 + sweetTooth.getAmplifier())/2,
							0.1, 0, 0.1,
							0.2
					);
				}
			}
            return ActionResult.PASS;
        });

		// tem groups
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register((itemGroup -> {
			itemGroup.addAfter(Items.CARROT, COOKED_CARROT);
			itemGroup.addAfter(Items.APPLE, BAKED_APPLE);
			itemGroup.addAfter(Items.BEETROOT, COOKED_BEETROOT, COOKED_EGG, COOKED_TURTLE_EGG, COOKED_BROWN_MUSHROOM, COOKED_RED_MUSHROOM);
			itemGroup.addAfter(Items.HONEY_BOTTLE, SWEET_BERRY_JAM, GLOW_BERRY_JAM);
			itemGroup.addAfter(Items.BREAD, SWEET_BERRY_BREAD, GLOW_BERRY_BREAD, HONEY_BREAD);
			itemGroup.addAfter(Items.COOKED_MUTTON, SHEPHERDS_PIE);
			itemGroup.addBefore(Items.PUMPKIN_PIE,
					ICE_CREAM, APPLE_ICE_CREAM, CHOCOLATE_ICE_CREAM, CHORUS_ICE_CREAM, MELON_ICE_CREAM, PUMPKIN_ICE_CREAM, SWEET_BERRY_ICE_CREAM, GLOW_BERRY_ICE_CREAM, HONEY_ICE_CREAM,
					APPLE_PIE, CHOCOLATE_PIE, CHORUS_PIE, MELON_PIE);
			itemGroup.addAfter(Items.PUMPKIN_PIE, SWEET_BERRY_PIE, GLOW_BERRY_PIE);
			itemGroup.addAfter(Items.COOKED_PORKCHOP, RAW_BACON, COOKED_BACON);
			itemGroup.addBefore(Items.MILK_BUCKET, BREAKFAST_PLATTER, GLAZED_CARROTS, MIXED_PICKLES);
			itemGroup.addAfter(Items.MILK_BUCKET, HONEY_FLAN);
			itemGroup.addAfter(Items.MUSHROOM_STEW, CRIMSON_FUNGUS_STEW, WARPED_FUNGUS_STEW, CHOCOLATE_PUDDING, CACTUS_SOUP, ROOT_RISOTTO);
			itemGroup.addAfter(Items.BEETROOT_SOUP, CHUTNEY);
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

		for (String s : config.fastFood) {
			try {
				Item i = Registries.ITEM.get(Identifier.of(s));
				if (i.getComponents().contains(DataComponentTypes.FOOD)) {
					((FoodComponentAccessor) (Object) Objects.requireNonNull(i.getComponents().get(DataComponentTypes.FOOD))).setEatSeconds(0.8F);
                    LOGGER.debug("Registered food item {}", s);
				} else {
					LOGGER.error("Something went wrong with item " + s + "!  It's not an item or doesn't have a food component.");
				}
			} catch (Exception e) {
				LOGGER.error("Something went wrong with item " + s + "!  It's not an item or doesn't have a food component.");
			}
		}



		DefaultItemComponentEvents.MODIFY.register(modifyContext -> {
			for (String s : config.stews) {
				try {
					Item i = Registries.ITEM.get(Identifier.of(s));
					modifyContext.modify(i, builder -> {
						builder.add(DataComponentTypes.MAX_STACK_SIZE, config.stewStackSize);
					});
					LOGGER.debug("Registered stew item {}", s);
				} catch (Exception e) {
					LOGGER.error("Something went wrong with item " + s + "!");
				}
			}
		});


		// trades
		if (config.fishSoupTrade) {
			TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 4,
					factories -> {
						factories.add((entity, random) -> new TradeOffer(new TradedItem(Items.EMERALD, 1), new ItemStack(FISH_SOUP, 1), 16, 3, 0.05f));
					});
		}

		LOGGER.info("Hello Fabric world!");
	}

	public static Item registerItem(Item item, String id) {
		return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, id), item);
	}

	public static RegistryEntry<StatusEffect> registerStatusEffect(String id, StatusEffect effect) {
		return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, id), effect);
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
			food = FROSTED_COOKIE_FOOD;
		}

		return registerItem(new TooltippedItem(new Item.Settings().food(food), List.of(Text.translatable("item.craftycuisine.".concat(cookieName).concat("_tooltip")).formatted(Formatting.GRAY))), cookieName);
	}
}