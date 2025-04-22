package fr.vlrjuan.unclaimfinder.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

public class ChatUtils {

    private static final ComponentSerializer<Component, TextComponent, String> COMPONENT_SERIALIZER = LegacyComponentSerializer.legacyAmpersand();

    public static void sendMessage(Player player, String message) {
        player.sendMessage(COMPONENT_SERIALIZER.deserialize(message));
    }
}
