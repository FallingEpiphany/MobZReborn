package net.mobz;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.mobz.init.MobZArmors;
import net.mobz.init.MobZBlocks;
import net.mobz.init.MobZEntities;
import net.mobz.init.MobZIcons;
import net.mobz.init.MobZItems;
import net.mobz.init.MobZSounds;
import net.mobz.init.MobZWeapons;
import net.mobz.portable.StaticAPIWrapper;

public class MobZ {
	public static final String MODID = "mobz";

	// ItemGroup
	public final static CreativeModeTab tab = StaticAPIWrapper.instance.tab(
			new ResourceLocation(MODID, "glomod"), () -> new ItemStack(MobZArmors.boss_helmet));

	public final static CreativeModeTab eggs = StaticAPIWrapper.instance.tab(
			new ResourceLocation(MODID, "glomodegg"), () -> new ItemStack(MobZItems.SHOWEGG));

	// Make sure the static initialization is invoked before the registration phase is done
	public static void invokeStaticFields() {
    	MobZItems.BOSS_INGOT.toString();
    	MobZBlocks.BOSS_BLOCK.toString();
    	MobZEntities.BOSS.toString();
    	MobZArmors.boss_boots.toString();
    	MobZWeapons.BossSword.toString();
    	MobZSounds.MEDIVEALSOUNDEVENT.toString();
    	MobZIcons.BOSSHEAD.toString();
	}
}
