package io.github.haintrain.commands;

import io.github.haintrain.managers.NameManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class NameCommand implements CommandExecutor{

    private NameManager nameManager = NameManager.getInstance();

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            src.sendMessage(Text.of("Only players can use this command"));
            return CommandResult.empty();
        }

        String name = args.<String>getOne(Text.of("name")).get();
        Player player = (Player) src;

        NameManager.getInstance().newName(player.getUniqueId(), name);

        player.sendMessage(Text.of("Changed named to " + nameManager.getName(player.getUniqueId())));

        return CommandResult.success();
    }
}
