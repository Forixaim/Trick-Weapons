package net.forixaim.epic_fight_battle_styles.core_assets.skills;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.burst_arts.FlareBurst;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.legendary.ImperatriceLumiere;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.skill.SkillDataKey;

public class EFBSDataKeys
{
    public static final DeferredRegister<SkillDataKey<?>> DATA_KEYS = DeferredRegister.create(new ResourceLocation("epicfight", "skill_data_keys"), EpicFightBattleStyles.MOD_ID);

    public static final RegistryObject<SkillDataKey<Integer>> HEAT;
    public static final RegistryObject<SkillDataKey<Boolean>> FORWARD;
    public static final RegistryObject<SkillDataKey<Boolean>> BACKWARD;
    public static final RegistryObject<SkillDataKey<Boolean>> LEFT;
    public static final RegistryObject<SkillDataKey<Boolean>> RIGHT;
    public static final RegistryObject<SkillDataKey<Boolean>> UP;
    public static final RegistryObject<SkillDataKey<Boolean>> DOWN;
    public static final RegistryObject<SkillDataKey<Boolean>> IN_AIR;

    static
    {
        HEAT = DATA_KEYS.register("heat", () -> SkillDataKey.createIntKey(0, true, new Class[]{
                ImperatriceLumiere.class,
                FlareBurst.class
        }));

        FORWARD = DATA_KEYS.register("forward", () -> SkillDataKey.createBooleanKey(false, true, new Class[]{
                ImperatriceLumiere.class,
        }));

        BACKWARD = DATA_KEYS.register("backward", () -> SkillDataKey.createBooleanKey(true, true, new Class[]{
                ImperatriceLumiere.class,
        }));

        LEFT = DATA_KEYS.register("left", () -> SkillDataKey.createBooleanKey(true, true, new Class[]{
                ImperatriceLumiere.class,
        }));

        RIGHT = DATA_KEYS.register("right", () -> SkillDataKey.createBooleanKey(true, true, new Class[]{
                ImperatriceLumiere.class,
        }));

        UP = DATA_KEYS.register("up", () -> SkillDataKey.createBooleanKey(true, true, new Class[]{
                ImperatriceLumiere.class,
        }));

        DOWN = DATA_KEYS.register("down", () -> SkillDataKey.createBooleanKey(true, true, new Class[]{
                ImperatriceLumiere.class,
        }));

        IN_AIR = DATA_KEYS.register("in_air", () -> SkillDataKey.createBooleanKey(true, true, new Class[]{
                ImperatriceLumiere.class,
        }));

    }
}
