package org.hsw.wikitools.feature.copy_data_tags.outbound;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.screen.slot.Slot;
import org.hsw.wikitools.feature.copy_data_tags.app.FindHoveredItemDataTags;
import org.hsw.wikitools.feature.copy_data_tags.app.ItemDataTags;
import org.hsw.wikitools.mixin.common.outbound.HandledScreenAccessor;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class HoveredItemDataTagsFinder implements FindHoveredItemDataTags {
    @Override
    public Optional<ItemDataTags> findHoveredItemDataTags() {
        Optional<ItemStack> focusedItemStack = findFocusedItemStack();

        if (focusedItemStack.isEmpty()) {
            return Optional.empty();  // No hovered item
        }

        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld clientWorld = client.world;

        if (clientWorld == null) {
            return Optional.empty(); // Cannot find world
        }

        NbtElement nbtElement = focusedItemStack.get().toNbt(clientWorld.getRegistryManager());

        ItemDataTags itemDataTags = new ItemDataTags(nbtElement.toString());
        return Optional.of(itemDataTags);
    }

    private static @NotNull Optional<ItemStack> findFocusedItemStack() {
        MinecraftClient client = MinecraftClient.getInstance();
        Screen screen = client.currentScreen;

        if (!(screen instanceof HandledScreen<?> handledScreen)) {
            return Optional.empty(); // Not a handled screen, cannot find hovered item
        }

        Slot focusedSlot = ((HandledScreenAccessor) handledScreen).getFocusedSlot();

        if (focusedSlot == null) {
            return Optional.empty(); // No focused slot, cannot find hovered item
        }

        ItemStack focusedItemStack = focusedSlot.getStack();

        if (focusedItemStack.isEmpty()) {
            return Optional.empty(); // No item hovered
        }

        return Optional.of(focusedItemStack);
    }
}
