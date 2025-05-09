package net.mobz.data;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import net.mobz.init.MobZArmors;
import net.mobz.init.MobZBlocks;
import net.mobz.init.MobZItems;
import net.mobz.init.MobZWeapons;

public class Recipes extends RecipeProvider {
	public static class Runner extends RecipeProvider.Runner {
		public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
	            super(output, registries);
	      }

		@Override
		protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
			return new Recipes(registries, output);
		}

		@Override
		public String getName() {
			return "MobZ Recipes";
		}
	}

	private final HolderGetter<Item> items;

	public Recipes(HolderLookup.Provider registries, RecipeOutput output) {
		super(registries, output);
		this.items = registries.lookupOrThrow(Registries.ITEM);
	}

	@Override
	public void buildRecipes() {
		// Amat
		mobZ9BlockStorageRecipes(RecipeCategory.MISC, MobZItems.AMAT_INGOT.get(),
				RecipeCategory.MISC, MobZBlocks.AMAT_BLOCK.get());

		armorSet(MobZItems.AMAT_INGOT.get(), "has_amat_ingot",
				MobZArmors.AMAT_HELMET.get(),
				MobZArmors.AMAT_CHESTPLATE.get(),
				MobZArmors.AMAT_LEGGINGS.get(),
				MobZArmors.AMAT_BOOTS.get());

		// Lilith bow
		ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, MobZItems.LILITH_BOW.get())
			.define('A', MobZItems.AMAT_INGOT.get())
			.define('G', Items.GOLD_INGOT)
			.define('S', Items.STRING)
			.pattern(" AS")
			.pattern("G S")
			.pattern(" AS")
			.unlockedBy("has_amat_ingot", has(MobZItems.AMAT_INGOT.get()))
			.save(this.output);

		ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, MobZItems.AMAT_INGOT.get())
			.define('D', Blocks.NETHER_BRICKS)
			.define('E', MobZItems.WITHER_POWDER.get()).define('F', Items.BLAZE_POWDER)
			.define('O', Items.MAGMA_CREAM).pattern("FOF").pattern("EDE").pattern("FOF")
			.unlockedBy("has_wither_powder", has(MobZItems.WITHER_POWDER.get()))
			.save(this.output);

		// Boss
		mobZ9BlockStorageRecipes(RecipeCategory.MISC, MobZItems.BOSS_INGOT.get(),
				RecipeCategory.MISC, MobZBlocks.BOSS_BLOCK.get());

		armorSet(MobZItems.BOSS_INGOT.get(), "has_boss_ingot",
				MobZArmors.BOSS_HELMET.get(),
				MobZArmors.BOSS_CHESTPLATE.get(),
				MobZArmors.BOSS_LEGGINGS.get(),
				MobZArmors.BOSS_BOOTS.get());

		ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, MobZWeapons.BOSS_SWORD.get())
			.define('X', MobZItems.BOSS_INGOT.get())
			.define('#', Items.STICK)
			.pattern("X")
			.pattern("X")
			.pattern("#")
			.unlockedBy("has_boss_ingot", has(MobZItems.BOSS_INGOT.get()))
			.save(this.output);

		// Hardened metal ingot
		mobZ9BlockStorageRecipes(RecipeCategory.MISC, MobZItems.HARDENEDMETAL_INGOT.get(),
				RecipeCategory.MISC, MobZBlocks.HARDENED_METAL_BLOCK.get());

		armorSet(MobZItems.HARDENEDMETAL_INGOT.get(), "has_hardened_metal_ingot",
				MobZArmors.LIFE_HELMET.get(),
				MobZArmors.LIFE_CHESTPLATE.get(),
				MobZArmors.LIFE_LEGGINGS.get(),
				MobZArmors.LIFE_BOOTS.get());

		SimpleCookingRecipeBuilder.blasting(Ingredient.of(MobZItems.HARDENEDMETAL_INGOT.get()),
				RecipeCategory.MISC, Items.IRON_INGOT, 0.4F, 400)
				.unlockedBy("has_hardened_metal_ingot", has(MobZItems.HARDENEDMETAL_INGOT.get()))
				.save(this.output, "mobz:iron_ingot_from_hardened_metal_ingot");

		// Sacrifice Knife
		ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, MobZItems.SACRIFICE_KNIFE.get())
			.define('X', MobZItems.HARDENEDMETAL_INGOT.get())
			.define('#', Items.STICK)
			.pattern("X")
			.pattern("#")
			.unlockedBy("has_hardened_metal_ingot", has(MobZItems.HARDENEDMETAL_INGOT.get()))
			.save(this.output);

		// Shield
		ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, MobZItems.SHIELD.get())
			.define('D', MobZItems.HARDENEDMETAL_INGOT.get())
			.define('E', ItemTags.PLANKS)
			.define('F', MobZItems.BEAR_LEATHER.get())
			.pattern("DED")
			.pattern("EFE")
			.pattern("DED")
			.unlockedBy("has_hardened_metal_ingot", has(MobZItems.HARDENEDMETAL_INGOT.get()))
			.save(this.output);

		// Bear leather
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(MobZItems.BEAR_LEATHER.get()),
				RecipeCategory.MISC, Items.LEATHER, 0.1F, 600)
			.unlockedBy("has_bear_leather", has(MobZItems.BEAR_LEATHER.get()))
			.save(this.output, "mobz:leather_from_bear_leather");

		armorSet(MobZItems.BEAR_LEATHER.get(), "has_bear_leather",
				null, null, null, MobZArmors.SPEED_BOOTS.get());

		// Speed2 boots
		ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, MobZArmors.SPEED2_BOOTS.get())
			.define('D', MobZArmors.SPEED_BOOTS.get())
			.define('E', Items.EMERALD)
			.define('F', Items.EMERALD_BLOCK)
			.pattern("EDE")
			.pattern("FEF")
			.unlockedBy("has_speed_boots", has(MobZArmors.SPEED_BOOTS.get()))
			.save(this.output);

		// Frozen sword
		ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, MobZWeapons.FROZEN_SWORD.get())
			.define('D', Items.DIAMOND)
			.define('E', Items.STICK)
			.define('F', MobZItems.FROZEN_POWDER.get())
			.define('O', Items.SNOWBALL)
			.pattern("ODO")
			.pattern("FDF")
			.pattern("OEO")
			.unlockedBy("has_frozen_powder", has(MobZItems.FROZEN_POWDER.get()))
			.save(this.output);

		// Rotten flesh
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(MobZItems.ROTTEN_FLESH.get()), RecipeCategory.MISC,
				Items.LEATHER, 0.1F, 240)
			.unlockedBy("has_rotten_flesh", has(MobZItems.ROTTEN_FLESH.get()))
			.save(this.output, "mobz:leather_from_rotten_flesh");

		// Totem middle
		SingleItemRecipeBuilder.stonecutting(this.tag(ItemTags.LOGS),
				RecipeCategory.MISC, MobZBlocks.TOTEM_MIDDLE.get())
			.unlockedBy("has_log", has(ItemTags.LOGS))
			.save(this.output);

		// Wither powder
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.WITHER_SKELETON_SKULL), RecipeCategory.MISC,
				MobZItems.WITHER_POWDER.get(), 1.0F, 800)
			.unlockedBy("has_wither_skull", has(Items.WITHER_SKELETON_SKULL))
			.save(this.output);

		// Wither sword
		ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, MobZWeapons.WITHER_SWORD.get())
			.define('D', Items.IRON_SWORD)
			.define('E', MobZItems.WITHER_POWDER.get())
			.define('F', Items.END_CRYSTAL)
			.define('O', Items.DRAGON_BREATH)
			.pattern("EFE").pattern("ODO")
			.pattern("EFE")
			.unlockedBy("has_wither_powder", has(MobZItems.WITHER_POWDER.get()))
			.save(this.output);
	}

	public void mobZ9BlockStorageRecipes(RecipeCategory pUnpackedCategory,
			ItemLike pUnpacked, RecipeCategory pPackedCategory, ItemLike pPacked) {
		ResourceLocation unpackedName = BuiltInRegistries.ITEM.getKey(pUnpacked.asItem());
		ResourceLocation packedName = BuiltInRegistries.ITEM.getKey(pPacked.asItem());
		nineBlockStorageRecipes(pUnpackedCategory, pUnpacked, pPackedCategory, pPacked,
				packedName.toString() + "_from_unpacked", null, unpackedName.toString() + "_from_packed", null);
	}

	public void armorSet(ItemLike ingredient, String unlockedByName, Item helmet,
			Item chestplate, Item leggings, @Nullable Item boots) {
		if (helmet != null) {
			ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, helmet)
				.define('X', ingredient)
				.pattern("XXX")
				.pattern("X X")
				.unlockedBy(unlockedByName, has(ingredient))
				.save(this.output);
		}

		if (chestplate != null) {
			ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, chestplate)
				.define('X', ingredient)
				.pattern("X X")
				.pattern("XXX")
				.pattern("XXX")
				.unlockedBy(unlockedByName, has(ingredient))
				.save(this.output);
		}

		if (leggings != null) {
			ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, leggings)
				.define('X', ingredient)
				.pattern("XXX")
				.pattern("X X")
				.pattern("X X")
				.unlockedBy(unlockedByName, has(ingredient))
				.save(this.output);
		}

		if (boots != null) {
			ShapedRecipeBuilder.shaped(this.items, RecipeCategory.COMBAT, boots)
				.define('X', ingredient)
				.pattern("X X")
				.pattern("X X")
				.unlockedBy(unlockedByName, has(ingredient))
				.save(this.output);
		}
	}
}
