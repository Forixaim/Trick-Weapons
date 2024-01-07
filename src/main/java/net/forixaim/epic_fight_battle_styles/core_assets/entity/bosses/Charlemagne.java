package net.forixaim.epic_fight_battle_styles.core_assets.entity.bosses;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

// TODO: Implement
public class Charlemagne extends Mob implements Enemy
{
	public static AttributeSupplier.Builder createAttributes()
	{
		return PathfinderMob.createLivingAttributes()
			.add(Attributes.MAX_HEALTH, 500f)
			.add(Attributes.ARMOR, 20f)
			.add(Attributes.ARMOR_TOUGHNESS, 20f)
			.add(EpicFightAttributes.STUN_ARMOR.get(), 20f);
	}
	protected Charlemagne(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
	}
}
