package fr.vlrjuan.unclaimfinder;

import fr.vlrjuan.unclaimfinder.configuration.Configuration;
import fr.vlrjuan.unclaimfinder.configuration.MalformedConfigurationException;
import fr.vlrjuan.unclaimfinder.integration.ItemsAdderIntegration;
import fr.vlrjuan.unclaimfinder.listeners.PlayerInteractListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class UnclaimFinderPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Configuration configuration = new Configuration(getComponentLogger(), getConfig());
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try {
                configuration.load();
            } catch (MalformedConfigurationException e) {
                getComponentLogger().error(Component.text("Failed to load configuration", NamedTextColor.RED), e);
                getServer().getPluginManager().disablePlugin(this);
            }
        });

        // Check if ItemsAdder is loaded
        ItemsAdderIntegration itemsAdderIntegration = ItemsAdderIntegration.of(Bukkit.getPluginManager().getPlugin("ItemsAdder") != null);

        getComponentLogger().info(
                Component.text()
                        .append(Component.text("ItemsAdder"))
                        .append(Component.text(" -> "))
                        .append(Component.text(itemsAdderIntegration.name(), itemsAdderIntegration.getColor(), TextDecoration.BOLD))
                        .build()
        );

        getServer().getPluginManager().registerEvents(new PlayerInteractListener(configuration, itemsAdderIntegration), this);
    }
}
