package io.github.haintrain;

import com.google.inject.Inject;
import io.github.haintrain.commands.*;
import io.github.haintrain.managers.*;
import io.github.haintrain.nbt.*;
import io.github.haintrain.util.Utility;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import java.io.File;


@Plugin(id = "nbt", name = "io.github.haintrain.nbt", version = "1.0")
public class Main {

    private CommandSpec NBTName, NBTLore, NBTAuthor, NBTCommandSpec, rollCommandSpec, emoteCommandSpec, eventCommandSpec, nameCommandSpec;
    private CommandManager cmdManager;

    private String channel;

    private NameManager nameManager;
    private ConfigurationManager configurationManager;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private File configFile;

    @Inject
    private Logger logger;

    @Inject
    @DefaultConfig(sharedRoot = true)
    ConfigurationLoader<CommentedConfigurationNode> configManager;

    @Listener
    public void onIntialization(GameInitializationEvent e){
        nameManager = new NameManager();
        configurationManager = new ConfigurationManager();

        ConfigurationManager.getInstance().setup(configFile, configManager);

        cmdManager = Sponge.getCommandManager();

        NBTName = CommandSpec.builder()
                .permission("nbt.edit.name")
                .description(Text.of("Edit name of items"))
                .arguments(
                        GenericArguments.remainingJoinedStrings(Text.of("name")))
                .executor(new NBTName() )
                .build();

        NBTLore = CommandSpec.builder()
                .permission("nbt.edit.lore")
                .description(Text.of("Edit lore of items"))
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("line"))),
                        GenericArguments.remainingJoinedStrings(Text.of("lore")))
                .executor(new NBTLore() )
                .build();

        NBTAuthor = CommandSpec.builder()
                .permission("nbt.edit.author")
                .description(Text.of("Sign as author of items"))
                .executor(new NBTAuthor() )
                .build();

        NBTCommandSpec = CommandSpec.builder()
                .permission("nbt.edit")
                .description(Text.of("io.github.haintrain.nbt Command"))
                .child(NBTName, "name")
                .child(NBTLore, "lore")
                .child(NBTAuthor, "author")
                .build();

        rollCommandSpec = CommandSpec.builder()
                .description(Text.of("Roll Command"))
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.optional(GenericArguments.integer(Text.of("roll")))),
                        GenericArguments.onlyOne(GenericArguments.optional(GenericArguments.integer(Text.of("mod"))))
                )
                .executor(new RollCommand() )
                .build();

        emoteCommandSpec = CommandSpec.builder()
                .description(Text.of("Emote command"))
                .arguments(
                        GenericArguments.remainingJoinedStrings(Text.of("text")))
                .executor(new EmoteCommand() )
                .build();

        eventCommandSpec = CommandSpec.builder()
                .permission("nbt.event")
                .description(Text.of("Event command"))
                .arguments(
                        GenericArguments.remainingJoinedStrings(Text.of("text")))
                .executor(new EventCommand() )
                .build();

        nameCommandSpec = CommandSpec.builder()
                .description(Text.of("Name command"))
                .arguments(
                        GenericArguments.remainingJoinedStrings(Text.of("name")))
                .executor(new NameCommand() )
                .build();

        registerCommands();
    }

    @Listener
    public void serverStopEvent(GameStoppedServerEvent e){
        NameManager.getInstance().saveNames();
        ConfigurationManager.getInstance().saveConfig();
    }

    @Listener
    public void playerJoinEvent(ClientConnectionEvent.Join e){
        ConfigurationManager config = ConfigurationManager.getInstance();
        NameManager name = NameManager.getInstance();
        Player player = e.getTargetEntity();

        if(config.getConfig().getNode(player.getUniqueId()).getString() == null){
            name.newName(player.getUniqueId(), "Unnamed");
        }
        else{
            name.loadPlayer(player.getUniqueId());
        }
    }

    public Logger getLogger() {
        return logger;
    }

    private void registerCommands(){
        cmdManager.register(this, NBTCommandSpec, "nbt");
        cmdManager.register(this, rollCommandSpec, "roll");
        cmdManager.register(this, emoteCommandSpec,  "me");
        cmdManager.register(this, nameCommandSpec, "name");
        cmdManager.register(this, eventCommandSpec, "eb");

        Sponge.getEventManager().registerListeners(this, new ChatListener());
    }

}
