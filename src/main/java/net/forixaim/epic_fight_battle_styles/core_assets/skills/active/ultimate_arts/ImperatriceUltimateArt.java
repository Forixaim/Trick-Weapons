package net.forixaim.epic_fight_battle_styles.core_assets.skills.active.ultimate_arts;

import com.google.common.collect.Lists;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.BattleStyleCategories;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SoundRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.effect.EpicFightMobEffects;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.List;
import java.util.UUID;

public class ImperatriceUltimateArt extends UltimateArt
{
	private static final UUID EVENT_UUID = UUID.fromString("9febc8c6-a0b3-4ee8-8da0-65cccb5a3ca0");
	private static final AnimationProvider<AttackAnimation> TRY_ANIMATION = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_ULTIMATE_TRY;
	private static final AnimationProvider<AttackAnimation> ATTACK_ANIMATION = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_FLARE_BLADE_CLEAVE;
	private static final List<WeaponCategory> ALLOWED_WEAPONS = Lists.newArrayList(
			CapabilityItem.WeaponCategories.SWORD,
			CapabilityItem.WeaponCategories.GREATSWORD,
			CapabilityItem.WeaponCategories.LONGSWORD,
			CapabilityItem.WeaponCategories.TACHI,
			CapabilityItem.WeaponCategories.UCHIGATANA,
			BattleStyleCategories.DERIVED_JOYEUSE,
			BattleStyleCategories.ORIGIN_JOYEUSE
	);

	public ImperatriceUltimateArt(Builder<? extends Skill> builder)
	{
		super(builder);
	}

	@Override
	public void onInitiate(SkillContainer container)
	{
		PlayerEventListener listener = container.getExecuter().getEventListener();
		listener.addEventListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_HURT, EVENT_UUID, event ->
				{
					if (event.getDamageSource().getAnimation() ==  ATTACK_ANIMATION.get())
					{
						LivingEntityPatch<?> enemyPatch = EpicFightCapabilities.getEntityPatch(event.getTarget(), LivingEntityPatch.class);
						if (enemyPatch != null)
							enemyPatch.playSound(SoundRegistry.IMPERATRICE_ANTI_CHEESE.get(), 2, 0, 0);
						if (event.getDamageSource().getDamageModifier().getSetter() >= event.getTarget().getHealth() && enemyPatch != null)
						{
							enemyPatch.playSound(SoundRegistry.FATAL_ULTIMATE_HIT.get(), 3, 0,0);
						}
					}
					if (event.getDamageSource().getAnimation() == TRY_ANIMATION.get() && !isConnected(container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE)))
					{
						LivingEntityPatch<?> enemyPatch = EpicFightCapabilities.getEntityPatch(event.getTarget(), LivingEntityPatch.class);
						if (enemyPatch != null)
						{
							enemyPatch.knockBackEntity(event.getTarget().getPosition(0), 10);
						}
						else
						{
							event.getTarget().knockback(10, 5,0);
						}
						event.getPlayerPatch().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().setDataSync(EFBSDataKeys.ULTIMATE_ART_TRY_CONNECTED.get(), true, event.getPlayerPatch().getOriginal());
					}
					else
					{
						if (event.getPlayerPatch().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).hasSkill(SkillRegistry.IMPERATRICE_LUMIERE))
							event.getPlayerPatch().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().setDataSync(EFBSDataKeys.ULTIMATE_ART_TRY_CONNECTED.get(), false, event.getPlayerPatch().getOriginal());
					}
				});
		listener.addEventListener(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID, event ->
		{
			if (event.getAnimation() == TRY_ANIMATION.get() && isConnected(container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE)))
			{
				executeOnServer(event.getPlayerPatch(), null);
			}
			else if (event.getAnimation() == TRY_ANIMATION.get() && !isConnected(container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE)))
			{
				event.getPlayerPatch().getOriginal().setInvulnerable(false);
				container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().setDataSync(EFBSDataKeys.ULTIMATE_ART_ACTIVE.get(), false, event.getPlayerPatch().getOriginal());
			}
			if (event.getAnimation() == ATTACK_ANIMATION.get())
			{
				container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().setDataSync(EFBSDataKeys.ULTIMATE_ART_ACTIVE.get(), false, event.getPlayerPatch().getOriginal());
			}
		});
	}

	private boolean weaponCategoryCheck(List<WeaponCategory> allowedWeapons, PlayerPatch<?> playerPatch)
	{
		boolean test = false;
		for (WeaponCategory category : allowedWeapons)
		{
			if (playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == category)
				test = true;
		}
		return test;
	}

	private boolean isConnected(SkillContainer container)
	{
		return container.hasSkill(SkillRegistry.IMPERATRICE_LUMIERE) && container.getDataManager().getDataValue(EFBSDataKeys.ULTIMATE_ART_TRY_CONNECTED.get());
	}

	@Override
	public void onRemoved(SkillContainer container)
	{
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_HURT, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID);
	}

	@Override
	public boolean canExecute(PlayerPatch<?> executor)
	{
		SkillDataManager BattleStyleManager = executor.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager();
		if (executor.getOriginal().isCreative())
		{
			return this.weaponCategoryCheck(ALLOWED_WEAPONS, executor) &&
					!BattleStyleManager.getDataValue(EFBSDataKeys.ULTIMATE_ART_ACTIVE.get()) &&
					executor.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).hasSkill(SkillRegistry.IMPERATRICE_LUMIERE) &&
					executor.getOriginal().onGround();
		}
		return this.weaponCategoryCheck(ALLOWED_WEAPONS, executor) &&
				executor.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).hasSkill(SkillRegistry.IMPERATRICE_LUMIERE) &&
				!BattleStyleManager.getDataValue(EFBSDataKeys.ULTIMATE_ART_ACTIVE.get()) &&
				BattleStyleManager.getDataValue(EFBSDataKeys.FLARE_BURST.get()) &&
				BattleStyleManager.getDataValue(EFBSDataKeys.HEAT.get()) >= 700 &&
				executor.getOriginal().onGround();

	}

	@Override
	public void executeOnServer(ServerPlayerPatch executor, FriendlyByteBuf args)
	{
		executor.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().setDataSync(EFBSDataKeys.ULTIMATE_ART_ACTIVE.get(), true, executor.getOriginal());
		if (executor.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.ULTIMATE_ART_TRY_CONNECTED.get()))
		{
			executor.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().setDataSync(EFBSDataKeys.ULTIMATE_ART_TRY_CONNECTED.get(), false, executor.getOriginal());
			executor.playAnimationSynchronized(ATTACK_ANIMATION.get(), 0);
		}
		else
		{
			if (!executor.getOriginal().isCreative())
				executor.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().setDataSync(EFBSDataKeys.HEAT.get(), executor.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.HEAT.get()) - 700, executor.getOriginal());
			executor.getOriginal().addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 400));

			executor.playSound(SoundRegistry.ULTIMATE_ART_EXECUTE.get(), 0, 0);
			executor.playAnimationSynchronized(TRY_ANIMATION.get(), 0);
		}
	}
}
