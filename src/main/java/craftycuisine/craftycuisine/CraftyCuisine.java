package craftycuisine.craftycuisine;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import craftycuisine.craftycuisine.config.CraftyCuisineConfig;
import craftycuisine.craftycuisine.items.*;
//import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
//import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import craftycuisine.craftycuisine.mixin.FoodComponentAccessor;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntryList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;

public class CraftyCuisine implements ModInitializer {
    public static final String mod_id = "craftycuisine";
    public static final Logger LOGGER = LoggerFactory.getLogger(mod_id);

    // tags
    //public static final TagKey<Item> FAST_FOOD = TagKey.of(Registry.ITEM_KEY, new Identifier(mod_id, "fast_food"));

    // food components
    public static final FoodComponent COOKED_CARROT_FOOD = new FoodComponent.Builder().hunger(4).saturationModifier(1).build();
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
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300), 1.0F)
            .build();
    public static final FoodComponent SALMON_CAKES_FOOD = new FoodComponent.Builder().hunger(9).saturationModifier(0.5F)
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600), 1.0F)
            .build();
    public static final FoodComponent SUSHI_FOOD = new FoodComponent.Builder().hunger(10).saturationModifier(0.5F)
            .statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 600), 1.0F)
            .meat()
            .build();

    // items
    public static final Item COOKIE_CUTTER_SQUARE = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item COOKIE_CUTTER_STAR = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item COOKIE_CUTTER_TREE = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item COOKIE_CUTTER_CREEPER = new Item(new FabricItemSettings().group(ItemGroup.MISC));

    public static final Item COOKED_CARROT = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(COOKED_CARROT_FOOD));
    public static final Item COOKED_EGG = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.POTATO));
    public static final Item COOKED_TURTLE_EGG = new CookedTurtleEggItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.GOLDEN_CARROT));

    public static final Item APPLE_PIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE));
    public static final Item CHOCOLATE_PIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE));
    public static final Item CHORUS_PIE = new ChorusFruitItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE));
    public static final Item MELON_PIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE));
    public static final Item SWEET_BERRY_PIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE));
    public static final Item GLOW_BERRY_PIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE));
    public static final Item SHEPHERDS_PIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(SHEPHERDS_PIE_FOOD));

    public static final Item SWEET_BERRY_JAM = new DrinkableItem(new FabricItemSettings().group(ItemGroup.FOOD).food(SWEET_BERRY_JAM_FOOD).recipeRemainder(Items.GLASS_BOTTLE));
    public static final Item GLOW_BERRY_JAM = new DrinkableItem(new FabricItemSettings().group(ItemGroup.FOOD).food(GLOW_BERRY_JAM_FOOD).recipeRemainder(Items.GLASS_BOTTLE));
    public static final Item SWEET_BERRY_BREAD = new PoisonCureItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.SUSPICIOUS_STEW), false);
    public static final Item GLOW_BERRY_BREAD = new PoisonCureItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.SUSPICIOUS_STEW), true);
    public static final Item HONEY_BREAD = new PoisonCureItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.SUSPICIOUS_STEW), false);

    public static final Item CANDIED_APPLE = new CandiedItem(new FabricItemSettings().group(ItemGroup.FOOD).food(CANDIED_FOOD));
    public static final Item CANDIED_CHORUS_FRUIT = new ChorusFruitItem(new FabricItemSettings().group(ItemGroup.FOOD).food(CANDIED_FOOD));
    public static final Item CANDIED_MELON_SLICE = new CandiedItem(new FabricItemSettings().group(ItemGroup.FOOD).food(CANDIED_FOOD));
    public static final Item SWEET_BERRY_CANDY = new DrinkableItem(new FabricItemSettings().group(ItemGroup.FOOD).food(SWEET_BERRY_CANDY_FOOD).recipeRemainder(Items.GLASS_BOTTLE));

    public static final Item SUSHI = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(SUSHI_FOOD));
    public static final Item SALMON_CAKES = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(SALMON_CAKES_FOOD));

    public static final Item SUGAR_COOKIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.COOKIE));
    public static final Item FROSTED_SUGAR_COOKIE = new DyeableCookieItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FROSTED_COOKIE_FOOD), "");
    public static final Item SUGAR_COOKIE_SQUARE = new TooltipItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.COOKIE), "item.craftycuisine.sugar_cookie_square_tooltip");
    public static final Item FROSTED_SUGAR_COOKIE_SQUARE = new DyeableCookieItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FROSTED_COOKIE_FOOD), "item.craftycuisine.frosted_sugar_cookie_square_tooltip");
    public static final Item SUGAR_COOKIE_STAR = new TooltipItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.COOKIE), "item.craftycuisine.sugar_cookie_star_tooltip");
    public static final Item FROSTED_SUGAR_COOKIE_STAR = new DyeableCookieItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FROSTED_COOKIE_FOOD), "item.craftycuisine.frosted_sugar_cookie_star_tooltip");
    public static final Item SUGAR_COOKIE_TREE = new TooltipItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.COOKIE), "item.craftycuisine.sugar_cookie_tree_tooltip");
    public static final Item FROSTED_SUGAR_COOKIE_TREE = new DyeableCookieItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FROSTED_COOKIE_FOOD), "item.craftycuisine.frosted_sugar_cookie_tree_tooltip");
    public static final Item SUGAR_COOKIE_CREEPER = new TooltipItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.COOKIE), "item.craftycuisine.sugar_cookie_creeper_tooltip");
    public static final Item FROSTED_SUGAR_COOKIE_CREEPER = new DyeableCookieItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FROSTED_COOKIE_FOOD), "item.craftycuisine.frosted_sugar_cookie_creeper_tooltip");

    public static final Item PUMPKIN_COOKIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(BIG_COOKIE_FOOD));
    public static final Item FROSTED_PUMPKIN_COOKIE = new DyeableCookieItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FROSTED_BIG_COOKIE_FOOD), "");

    public static final Item COOKED_SPIDER_EYE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(COOKED_SPIDER_EYE_FOOD));
    public static final Item MONSTER_MEATBALLS = new StewItem(new FabricItemSettings().group(ItemGroup.FOOD).food(MONSTER_MEATBALLS_FOOD).maxCount(1));
    public static final Item MONSTER_MANICOTTI = new MonsterManicottiItem(new FabricItemSettings().group(ItemGroup.FOOD).food(MONSTER_MANICOTTI_FOOD));

    public static final Item CHOCOLATE_PUDDING = new PotionBowlItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.BREAD).maxCount(1), StatusEffects.REGENERATION);
    public static final Item CACTUS_SOUP = new PotionBowlItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.POTATO).maxCount(1), StatusEffects.SPEED);
    public static final Item CRIMSON_STEW = new StewItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.BREAD).maxCount(1));
    public static final Item WARPED_STEW = new RandomPotionBowlItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.MUSHROOM_STEW).maxCount(1));
    public static final Item GLOW_RAMEN = new PotionBowlItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.RABBIT_STEW).maxCount(1), StatusEffects.GLOWING);
    public static final Item ROOT_RISOTTO = new PotionBowlItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.MUSHROOM_STEW).maxCount(1), StatusEffects.STRENGTH);

    public static final Item BACON = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(BACON_FOOD));
    public static final Item COOKED_BACON = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(COOKED_BACON_FOOD));
    public static CraftyCuisineConfig config;

    @Override
    public void onInitialize() {
        config = AutoConfig.register(CraftyCuisineConfig.class, GsonConfigSerializer::new).getConfig();

        Registry.register(Registry.ITEM, new Identifier(mod_id, "cookie_cutter_square"), COOKIE_CUTTER_SQUARE);
        Registry.register(Registry.ITEM, new Identifier(mod_id, "cookie_cutter_star"), COOKIE_CUTTER_STAR);
        Registry.register(Registry.ITEM, new Identifier(mod_id, "cookie_cutter_tree"), COOKIE_CUTTER_TREE);
        Registry.register(Registry.ITEM, new Identifier(mod_id, "cookie_cutter_creeper"), COOKIE_CUTTER_CREEPER);

        //if (!config.disableAllFoods) {
            //if (config.cookedCarrotEnabled) {
                Registry.register(Registry.ITEM, new Identifier(mod_id, "cooked_carrot"), COOKED_CARROT);
            //}
            //if (config.friedEggEnabled) {
                Registry.register(Registry.ITEM, new Identifier(mod_id, "cooked_egg"), COOKED_EGG);
            //}
            //if (config.turtleEggEnabled) {
                Registry.register(Registry.ITEM, new Identifier(mod_id, "cooked_turtle_egg"), COOKED_TURTLE_EGG);
            //}
            //if (config.piesEnabled) {
                Registry.register(Registry.ITEM, new Identifier(mod_id, "apple_pie"), APPLE_PIE);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "chocolate_pie"), CHOCOLATE_PIE);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "chorus_pie"), CHORUS_PIE);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "melon_pie"), MELON_PIE);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "sweet_berry_pie"), SWEET_BERRY_PIE);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "glow_berry_pie"), GLOW_BERRY_PIE);
            //}
            //if (config.shepherdsPieEnabled) {
                Registry.register(Registry.ITEM, new Identifier(mod_id, "shepherds_pie"), SHEPHERDS_PIE);
            //}
            //if (config.jamsAndBreadEnabled) {
                Registry.register(Registry.ITEM, new Identifier(mod_id, "sweet_berry_jam"), SWEET_BERRY_JAM);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "glow_berry_jam"), GLOW_BERRY_JAM);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "sweet_berry_bread"), SWEET_BERRY_BREAD);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "glow_berry_bread"), GLOW_BERRY_BREAD);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "honey_bread"), HONEY_BREAD);
            //}
            //if (config.candiedEnabled) {
                Registry.register(Registry.ITEM, new Identifier(mod_id, "candied_apple"), CANDIED_APPLE);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "candied_chorus_fruit"), CANDIED_CHORUS_FRUIT);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "candied_melon_slice"), CANDIED_MELON_SLICE);
            //}
            //if (config.sweetBerryCandyEnabled) {
                Registry.register(Registry.ITEM, new Identifier(mod_id, "sweet_berry_candy"), SWEET_BERRY_CANDY);
            //}
            //if (config.sushiEnabled) {
                Registry.register(Registry.ITEM, new Identifier(mod_id, "sushi"), SUSHI);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "salmon_cakes"), SALMON_CAKES);
            //}
            //if (config.cookiesEnabled) {
                Registry.register(Registry.ITEM, new Identifier(mod_id, "sugar_cookie"), SUGAR_COOKIE);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "frosted_sugar_cookie"), FROSTED_SUGAR_COOKIE);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "sugar_cookie_square"), SUGAR_COOKIE_SQUARE);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "frosted_sugar_cookie_square"), FROSTED_SUGAR_COOKIE_SQUARE);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "sugar_cookie_star"), SUGAR_COOKIE_STAR);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "frosted_sugar_cookie_star"), FROSTED_SUGAR_COOKIE_STAR);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "sugar_cookie_tree"), SUGAR_COOKIE_TREE);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "frosted_sugar_cookie_tree"), FROSTED_SUGAR_COOKIE_TREE);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "sugar_cookie_creeper"), SUGAR_COOKIE_CREEPER);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "frosted_sugar_cookie_creeper"), FROSTED_SUGAR_COOKIE_CREEPER);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "pumpkin_cookie"), PUMPKIN_COOKIE);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "frosted_pumpkin_cookie"), FROSTED_PUMPKIN_COOKIE);
            //}
            //if (config.monsterFoodsEnabled) {
                Registry.register(Registry.ITEM, new Identifier(mod_id, "cooked_spider_eye"), COOKED_SPIDER_EYE);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "monster_meatballs"), MONSTER_MEATBALLS);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "monster_manicotti"), MONSTER_MANICOTTI);
            //}
            //if (config.soupsAndStewsEnabled) {
                Registry.register(Registry.ITEM, new Identifier(mod_id, "chocolate_pudding"), CHOCOLATE_PUDDING);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "cactus_soup"), CACTUS_SOUP);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "crimson_fungus_stew"), CRIMSON_STEW);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "warped_fungus_stew"), WARPED_STEW);
            //}
            //if (config.pastasEnabled) {
                Registry.register(Registry.ITEM, new Identifier(mod_id, "glow_ramen"), GLOW_RAMEN);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "root_risotto"), ROOT_RISOTTO);
            //}
            //if (config.baconEnabled) {
                Registry.register(Registry.ITEM, new Identifier(mod_id, "bacon"), BACON);
                Registry.register(Registry.ITEM, new Identifier(mod_id, "cooked_bacon"), COOKED_BACON);
            //}
        //}

        // fast food
        // have this iterate through tags someday or something
        // actually, no, this is fine because tags don't register at runtime lol
        for (String s : config.fastFood) {
            try {
                Item i = Registry.ITEM.get(new Identifier(s));
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
