package io.github.haintrain.managers;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager instance = new ConfigurationManager();

    public static ConfigurationManager getInstance() {
        return instance;
    }

    private ConfigurationLoader<CommentedConfigurationNode> configLoader;
    private CommentedConfigurationNode config;

    public void setup(File configFile, ConfigurationLoader<CommentedConfigurationNode> configLoader) {
        this.configLoader = configLoader;

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                loadConfig();
                config.getNode("eventRange").setComment("Event chat range").setValue(20);
                config.getNode("channelName").setComment("Chat channel").setValue("local");
                config.getNode("channelWhisperName").setComment("Chat whisper channel").setValue("whisper");
                saveConfig();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            loadConfig();
        }
    }

    public CommentedConfigurationNode getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            configLoader.save(config);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            config = configLoader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}