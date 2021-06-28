package net.mobz.item.weapon;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.world.World;

public class SwordBase extends SwordItem {
    public SwordBase(IItemTier IItemTier_1, Item.Properties properties) {
        super(IItemTier_1, 0, -2.4f, properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new TranslationTextComponent("item.mobz.sword.tooltip"));
    }
}
