package fr.vlrjuan.unclaimfinder;

import java.util.Collection;
import java.util.Map;

public record UnclaimFinder(String namespace, int range, Collection<String> containers) {

    @SuppressWarnings("unchecked")
    public static UnclaimFinder deserialize(Map<String, Object> data) {
        String name = (String) data.get("namespace");
        int range = (int) data.get("range");
        Collection<String> containers = (Collection<String>) data.get("containers");

        if (name == null || range <= 0 || containers == null) {
            throw new IllegalArgumentException("Invalid finder data");
        }

        return new UnclaimFinder(name, range, containers);
    }
}
