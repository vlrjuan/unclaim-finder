package fr.vlrjuan.unclaimfinder.configuration;

public class MalformedConfigurationException extends RuntimeException {

    public MalformedConfigurationException(String message) {
        super("Malformed configuration: %s".formatted(message));
    }
}
