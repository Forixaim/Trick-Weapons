package net.forixaim.epic_fight_battle_styles.core_assets.animations;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.C;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

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

	public static final boolean isInAir(LivingEntityPatch<?> entityPatch)
	{
		Vec3 playerPos = entityPatch.getOriginal().getPosition(0f);
		Vec3 scanPos = playerPos.subtract(0, 0.2f, 0);
		HitResult hitResult = entityPatch.getOriginal().level().clip(new ClipContext(playerPos, scanPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entityPatch.getOriginal()));
		if (hitResult.getType() == HitResult.Type.MISS)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
