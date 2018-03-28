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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.spongepowered.api.data.type.HandTypes.MAIN_HAND;

public class NBTLore implements CommandExecutor{
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)) {
            src.sendMessage(Text.of("Only players can use this command"));
            return CommandResult.empty();
        }

        Player player = (Player) src;
        String text = args.<String>getOne("lore").get();
        Integer line = args.<Integer>getOne("line").get();

        line -= 1;


        Optional<ItemStack> optional = player.getItemInHand(MAIN_HAND);

        if(optional.isPresent()) {
            ItemStack item = optional.get();
            Optional <List<Text>> stuff = item.get(Keys.ITEM_LORE);
            List<Text> lore = new ArrayList<>();

            if(stuff.isPresent()) {
                lore = stuff.get();
            }

            if(line >= lore.size()){
                lore.add(Text.of(text));
            }
            else {
                lore.set(line, Text.of(text));
            }

            item.offer(Keys.ITEM_LORE, lore);

            player.setItemInHand(MAIN_HAND, item);
            src.sendMessage(Text.of("Line " + (line + 1) + " added to lore: " + text));
            return CommandResult.success();
        }

        return CommandResult.empty();
    }
}
