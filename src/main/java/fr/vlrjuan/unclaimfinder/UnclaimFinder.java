package fr.vlrjuan.unclaimfinder;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public record UnclaimFinder(String namespace, int range, Collection<String> containers, String message) {

    public static final String CONTAINERS_PLACEHOLDER = "{containers}";
    public static final String RANGE_PLACEHOLDER = "{range}";

    private static final String DEFAULT_FINDER_RESULT_MESSAGE =
            "&7UnclaimFinder &8| &7&c%s &7conteneur(s) trouvé(s) dans un rayon de &c%s &7bloc(s)"
                    .formatted(CONTAINERS_PLACEHOLDER, RANGE_PLACEHOLDER);

    @SuppressWarnings("unchecked")
    public static UnclaimFinder deserialize(Map<String, Object> data) {
        String name = (String) data.getOrDefault("namespace", "minecraft:compass");
        int range = (int) data.getOrDefault("range", 30);
        Collection<String> containers = (Collection<String>) data.getOrDefault("containers", Collections.singleton("CHEST"));
        String message = (String) data.getOrDefault("message", DEFAULT_FINDER_RESULT_MESSAGE);

        if (name == null || range <= 0 || containers == null || message == null) {
            throw new IllegalArgumentException("Invalid finder data");
        }

        return new UnclaimFinder(name, range, containers, message);
    }
}
