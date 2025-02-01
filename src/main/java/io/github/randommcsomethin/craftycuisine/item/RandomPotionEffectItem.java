package io.github.randommcsomethin.craftycuisine.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public class RandomPotionEffectItem extends Item {
    private final StatusEffect[] effects;

    public RandomPotionEffectItem(Settings settings, StatusEffect[] effects) {
        super(settings);
        this.effects = effects;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient) {
            user.addStatusEffect(new StatusEffectInstance(world.getRegistryManager().get(Registries.STATUS_EFFECT.getKey()).getEntry(effects[world.getRandom().nextInt(effects.length)]), 600));
        }
        return super.finishUsing(stack, world, user);
    }
}