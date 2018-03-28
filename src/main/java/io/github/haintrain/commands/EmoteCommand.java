package io.github.haintrain.commands;

import io.github.haintrain.managers.NameManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import io.github.haintrain.util.Utility;

public class EmoteCommand implements CommandExecutor {

    private NameManager nameManager = NameManager.getInstance();

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            src.sendMessage(Text.of("Only players can use this command"));
            return CommandResult.empty();
        }

        String text = args.<String>getOne(Text.of("text")).get();
        Player player = (Player) src;

        Utility.chatNearby(player, 30, "§e**" + nameManager.getName(player.getUniqueId()) + " " + text + "**");

        return CommandResult.success();
    }
}
