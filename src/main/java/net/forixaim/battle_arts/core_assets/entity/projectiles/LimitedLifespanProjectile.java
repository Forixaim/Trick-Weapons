package net.forixaim.battle_arts.core_assets.entity.projectiles;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.Level;

public class LimitedLifespanProjectile extends AbstractHurtingProjectile
{

	protected LimitedLifespanProjectile(EntityType<? extends AbstractHurtingProjectile> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
	}

	public LimitedLifespanProjectile(EntityType<? extends AbstractHurtingProjectile> pEntityType, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel)
	{
		super(pEntityType, pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
	}

	public LimitedLifespanProjectile(EntityType<? extends AbstractHurtingProjectile> pEntityType, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel)
	{
		super(pEntityType, pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
	}

	@Override
	public void tick()
	{
		super.tick();
	}
}
