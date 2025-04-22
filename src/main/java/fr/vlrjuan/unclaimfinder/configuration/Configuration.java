package fr.vlrjuan.unclaimfinder.configuration;

import fr.vlrjuan.unclaimfinder.UnclaimFinder;
import org.bukkit.configuration.file.FileConfiguration;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Configuration {

    private static final String DEFAULT_FINDER_RESULT_MESSAGE = "&7UnclaimFinder &8| &7&c{containers} &7conteneur(s) trouvé(s) dans un rayon de &c{range} &7bloc(s)";

    private final Logger logger;
    private final FileConfiguration configuration;

    // Data
    private Map<String, UnclaimFinder> finders = new HashMap<>();
    private String finderResultMessage;

    public Configuration(Logger logger, FileConfiguration configuration) {
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
                .collect(Collectors.toMap(UnclaimFinder::name, Function.identity()));

        logger.info("Loaded {} finders ({})", this.finders.size(), String.join(", ", this.finders.keySet()));

        finderResultMessage = configuration.getString("messages.result", DEFAULT_FINDER_RESULT_MESSAGE);
    }

    public Optional<UnclaimFinder> getFinder(String name) {
        return Optional.ofNullable(finders.get(name));
    }

    public String getFinderResultMessage() {
        return finderResultMessage;
    }
}
