package net.mobz.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.fabric.impl.biome.modification.BiomeModificationImpl;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.storage.PrimaryLevelData;

import net.mobz.fabric.AddSpawnsBiomeModifier;

@Mixin(BiomeModificationImpl.class)
public abstract class BiomeModificationImplMixin {
	@Inject(method = "finalizeWorldGen", at = @At(value = "HEAD"))
	private void parseBiomeModifiers(RegistryAccess impl, PrimaryLevelData levelProperties, CallbackInfo ci) {
		AddSpawnsBiomeModifier.parseBiomeModifiers(impl);
	}
}
