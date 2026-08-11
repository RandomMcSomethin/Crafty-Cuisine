package io.github.randommcsomethin.craftycuisine.mixin;

import io.github.randommcsomethin.craftycuisine.event.EntityAttackingEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class LivingEntityMixin {

    @Inject(method = "handleAttack", at = @At("HEAD"), cancellable = true)
    private void onAttack(Entity attacker, CallbackInfoReturnable<Boolean> cir) {
        ActionResult result = EntityAttackingEntityCallback.EVENT.invoker().interact(attacker, (Entity) (Object) this);

        if (result == ActionResult.FAIL) {
            cir.setReturnValue(false);
        }
    }
}
