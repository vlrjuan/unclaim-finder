package fr.vlrjuan.unclaimfinder.configuration;

import fr.vlrjuan.unclaimfinder.UnclaimFinder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Configuration {

    private final ComponentLogger logger;
    private final FileConfiguration configuration;

    // Data
    private Map<String, UnclaimFinder> finders = new HashMap<>();

    public Configuration(ComponentLogger logger, FileConfiguration configuration) {
        this.logger = logger;
        this.configuration = configuration;
    }

    @SuppressWarnings("unchecked")
    public void load() throws MalformedConfigurationException {
        List<?> finders = configuration.getList("finders");
        if (finders == null) {
            throw new MalformedConfigurationException("No finders found in configuration");
        }

        this.finders = finders.stream()
                .map(data -> {
                    if (!(data instanceof Map)) {
                        throw new MalformedConfigurationException("Finder should be a map");
                    }

                    return UnclaimFinder.deserialize((Map<String, Object>) data);
                })
                .collect(Collectors.toMap(UnclaimFinder::namespace, Function.identity()));

        logger.info(
                Component.text()
                        .append(Component.text("Loaded"))
                        .appendSpace()
                        .append(Component.text(this.finders.size(), NamedTextColor.GREEN, TextDecoration.BOLD))
                        .appendSpace()
                        .append(Component.text("finder(s)"))
                        .appendSpace()
                        .append(
                                Component.join(
                                        JoinConfiguration.builder()
                                                .prefix(Component.text("("))
                                                .suffix(Component.text(")"))
                                                .separator(Component.text(","))
                                                .build(),
                                        this.finders.keySet().stream()
                                                .map(key -> Component.text()
                                                        .append(Component.text("\""))
                                                        .append(Component.text(key, NamedTextColor.GRAY))
                                                        .append(Component.text("\""))
                                                )
                                                .toList()
                                )
                        )
                        .build()
        );
    }

    public Optional<UnclaimFinder> getUnclaimFinder(String namespace) {
        return Optional.ofNullable(finders.get(namespace));
    }
}
