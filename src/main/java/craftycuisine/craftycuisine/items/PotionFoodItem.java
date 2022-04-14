package craftycuisine.craftycuisine.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.StewItem;
import net.minecraft.world.World;

public class PotionFoodItem extends Item {

    StatusEffect effect;

    public PotionFoodItem(Item.Settings settings, StatusEffect effect) {
        super(settings);
        this.effect = effect;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        if (!world.isClient) {
            user.addStatusEffect(new StatusEffectInstance(effect, 600));
        }
        return itemStack;
    }
}
