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

    StatusEffect effect;

    public PotionBowlItem(Settings settings, StatusEffect effect) {
        super(settings);
        this.effect = effect;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        if (!world.isClient) {
            user.addStatusEffect(new StatusEffectInstance(effect, 600));
        }
        return user instanceof PlayerEntity && ((PlayerEntity)user).getAbilities().creativeMode ? itemStack : new ItemStack(Items.BOWL);
    }
}
