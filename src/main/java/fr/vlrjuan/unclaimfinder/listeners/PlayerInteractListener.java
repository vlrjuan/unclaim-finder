package fr.vlrjuan.unclaimfinder.listeners;

import fr.vlrjuan.unclaimfinder.UnclaimFinder;
import fr.vlrjuan.unclaimfinder.integration.ItemsAdderIntegration;
import fr.vlrjuan.unclaimfinder.configuration.Configuration;
import fr.vlrjuan.unclaimfinder.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Map;

public class PlayerInteractListener implements Listener {

    private final Configuration configuration;
    private final ItemsAdderIntegration itemsAdderIntegration;

    public PlayerInteractListener(Configuration configuration, ItemsAdderIntegration itemsAdderIntegration) {
        this.configuration = configuration;
        this.itemsAdderIntegration = itemsAdderIntegration;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) {
            return;
        }

        Player player = event.getPlayer();

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.isEmpty()) {
            return;
        }

        String namespace = itemsAdderIntegration.getNamespace(item);

        configuration.getUnclaimFinder(namespace)
                .ifPresent(finder -> useFinder(player, finder));
    }

    private void useFinder(Player player, UnclaimFinder finder) {
        int foundContainers = getContainers(player.getLocation(), finder.range(), finder.containers());

        ChatUtils.sendMessage(player, finder.message(),
                Map.entry(UnclaimFinder.CONTAINERS_PLACEHOLDER, String.valueOf(foundContainers)),
                Map.entry(UnclaimFinder.RANGE_PLACEHOLDER, String.valueOf(finder.range()))
        );
    }

    private int getContainers(Location middle, int range, Collection<String> availableContainers) {
        int containers = 0;

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    Block block = middle.getBlock().getRelative(x, y, z);

                    if (availableContainers.contains(itemsAdderIntegration.getNamespace(block))) {
                        containers++;
                    }
                }
            }
        }

        return containers;
    }
}
