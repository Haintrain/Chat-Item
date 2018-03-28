package io.github.haintrain.commands;

import io.github.haintrain.managers.ConfigurationManager;
import io.github.haintrain.managers.NameManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import io.github.haintrain.util.Utility;

public class EventCommand implements CommandExecutor {

    private int eventRange = ConfigurationManager.getInstance().getConfig().getNode("eventRange").getInt();
    private NameManager nameManager = NameManager.getInstance();

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            src.sendMessage(Text.of("Only players can use this command"));
            return CommandResult.empty();
        }

        String message = args.<String>getOne(Text.of("text")).get();
        Player player = (Player) src;

        if(message.equals("reload")){
            reloadEventRange();
            return CommandResult.success();
        }

        Utility.chatNearby(player, eventRange, "§e**" + nameManager.getName(player.getUniqueId()) + " " + message + "**");

        return CommandResult.success();
    }

    private void reloadEventRange(){
        eventRange = ConfigurationManager.getInstance().getConfig().getNode("eventRange").getInt();
    }
}
