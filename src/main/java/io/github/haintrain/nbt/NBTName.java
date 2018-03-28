package io.github.haintrain.nbt;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.Color;

import java.util.Optional;

import static org.spongepowered.api.data.type.HandTypes.MAIN_HAND;

public class NBTName implements CommandExecutor{
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)) {
            src.sendMessage(Text.of("Only players can use this command"));
            return CommandResult.empty();
        }

        Player player = (Player) src;
        String name = args.<String>getOne(Text.of("name")).get();

        player.sendMessage(Text.of(player.getName()));

        Optional<ItemStack> optional = player.getItemInHand(MAIN_HAND);

        if(optional.isPresent()) {
            ItemStack item = optional.get();
            src.sendMessage(Text.of("Set name of item to " + name));

            item.offer(Keys.DISPLAY_NAME, Text.of(name));
            item.offer(Keys.COLOR, Color.GREEN);

            player.setItemInHand(MAIN_HAND, item);
            return CommandResult.success();
        }
        return CommandResult.success();
    }
}
