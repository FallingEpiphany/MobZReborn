package net.mobz.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Sbow extends SimpleItem {
    public Sbow(String name, Item.Properties properties) {
        super(name, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        EffectInstance spd = new EffectInstance(Effect.byId(1), 7, 1, false, false);
        LivingEntity bob = (LivingEntity) entity;
        if (selected == true) {
           if (bob.isUsingItem()) {
              bob.addEffect(spd);
           }
        }
    }
}