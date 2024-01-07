package net.forixaim.epic_fight_battle_styles.core_assets.capabilities;

import com.mojang.datafixers.util.Pair;
import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.WoMUniqueStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLuminelleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.Staff;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.Sword;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.unique.Herrscher;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.unique.Satsujin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMColliders;
import reascer.wom.gameasset.WOMSkills;
import reascer.wom.skill.weaponinnate.SoulSnatchSkill;
import reascer.wom.skill.weaponpassive.LunarEchoPassiveSkill;
import reascer.wom.world.capabilities.item.WOMWeaponCategories;
import reascer.wom.world.item.WOMItems;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.function.Function;

/**
 * This class only loads when WoM is loaded
 */
@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WoMPresets
{
	public static final Function<Item, CapabilityItem.Builder> STAFF = (item) -> {
		CapabilityItem.Builder builder = EFBSWeaponCapability.builder()
				.redirectedCategory(WOMWeaponCategories.STAFF)
				.redirectedProvider(Staff.styleProvider)
				.redirectedCollider(WOMColliders.STAFF)
				.redirectedHitSound(EpicFightSounds.BLUNT_HIT.get())
				.createStyleCategory(CapabilityItem.Styles.TWO_HAND, Staff.defaultAttackCycle)
				.hitParticle(EpicFightParticles.HIT_BLUNT.get())
				.canBePlacedOffhand(false)
				.newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SPEAR_MOUNT_ATTACK)
				.comboCancel((style) -> false);
		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> AGONY = (item) -> {
		CapabilityItem.Builder builder = WeaponCapability.builder()
				.category(WOMWeaponCategories.AGONY)
				.styleProvider((playerpatch) -> CapabilityItem.Styles.TWO_HAND)
				.collider(WOMColliders.AGONY)
				.hitSound(EpicFightSounds.BLADE_HIT.get())
				.canBePlacedOffhand(false)
				.newStyleCombo(CapabilityItem.Styles.TWO_HAND, WOMAnimations.AGONY_AUTO_1, WOMAnimations.AGONY_AUTO_2, WOMAnimations.AGONY_AUTO_3, WOMAnimations.AGONY_AUTO_4, WOMAnimations.AGONY_CLAWSTRIKE, WOMAnimations.AGONY_AIR_SLASH)
				.newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SPEAR_MOUNT_ATTACK)
				.innateSkill(CapabilityItem.Styles.TWO_HAND,(itemstack) -> WOMSkills.AGONY_PLUNGE)
				.comboCancel((style) -> {
					return false;
				})
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.AGONY_IDLE)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.AGONY_WALK)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, WOMAnimations.AGONY_RUN)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.AGONY_RUN)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, WOMAnimations.AGONY_GUARD);
		return builder;
	};

	public static final Function<Item, CapabilityItem.Builder> TORMENT = (item) -> {
		CapabilityItem.Builder builder = WeaponCapability.builder()
				.category(WOMWeaponCategories.TORMENT)
				.styleProvider((entitypatch) -> {
					if (entitypatch instanceof PlayerPatch<?>) {
						if (((PlayerPatch<?>)entitypatch).getSkill(SkillSlots.WEAPON_INNATE).getRemainDuration() > 0) {
							return CapabilityItem.Styles.OCHS;
						}
					}
					return CapabilityItem.Styles.TWO_HAND;
				})
				.collider(WOMColliders.TORMENT)
				.hitSound(EpicFightSounds.BLADE_HIT.get())
				.swingSound(EpicFightSounds.WHOOSH_BIG.get())
				.canBePlacedOffhand(false)
				.newStyleCombo(CapabilityItem.Styles.TWO_HAND, WOMAnimations.TORMENT_AUTO_1, WOMAnimations.TORMENT_AUTO_2, WOMAnimations.TORMENT_AUTO_3, WOMAnimations.TORMENT_AUTO_4, WOMAnimations.TORMENT_DASH, WOMAnimations.TORMENT_AIRSLAM)
				.newStyleCombo(CapabilityItem.Styles.OCHS, WOMAnimations.TORMENT_BERSERK_AUTO_1, WOMAnimations.TORMENT_BERSERK_AUTO_2, WOMAnimations.TORMENT_BERSERK_DASH, WOMAnimations.TORMENT_BERSERK_AIRSLAM)
				.newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.innateSkill(CapabilityItem.Styles.TWO_HAND,(itemstack) -> WOMSkills.TRUE_BERSERK)
				.innateSkill(CapabilityItem.Styles.OCHS,(itemstack) -> WOMSkills.TRUE_BERSERK)
				.passiveSkill(WOMSkills.TORMENT_PASSIVE)
				.comboCancel((style) -> {
					return false;
				})
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.TORMENT_IDLE)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.TORMENT_WALK)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, WOMAnimations.TORMENT_RUN)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.TORMENT_RUN)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, WOMAnimations.TORMENT_CHARGE)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.IDLE, WOMAnimations.TORMENT_BERSERK_IDLE)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.WALK, WOMAnimations.TORMENT_BERSERK_WALK)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.CHASE, WOMAnimations.TORMENT_BERSERK_RUN)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.RUN, WOMAnimations.TORMENT_BERSERK_RUN)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR);
		return builder;
	};

	public static final Function<Item, CapabilityItem.Builder> RUINE = (item) -> {
		CapabilityItem.Builder builder = WeaponCapability.builder()
				.category(WOMWeaponCategories.RUINE)
				.styleProvider((entitypatch) -> {
					if (entitypatch instanceof PlayerPatch<?>) {
						if (((PlayerPatch<?>)entitypatch).getSkill(SkillSlots.WEAPON_INNATE).getDataManager().getDataValue(SoulSnatchSkill.BUFFED) != null) {
							if (((PlayerPatch<?>)entitypatch).getSkill(SkillSlots.WEAPON_INNATE).getDataManager().getDataValue(SoulSnatchSkill.BUFFED)) {
								return CapabilityItem.Styles.OCHS;
							}
						}
					}
					return CapabilityItem.Styles.TWO_HAND;
				})
				.hitSound(EpicFightSounds.BLADE_HIT.get())
				.collider(WOMColliders.RUINE)
				.canBePlacedOffhand(false)
				.newStyleCombo(CapabilityItem.Styles.TWO_HAND, WOMAnimations.RUINE_AUTO_1, WOMAnimations.RUINE_AUTO_2, WOMAnimations.RUINE_AUTO_3, WOMAnimations.RUINE_AUTO_4, WOMAnimations.RUINE_DASH, WOMAnimations.RUINE_COMET)
				.newStyleCombo(CapabilityItem.Styles.OCHS, WOMAnimations.RUINE_AUTO_1, WOMAnimations.RUINE_AUTO_2, WOMAnimations.RUINE_AUTO_3, WOMAnimations.RUINE_AUTO_4, WOMAnimations.RUINE_DASH, WOMAnimations.RUINE_COMET)
				.newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.innateSkill(CapabilityItem.Styles.TWO_HAND,(itemstack) -> WOMSkills.SOUL_SNATCH)
				.innateSkill(CapabilityItem.Styles.OCHS,(itemstack) -> WOMSkills.SOUL_SNATCH)
				.passiveSkill(WOMSkills.RUINE_PASSIVE)
				.comboCancel((style) -> {
					return false;
				})
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.RUINE_IDLE)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.RUINE_WALK)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.RUINE_RUN)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, WOMAnimations.RUINE_BLOCK)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.IDLE, WOMAnimations.RUINE_BOOSTED_IDLE)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.WALK, WOMAnimations.RUINE_BOOSTED_WALK)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.CHASE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.RUN, WOMAnimations.RUINE_RUN)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.BLOCK, WOMAnimations.RUINE_BLOCK);
		return builder;
	};

	public static final Function<Item, CapabilityItem.Builder> EFBS_SATSUJIN = (item) -> {
		CapabilityItem.Builder builder = EFBSWeaponCapability.builder()
				.redirectedCategory(BattleStyleCategories.SATSUJIN)
				.redirectedProvider(Satsujin.styleProvider)
				.createStyleCategory(WoMUniqueStyles.DEMON, Satsujin.demonAttackCycle)
				.createStyleCategory(WoMUniqueStyles.DEMON_SHEATH, Satsujin.demonSheathAttackCycle)
				.createStyleCategory(CapabilityItem.Styles.TWO_HAND, Satsujin.defaultTwoHandAttackCycle)
				.hitSound(EpicFightSounds.BLADE_HIT.get())
				.passiveSkill(WOMSkills.SATSUJIN_PASSIVE)
				.collider(WOMColliders.KATANA)
				.canBePlacedOffhand(false)
				.newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.comboCancel((style) -> {
					return false;
				});
		return builder;
	};

	public static final Function<Item, CapabilityItem.Builder> ENDER_BLASTER = (item) -> {
		CapabilityItem.Builder builder = WeaponCapability.builder()
				.category(WOMWeaponCategories.ENDERBLASTER)
				.styleProvider((playerpatch) -> playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WOMWeaponCategories.ENDERBLASTER ? CapabilityItem.Styles.TWO_HAND : CapabilityItem.Styles.ONE_HAND)
				.hitSound(EpicFightSounds.BLADE_HIT.get())
				.collider(WOMColliders.PUNCH)
				.newStyleCombo(CapabilityItem.Styles.ONE_HAND, WOMAnimations.ENDERBLASTER_ONEHAND_AUTO_1, WOMAnimations.ENDERBLASTER_ONEHAND_AUTO_2, WOMAnimations.ENDERBLASTER_ONEHAND_AUTO_3, WOMAnimations.ENDERBLASTER_ONEHAND_AUTO_4, WOMAnimations.ENDERBLASTER_ONEHAND_DASH, WOMAnimations.ENDERBLASTER_ONEHAND_JUMPKICK)
				.newStyleCombo(CapabilityItem.Styles.TWO_HAND, WOMAnimations.ENDERBLASTER_TWOHAND_AUTO_1, WOMAnimations.ENDERBLASTER_TWOHAND_AUTO_2, WOMAnimations.ENDERBLASTER_TWOHAND_AUTO_3, WOMAnimations.ENDERBLASTER_TWOHAND_AUTO_4, WOMAnimations.ENDERBLASTER_ONEHAND_DASH, WOMAnimations.ENDERBLASTER_TWOHAND_TISHNAW)
				.newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.innateSkill(CapabilityItem.Styles.ONE_HAND,(itemstack) -> WOMSkills.ENDER_BLAST)
				.innateSkill(CapabilityItem.Styles.TWO_HAND,(itemstack) -> WOMSkills.ENDER_FUSION)
				.comboCancel((style) -> {
					return false;
				})
				.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, WOMAnimations.ENDERBLASTER_ONEHAND_IDLE)
				.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, WOMAnimations.ENDERBLASTER_ONEHAND_WALK)
				.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.CHASE, WOMAnimations.ENDERBLASTER_ONEHAND_RUN)
				.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, WOMAnimations.ENDERBLASTER_ONEHAND_RUN)
				.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.SWIM, Animations.BIPED_SWIM)
				.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, WOMAnimations.ENDERBLASTER_ONEHAND_AIMING)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.ENDERBLASTER_TWOHAND_IDLE)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.ENDERBLASTER_ONEHAND_WALK)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, WOMAnimations.ENDERBLASTER_ONEHAND_RUN)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.ENDERBLASTER_ONEHAND_RUN)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_SWIM)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, WOMAnimations.ENDERBLASTER_TWOHAND_AIMING)
				.weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCollider() == WOMColliders.PUNCH);
		return builder;
	};

	public static final Function<Item, CapabilityItem.Builder> ANTITHEUS = (item) -> {
		CapabilityItem.Builder builder = WeaponCapability.builder()
				.category(WOMWeaponCategories.ANTITHEUS)
				.styleProvider((entitypatch) -> {
					if (entitypatch instanceof PlayerPatch<?>) {
						if (((PlayerPatch<?>)entitypatch).getSkill(SkillSlots.WEAPON_INNATE).getRemainDuration() > 0) {
							return CapabilityItem.Styles.OCHS;
						}
					}
					return CapabilityItem.Styles.TWO_HAND;
				})
				.collider(WOMColliders.ANTITHEUS)
				.hitSound(EpicFightSounds.BLADE_HIT.get())
				.swingSound(EpicFightSounds.WHOOSH.get())
				.passiveSkill(WOMSkills.DEMON_MARK_PASSIVE)
				.canBePlacedOffhand(false)
				.newStyleCombo(CapabilityItem.Styles.TWO_HAND, WOMAnimations.ANTITHEUS_AUTO_1, WOMAnimations.ANTITHEUS_AUTO_2, WOMAnimations.ANTITHEUS_AUTO_3, WOMAnimations.ANTITHEUS_AUTO_4, WOMAnimations.ANTITHEUS_AGRESSION, WOMAnimations.ANTITHEUS_GUILLOTINE)
				.newStyleCombo(CapabilityItem.Styles.OCHS, WOMAnimations.ANTITHEUS_ASCENDED_AUTO_1, WOMAnimations.ANTITHEUS_ASCENDED_AUTO_2, WOMAnimations.ANTITHEUS_ASCENDED_AUTO_3, WOMAnimations.ANTITHEUS_ASCENDED_BLINK, WOMAnimations.ANTITHEUS_ASCENDED_DEATHFALL)
				.newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.innateSkill(CapabilityItem.Styles.TWO_HAND,(itemstack) -> WOMSkills.DEMONIC_ASCENSION)
				.innateSkill(CapabilityItem.Styles.OCHS,(itemstack) -> WOMSkills.DEMONIC_ASCENSION)
				.comboCancel((style) -> {
					return false;
				})
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.ANTITHEUS_IDLE)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.ANTITHEUS_WALK)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, WOMAnimations.ANTITHEUS_RUN)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.ANTITHEUS_RUN)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, WOMAnimations.ANTITHEUS_AIMING)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.IDLE, WOMAnimations.ANTITHEUS_ASCENDED_IDLE)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.WALK, WOMAnimations.ANTITHEUS_ASCENDED_WALK)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.CHASE, WOMAnimations.ANTITHEUS_ASCENDED_RUN)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.RUN, WOMAnimations.ANTITHEUS_ASCENDED_RUN)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_SWIM);
		return builder;
	};

	public static final Function<Item, CapabilityItem.Builder> EFBS_HERRSCHER = (item) -> {
		CapabilityItem.Builder builder = EFBSWeaponCapability.builder()
				.redirectedCategory(CapabilityItem.WeaponCategories.SWORD)
				.redirectedProvider(Herrscher.styleProvider)
				.redirectedCollider(WOMColliders.HERSCHER)
				.redirectedHitSound(EpicFightSounds.BLADE_HIT.get())
				.createStyleCategory(CapabilityItem.Styles.TWO_HAND, Sword.defaultTwoHandAttackCycle)
				.createStyleCategory(CapabilityItem.Styles.ONE_HAND, Sword.defaultOneHandAttackCycle)
				.createStyleCategory(ImperatriceLuminelleStyles.SWORD, Sword.imperatriceLuminelle)
				.createStyleCategory(WoMUniqueStyles.ATLANTEAN, Herrscher.atlanteanAttackCycle)
				.comboCancel((style) -> false)
				.newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)

				.weaponCombinationPredicator(Herrscher.comboPredicator);

		if (item instanceof TieredItem) {
			int harvestLevel = ((TieredItem)item).getTier().getLevel();
			builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of(EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.5D + 0.2D * harvestLevel)));
			builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of(EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(1)));
		}

		return builder;
	};

	public static final Function<Item, CapabilityItem.Builder> MOONLESS = (item) -> {
		CapabilityItem.Builder builder = WeaponCapability.builder()
				.category(WOMWeaponCategories.MOONLESS)
				.styleProvider((entitypatch) -> {
					if (entitypatch instanceof PlayerPatch<?> playerpatch && (playerpatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().hasData(LunarEchoPassiveSkill.VERSO) &&
							playerpatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().getDataValue(LunarEchoPassiveSkill.VERSO))) {
						return CapabilityItem.Styles.OCHS;
					}
					return CapabilityItem.Styles.TWO_HAND;
				})
				.collider(WOMColliders.MOONLESS)
				.hitSound(EpicFightSounds.BLADE_HIT.get())
				.canBePlacedOffhand(false)
				.newStyleCombo(CapabilityItem.Styles.TWO_HAND, WOMAnimations.MOONLESS_AUTO_1, WOMAnimations.MOONLESS_AUTO_2, WOMAnimations.MOONLESS_AUTO_3, WOMAnimations.MOONLESS_REVERSED_BYPASS, WOMAnimations.MOONLESS_CRESCENT)
				.innateSkill(CapabilityItem.Styles.TWO_HAND,(itemstack) -> WOMSkills.lUNAR_ECLIPSE)
				.newStyleCombo(CapabilityItem.Styles.OCHS, WOMAnimations.MOONLESS_AUTO_1_VERSO, WOMAnimations.MOONLESS_AUTO_2_VERSO,WOMAnimations.MOONLESS_AUTO_3_VERSO, WOMAnimations.MOONLESS_BYPASS, WOMAnimations.MOONLESS_FULLMOON)
				.innateSkill(CapabilityItem.Styles.OCHS,(itemstack) -> WOMSkills.lUNAR_ECLIPSE)
				.newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.passiveSkill(WOMSkills.LUNAR_ECHO_PASSIVE)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.MOONLESS_IDLE)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.MOONLESS_WALK)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, WOMAnimations.MOONLESS_RUN)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.MOONLESS_RUN)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, WOMAnimations.MOONLESS_GUARD)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.IDLE, WOMAnimations.MOONLESS_IDLE)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.WALK, WOMAnimations.MOONLESS_WALK)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.CHASE, WOMAnimations.MOONLESS_RUN)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.RUN, WOMAnimations.MOONLESS_RUN)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.BLOCK, WOMAnimations.MOONLESS_GUARD);
		return builder;
	};
	@SubscribeEvent
	public static void register(WeaponCapabilityPresetRegistryEvent event) {
		event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID,"satsujin"), EFBS_SATSUJIN);
		event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "herrscher"), EFBS_HERRSCHER);
	}
}
