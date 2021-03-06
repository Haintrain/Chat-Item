package io.github.haintrain.util;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Random;

public final class Utility {

    private Utility() {
    }

    public static int randomRange(int low, int high)
    {
        Random generator = new Random();
        return generator.nextInt(high - low + 1) + low;
    }

    public static void chatNearby(Player player, Integer distance, String message) {
        for (Player pl : player.getWorld().getPlayers()) {
            if (pl.getLocation().getPosition().distanceSquared(player.getLocation().getPosition()) < distance) {
                pl.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize(message)));
            }
        }
    }
}
