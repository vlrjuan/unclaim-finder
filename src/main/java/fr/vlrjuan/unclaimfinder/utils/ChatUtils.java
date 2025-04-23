package fr.vlrjuan.unclaimfinder.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

import java.util.Map;

public class ChatUtils {

    private static final ComponentSerializer<Component, TextComponent, String> COMPONENT_SERIALIZER = LegacyComponentSerializer.legacyAmpersand();

    @SafeVarargs
    public static void sendMessage(Player player, String message, Map.Entry<String, String>... args) {
        for (Map.Entry<String, String> entry : args) {
            message = message.replace(entry.getKey(), entry.getValue());
        }

        player.sendMessage(COMPONENT_SERIALIZER.deserialize(message));
    }
}
