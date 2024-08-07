package net.forixaim.epic_fight_battle_styles.core_assets.skills;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.burst_arts.FlareBurst;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.basic_attack.ImperatriceAttacks;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.common.advanced.Ronin;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.legendary.ImperatriceLumiere;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.dodge.Trailblaze;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.weaponinnate.BlazeStingerSkill;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.skill.SkillDataKey;

public class EFBSDataKeys
{
    public static final DeferredRegister<SkillDataKey<?>> DATA_KEYS = DeferredRegister.create(new ResourceLocation("epicfight", "skill_data_keys"), EpicFightBattleStyles.MOD_ID);

    public static final RegistryObject<SkillDataKey<Integer>> HEAT;
    public static final RegistryObject<SkillDataKey<Integer>> BLAZE_COMBO;
    public static final RegistryObject<SkillDataKey<Boolean>> FORWARD;
    public static final RegistryObject<SkillDataKey<Boolean>> BACKWARD;
    public static final RegistryObject<SkillDataKey<Boolean>> LEFT;
    public static final RegistryObject<SkillDataKey<Boolean>> RIGHT;
    public static final RegistryObject<SkillDataKey<Boolean>> UP;
    public static final RegistryObject<SkillDataKey<Boolean>> DOWN;
    public static final RegistryObject<SkillDataKey<Boolean>> IN_AIR;
    public static final RegistryObject<SkillDataKey<Boolean>> FLARE_BURST;
    public static final RegistryObject<SkillDataKey<Boolean>> SPOT_DODGE;
    public static final RegistryObject<SkillDataKey<Boolean>> JAB;
    public static final RegistryObject<SkillDataKey<Integer>> CERCLE_DE_FEU;
    public static final RegistryObject<SkillDataKey<Boolean>> FTILT;
    public static final RegistryObject<SkillDataKey<Integer>> HEAT_COOL_TICK;
    public static final RegistryObject<SkillDataKey<Boolean>> ULTIMATE_ART_ACTIVE;
    public static final RegistryObject<SkillDataKey<Boolean>> ULTIMATE_ART_TRY_CONNECTED;
    public static final RegistryObject<SkillDataKey<Boolean>> ULTIMATE_ART_READY;
    public static final RegistryObject<SkillDataKey<Boolean>> CHARGE_EXECUTING;
    public static final RegistryObject<SkillDataKey<Boolean>> CHARGE_AERIAL;

    public static final RegistryObject<SkillDataKey<Boolean>> BATTO_SHEATH;

    static
    {
        FTILT = DATA_KEYS.register("ftilt", () -> SkillDataKey.createBooleanKey(false, true, ImperatriceAttacks.class));

        HEAT_COOL_TICK = DATA_KEYS.register("imperatrice_cool_tick", () -> SkillDataKey.createIntKey(0, true, ImperatriceLumiere.class));
        SPOT_DODGE = DATA_KEYS.register("spot_dodge", () -> SkillDataKey.createBooleanKey(false, true, Trailblaze.class));
        ULTIMATE_ART_ACTIVE = DATA_KEYS.register("ultimate_art_active", () -> SkillDataKey.createBooleanKey(false, true,
                ImperatriceLumiere.class));

        BATTO_SHEATH = DATA_KEYS.register("batto_sheath", () -> SkillDataKey.createBooleanKey(false, true, Ronin.class));

        CHARGE_EXECUTING = DATA_KEYS.register("charge_executing", () -> SkillDataKey.createBooleanKey(false, true, BlazeStingerSkill.class));
        CHARGE_AERIAL = DATA_KEYS.register("charge_aerial_used", () -> SkillDataKey.createBooleanKey(false, true, BlazeStingerSkill.class));

        ULTIMATE_ART_TRY_CONNECTED = DATA_KEYS.register("ultimate_art_try_connect", () -> SkillDataKey.createBooleanKey(false, true,
                ImperatriceLumiere.class));
        ULTIMATE_ART_READY = DATA_KEYS.register("ultimate_art_ready", () -> SkillDataKey.createBooleanKey(false, true,
                ImperatriceLumiere.class));

        JAB = DATA_KEYS.register("jab", () -> SkillDataKey.createBooleanKey(false, true, ImperatriceAttacks.class));
        HEAT = DATA_KEYS.register("heat", () -> SkillDataKey.createIntKey(0, true,
                ImperatriceLumiere.class,
                ImperatriceAttacks.class,
		        BlazeStingerSkill.class,
		        FlareBurst.class
        ));

        FLARE_BURST = DATA_KEYS.register("flare_burst", () -> SkillDataKey.createBooleanKey(false, true,
                ImperatriceLumiere.class,
                FlareBurst.class
        ));

        FORWARD = DATA_KEYS.register("forward", () -> SkillDataKey.createBooleanKey(false, true,
                ImperatriceLumiere.class,
                BlazeStingerSkill.class
        ));

        BACKWARD = DATA_KEYS.register("backward", () -> SkillDataKey.createBooleanKey(true, true,
                ImperatriceLumiere.class
        ));

        LEFT = DATA_KEYS.register("left", () -> SkillDataKey.createBooleanKey(true, true,
                ImperatriceLumiere.class
        ));

        RIGHT = DATA_KEYS.register("right", () -> SkillDataKey.createBooleanKey(true, true,
		        ImperatriceLumiere.class
        ));

        UP = DATA_KEYS.register("up", () -> SkillDataKey.createBooleanKey(true, true,
                ImperatriceLumiere.class
        ));

        DOWN = DATA_KEYS.register("down", () -> SkillDataKey.createBooleanKey(true, true, new Class[]{
                ImperatriceLumiere.class,
        }));

        IN_AIR = DATA_KEYS.register("in_air", () -> SkillDataKey.createBooleanKey(true, true, new Class[]{
                ImperatriceLumiere.class,
        }));

        BLAZE_COMBO = DATA_KEYS.register("blaze_combo", () -> SkillDataKey.createIntKey(0, false, ImperatriceAttacks.class));
        CERCLE_DE_FEU = DATA_KEYS.register("cercle_de_feu", () -> SkillDataKey.createIntKey(0, false, ImperatriceAttacks.class));
    }
}
