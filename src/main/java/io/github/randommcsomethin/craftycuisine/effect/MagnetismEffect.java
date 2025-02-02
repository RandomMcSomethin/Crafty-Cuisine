package io.github.randommcsomethin.craftycuisine.effect;

import io.github.randommcsomethin.craftycuisine.CraftyCuisine;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class MagnetismEffect extends StatusEffect {
    public MagnetismEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xAA9988);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient()) {
            boolean isPlayer = entity instanceof PlayerEntity;
            for (ItemEntity item : entity.getWorld().getNonSpectatingEntities(ItemEntity.class, new Box(entity.getPos(), entity.getPos()).expand(3.0F + 2 * amplifier))) {
                // player-specific way of magnetizing items
                if (isPlayer && !entity.isSneaking()) {
                    item.onPlayerCollision((PlayerEntity) entity);
                    continue;
                }
                // cooler but admittedly less practical way of magnetizing items
                if (!item.cannotPickup()) {
                    item.addVelocity(new Vec3d(entity.getX() - item.getX(), entity.getY() - item.getY(), entity.getZ() - item.getZ()).normalize().multiply(0.1));
                }
            }
        }
        return super.applyUpdateEffect(entity, amplifier);
    }
}
