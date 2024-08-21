package net.forixaim.battle_arts.core_assets.entity.projectiles;

import net.forixaim.battle_arts.initialization.registry.EntityRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.particle.WOMParticles;
import reascer.wom.world.entity.WOMEntities;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HurtableEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Random;

public class IncandescentFirework extends AbstractHurtingProjectile
{
	public IncandescentFirework(EntityType<? extends IncandescentFirework> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
	}

	public IncandescentFirework(Level level, LivingEntity shooter, double pOffsetX, double pOffsetY, double pOffsetZ) {
		super(EntityRegistry.INCANDESCENT_FIREWORK.get(), shooter, pOffsetX, pOffsetY, pOffsetZ, level);
	}


	public void tick() {
		Entity entity = this.getOwner();
		if (!this.level().isClientSide() && entity != null && entity.isRemoved()) {
			this.discard();
		} else {
			if (this.shouldBurn()) {
				this.setSecondsOnFire(1);
			}

			HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, (x$0) -> {
				return this.canHitEntity(x$0);
			});
			if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
				this.onHit(hitresult);
			}

			this.checkInsideBlocks();
			Vec3 vec3 = this.getDeltaMovement();
			double d0 = this.getX() + vec3.x;
			double d1 = this.getY() + vec3.y;
			double d2 = this.getZ() + vec3.z;
			ProjectileUtil.rotateTowardsMovement(this, 1.0F);
			float f = this.getInertia();
			if (this.isInWater()) {
				for(int i = 0; i < 4; ++i) {
					this.level().addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25, d1 - vec3.y * 0.25, d2 - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
				}

				f = 0.8F;
			}

			this.setDeltaMovement(vec3.add(this.xPower, this.yPower, this.zPower).scale((double)f));
			int numberOf = 5;
			float partialScale = 1.0F / (float)(numberOf - 1);
			float interpolation = 0.0F;

			for(int i = 0; i < numberOf; ++i) {
				float x = (float)this.xOld;
				float y = (float)this.yOld;
				float z = (float)this.zOld;
				MathUtils.lerpBetween(x, (float)d0, interpolation);
				MathUtils.lerpBetween(y, (float)d1, interpolation);
				MathUtils.lerpBetween(z, (float)d2, interpolation);
				this.level().addParticle(this.getTrailParticle(), d0 + (double)(((new Random()).nextFloat() - 0.5F) * 0.1F), d1 + (double)(((new Random()).nextFloat() - 0.5F) * 0.1F), d2 + (double)(((new Random()).nextFloat() - 0.5F) * 0.1F), (double)(((new Random()).nextFloat() - 0.5F) * 0.17F), (double)(((new Random()).nextFloat() - 0.5F) * 0.17F - 0.05F), (double)(((new Random()).nextFloat() - 0.5F) * 0.17F));
				this.level().addParticle(this.getTrailParticle(), d0, d1, d2, 0.0, -0.05000000074505806, 0.0);
				interpolation += partialScale;
			}

			d0 = this.getX() + vec3.x;
			d1 = this.getY() + vec3.y;
			d2 = this.getZ() + vec3.z;
			this.setPos(d0, d1, d2);
			if (this.tickCount > 60 || this.touchingUnloadedChunk()) {
				this.discard();
			}
		}

	}

	protected float getInertia() {
		return 0.98F;
	}






	protected void onHitEntity(@NotNull EntityHitResult pResult) {
		super.onHitEntity(pResult);
		if (!this.level().isClientSide()) {
			Entity entity = pResult.getEntity();
			Entity entity1 = this.getOwner();
			PlayerPatch<?> playerpatch = EpicFightCapabilities.getEntityPatch(this.getOwner(), PlayerPatch.class);
			if (entity1 instanceof LivingEntity livingentity && playerpatch != null) {
				HurtableEntityPatch<?> hitHurtableEntityPatch = EpicFightCapabilities.getEntityPatch(entity, HurtableEntityPatch.class);
				EpicFightDamageSource damage = playerpatch.getDamageSource(WOMAnimations.ANTITHEUS_SHOOT, InteractionHand.MAIN_HAND);
				damage.setImpact(2.0F);
				damage.setStunType(StunType.LONG);
				int prevInvulnerableTime = entity.invulnerableTime;
				entity.invulnerableTime = 0;
				float entity1damage = 4.0F;
				float enchantmentDamage = 0.0F;
				if (entity instanceof LivingEntity) {
					this.level().playSound(null, livingentity.getX(), livingentity.getY(), livingentity.getZ(), SoundEvents.WITHER_AMBIENT, this.getSoundSource(), 0.4F, 2.0F);
					enchantmentDamage = EnchantmentHelper.getDamageBonus(livingentity.getItemInHand(InteractionHand.MAIN_HAND), ((LivingEntity)entity).getMobType());
					entity1damage += enchantmentDamage;
				}

				entity.hurt(damage, entity1damage);
				if (hitHurtableEntityPatch != null) {
					hitHurtableEntityPatch.knockBackEntity(livingentity.getPosition(1.0F), 0.5F);
				}

				if (enchantmentDamage != 0.0F) {
					((ServerLevel)this.level()).sendParticles(ParticleTypes.ENCHANTED_HIT, this.getX(), this.getY(), this.getZ(), 20, 0.0, 0.0, 0.0, 0.5);
				}

				entity.invulnerableTime = prevInvulnerableTime;
			} else {
				entity.hurt(this.damageSources().magic(), 4.0F);
			}
		}

	}

	protected ParticleOptions getTrailParticle() {
		return ParticleTypes.LARGE_SMOKE;
	}

	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (!this.level().isClientSide()) {
			((ServerLevel)this.level()).sendParticles((SimpleParticleType) WOMParticles.ANTITHEUS_PUNCH.get(), this.getX(), this.getY(), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
			((ServerLevel)this.level()).sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 20, 0.0, 0.0, 0.0, 0.10000000149011612);
			((ServerLevel)this.level()).playSound((Player)null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITHER_BREAK_BLOCK, this.getSoundSource(), 0.8F, 1.0F);
			this.discard();
		}

	}

	public boolean isPickable() {
		return false;
	}

	public boolean hurt(DamageSource p_37616_, float p_37617_) {
		return false;
	}

	protected boolean shouldBurn() {
		return false;
	}

}
