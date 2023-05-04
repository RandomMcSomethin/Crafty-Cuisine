package craftycuisine.craftycuisine.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.StewItem;
import net.minecraft.world.World;

public class PotionBowlItem extends StewItem {

    StatusEffect[] effects;

    public PotionBowlItem(Settings settings, StatusEffect effect) {
        super(settings);
        this.effects[0] = effect;
    }

    public PotionBowlItem(Settings settings, StatusEffect[] effects) {
        super(settings);
        this.effects = effects;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        if (!world.isClient) {
            if (effects.length == 1)
                user.addStatusEffect(new StatusEffectInstance(effects[0], 600));
            else // pick a random potion effect
                user.addStatusEffect(new StatusEffectInstance(effects[world.getRandom().nextInt(effects.length)], 600));
        }
        return user instanceof PlayerEntity && ((PlayerEntity)user).getAbilities().creativeMode ? itemStack : new ItemStack(Items.BOWL);
    }
}
