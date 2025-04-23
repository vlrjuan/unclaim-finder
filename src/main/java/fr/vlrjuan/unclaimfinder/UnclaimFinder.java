package fr.vlrjuan.unclaimfinder;

import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

public record UnclaimFinder(String name, int range, Collection<String> containers) {

    @SuppressWarnings("unchecked")
    public static UnclaimFinder deserialize(Map<String, Object> data) {
        String name = (String) data.get("name");
        int range = (int) data.get("range");
        Collection<String> containers = (Collection<String>) data.get("containers");

        if (name == null || range <= 0 || containers == null) {
            throw new IllegalArgumentException("Invalid finder data");
        }

        return new UnclaimFinder(name, range, containers);
    }

    @SuppressWarnings("ConstantValue") // False positive
    public boolean matchesContainer(Block block) {
        return matchesContainer(block.getType()) || matchesContainer(CustomBlock.byAlreadyPlaced(block));
    }

    private boolean matchesContainer(Material material) {
        return containers.contains("%s:%s".formatted(NamespacedKey.MINECRAFT, material.name().toLowerCase(Locale.ROOT)));
    }

    private boolean matchesContainer(CustomBlock customBlock) {
        return customBlock != null && containers.contains(customBlock.getNamespacedID());
    }
}
