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

import java.util.Optional;

public class RollCommand implements CommandExecutor{

    private NameManager nameManager = NameManager.getInstance();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)) {
            src.sendMessage(Text.of("Only players can use this command"));
            return CommandResult.empty();
        }

        Player player = (Player) src;

        Optional<Integer> opt = args.getOne(Text.of("roll"));
        Optional<Integer> optMod = args.getOne(Text.of("mod"));

        int roll = opt.orElse(20);
        int result = Utility.randomRange(0, roll);

        if(optMod.isPresent()) {
            int mod = optMod.orElse(0);
            result += mod;

            Utility.chatNearby(player, 30,  "§e" + nameManager.getName(player.getUniqueId()) + "§7 rolled a §a" + Integer.toString(result) + " out of " + Integer.toString(roll) + "§7 with a base modifier of " + Integer.toString(mod));
        }
        else {
            Utility.chatNearby(player, 30, "§e" + nameManager.getName(player.getUniqueId()) + "&7 rolled a §a" + Integer.toString(result) + " out of " + Integer.toString(roll));
        }

        return CommandResult.success();
    }
}
