package fr.vlrjuan.unclaimfinder.listeners;

import dev.lone.itemsadder.api.CustomStack;
import fr.vlrjuan.unclaimfinder.UnclaimFinder;
import fr.vlrjuan.unclaimfinder.configuration.Configuration;
import fr.vlrjuan.unclaimfinder.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    private final Configuration configuration;

    public PlayerInteractListener(Configuration configuration) {
        this.configuration = configuration;
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

        CustomStack finderItem = CustomStack.byItemStack(item);
        if (finderItem == null) {
            return;
        }

        configuration.getFinder(finderItem.getNamespacedID())
                .ifPresent(finder -> useFinder(player, finder));
    }

    private void useFinder(Player player, UnclaimFinder finder) {
        int range = finder.range();

        String resultMessage = configuration.getFinderResultMessage()
                .replace("{containers}", String.valueOf(getContainers(player.getLocation(), range, finder)))
                .replace("{range}", String.valueOf(range));

        ChatUtils.sendMessage(player, resultMessage);
    }

    private int getContainers(Location middle, int range, UnclaimFinder finder) {
        int containers = 0;

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    Block block = middle.getBlock().getRelative(x, y, z);

                    if (finder.matchesContainer(block)) {
                        containers++;
                    }
                }
            }
        }

        return containers;
    }
}
