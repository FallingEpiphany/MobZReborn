package net.mobz.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.mobz.MobZ;
import net.mobz.init.MobZSounds;

public class NetherWolf extends Wolf {
	public NetherWolf(EntityType<? extends Wolf> entityType, Level world) {
		super(entityType, world);
	}

	public static AttributeSupplier.Builder createMobzAttributes() {
		return Animal.createAnimalAttributes()
				.add(Attributes.MAX_HEALTH, MobZ.configs.nether_wolf.life * MobZ.configs.life_multiplier)
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ATTACK_DAMAGE, MobZ.configs.nether_wolf.attack * MobZ.configs.damage_multiplier)
				.add(Attributes.FOLLOW_RANGE, 32.0D);
	}

	@Override
	public boolean isFood(ItemStack itemStack_1) {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return MobZSounds.WGROWLEVENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return MobZSounds.WHURTEVENT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return MobZSounds.WDEATHEVENT.get();
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader view) {
		BlockPos posentity = this.blockPosition();
		return MobZ.configs.nether_wolf.spawn
				&& this.level().getMaxLocalRawBrightness(posentity) <= 10
				&& MobSpawnHelper.checkSpawnObstruction(this, view);
	}
}
