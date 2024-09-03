package net.mobz.data;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.mobz.MobZ;
import net.mobz.init.MobZArmors;
import net.mobz.init.MobZBlocks;
import net.mobz.init.MobZIcons;
import net.mobz.init.MobZItems;
import net.mobz.init.MobZWeapons;

public class ItemModelDataProvider implements DataProvider {
	public final static ModelTemplate BOW_PULLING = new ModelTemplate(Optional.of(new ResourceLocation("minecraft", "item/bow")),
			Optional.empty(), TextureSlot.LAYER0);

	private final PackOutput.PathProvider modelPathProvider;
	private final Registry<Item> itemRegistry;
	private final Predicate<ResourceLocation> existenceChecker;

	public ItemModelDataProvider(PackOutput packOutput, Registry<Item> itemRegistry, @Nullable Predicate<ResourceLocation> existenceChecker) {
		this.modelPathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
		this.itemRegistry = itemRegistry;
		this.existenceChecker = (existenceChecker == null) ? resLoc->true : existenceChecker;
	}

	@Override
	public String getName() {
		return "Item models for " + MobZ.MODID;
	}

	private void collect(Builder builder) {
		// Spawn Eggs
		this.itemRegistry.stream()
			.filter((item) -> this.itemRegistry.getKey(item).getNamespace().equals(MobZ.MODID))
			.filter((item) -> item instanceof SpawnEggItem)
			.forEach((item) -> builder.spawnEgg((SpawnEggItem) item));

		// Advancement Icons
		for (String headName: MobZIcons.headNames) {
			builder.simpleItem(headName);
		}

		// Items
		builder.simpleItem(MobZItems.AMAT_INGOT.get());
		builder.simpleItem(MobZItems.BEAR_LEATHER.get());
		builder.simpleItem(MobZItems.BOSS_INGOT.get());
		builder.simpleItem(MobZItems.FROZEN_POWDER.get());
		builder.simpleItem(MobZItems.HARDENEDMETAL_INGOT.get());

		builder.simpleItem(MobZItems.IMMUNITY_ORB.get());
		builder.simpleItem(MobZItems.LEVITATION_ORB.get());
		builder.simpleItem(MobZItems.ROTTEN_FLESH.get());
		for (int i = 1; i<=4; i++) {
			builder.simpleItem("sacrifice_knife_blood" + i);
			builder.simpleItem("sacrifice_knife_blood" + i + "dry1");
			builder.simpleItem("sacrifice_knife_blood" + i + "dry2");
		}

		builder.bowPulling("lilith_bow_pulling", 3);
		builder.simpleItem(MobZItems.SEAL_KEY.get());
		builder.simpleItem(MobZItems.SPAWN_EGG.get());
		builder.simpleItem(MobZItems.WHITE_BAG.get());

		builder.simpleItem(MobZItems.WITHER_POWDER.get());
		builder.simpleItem(MobZItems.MEDIVEAL_DISC.get());
		builder.simpleItem(MobZItems.MEDIVEAL_DISC2.get());

		// Weapon
		builder.handheldItem(MobZWeapons.ARMORED_SWORD.get());
		builder.handheldItem(MobZWeapons.BOSS_SWORD.get());
		builder.handheldItem(MobZWeapons.POISON_SWORD.get());
		builder.handheldItem(MobZWeapons.RAINBOW_SWORD.get());
		builder.handheldItem(MobZWeapons.STONE_TOMAHAWK.get());

		// Armor
		builder.simpleItem(MobZArmors.AMAT_HELMET.get());
		builder.simpleItem(MobZArmors.AMAT_CHESTPLATE.get());
		builder.simpleItem(MobZArmors.AMAT_LEGGINGS.get());
		builder.simpleItem(MobZArmors.AMAT_BOOTS.get());
		builder.simpleItem(MobZArmors.BOSS_HELMET.get());
		builder.simpleItem(MobZArmors.BOSS_CHESTPLATE.get());
		builder.simpleItem(MobZArmors.BOSS_LEGGINGS.get());
		builder.simpleItem(MobZArmors.BOSS_BOOTS.get());
		builder.simpleItem(MobZArmors.LIFE_HELMET.get());
		builder.simpleItem(MobZArmors.LIFE_CHESTPLATE.get());
		builder.simpleItem(MobZArmors.LIFE_LEGGINGS.get());
		builder.simpleItem(MobZArmors.LIFE_BOOTS.get());
		builder.simpleItem(MobZArmors.SPEED_BOOTS.get());
		builder.simpleItem(MobZArmors.SPEED2_BOOTS.get());

		// Blocks
		builder.block(MobZBlocks.AMAT_BLOCK.get());
		builder.block(MobZBlocks.BOSS_BLOCK.get());
		builder.block(MobZBlocks.BOSS_TROPHY.get());
		builder.block(MobZBlocks.ENDER_HEADER.get());
		builder.block(MobZBlocks.HARDENED_METAL_BLOCK.get());

		builder.block(MobZBlocks.TOTEM_BASE.get());
		builder.block(MobZBlocks.TOTEM_MIDDLE.get());
		builder.block(MobZBlocks.TOTEM_TOP.get());
	}

