package io.github.randommcsomethin.craftycuisine.effect;

import io.github.randommcsomethin.craftycuisine.CraftyCuisine;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class SweetToothEffect extends StatusEffect {
    public SweetToothEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xFF88AA);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getAttacking() instanceof LivingEntity && !entity.getAttacking().getType().isIn(TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CraftyCuisine.MOD_ID, "cannot_activate_sweet_tooth"))) && (entity.age - entity.getLastAttackTime()) == 1) {
            entity.heal(2.0F + amplifier);
        }
        return super.applyUpdateEffect(entity, amplifier);
    }
}
