package craftycuisine.craftycuisine;

import craftycuisine.craftycuisine.items.*;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Random;

public class CraftyCuisine implements ModInitializer {

    public static final FoodComponent COOKED_CARROT_FOOD = new FoodComponent.Builder().hunger(4).saturationModifier(1).build();
    public static final FoodComponent SWEET_BERRY_JAM_FOOD = new FoodComponent.Builder().hunger(2).saturationModifier(1.5F).build();
    public static final FoodComponent CANDIED_FOOD = new FoodComponent.Builder().hunger(6).saturationModifier(1).build();
    public static final FoodComponent BIG_COOKIE_FOOD = new FoodComponent.Builder().hunger(3).saturationModifier(0.4F).build();
    public static final FoodComponent COOKED_BACON_FOOD = new FoodComponent.Builder().hunger(4).saturationModifier(0.8F).build();

    public static final Item COOKED_CARROT = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(COOKED_CARROT_FOOD));
    public static final Item COOKED_EGG = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.POTATO));
    public static final Item COOKED_TURTLE_EGG = new CookedTurtleEggItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.GOLDEN_CARROT));
    public static final Item APPLE_PIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE));
    public static final Item CHOCOLATE_PIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE));
    public static final Item CHORUS_PIE = new ChorusFruitItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE));
    public static final Item MELON_PIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE));
    public static final Item SWEET_BERRY_PIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.PUMPKIN_PIE));
    public static final Item SHEPHERDS_PIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.GOLDEN_CARROT));
    public static final Item SWEET_BERRY_JAM = new DrinkableItem(new FabricItemSettings().group(ItemGroup.FOOD).food(SWEET_BERRY_JAM_FOOD).recipeRemainder(Items.GLASS_BOTTLE));
    public static final Item SWEET_BERRY_BREAD = new PoisonCureItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.SUSPICIOUS_STEW));
    public static final Item HONEY_BREAD = new PoisonCureItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.SUSPICIOUS_STEW));
    public static final Item CANDIED_APPLE = new CandiedItem(new FabricItemSettings().group(ItemGroup.FOOD).food(CANDIED_FOOD));
    public static final Item CANDIED_CHORUS_FRUIT = new ChorusFruitItem(new FabricItemSettings().group(ItemGroup.FOOD).food(CANDIED_FOOD));
    public static final Item CANDIED_MELON_SLICE = new CandiedItem(new FabricItemSettings().group(ItemGroup.FOOD).food(CANDIED_FOOD));
    public static final Item SUSHI = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.COOKED_BEEF));
    public static final Item SUGAR_COOKIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.COOKIE));
    public static final Item PUMPKIN_COOKIE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(BIG_COOKIE_FOOD));
    public static final Item COOKED_SPIDER_EYE = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.CARROT));
    public static final Item CHOCOLATE_PUDDING = new PotionBowlItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.BREAD).maxCount(1), StatusEffects.REGENERATION);
    public static final Item CACTUS_SOUP = new PotionBowlItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.POTATO).maxCount(1), StatusEffects.SPEED);
    public static final Item CRIMSON_STEW = new MushroomStewItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.BREAD).maxCount(1));
    public static final Item WARPED_STEW = new RandomPotionBowlItem(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.MUSHROOM_STEW).maxCount(1));
    public static final Item BACON = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(FoodComponents.TROPICAL_FISH));
    public static final Item COOKED_BACON = new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(COOKED_BACON_FOOD));
    private CuisineConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(CuisineConfig.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(CuisineConfig.class).getConfig();

        Registry.register(Registry.ITEM, new Identifier("craftycuisine", "cooked_carrot"), COOKED_CARROT);
        Registry.register(Registry.ITEM, new Identifier("craftycuisine", "cooked_egg"), COOKED_EGG);
        Registry.register(Registry.ITEM, new Identifier("craftycuisine", "cooked_turtle_egg"), COOKED_TURTLE_EGG);
        if (config.piesEnabled) {
            Registry.register(Registry.ITEM, new Identifier("craftycuisine", "apple_pie"), APPLE_PIE);
            Registry.register(Registry.ITEM, new Identifier("craftycuisine", "chocolate_pie"), CHOCOLATE_PIE);
            Registry.register(Registry.ITEM, new Identifier("craftycuisine", "chorus_pie"), CHORUS_PIE);
            Registry.register(Registry.ITEM, new Identifier("craftycuisine", "melon_pie"), MELON_PIE);
            Registry.register(Registry.ITEM, new Identifier("craftycuisine", "sweet_berry_pie"), SWEET_BERRY_PIE);
        }
        Registry.register(Registry.ITEM, new Identifier("craftycuisine", "shepherds_pie"), SHEPHERDS_PIE);
        Registry.register(Registry.ITEM, new Identifier("craftycuisine", "sweet_berry_jam"), SWEET_BERRY_JAM);
        Registry.register(Registry.ITEM, new Identifier("craftycuisine", "sweet_berry_bread"), SWEET_BERRY_BREAD);
        Registry.register(Registry.ITEM, new Identifier("craftycuisine", "honey_bread"), HONEY_BREAD);
        Registry.register(Registry.ITEM, new Identifier("craftycuisine", "candied_apple"), CANDIED_APPLE);
        Registry.register(Registry.ITEM, new Identifier("craftycuisine", "candied_chorus_fruit"), CANDIED_CHORUS_FRUIT);
        Registry.register(Registry.ITEM, new Identifier("craftycuisine", "candied_melon_slice"), CANDIED_MELON_SLICE);
        if (config.sushiEnabled) {
            Registry.register(Registry.ITEM, new Identifier("craftycuisine", "sushi"), SUSHI);
        }
        if (config.cookiesEnabled) {
            Registry.register(Registry.ITEM, new Identifier("craftycuisine", "sugar_cookie"), SUGAR_COOKIE);
            Registry.register(Registry.ITEM, new Identifier("craftycuisine", "pumpkin_cookie"), PUMPKIN_COOKIE);
        }
        Registry.register(Registry.ITEM, new Identifier("craftycuisine", "cooked_spider_eye"), COOKED_SPIDER_EYE);
        Registry.register(Registry.ITEM, new Identifier("craftycuisine", "chocolate_pudding"), CHOCOLATE_PUDDING);
        Registry.register(Registry.ITEM, new Identifier("craftycuisine", "cactus_soup"), CACTUS_SOUP);
        Registry.register(Registry.ITEM, new Identifier("craftycuisine", "crimson_fungus_stew"), CRIMSON_STEW);
        Registry.register(Registry.ITEM, new Identifier("craftycuisine", "warped_fungus_stew"), WARPED_STEW);
        if (config.baconEnabled) {
            Registry.register(Registry.ITEM, new Identifier("craftycuisine", "bacon"), BACON);
            Registry.register(Registry.ITEM, new Identifier("craftycuisine", "cooked_bacon"), COOKED_BACON);
        }
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
