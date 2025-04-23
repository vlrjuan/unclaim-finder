package fr.vlrjuan.unclaimfinder.integration;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public enum ItemsAdderIntegration {
    ON(NamedTextColor.GREEN) {
        @Override
        public String getNamespace(Block block) {
            CustomBlock customBlock = CustomBlock.byAlreadyPlaced(block);

            if (customBlock != null) {
                return customBlock.getNamespacedID();
            }

            return super.getNamespace(block);
        }

        @Override
        public String getNamespace(ItemStack item) {
            CustomStack customItem = CustomStack.byItemStack(item);

            if (customItem != null) {
                return customItem.getNamespacedID();
            }

            return super.getNamespace(item);
        }
    },
    OFF(NamedTextColor.RED);

    private final NamedTextColor color;

    ItemsAdderIntegration(NamedTextColor color) {
        this.color = color;
    }

    public static ItemsAdderIntegration of(boolean enabled) {
        return enabled ? ON : OFF;
    }

    public String getNamespace(Block block) {
        return getNamespace(block.getType());
    }

    public String getNamespace(ItemStack item) {
        return getNamespace(item.getType());
    }

    private String getNamespace(Material material) {
        return "%s:%s".formatted(NamespacedKey.MINECRAFT, material.name().toLowerCase(Locale.ROOT));
    }

    public NamedTextColor getColor() {
        return color;
    }
}
