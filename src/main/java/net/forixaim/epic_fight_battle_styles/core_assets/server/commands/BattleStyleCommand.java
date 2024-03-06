package net.forixaim.epic_fight_battle_styles.core_assets.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillCategories;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import yesman.epicfight.server.commands.PlayerSkillCommand;
import yesman.epicfight.server.commands.arguments.SkillArgument;
import yesman.epicfight.skill.SkillSlot;

import java.util.function.Supplier;

public class BattleStyleCommand
{
    private static final SimpleCommandExceptionType ERROR_ADD_FAILED = new SimpleCommandExceptionType(Component.translatable("commands." + EpicFightBattleStyles.MOD_ID +".skill.add.failed"));
    private static final SimpleCommandExceptionType ERROR_REMOVE_FAILED = new SimpleCommandExceptionType(Component.translatable("commands." + EpicFightBattleStyles.MOD_ID + ".skill.remove.failed"));
    private static final SimpleCommandExceptionType ERROR_CLEAR_FAILED = new SimpleCommandExceptionType(Component.translatable("commands." + EpicFightBattleStyles.MOD_ID + ".skill.clear.failed"));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        RequiredArgumentBuilder<CommandSourceStack, EntitySelector> addCommandBuilder = Commands.argument("targets", EntityArgument.players());

            addCommandBuilder
                    .then(Commands.argument("skill", SkillArgument.skill()))
                    .executes(
                            (context) -> PlayerSkillCommand.addSkill(context.getSource(), EntityArgument.getPlayers(context, "targets"), EpicFightBattleStyleSkillSlots.BATTLE_STYLE, SkillArgument.getSkill(context, "skill"))
                    );


        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("battle_style").requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                .then(Commands.literal("set")
                        .then(addCommandBuilder));

        dispatcher.register(Commands.literal("epicfight_battlestyles").then(builder));
    }

    private static <T> Supplier<T> wrap(T value) {
        return () -> value;
    }
}
