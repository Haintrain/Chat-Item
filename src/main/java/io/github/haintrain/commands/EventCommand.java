package io.github.haintrain.commands;

import io.github.haintrain.managers.ConfigurationManager;
import io.github.haintrain.util.Utility;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.HashMap;
import java.util.UUID;

public class EventCommand implements CommandExecutor {
    private int eventRange;
    private HashMap<UUID, String> emote = new HashMap<>();

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            src.sendMessage(Text.of("Only players can use this command"));
            return CommandResult.empty();
        }


        String message = args.<String>getOne(Text.of("text")).get();
        Player player = (Player) src;

        if(message.equals("clear")){
            emote.remove(player.getUniqueId());
            return CommandResult.success();
        }

        if(message.charAt(message.length() - 1) == '-' && emote.get(player.getUniqueId()) == null){
            emote.put(player.getUniqueId(), message.substring(0, message.length() - 1));
        }
        else if(message.charAt(message.length() - 1) == '-' && emote.get(player.getUniqueId()) != null){
            emote.put(player.getUniqueId(), emote.get(player.getUniqueId()).concat(message.substring(0, message.length() -1)));
        }
        else if(emote.get(player.getUniqueId()) != null){
            Utility.chatNearby(player, 20, "§8{§4!§8} §7" + emote.get(player.getUniqueId()).concat(message.substring(0, message.length() - 1)) + "§8{§4!§8}");
        }
        else{
            Utility.chatNearby(player, 20, "§8{§4!§8} §7" + message + "§8{§4!§8}");
        }



        return CommandResult.success();
    }

    public void reloadEventRange(){
        eventRange = ConfigurationManager.getInstance().getConfig().getNode("eventRange").getInt();
    }
}