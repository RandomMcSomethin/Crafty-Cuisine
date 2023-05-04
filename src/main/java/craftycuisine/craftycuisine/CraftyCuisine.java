package craftycuisine.craftycuisine;

import craftycuisine.craftycuisine.config.CraftyCuisineConfig;
import craftycuisine.craftycuisine.items.*;
//import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
//import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import craftycuisine.craftycuisine.mixin.FoodComponentAccessor;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import net.minecraft.util.math.random.Random;

import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class CraftyCuisine implements ModInitializer {
    public static final String mod_id = "craftycuisine";
    public static final Logger LOGGER = LoggerFactory.getLogger(mod_id);

    // tags
    //public static final TagKey<Item> FAST_FOOD = TagKey.of(Registries.ITEM_KEY, new Identifier(mod_id, "fast_food"));

    // food components
    public static final FoodComponent COOKED_CARROT_FOOD = new FoodComponent.Builder().hunger(4).saturationModifier(1).build();
    public static final FoodComponent BAKED_APPLE_FOOD = new FoodComponent.Builder().hunger(5).saturationModifier(1).build();
    public static final FoodComponent COOKED_BROWN_MUSHROOM_FOOD = new FoodComponent.Builder().hunger(1).saturationModifier(1).build();
    public static final FoodComponent COOKED_RED_MUSHROOM_FOOD = new FoodComponent.Builder().hunger(2).saturationModifier(0.5F).build();
    public static final FoodComponent SWEET_BERRY_JAM_FOOD = new FoodComponent.Builder().hunger(6).saturationModifier(0.15F).build();
    public static final FoodComponent GLOW_BERRY_JAM_FOOD = new FoodComponent.Builder().hunger(6).saturationModifier(0.15F)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 300), 1.0F)
            .build();
    public static final FoodComponent CANDIED_FOOD = new FoodComponent.Builder().hunger(6).saturationModifier(0.5F).build();
    public static final FoodComponent FROSTED_COOKIE_FOOD = new FoodComponent.Builder().hunger(2).saturationModifier(0.25F).build();
    public static final FoodComponent BIG_COOKIE_FOOD = new FoodComponent.Builder().hunger(3).saturationModifier(0.4F).build();
    public static final FoodComponent FROSTED_BIG_COOKIE_FOOD = new FoodComponent.Builder().hunger(4).saturationModifier(0.4F).build();
    public static final FoodComponent BACON_FOOD = new FoodComponent.Builder().hunger(1).saturationModifier(0.2F).meat().build();
    public static final FoodComponent COOKED_BACON_FOOD = new FoodComponent.Builder().hunger(3).saturationModifier(0.8F).meat().build();
    public static final FoodComponent MONSTER_MANICOTTI_FOOD = new FoodComponent.Builder().hunger(8).saturationModifier(1.2F)
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 300), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 300), 1.0F)
            .meat()
            .build();
    public static final FoodComponent COOKED_SPIDER_EYE_FOOD = new FoodComponent.Builder().hunger(3).saturationModifier(0.6F)
            .meat()
            .build();
    public static final FoodComponent SHEPHERDS_PIE_FOOD = new FoodComponent.Builder().hunger(9).saturationModifier(0.75F)
            .meat()
            .build();
    public static final FoodComponent MONSTER_MEATBALLS_FOOD = new FoodComponent.Builder().hunger(6).saturationModifier(0.6F)
            .meat()
            .build();
    public static final FoodComponent SWEET_BERRY_CANDY_FOOD = new FoodComponent.Builder().hunger(10).saturationModifier(1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 1200), 1.0F)
            .build();
    public static final FoodComponent SALMON_CAKES_FOOD = new FoodComponent.Builder().hunger(9).saturationModifier(0.5F)
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600), 1.0F)
            .build();
    public static final FoodComponent SEAFOAM_PUDDING_FOOD = new FoodComponent.Builder().hunger(8).saturationModifier(0.6F)
            .statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 3600), 1.0F)
            .build();
    public static final FoodComponent SUSHI_FOOD = new FoodComponent.Builder().hunger(10).saturationModifier(0.5F)
            .statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 600), 1.0F)
            .meat()
            .build();
    public static final FoodComponent SWEET_BERRY_BREAD_FOOD = new FoodComponent.Builder().hunger(6).saturationModifier(0.6F)
            .build();
    public static final FoodComponent HONEY_BREAD_FOOD = new FoodComponent.Builder().hunger(6).saturationModifier(0.6F)
            .build();
    public static final FoodComponent GLOW_BERRY_BREAD_FOOD = new FoodComponent.Builder().hunger(6).saturationModifier(0.6F)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600), 1.0F)
            .build();

    // Soups/stews
    public static final FoodComponent CHOCOLATE_PUDDING_FOOD = new FoodComponent.Builder().hunger(5).saturationModifier(0.6F)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600), 1.0F)
            .build();
    public static final FoodComponent CACTUS_SOUP_FOOD = new FoodComponent.Builder().hunger(2).saturationModifier(0.3F)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600), 1.0F)
            .build();
    public static final FoodComponent FISH_SOUP_FOOD = new FoodComponent.Builder().hunger(5).saturationModifier(0.6F)
            .build();
    public static final FoodComponent CRIMSON_STEW_FOOD = new FoodComponent.Builder().hunger(5).saturationModifier(0.6F)
            .build();
    public static final FoodComponent WARPED_STEW_FOOD = new FoodComponent.Builder().hunger(6).saturationModifier(0.6F)
            .build();
    public static final FoodComponent GLOW_RAMEN_FOOD = new FoodComponent.Builder().hunger(10).saturationModifier(0.6F)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 600), 1.0F)
            .build();
    public static final FoodComponent ROOT_RISOTTO_FOOD = new FoodComponent.Builder().hunger(6).saturationModifier(0.6F)
            .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600), 1.0F)
            .build();

    // ice creams
    public static final FoodComponent ICE_CREAM_FOOD = new FoodComponent.Builder().hunger(3).saturationModifier(0.3F)
            .build();
    public static final FoodComponent APPLE_ICE_CREAM_FOOD = new FoodComponent.Builder().hunger(5).saturationModifier(0.5F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 1200), 1.0F)
            .build();
    public static final FoodComponent CHOCOLATE_ICE_CREAM_FOOD = new FoodComponent.Builder().hunger(5).saturationModifier(0.5F)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 1200), 1.0F)
            .build();
    public static final FoodComponent MELON_ICE_CREAM_FOOD = new FoodComponent.Builder().hunger(5).saturationModifier(0.5F)
            .statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 0), 1.0F)
            .build();
    public static final FoodComponent CHORUS_ICE_CREAM_FOOD = new FoodComponent.Builder().hunger(5).saturationModifier(0.5F)
            .build();
    public static final FoodComponent PUMPKIN_ICE_CREAM_FOOD = new FoodComponent.Builder().hunger(5).saturationModifier(0.5F)
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 1200), 1.0F)
            .build();
    public static final FoodComponent SWEET_BERRY_ICE_CREAM_FOOD = new FoodComponent.Builder().hunger(5).saturationModifier(0.5F)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1200), 1.0F)
            .build();
    public static final FoodComponent GLOW_BERRY_ICE_CREAM_FOOD = new FoodComponent.Builder().hunger(5).saturationModifier(0.5F)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 1200), 1.0F)
            .build();
    public static final FoodComponent HONEY_ICE_CREAM_FOOD = new FoodComponent.Builder().hunger(5).saturationModifier(0.5F)
            .build();

    // items

    // cookie cutters
    public static final Item COOKIE_CUTTER_SQUARE = new CookieCutterItem(new FabricItemSettings());
    public static final Item COOKIE_CUTTER_STAR = new CookieCutterItem(new FabricItemSettings());
    public static final Item COOKIE_CUTTER_TREE = new CookieCutterItem(new FabricItemSettings());
    public static final Item COOKIE_CUTTER_CREEPER = new CookieCutterItem(new FabricItemSettings());
    public static final Item COOKIE_CUTTER_HEART = new CookieCutterItem(new FabricItemSettings());
    public static final Item COOKIE_CUTTER_SHAMROCK = new CookieCutterItem(new FabricItemSettings());
    public static final Item COOKIE_CUTTER_EGG = new CookieCutterItem(new FabricItemSettings());

    static {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.addBefore(Items.EXPERIENCE_BOTTLE.getDefaultStack(),
                new Item[]{
                        COOKIE_CUTTER_SQUARE,
                        COOKIE_CUTTER_STAR,
                        COOKIE_CUTTER_TREE,
                        COOKIE_CUTTER_CREEPER,
                        COOKIE_CUTTER_HEART,
                        COOKIE_CUTTER_SHAMROCK,
                        COOKIE_CUTTER_EGG
            }
        ));
    }

    // basic cooked foods
    public static final Item COOKED_CARROT = new Item(new FabricItemSettings().food(COOKED_CARROT_FOOD));
    public static final Item BAKED_APPLE = new Item(new FabricItemSettings().food(BAKED_APPLE_FOOD));
    public static final Item COOKED_BROWN_MUSHROOM = new Item(new FabricItemSettings().food(COOKED_BROWN_MUSHROOM_FOOD));
    public static final Item COOKED_RED_MUSHROOM = new Item(new FabricItemSettings().food(COOKED_RED_MUSHROOM_FOOD));
    public static final Item COOKED_EGG = new Item(new FabricItemSettings().food(FoodComponents.POTATO));
    public static final Item COOKED_TURTLE_EGG = new CookedTurtleEggItem(new FabricItemSettings().food(FoodComponents.GOLDEN_CARROT));

    static {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.CARROT.getDefaultStack(), COOKED_CARROT));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.APPLE.getDefaultStack(), BAKED_APPLE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.BEETROOT.getDefaultStack(),
                new Item[]{
                        COOKED_EGG,
                        COOKED_TURTLE_EGG,
                        COOKED_BROWN_MUSHROOM,
                        COOKED_RED_MUSHROOM
                }
        ));
    }

    // pies
    public static final Item APPLE_PIE = new Item(new FabricItemSettings().food(FoodComponents.PUMPKIN_PIE));
    public static final Item CHOCOLATE_PIE = new Item(new FabricItemSettings().food(FoodComponents.PUMPKIN_PIE));
    public static final Item CHORUS_PIE = new ChorusFruitItem(new FabricItemSettings().food(FoodComponents.PUMPKIN_PIE));
    public static final Item MELON_PIE = new Item(new FabricItemSettings().food(FoodComponents.PUMPKIN_PIE));
    public static final Item SWEET_BERRY_PIE = new Item(new FabricItemSettings().food(FoodComponents.PUMPKIN_PIE));
    public static final Item GLOW_BERRY_PIE = new Item(new FabricItemSettings().food(FoodComponents.PUMPKIN_PIE));
    public static final Item SHEPHERDS_PIE = new Item(new FabricItemSettings().food(SHEPHERDS_PIE_FOOD));

    static {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.COOKED_MUTTON.getDefaultStack(), SHEPHERDS_PIE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.PUMPKIN_PIE.getDefaultStack(),
                new Item[]{
                        APPLE_PIE,
                        CHOCOLATE_PIE,
                        CHORUS_PIE,
                        MELON_PIE,
                        SWEET_BERRY_PIE,
                        GLOW_BERRY_PIE
                }
        ));
    }

    // ice creams
    public static final Item ICE_CREAM = new StewItem(new FabricItemSettings().food(ICE_CREAM_FOOD).maxCount(1));
    public static final Item APPLE_ICE_CREAM = new StewItem(new FabricItemSettings().food(APPLE_ICE_CREAM_FOOD).maxCount(1));
    public static final Item CHOCOLATE_ICE_CREAM = new StewItem(new FabricItemSettings().food(CHOCOLATE_ICE_CREAM_FOOD).maxCount(1));
    public static final Item CHORUS_ICE_CREAM = new ChorusFruitBowlItem(new FabricItemSettings().food(CHORUS_ICE_CREAM_FOOD).maxCount(1));
    public static final Item MELON_ICE_CREAM = new StewItem(new FabricItemSettings().food(MELON_ICE_CREAM_FOOD).maxCount(1));
    public static final Item PUMPKIN_ICE_CREAM = new StewItem(new FabricItemSettings().food(PUMPKIN_ICE_CREAM_FOOD).maxCount(1));
    public static final Item SWEET_BERRY_ICE_CREAM = new StewItem(new FabricItemSettings().food(SWEET_BERRY_ICE_CREAM_FOOD).maxCount(1));
    public static final Item GLOW_BERRY_ICE_CREAM = new StewItem(new FabricItemSettings().food(GLOW_BERRY_ICE_CREAM_FOOD).maxCount(1));
    public static final Item HONEY_ICE_CREAM = new PoisonCureBowlItem(new FabricItemSettings().food(HONEY_ICE_CREAM_FOOD).maxCount(1));

    static {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addBefore(Items.PUMPKIN_PIE.getDefaultStack(),
                new Item[]{
                        ICE_CREAM,
                        APPLE_ICE_CREAM,
                        CHOCOLATE_ICE_CREAM,
                        CHORUS_ICE_CREAM,
                        MELON_ICE_CREAM,
                        PUMPKIN_ICE_CREAM,
                        SWEET_BERRY_ICE_CREAM,
                        GLOW_BERRY_ICE_CREAM,
                        HONEY_ICE_CREAM
                }
        ));
    }

    // jams and bread
    public static final Item SWEET_BERRY_JAM = new DrinkableItem(new FabricItemSettings().food(SWEET_BERRY_JAM_FOOD).recipeRemainder(Items.GLASS_BOTTLE));
    public static final Item GLOW_BERRY_JAM = new DrinkableItem(new FabricItemSettings().food(GLOW_BERRY_JAM_FOOD).recipeRemainder(Items.GLASS_BOTTLE));
    public static final Item SWEET_BERRY_BREAD = new PoisonCureItem(new FabricItemSettings().food(SWEET_BERRY_BREAD_FOOD));
    public static final Item GLOW_BERRY_BREAD = new PoisonCureItem(new FabricItemSettings().food(GLOW_BERRY_BREAD_FOOD));
    public static final Item HONEY_BREAD = new PoisonCureItem(new FabricItemSettings().food(HONEY_BREAD_FOOD));

    static {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.BREAD.getDefaultStack(),
                new Item[]{
                        SWEET_BERRY_BREAD,
                        GLOW_BERRY_BREAD,
                        HONEY_BREAD
                }
        ));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.HONEY_BOTTLE.getDefaultStack(),
                new Item[]{
                        SWEET_BERRY_JAM,
                        GLOW_BERRY_JAM
                }
        ));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.addAfter(Items.WHEAT.getDefaultStack(),
                new Item[]{
                        SWEET_BERRY_JAM,
                        GLOW_BERRY_JAM
                }
        ));
    }

    // candy
    public static final Item CANDIED_APPLE = new CandiedItem(new FabricItemSettings().food(CANDIED_FOOD));
    public static final Item CANDIED_CHORUS_FRUIT = new ChorusFruitItem(new FabricItemSettings().food(CANDIED_FOOD));
    public static final Item CANDIED_MELON_SLICE = new CandiedItem(new FabricItemSettings().food(CANDIED_FOOD));
    public static final Item SWEET_BERRY_CANDY = new DrinkableItem(new FabricItemSettings().food(SWEET_BERRY_CANDY_FOOD).recipeRemainder(Items.GLASS_BOTTLE));

    static {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addBefore(Items.COOKIE.getDefaultStack(),
                new Item[]{
                        CANDIED_APPLE,
                        CANDIED_CHORUS_FRUIT,
                        CANDIED_MELON_SLICE,
                        SWEET_BERRY_CANDY
                }
        ));
    }

    // fish
    public static final Item SUSHI = new Item(new FabricItemSettings().food(SUSHI_FOOD));
    public static final Item SALMON_CAKES = new Item(new FabricItemSettings().food(SALMON_CAKES_FOOD));
    public static final Item FISH_SOUP = new StewItem(new FabricItemSettings().food(FISH_SOUP_FOOD).maxCount(1));
    public static final Item COD_SURPRISE = new PotionBowlItem(new FabricItemSettings().food(FoodComponents.MUSHROOM_STEW).maxCount(1),
            new StatusEffect[] {
                    StatusEffects.HASTE,
                    StatusEffects.REGENERATION,
                    StatusEffects.JUMP_BOOST,
                    StatusEffects.RESISTANCE,
                    StatusEffects.STRENGTH,
                    StatusEffects.DOLPHINS_GRACE,
                    StatusEffects.INVISIBILITY,
                    StatusEffects.NIGHT_VISION,
                    StatusEffects.WATER_BREATHING
            });
    public static final Item SEAFOAM_PUDDING = new StewItem(new FabricItemSettings().food(SEAFOAM_PUDDING_FOOD).maxCount(1));

    static {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.PUFFERFISH.getDefaultStack(), SUSHI));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.COOKED_SALMON.getDefaultStack(),
                new Item[]{
                        SALMON_CAKES,
                        FISH_SOUP,
                        COD_SURPRISE,
                        SEAFOAM_PUDDING
                }
        ));
    }

    // cookies
    public static final Item SUGAR_COOKIE = new Item(new FabricItemSettings().food(FoodComponents.COOKIE));
    public static final Item FROSTED_SUGAR_COOKIE = new DyeableCookieItem(new FabricItemSettings().food(FROSTED_COOKIE_FOOD), "");
    public static final Item SUGAR_COOKIE_SQUARE = new TooltipItem(new FabricItemSettings().food(FoodComponents.COOKIE), "item.craftycuisine.sugar_cookie_square_tooltip");
    public static final Item FROSTED_SUGAR_COOKIE_SQUARE = new DyeableCookieItem(new FabricItemSettings().food(FROSTED_COOKIE_FOOD), "item.craftycuisine.frosted_sugar_cookie_square_tooltip");
    public static final Item SUGAR_COOKIE_STAR = new TooltipItem(new FabricItemSettings().food(FoodComponents.COOKIE), "item.craftycuisine.sugar_cookie_star_tooltip");
    public static final Item FROSTED_SUGAR_COOKIE_STAR = new DyeableCookieItem(new FabricItemSettings().food(FROSTED_COOKIE_FOOD), "item.craftycuisine.frosted_sugar_cookie_star_tooltip");
    public static final Item SUGAR_COOKIE_TREE = new TooltipItem(new FabricItemSettings().food(FoodComponents.COOKIE), "item.craftycuisine.sugar_cookie_tree_tooltip");
    public static final Item FROSTED_SUGAR_COOKIE_TREE = new DyeableCookieItem(new FabricItemSettings().food(FROSTED_COOKIE_FOOD), "item.craftycuisine.frosted_sugar_cookie_tree_tooltip");
    public static final Item SUGAR_COOKIE_CREEPER = new TooltipItem(new FabricItemSettings().food(FoodComponents.COOKIE), "item.craftycuisine.sugar_cookie_creeper_tooltip");
    public static final Item FROSTED_SUGAR_COOKIE_CREEPER = new DyeableCookieItem(new FabricItemSettings().food(FROSTED_COOKIE_FOOD), "item.craftycuisine.frosted_sugar_cookie_creeper_tooltip");
    public static final Item SUGAR_COOKIE_HEART = new TooltipItem(new FabricItemSettings().food(FoodComponents.COOKIE), "item.craftycuisine.sugar_cookie_heart_tooltip");
    public static final Item FROSTED_SUGAR_COOKIE_HEART = new DyeableCookieItem(new FabricItemSettings().food(FROSTED_COOKIE_FOOD), "item.craftycuisine.frosted_sugar_cookie_heart_tooltip");
    public static final Item SUGAR_COOKIE_SHAMROCK = new TooltipItem(new FabricItemSettings().food(FoodComponents.COOKIE), "item.craftycuisine.sugar_cookie_shamrock_tooltip");
    public static final Item FROSTED_SUGAR_COOKIE_SHAMROCK = new DyeableCookieItem(new FabricItemSettings().food(FROSTED_COOKIE_FOOD), "item.craftycuisine.frosted_sugar_cookie_shamrock_tooltip");
    public static final Item SUGAR_COOKIE_EGG = new TooltipItem(new FabricItemSettings().food(FoodComponents.COOKIE), "item.craftycuisine.sugar_cookie_egg_tooltip");
    public static final Item FROSTED_SUGAR_COOKIE_EGG = new DyeableCookieItem(new FabricItemSettings().food(FROSTED_COOKIE_FOOD), "item.craftycuisine.frosted_sugar_cookie_egg_tooltip");

    public static final Item PUMPKIN_COOKIE = new Item(new FabricItemSettings().food(BIG_COOKIE_FOOD));
    public static final Item FROSTED_PUMPKIN_COOKIE = new DyeableCookieItem(new FabricItemSettings().food(FROSTED_BIG_COOKIE_FOOD), "");

    static {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.COOKIE.getDefaultStack(),
                new Item[]{
                        SUGAR_COOKIE,
                        FROSTED_SUGAR_COOKIE,
                        SUGAR_COOKIE_SQUARE,
                        FROSTED_SUGAR_COOKIE_SQUARE,
                        SUGAR_COOKIE_SQUARE,
                        SUGAR_COOKIE_STAR,
                        FROSTED_SUGAR_COOKIE_STAR,
                        SUGAR_COOKIE_TREE,
                        FROSTED_SUGAR_COOKIE_TREE,
                        SUGAR_COOKIE_CREEPER,
                        FROSTED_SUGAR_COOKIE_CREEPER,
                        SUGAR_COOKIE_HEART,
                        FROSTED_SUGAR_COOKIE_HEART,
                        SUGAR_COOKIE_SHAMROCK,
                        FROSTED_SUGAR_COOKIE_SHAMROCK,
                        SUGAR_COOKIE_EGG,
                        FROSTED_SUGAR_COOKIE_EGG,
                        PUMPKIN_COOKIE,
                        FROSTED_PUMPKIN_COOKIE
                }
        ));
    }

    // monster
    public static final Item COOKED_SPIDER_EYE = new Item(new FabricItemSettings().food(COOKED_SPIDER_EYE_FOOD));
    public static final Item MONSTER_MEATBALLS = new StewItem(new FabricItemSettings().food(MONSTER_MEATBALLS_FOOD).maxCount(1));
    public static final Item MONSTER_MANICOTTI = new MonsterManicottiItem(new FabricItemSettings().food(MONSTER_MANICOTTI_FOOD));

    static {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.SPIDER_EYE.getDefaultStack(),
                new Item[]{
                        COOKED_SPIDER_EYE,
                        MONSTER_MEATBALLS,
                        MONSTER_MANICOTTI
                }
        ));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.addBefore(Items.HONEYCOMB.getDefaultStack(), COOKED_SPIDER_EYE));
    }

    // soups and stews
    public static final Item CHOCOLATE_PUDDING = new StewItem(new FabricItemSettings().food(CHOCOLATE_PUDDING_FOOD).maxCount(1));
    public static final Item CACTUS_SOUP = new StewItem(new FabricItemSettings().food(CACTUS_SOUP_FOOD).maxCount(1));
    public static final Item CRIMSON_STEW = new StewItem(new FabricItemSettings().food(CRIMSON_STEW_FOOD).maxCount(1));
    public static final Item WARPED_STEW = new RandomPotionBowlItem(new FabricItemSettings().food(WARPED_STEW_FOOD).maxCount(1));
    public static final Item GLOW_RAMEN = new StewItem(new FabricItemSettings().food(GLOW_RAMEN_FOOD).maxCount(1));
    public static final Item ROOT_RISOTTO = new StewItem(new FabricItemSettings().food(ROOT_RISOTTO_FOOD).maxCount(1));

    static {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.MUSHROOM_STEW.getDefaultStack(),
                new Item[]{
                        CRIMSON_STEW,
                        WARPED_STEW,
                        CHOCOLATE_PUDDING,
                        CACTUS_SOUP
                }
        ));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.BEETROOT_SOUP.getDefaultStack(),
                new Item[]{
                        GLOW_RAMEN,
                        ROOT_RISOTTO
                }
        ));
    }

    // bacon
    public static final Item BACON = new Item(new FabricItemSettings().food(BACON_FOOD));
    public static final Item COOKED_BACON = new Item(new FabricItemSettings().food(COOKED_BACON_FOOD));

    static {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.COOKED_PORKCHOP.getDefaultStack(),
                new Item[]{
                        BACON,
                        COOKED_BACON
                }
        ));
    }

    public static CraftyCuisineConfig config;

    @Override
    public void onInitialize() {
        config = AutoConfig.register(CraftyCuisineConfig.class, GsonConfigSerializer::new).getConfig();

        Registry.register(Registries.ITEM, new Identifier(mod_id, "cookie_cutter_square"), COOKIE_CUTTER_SQUARE);
        Registry.register(Registries.ITEM, new Identifier(mod_id, "cookie_cutter_star"), COOKIE_CUTTER_STAR);
        Registry.register(Registries.ITEM, new Identifier(mod_id, "cookie_cutter_tree"), COOKIE_CUTTER_TREE);
        Registry.register(Registries.ITEM, new Identifier(mod_id, "cookie_cutter_creeper"), COOKIE_CUTTER_CREEPER);
        Registry.register(Registries.ITEM, new Identifier(mod_id, "cookie_cutter_heart"), COOKIE_CUTTER_HEART);
        Registry.register(Registries.ITEM, new Identifier(mod_id, "cookie_cutter_shamrock"), COOKIE_CUTTER_SHAMROCK);
        Registry.register(Registries.ITEM, new Identifier(mod_id, "cookie_cutter_egg"), COOKIE_CUTTER_EGG);

        // ice creams
        Registry.register(Registries.ITEM, new Identifier(mod_id, "ice_cream"), ICE_CREAM);
        Registry.register(Registries.ITEM, new Identifier(mod_id, "apple_ice_cream"), APPLE_ICE_CREAM);
        Registry.register(Registries.ITEM, new Identifier(mod_id, "chocolate_ice_cream"), CHOCOLATE_ICE_CREAM);
        Registry.register(Registries.ITEM, new Identifier(mod_id, "chorus_ice_cream"), CHORUS_ICE_CREAM);
        Registry.register(Registries.ITEM, new Identifier(mod_id, "melon_ice_cream"), MELON_ICE_CREAM);
        Registry.register(Registries.ITEM, new Identifier(mod_id, "pumpkin_ice_cream"), PUMPKIN_ICE_CREAM);
        Registry.register(Registries.ITEM, new Identifier(mod_id, "sweet_berry_ice_cream"), SWEET_BERRY_ICE_CREAM);
        Registry.register(Registries.ITEM, new Identifier(mod_id, "glow_berry_ice_cream"), GLOW_BERRY_ICE_CREAM);
        Registry.register(Registries.ITEM, new Identifier(mod_id, "honey_ice_cream"), HONEY_ICE_CREAM);

        //if (!config.disableAllFoods) {
            //if (config.cookedCarrotEnabled) {
                Registry.register(Registries.ITEM, new Identifier(mod_id, "cooked_carrot"), COOKED_CARROT);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "baked_apple"), BAKED_APPLE);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "cooked_brown_mushroom"), COOKED_BROWN_MUSHROOM);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "cooked_red_mushroom"), COOKED_RED_MUSHROOM);
            //}
            //if (config.friedEggEnabled) {
                Registry.register(Registries.ITEM, new Identifier(mod_id, "cooked_egg"), COOKED_EGG);
            //}
            //if (config.turtleEggEnabled) {
                Registry.register(Registries.ITEM, new Identifier(mod_id, "cooked_turtle_egg"), COOKED_TURTLE_EGG);
            //}
            //if (config.piesEnabled) {
                Registry.register(Registries.ITEM, new Identifier(mod_id, "apple_pie"), APPLE_PIE);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "chocolate_pie"), CHOCOLATE_PIE);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "chorus_pie"), CHORUS_PIE);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "melon_pie"), MELON_PIE);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "sweet_berry_pie"), SWEET_BERRY_PIE);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "glow_berry_pie"), GLOW_BERRY_PIE);
            //}
            //if (config.shepherdsPieEnabled) {
                Registry.register(Registries.ITEM, new Identifier(mod_id, "shepherds_pie"), SHEPHERDS_PIE);
            //}
            //if (config.jamsAndBreadEnabled) {
                Registry.register(Registries.ITEM, new Identifier(mod_id, "sweet_berry_jam"), SWEET_BERRY_JAM);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "glow_berry_jam"), GLOW_BERRY_JAM);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "sweet_berry_bread"), SWEET_BERRY_BREAD);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "glow_berry_bread"), GLOW_BERRY_BREAD);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "honey_bread"), HONEY_BREAD);
            //}
            //if (config.candiedEnabled) {
                Registry.register(Registries.ITEM, new Identifier(mod_id, "candied_apple"), CANDIED_APPLE);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "candied_chorus_fruit"), CANDIED_CHORUS_FRUIT);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "candied_melon_slice"), CANDIED_MELON_SLICE);
            //}
            //if (config.sweetBerryCandyEnabled) {
                Registry.register(Registries.ITEM, new Identifier(mod_id, "sweet_berry_candy"), SWEET_BERRY_CANDY);
            //}
            //if (config.sushiEnabled) {
                Registry.register(Registries.ITEM, new Identifier(mod_id, "sushi"), SUSHI);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "salmon_cakes"), SALMON_CAKES);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "fish_soup"), FISH_SOUP);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "cod_surprise"), COD_SURPRISE);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "seafoam_pudding"), SEAFOAM_PUDDING);
            //}
            //if (config.cookiesEnabled) {
                Registry.register(Registries.ITEM, new Identifier(mod_id, "sugar_cookie"), SUGAR_COOKIE);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "frosted_sugar_cookie"), FROSTED_SUGAR_COOKIE);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "sugar_cookie_square"), SUGAR_COOKIE_SQUARE);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "frosted_sugar_cookie_square"), FROSTED_SUGAR_COOKIE_SQUARE);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "sugar_cookie_star"), SUGAR_COOKIE_STAR);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "frosted_sugar_cookie_star"), FROSTED_SUGAR_COOKIE_STAR);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "sugar_cookie_tree"), SUGAR_COOKIE_TREE);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "frosted_sugar_cookie_tree"), FROSTED_SUGAR_COOKIE_TREE);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "sugar_cookie_creeper"), SUGAR_COOKIE_CREEPER);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "frosted_sugar_cookie_creeper"), FROSTED_SUGAR_COOKIE_CREEPER);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "sugar_cookie_heart"), SUGAR_COOKIE_HEART);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "frosted_sugar_cookie_heart"), FROSTED_SUGAR_COOKIE_HEART);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "sugar_cookie_shamrock"), SUGAR_COOKIE_SHAMROCK);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "frosted_sugar_cookie_shamrock"), FROSTED_SUGAR_COOKIE_SHAMROCK);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "sugar_cookie_egg"), SUGAR_COOKIE_EGG);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "frosted_sugar_cookie_egg"), FROSTED_SUGAR_COOKIE_EGG);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "pumpkin_cookie"), PUMPKIN_COOKIE);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "frosted_pumpkin_cookie"), FROSTED_PUMPKIN_COOKIE);
            //}
            //if (config.monsterFoodsEnabled) {
                Registry.register(Registries.ITEM, new Identifier(mod_id, "cooked_spider_eye"), COOKED_SPIDER_EYE);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "monster_meatballs"), MONSTER_MEATBALLS);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "monster_manicotti"), MONSTER_MANICOTTI);
            //}
            //if (config.soupsAndStewsEnabled) {
                Registry.register(Registries.ITEM, new Identifier(mod_id, "chocolate_pudding"), CHOCOLATE_PUDDING);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "cactus_soup"), CACTUS_SOUP);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "crimson_fungus_stew"), CRIMSON_STEW);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "warped_fungus_stew"), WARPED_STEW);
            //}
            //if (config.pastasEnabled) {
                Registry.register(Registries.ITEM, new Identifier(mod_id, "glow_ramen"), GLOW_RAMEN);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "root_risotto"), ROOT_RISOTTO);
            //}
            //if (config.baconEnabled) {
                Registry.register(Registries.ITEM, new Identifier(mod_id, "bacon"), BACON);
                Registry.register(Registries.ITEM, new Identifier(mod_id, "cooked_bacon"), COOKED_BACON);
            //}
        //}

        // fast food
        // have this iterate through tags someday or something
        // actually, no, this is fine because tags don't register at runtime lol
        for (String s : config.fastFood) {
            try {
                Item i = Registries.ITEM.get(new Identifier(s));
                if (i.isFood()) {
                    ((FoodComponentAccessor) Objects.requireNonNull(i.getFoodComponent())).setSnack(true);
                    LOGGER.debug("Registered food item " + s);
                } else {
                    LOGGER.error("Something went wrong with item " + s + "!  It's not an item or doesn't have a food component.");
                }
            } catch (Exception e) {
                LOGGER.error("Something went wrong with item " + s + "!  It's not an item or doesn't have a food component.");
            }

        }


        // trades
        if (config.fishSoupTrade) {
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 4,
                    factories -> {
                        factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(FISH_SOUP, 1), 16, 3, 0.05f));
                    });
        }

        LOGGER.info("Crafty Cuisine has been initialized");
    }

    public static StatusEffect randomStatusEffect(Random random) {
        StatusEffect effect;
        int randomInt = random.nextInt(6);
        if (randomInt <= 0) {
            effect = StatusEffects.REGENERATION;
        } else if (randomInt <= 1) {
            effect = StatusEffects.FIRE_RESISTANCE;
        } else if (randomInt <= 2) {
            effect = StatusEffects.HASTE;
        } else if (randomInt <= 3) {
            effect = StatusEffects.POISON;
        } else if (randomInt <= 4) {
            effect = StatusEffects.BLINDNESS;
        } else {
            effect = StatusEffects.WEAKNESS;
        }
        return effect;
    }
}
