package io.github.randommcsomethin.craftycuisine.effect;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class AntidoteEffect extends StatusEffect {
    public AntidoteEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xFFBB22);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient()) {
            entity.removeStatusEffect(StatusEffects.POISON);
            entity.removeStatusEffect(StatusEffects.WEAKNESS);
            entity.removeStatusEffect(StatusEffects.HUNGER);
            entity.removeStatusEffect(StatusEffects.NAUSEA);
            if (amplifier > 0) {
                entity.removeStatusEffect(StatusEffects.WITHER);
            }
        }
        return super.applyUpdateEffect(entity, amplifier);
    }
}
