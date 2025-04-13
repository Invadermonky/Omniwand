package com.invadermonky.omniwand.command;

import com.invadermonky.omniwand.config.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;
import org.jetbrains.annotations.NotNull;

public class CommandReloadConfig extends CommandBase {
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandName() {
        return "omniwand_reload";
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "command.omniwand.reload.usage";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] par2ArrayOfStr) {
        ConfigHandler.init();
        iCommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("command.omniwand.reload.success"));
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return 0;
    }
}
