package net.mobz;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import javax.annotation.Nullable;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;

public interface IAbstractedAPI {
	<T extends Item> Supplier<T> registerItem(String name, @Nullable CreativeModeTab tab, Function<Item.Properties, T> constructor, @Nullable Consumer<T> setter);

	<T extends Block> Supplier<T> registerBlock(String name, CreativeModeTab tab, Function<BlockBehaviour.Properties, T> blockConstructor,
			BiFunction<T, Item.Properties, BlockItem> blockItemConstructor, Consumer<T> setter);

	<E extends Entity, T extends EntityType<E>> Supplier<T> registerEntityType(String name, Supplier<T> constructor,
			Supplier<AttributeSupplier.Builder> attribModifierSupplier, @Nullable Consumer<T> setter);

	Supplier<Holder<SoundEvent>> registerSound(String name, ResourceLocation resloc, Consumer<SoundEvent> setter);

	CreativeModeTab tab(ResourceLocation resLoc, Supplier<ItemStack> iconSupplier);

	Supplier<MobBucketItem> newMobBucketItem(Supplier<? extends EntityType<? extends Mob>> entitySupplier,
			Supplier<? extends Fluid> fluidSupplier, Supplier<? extends SoundEvent> soundSupplier,
			Item.Properties properties);

	<T> Supplier<DataComponentType<T>> registerDataComponentType(String name, UnaryOperator<DataComponentType.Builder<T>> builder, Consumer<DataComponentType<T>> setter);
}
