package craftycuisine.craftycuisine.items;

import craftycuisine.craftycuisine.CraftyCuisine;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MushroomStewItem;
import net.minecraft.world.World;

public class RandomPotionBowlItem extends MushroomStewItem {

    StatusEffect effect;

    public RandomPotionBowlItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        if (!world.isClient) {
            user.addStatusEffect(new StatusEffectInstance(CraftyCuisine.randomStatusEffect(world.getRandom()), 600));
        }
        return user instanceof PlayerEntity && ((PlayerEntity)user).abilities.creativeMode ? itemStack : new ItemStack(Items.BOWL);
    }
}
