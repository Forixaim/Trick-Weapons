package net.forixaim.epic_fight_battle_styles.core_assets.api.animation.types;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.minecraft.util.RandomSource;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Arrays;
import java.util.List;

public class WeaponIdleAnimation extends StaticAnimation
{
	protected final StaticAnimation[] animations;
	protected int tick = 0;
	protected int randomInterval = 0;

	public WeaponIdleAnimation(boolean repeatPlay, String path, Armature armature ,StaticAnimation... additionalIdles)
	{
		super(repeatPlay, path, armature);
		animations = additionalIdles;
	}

	public WeaponIdleAnimation(float convTime, boolean repeatPlay, String path, Armature armature, StaticAnimation... additionalIdles)
	{
		super(convTime, repeatPlay, path, armature);
		animations = additionalIdles;
	}

	@Override
	public void tick(LivingEntityPatch<?> entitypatch)
	{
		tick++;
		if (tick >= randomInterval)
		{
			entitypatch.reserveAnimation(animations[entitypatch.getOriginal().getRandom().nextInt(animations.length)]);
			tick = 0;
			randomInterval = entitypatch.getOriginal().getRandom().nextInt(300,400);
		}
		super.tick(entitypatch);
	}

	@Override
	public void begin(LivingEntityPatch<?> entitypatch)
	{
		super.begin(entitypatch);
		randomInterval = entitypatch.getOriginal().getRandom().nextInt(300, 400);
	}
}