	@Override
	public CompletableFuture<?> run(CachedOutput pOutput) {
        Map<ResourceLocation, Supplier<JsonElement>> cachedOutput = Maps.<ResourceLocation, Supplier<JsonElement>>newHashMap();
		BiConsumer<ResourceLocation, Supplier<JsonElement>> outputConsumer = (resourceLocation, supplier) -> {
			Supplier<JsonElement> supplier2 = cachedOutput.put(resourceLocation, supplier);
			if (supplier2 != null) {
				throw new IllegalStateException("Duplicate model definition for " + resourceLocation);
			}
		};

		Builder builder = new Builder(outputConsumer, this.existenceChecker);

		this.collect(builder);

		return saveCollection(pOutput, cachedOutput, this.modelPathProvider::json);
	}

	private <T> CompletableFuture<?> saveCollection(CachedOutput cachedOutput, Map<T, ? extends Supplier<JsonElement>> map, Function<T, Path> function) {
		return CompletableFuture.allOf(map.entrySet().stream().map(entry -> {
			Path path = function.apply(entry.getKey());
			JsonElement jsonElement = (entry.getValue()).get();
			return DataProvider.saveStable(cachedOutput, jsonElement, path);
		}).toArray(CompletableFuture[]::new));
	}

	public static class Builder {
		private final BiConsumer<ResourceLocation, Supplier<JsonElement>> outputConsumer;
		private final Predicate<ResourceLocation> existenceChecker;

		public Builder(BiConsumer<ResourceLocation, Supplier<JsonElement>> outputConsumer,
				Predicate<ResourceLocation> existenceChecker) {
			this.outputConsumer = outputConsumer;
			this.existenceChecker = existenceChecker;
		}

		public static ResourceLocation getRealTextureLoc(ResourceLocation resLoc) {
			return new ResourceLocation(resLoc.getNamespace(), "textures/" + resLoc.getPath() + ".png");
		}

		public ResourceLocation getRealTextureLocWithCheck(ResourceLocation resLoc) {
			ResourceLocation realLoc = getRealTextureLoc(resLoc);
			if (!existenceChecker.test(realLoc)) {
				System.out.println("MobZ ItemModelDataProvider cannot find texture: " + realLoc);
			};
			return realLoc;
		}

		// From ItemModelGenerators
		public void generateFlatItem(Item item, ModelTemplate modelTemplate) {
			ResourceLocation textureResLoc = ModelLocationUtils.getModelLocation(item);
			getRealTextureLocWithCheck(textureResLoc);

			modelTemplate.create(textureResLoc, TextureMapping.layer0(item), this.outputConsumer);
	    }

		public void simpleItem(String string) {
			ResourceLocation textureResLoc = new ResourceLocation(MobZ.MODID, "item/" + string);
			getRealTextureLocWithCheck(textureResLoc);
			TextureMapping textureMapping = TextureMapping.layer0(textureResLoc);
			ModelTemplates.FLAT_ITEM.create(textureResLoc, textureMapping, this.outputConsumer);
		}

		public void bowPulling(String baseName, int count) {
			for (int i = 0; i < count; i++) {
				ResourceLocation textureResLoc = new ResourceLocation(MobZ.MODID, "item/" + baseName + "_" + i);
				getRealTextureLocWithCheck(textureResLoc);
				BOW_PULLING.create(textureResLoc, TextureMapping.layer0(textureResLoc), this.outputConsumer);
			}
		}

		public void simpleItem(Item item) {
			generateFlatItem(item, ModelTemplates.FLAT_ITEM);
		}

		public void block(Block block) {
			ResourceLocation textureResLoc = ModelLocationUtils.getModelLocation(block.asItem());
			ModelTemplate template = new ModelTemplate(Optional.of(ModelLocationUtils.getModelLocation(block)), null);
			template.create(textureResLoc, new TextureMapping(), this.outputConsumer);
		}

		public void handheldItem(Item item) {
			generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
		}

		public void spawnEgg(SpawnEggItem egg) {
		    JsonObject root = new JsonObject();
		    root.addProperty("parent", "minecraft:item/template_spawn_egg");
			this.outputConsumer.accept(ModelLocationUtils.getModelLocation(egg), ()->root);
		}
	}
}
