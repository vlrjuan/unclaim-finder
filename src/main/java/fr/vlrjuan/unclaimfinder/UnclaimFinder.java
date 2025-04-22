package fr.vlrjuan.unclaimfinder;

import org.bukkit.Material;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public record UnclaimFinder(String name, int range, Collection<Material> containers) {

    @SuppressWarnings("unchecked")
    public static UnclaimFinder deserialize(Map<String, Object> data) {
        String name = (String) data.get("name");
        int radius = (int) data.get("range");
        Collection<String> containerNames = (Collection<String>) data.get("containers");

        if (name == null || radius <= 0 || containerNames == null) {
            throw new IllegalArgumentException("Invalid finder data");
        }

        Collection<Material> containers = containerNames.stream()
                .map(Material::matchMaterial)
                .filter(Objects::nonNull)
                .toList();

        return new UnclaimFinder(name, radius, containers);
    }

    public boolean matchesContainer(Material material) {
        return containers.contains(material);
    }
}
