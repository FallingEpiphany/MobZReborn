package net.mobz.entity;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.mobz.MobZ;

public class HoneySlime extends Slime {
	public HoneySlime(EntityType<? extends Slime> entityType, Level world) {
		super(entityType, world);
		this.xpReward = 1;
	}

	public static AttributeSupplier.Builder createMobzAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, MobZ.configs.honey_slime.life)
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.ATTACK_DAMAGE, MobZ.configs.honey_slime.attack);
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader view) {
		return MobZ.configs.honey_slime.spawn
				&& this.level().isBrightOutside()
				&& MobSpawnHelper.checkSpawnObstruction(this, view);
	}

	@Override
	public void setSize(int size, boolean resetHealth) {
		// Bypasses vanilla slime's setSize() so that health and attack don't follow vanilla slime
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public boolean isTiny() {
		return true;
	}

	@Override
	protected ParticleOptions getParticleType() {
		return ParticleTypes.FALLING_HONEY;
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		return this.getType().getDimensions();
	}
}
