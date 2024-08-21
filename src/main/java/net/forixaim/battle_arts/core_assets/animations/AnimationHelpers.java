package net.forixaim.battle_arts.core_assets.animations;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.particle.EpicFightParticles;
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

	public static final AnimationEvent.AnimationEventConsumer SHOOT_FIREWISP = ((livingEntityPatch, staticAnimation, parameters) -> {
		Vec3f pos = new Vec3f(0.0F, 1.7F, 1);
		OpenMatrix4f posRotation = new OpenMatrix4f().rotate(-((float)Math.toRadians(livingEntityPatch.getYRot())), new Vec3f(0.0F, 1.0F, 0.0F));
		OpenMatrix4f.transform3v(posRotation, pos, pos);
		Vec3 direction = livingEntityPatch.getOriginal().getViewVector(1f);
		Projectile fireball = new LargeFireball(EntityType.FIREBALL, livingEntityPatch.getOriginal().level());
		fireball.setOwner(livingEntityPatch.getOriginal());
		fireball.setPosRaw(pos.x + (livingEntityPatch.getOriginal()).getX(), pos.y + (livingEntityPatch.getOriginal()).getY(), pos.z + (livingEntityPatch.getOriginal()).getZ());
		fireball.shoot(direction.x, 0, direction.z, 2.0f, 0f);
		livingEntityPatch.getOriginal().level().addFreshEntity(fireball);
	});

	public static final AnimationEvent.AnimationEventConsumer BLINK = ((livingEntityPatch, staticAnimation, parameters) -> {
		livingEntityPatch.getOriginal().level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), livingEntityPatch.getOriginal().getX(), livingEntityPatch.getOriginal().getY(), livingEntityPatch.getOriginal().getZ(), Double.longBitsToDouble(livingEntityPatch.getOriginal().getId()), 0, 0.0D);
		livingEntityPatch.getOriginal().level().addParticle(ParticleTypes.EXPLOSION, livingEntityPatch.getOriginal().getX(), livingEntityPatch.getOriginal().getY(), livingEntityPatch.getOriginal().getZ(), 0.0D, 0.0D, 0.0D);
	});

	public static boolean isInAir(LivingEntityPatch<?> entityPatch)
	{
		Vec3 playerPos = entityPatch.getOriginal().getPosition(0f);
		Vec3 scanPos = playerPos.subtract(0, 0.2f, 0);
		HitResult hitResult = entityPatch.getOriginal().level().clip(new ClipContext(playerPos, scanPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entityPatch.getOriginal()));
		return hitResult.getType() == HitResult.Type.MISS;
	}
}
