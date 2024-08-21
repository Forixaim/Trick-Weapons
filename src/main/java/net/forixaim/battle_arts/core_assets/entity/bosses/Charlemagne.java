package net.forixaim.battle_arts.core_assets.entity.bosses;

import net.forixaim.battle_arts.core_assets.entity.bosses.patches.CharlemagnePatch;
import net.forixaim.battle_arts.core_assets.entity.friendly_npcs.AbstractFriendlyNPC;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

// TODO: Implement
public class Charlemagne extends AbstractFriendlyNPC
{
	private final CharlemagnePatch associatedPatch = EpicFightCapabilities.getEntityPatch(this, CharlemagnePatch.class);
	public Charlemagne(EntityType<? extends PathfinderMob> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
	}

	public static AttributeSupplier.Builder createAttributes()
	{
		return PathfinderMob.createLivingAttributes()
			.add(Attributes.MAX_HEALTH, 500f)
			.add(Attributes.ARMOR, 20f)
			.add(Attributes.ARMOR_TOUGHNESS, 20f)
			.add(EpicFightAttributes.STUN_ARMOR.get(), 20f);
	}

	@Override
	public boolean isInvulnerableTo(@NotNull DamageSource pSource)
	{
		return true;
	}

	@Override
	public @NotNull InteractionResult mobInteract(@NotNull Player pPlayer, @NotNull InteractionHand pHand)
	{
		//TODO: Implement a dialogue system
		return super.mobInteract(pPlayer, pHand);
	}
}
