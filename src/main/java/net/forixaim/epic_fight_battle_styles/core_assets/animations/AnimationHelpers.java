package net.forixaim.epic_fight_battle_styles.core_assets.animations;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;

public class AnimationHelpers
{
	/**
	 * Parameters
	 * 0: Explosive Radius
	 * 1: Explosive Distance
	 */
	public static final AnimationEvent.AnimationEventConsumer EXPLODE_DIRECTION = ((livingEntityPatch, staticAnimation, parameters) -> {
		Vec3f pos = new Vec3f(0.0F, 0.0F, (float) parameters[1]);
		OpenMatrix4f posRotation = (new OpenMatrix4f()).rotate(-((float)Math.toRadians(livingEntityPatch.getYRot())), new Vec3f(0.0F, 1.0F, 0.0F));
		OpenMatrix4f.transform3v(posRotation, pos, pos);
		RandomSource random = livingEntityPatch.getOriginal().getRandom();

		livingEntityPatch.getOriginal().level().explode(livingEntityPatch.getOriginal(), (double)pos.x + (livingEntityPatch.getOriginal()).getX(), (double)pos.y + livingEntityPatch.getOriginal().getY(), (double)pos.z + livingEntityPatch.getOriginal().getZ(), (float) parameters[0], Level.ExplosionInteraction.NONE);
		for (int i = 0; i < 100; i++)
		{
			livingEntityPatch.getOriginal().level().addParticle(ParticleTypes.LAVA, (double)pos.x + (livingEntityPatch.getOriginal()).getX(), (double)pos.y + livingEntityPatch.getOriginal().getY(), (double)pos.z + livingEntityPatch.getOriginal().getZ(), (random.nextDouble() - random.nextDouble()) * 30, 10, (random.nextDouble() - random.nextDouble()) * 30);
		}
	});
}
