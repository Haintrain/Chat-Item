package io.github.haintrain.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NameManager {

    private static NameManager instance = new NameManager();
    private HashMap<UUID, String> names = new HashMap<>();

    public static NameManager getInstance() {
        return instance;
    }

    public void loadPlayer(UUID uuid){
        names.put(uuid, ConfigurationManager.getInstance().getConfig().getNode(uuid).getString());
    }

    public void newName(UUID uuid, String name){
        names.put(uuid, name);
    }

    public String getName(UUID uuid){
        return names.get(uuid);
    }

    public void saveNames(){
        for (Map.Entry<UUID, String> name : names.entrySet()) {
            ConfigurationManager.getInstance().getConfig().getNode(name.getKey().toString()).setValue(name.getValue());
        }
    }
}
