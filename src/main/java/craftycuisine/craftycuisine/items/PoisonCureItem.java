package craftycuisine.craftycuisine.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class PoisonCureItem extends Item {
    private boolean glow;
    public PoisonCureItem(FabricItemSettings food, boolean glow) {
        super(food); this.glow = glow;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient) {
            user.removeStatusEffect(StatusEffects.POISON);
            if (glow) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 300));
            }
        }
        super.finishUsing(stack, world, user);
        return stack;
    }
}
