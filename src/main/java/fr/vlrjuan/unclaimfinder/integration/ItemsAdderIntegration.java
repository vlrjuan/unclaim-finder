package fr.vlrjuan.unclaimfinder.integration;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomEntity;
import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public enum ItemsAdderIntegration {
    ON(NamedTextColor.GREEN) {
        @Override
        public String getNamespace(Block block) {
            return Optional.ofNullable(CustomBlock.byAlreadyPlaced(block))
                    .map(CustomBlock::getNamespacedID)
                    .orElse(super.getNamespace(block));
        }

        @Override
        public String getNamespace(ItemStack item) {
            return Optional.ofNullable(CustomStack.byItemStack(item))
                    .map(CustomStack::getNamespacedID)
                    .orElse(super.getNamespace(item));
        }

        @Override
        public String getNamespace(Entity entity) {
            return Optional.ofNullable(CustomEntity.byAlreadySpawned(entity))
                    .map(CustomEntity::getNamespacedID)
                    .orElse(super.getNamespace(entity));
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

    public String getNamespace(Entity entity) {
        return entity.getType().getKey().toString();
    }

    private String getNamespace(Material material) {
        return material.getKey().toString();
    }

    public NamedTextColor getColor() {
        return color;
    }
}
