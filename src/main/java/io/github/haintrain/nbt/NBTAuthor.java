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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.spongepowered.api.data.type.HandTypes.MAIN_HAND;

public class NBTAuthor implements CommandExecutor{
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            src.sendMessage(Text.of("Only players can use this command"));
            return CommandResult.empty();
        }

        Player player = (Player) src;
        Text name = args.<Text>getOne("name").get();

        Optional<ItemStack> optional = player.getItemInHand(MAIN_HAND);

        if (optional.isPresent()) {
            ItemStack item = optional.get();
            String author = player.getName();

            Optional <List<Text>> stuff = item.get(Keys.ITEM_LORE);
            List<Text> lore = new ArrayList<>();

            if(stuff.isPresent()) {
                lore = stuff.get();
                lore.set(lore.size() + 2, Text.of(name));
            }
            else {
                lore.set(0 ,Text.of(name));
            }

            item.offer(Keys.ITEM_LORE, lore);
            item.offer(Keys.DISPLAY_NAME, name);
            item.offer(Keys.COLOR, Color.GREEN);
            return CommandResult.success();
        }

        return CommandResult.empty();
    }
}
