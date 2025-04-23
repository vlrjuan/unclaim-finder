package fr.vlrjuan.unclaimfinder;

import fr.vlrjuan.unclaimfinder.configuration.Configuration;
import fr.vlrjuan.unclaimfinder.configuration.MalformedConfigurationException;
import fr.vlrjuan.unclaimfinder.listeners.PlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class UnclaimFinderPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Configuration configuration = new Configuration(getSLF4JLogger(), getConfig());

        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try {
                configuration.load();
            } catch (MalformedConfigurationException e) {
                getSLF4JLogger().error("Failed to load configuration", e);
                getServer().getPluginManager().disablePlugin(this);
            }
        });

        getServer().getPluginManager().registerEvents(new PlayerInteractListener(configuration), this);
    }
}
