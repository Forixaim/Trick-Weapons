package net.forixaim.battle_arts.core_assets.skills.guard;

import net.forixaim.battle_arts.core_assets.animations.BattleAnimations;
import net.forixaim.battle_arts.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.battle_arts.core_assets.skills.BattleArtsSkillSlots;
import net.forixaim.battle_arts.initialization.registry.SkillRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import reascer.wom.skill.WOMSkillDataKeys;
import reascer.wom.world.capabilities.item.GesetzCapability;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.entity.eventlistener.HurtEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.Objects;
import java.util.UUID;

/**
 * This class represents Inferno Bulwark
 */
public class InfernoBulwark extends GuardSkill
{
	private static final UUID EVENT_UUID = UUID.fromString("c5547250-4aa6-44c4-a01e-cf4bd4f8e93b");

	public static GuardSkill.Builder createActiveGuardBuilder()
	{
		return GuardSkill.createGuardBuilder()
				.addAdvancedGuardMotion(CapabilityItem.WeaponCategories.SWORD, (itemCap, playerpatch) -> itemCap.getStyle(playerpatch) == ImperatriceLumiereStyles.IMPERATRICE_SWORD ?
						new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT1, Animations.SWORD_GUARD_ACTIVE_HIT2 } :
						new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT2, Animations.SWORD_GUARD_ACTIVE_HIT3 })
				.addAdvancedGuardMotion(CapabilityItem.WeaponCategories.LONGSWORD, (itemCap, playerpatch) ->
						new StaticAnimation[] { Animations.LONGSWORD_GUARD_ACTIVE_HIT1, Animations.LONGSWORD_GUARD_ACTIVE_HIT2 })
				.addAdvancedGuardMotion(CapabilityItem.WeaponCategories.TACHI, (itemCap, playerpatch) ->
						new StaticAnimation[] { Animations.LONGSWORD_GUARD_ACTIVE_HIT1, Animations.LONGSWORD_GUARD_ACTIVE_HIT2 });
	}
	public InfernoBulwark(GuardSkill.Builder builder)
	{
		super(builder);
	}

	public void onInitiate(SkillContainer container) {
		super.onInitiate(container);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.HURT_EVENT_PRE, GuardSkill.EVENT_UUID, 1);

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.CLIENT_ITEM_USE_EVENT, EVENT_UUID, (event) -> {
			CapabilityItem itemCapability = event.getPlayerPatch().getHoldingItemCapability(InteractionHand.MAIN_HAND);
			if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.GUARD)) {
				event.getPlayerPatch().getOriginal().startUsingItem(InteractionHand.MAIN_HAND);
			}

		});

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.SERVER_ITEM_STOP_EVENT, EVENT_UUID, event ->{

		});

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.SERVER_ITEM_USE_EVENT, EVENT_UUID, (event) -> {
			CapabilityItem itemCapability = event.getPlayerPatch().getHoldingItemCapability(InteractionHand.MAIN_HAND);
			if (event.getPlayerPatch().getOriginal().tickCount - container.getDataManager().getDataValue(WOMSkillDataKeys.LAST_ACTIVE.get()) > 20) {
				container.getDataManager().setDataSync(WOMSkillDataKeys.PARRYING.get(), false, event.getPlayerPatch().getOriginal());
			}

			if (!(Boolean)container.getDataManager().getDataValue(WOMSkillDataKeys.PARRYING.get()) && event.getPlayerPatch().getStamina() > 0.0F) {
				if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.ADVANCED_GUARD) && event.getPlayerPatch().getOriginal().tickCount - container.getDataManager().getDataValue(WOMSkillDataKeys.LAST_ACTIVE.get()) > 20 && !event.getPlayerPatch().getOriginal().isFallFlying() && event.getPlayerPatch().currentLivingMotion != LivingMotions.FALL && event.getPlayerPatch().getEntityState().canUseSkill() && event.getPlayerPatch().getEntityState().canBasicAttack())
				{
					StaticAnimation animation = BattleAnimations.IMPERATRICE_GUARD;



					event.getPlayerPatch().consumeForSkill(this, Resource.STAMINA, 3.0F, true);
					event.getPlayerPatch().getOriginal().level().playSound(null, container.getExecuter().getOriginal().getX(), container.getExecuter().getOriginal().getY(), container.getExecuter().getOriginal().getZ(), EpicFightSounds.CLASH.get(), container.getExecuter().getOriginal().getSoundSource(), 1.0F, 2.0F);
					event.getPlayerPatch().playAnimationSynchronized(animation, 0);
					event.getPlayerPatch().currentLivingMotion = LivingMotions.BLOCK;
					container.getDataManager().setDataSync(WOMSkillDataKeys.LAST_ACTIVE.get(), event.getPlayerPatch().getOriginal().tickCount, event.getPlayerPatch().getOriginal());
					container.getDataManager().setDataSync(WOMSkillDataKeys.PARRYING.get(), true, event.getPlayerPatch().getOriginal());
				} else if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.ADVANCED_GUARD) && event.getPlayerPatch().getEntityState().canBasicAttack()) {
					event.getPlayerPatch().getOriginal().level().playSound(null, container.getExecuter().getOriginal().getX(), container.getExecuter().getOriginal().getY(), container.getExecuter().getOriginal().getZ(), SoundEvents.LAVA_EXTINGUISH, container.getExecuter().getOriginal().getSoundSource(), 1.0F, 2.0F);
					container.getDataManager().setDataSync(WOMSkillDataKeys.LAST_ACTIVE.get(), event.getPlayerPatch().getOriginal().tickCount - 4, event.getPlayerPatch().getOriginal());
				}
			}

			if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.ADVANCED_GUARD) && (event.getPlayerPatch().getStamina() == 0.0F || event.getPlayerPatch().getOriginal().tickCount - container.getDataManager().getDataValue(WOMSkillDataKeys.LAST_ACTIVE.get()) > 10) && event.getPlayerPatch().getEntityState().canBasicAttack())
			{
				event.getPlayerPatch().getOriginal().level().playSound(null, container.getExecuter().getOriginal().getX(), container.getExecuter().getOriginal().getY(), container.getExecuter().getOriginal().getZ(), SoundEvents.LAVA_EXTINGUISH, container.getExecuter().getOriginal().getSoundSource(), 1.0F, 2.0F);
				container.getDataManager().setDataSync(WOMSkillDataKeys.LAST_ACTIVE.get(), event.getPlayerPatch().getOriginal().tickCount - 4, event.getPlayerPatch().getOriginal());
			}

			if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.GUARD)) {
				event.getPlayerPatch().getOriginal().startUsingItem(InteractionHand.MAIN_HAND);
			}

		});
		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.HURT_EVENT_PRE, EVENT_UUID, (event) -> {
			CapabilityItem itemCapability = event.getPlayerPatch().getHoldingItemCapability(event.getPlayerPatch().getOriginal().getUsedItemHand());
			DamageSource damageSource;
			boolean isFront;
			Vec3 sourceLocation;
			Vec3 viewVector;
			Vec3 toSourceLocation;
			float impact;
			float knockback;
			if (event.getPlayerPatch().getOriginal().tickCount - container.getDataManager().getDataValue(WOMSkillDataKeys.LAST_ACTIVE.get()) < 10 && container.getDataManager().getDataValue(WOMSkillDataKeys.PARRYING.get())) {
				container.getDataManager().setDataSync(WOMSkillDataKeys.PARRYING.get(), false, event.getPlayerPatch().getOriginal());
				damageSource = event.getDamageSource();
				isFront = false;
				sourceLocation = damageSource.getSourcePosition();
				if (sourceLocation != null) {
					viewVector = event.getPlayerPatch().getOriginal().getViewVector(1.0F);
					toSourceLocation = sourceLocation.subtract(event.getPlayerPatch().getOriginal().position()).normalize();
					if (toSourceLocation.dot(viewVector) > 0.0) {
						isFront = true;
					}
				}

				if (isFront) {
					impact = 0.5F;
					knockback = 0.25F;
					if (event.getDamageSource() instanceof EpicFightDamageSource) {
						impact = ((EpicFightDamageSource)event.getDamageSource()).getImpact();
						knockback += Math.min(impact * 0.1F, 1.0F);
					}

					this.guard(container, itemCapability, event, knockback, impact, false);
				}
			} else if (super.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.GUARD) && ((ServerPlayer)((ServerPlayerPatch)event.getPlayerPatch()).getOriginal()).isUsingItem()) {
				damageSource = event.getDamageSource();
				isFront = false;
				sourceLocation = damageSource.getSourcePosition();
				if (sourceLocation != null) {
					viewVector = event.getPlayerPatch().getOriginal().getViewVector(1.0F);
					toSourceLocation = sourceLocation.subtract(event.getPlayerPatch().getOriginal().position()).normalize();
					if (toSourceLocation.dot(viewVector) > 0.0) {
						isFront = true;
					}
				}

				if (isFront) {
					impact = 0.5F;
					knockback = 0.25F;
					if (event.getDamageSource() instanceof EpicFightDamageSource) {
						impact = ((EpicFightDamageSource)event.getDamageSource()).getImpact();
						knockback += Math.min(impact * 0.1F, 1.0F);
					}

					this.guard(container, itemCapability, event, knockback, impact, false);
				}
			}

		}, 2);
	}

	public void guard(SkillContainer container, CapabilityItem itemCapability, HurtEvent.Pre event, float knockback, float impact, boolean advanced) {
		if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.ADVANCED_GUARD)) {
			DamageSource damageSource = event.getDamageSource();
			if (this.isBlockableSource(damageSource, true)) {
				ServerPlayer playerentity = event.getPlayerPatch().getOriginal();
				int timing = playerentity.tickCount - container.getDataManager().getDataValue(WOMSkillDataKeys.LAST_ACTIVE.get());
				boolean successParrying = timing < 10;
				float penalty = container.getDataManager().getDataValue(SkillDataKeys.PENALTY.get());
				event.getPlayerPatch().playSound(EpicFightSounds.CLASH.get(), -0.05F, 0.1F);
				EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument((ServerLevel)playerentity.level(), HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, playerentity, damageSource.getDirectEntity());
				if (!(event.getPlayerPatch().getHoldingItemCapability(InteractionHand.OFF_HAND) instanceof GesetzCapability)) {
					if (successParrying) {
						penalty = 0.1F;
						knockback *= 0.4F;
					} else {
						penalty += this.getPenalizer(itemCapability);
						container.getDataManager().setDataSync(SkillDataKeys.PENALTY.get(), penalty, playerentity);
					}
				}

				if (damageSource.getDirectEntity() instanceof LivingEntity) {
					knockback += (float) EnchantmentHelper.getKnockbackBonus((LivingEntity)damageSource.getDirectEntity()) * 0.1F;
				}

				event.getPlayerPatch().knockBackEntity(Objects.requireNonNull(damageSource.getDirectEntity()).position(), knockback);
				if (container.getDataManager().getDataValue(WOMSkillDataKeys.CONSUMPTION_VALUE.get()) != null && container.getDataManager().getDataValue(WOMSkillDataKeys.CONSUMPTION_VALUE.get()) != 0.0F) {
					penalty *= container.getDataManager().getDataValue(WOMSkillDataKeys.CONSUMPTION_VALUE.get());
				}

				boolean enoughStamina = true;
				if (!(event.getPlayerPatch().getHoldingItemCapability(InteractionHand.OFF_HAND) instanceof GesetzCapability)) {
					if (penalty > 0.0F) {
						enoughStamina = event.getPlayerPatch().consumeForSkill(this, Resource.STAMINA, penalty * impact);
					} else {
						container.getDataManager().setDataSync(SkillDataKeys.PENALTY.get(), penalty, playerentity);
					}
				}

				GuardSkill.BlockType blockType = successParrying ? BlockType.ADVANCED_GUARD : (enoughStamina ? BlockType.GUARD : BlockType.GUARD_BREAK);
				StaticAnimation animation = this.getGuardMotion(event.getPlayerPatch(), itemCapability, blockType);
				float convert = timing <= 2 ? -0.15F : (timing < 5 ? 0.0F : 0.15F);
				if (animation == Animations.RUSHING_TEMPO2) {
					convert = timing <= 2 ? -0.1F : (timing < 5 ? 0.0F : 0.15F);
				}

				if (animation == Animations.SWEEPING_EDGE) {
					convert = timing <= 2 ? -0.1F : (timing < 5 ? 0.0F : 0.15F);
				}

				if (blockType == BlockType.GUARD) {
					convert = 0.0F;
				}

				if (animation != null) {
					event.getPlayerPatch().playAnimationSynchronized(animation, convert);
				}

				if (blockType == BlockType.GUARD_BREAK) {
					event.getPlayerPatch().playSound(EpicFightSounds.NEUTRALIZE_MOBS.get(), 3.0F, 0.0F, 0.1F);
				}

				this.dealEvent(event.getPlayerPatch(), event, advanced);
			}
		}

	}

	@Override
	public void updateContainer(SkillContainer container)
	{
		if (!container.getExecuter().isLogicalClient())
		{
			ServerPlayerPatch serverPlayerPatch = (ServerPlayerPatch) container.getExecuter();


		}
	}

	@Override
	public boolean canExecute(PlayerPatch<?> executor)
	{
		return executor.getSkill(BattleArtsSkillSlots.BATTLE_STYLE).hasSkill(SkillRegistry.IMPERATRICE_LUMIERE);
	}
}
