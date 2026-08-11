package io.github.randommcsomethin.craftycuisine.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

public interface EntityAttackingEntityCallback {
    Event<EntityAttackingEntityCallback> EVENT = EventFactory.createArrayBacked(EntityAttackingEntityCallback.class,
            (listeners) -> (attacker, target) -> {
                for (EntityAttackingEntityCallback listener : listeners) {
                    ActionResult result = listener.interact(attacker, target);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(Entity attacker, Entity target);
}
