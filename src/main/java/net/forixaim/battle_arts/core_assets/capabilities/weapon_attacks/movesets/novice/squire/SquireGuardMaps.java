package net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.movesets.novice.squire;

import com.google.common.collect.Maps;
import net.forixaim.battle_arts.core_assets.animations.battle_style.novice.squire.SquireSwordAnimations;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.guard.GuardSkill;

import java.util.Map;

public class SquireGuardMaps
{
    public static final Map<GuardSkill.BlockType, StaticAnimation> SwordGuards = Maps.newHashMap();

    static
    {
        SwordGuards.put(GuardSkill.BlockType.GUARD, SquireSwordAnimations.SQUIRE_SWORD_GUARD);
        SwordGuards.put(GuardSkill.BlockType.GUARD_BREAK, Animations.BIPED_COMMON_NEUTRALIZED);
    }
}
