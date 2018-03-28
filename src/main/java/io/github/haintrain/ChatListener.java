package io.github.haintrain;

import br.net.fabiozumbi12.UltimateChat.Sponge.API.SendChannelMessageEvent;
import io.github.haintrain.managers.ConfigurationManager;
import io.github.haintrain.managers.NameManager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.text.Text;

public class ChatListener {

    private String channel;

    public ChatListener(){
        channel = ConfigurationManager.getInstance().getConfig().getNode("channelName").getString();
    }

    @Listener
    public void uchatListener(SendChannelMessageEvent e){
        if(!(e.getSender() instanceof Player)){
            return;
        }

        Player player = ((Player) e.getSender()).getPlayer().get();

        if(e.getChannel().getAlias().equals(channel)){
            String name = NameManager.getInstance().getName(player.getUniqueId());

            String message = e.getMessage().toPlain();

            player.sendMessage(Text.of(message));
            message = name.concat(message);

            e.setMessage(Text.of(message));
        }
    }
}
