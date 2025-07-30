package org.hsw.wikitools.feature.view_item_id.outbound;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import org.hsw.wikitools.feature.view_item_id.app.FindHoveredItemId;
import org.hsw.wikitools.mixin.common.outbound.HandledScreenAccessor;

import java.util.Optional;

public class HoveredItemIdFinder implements FindHoveredItemId {

    @Override
    public Optional<String> findHoveredItemId() {
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

        return getItemIdFromItemStack(focusedItemStack);
    }

    private Optional<String> getItemIdFromItemStack(ItemStack itemStack) {
        ComponentMap components = itemStack.getComponents();

        NbtComponent nbtComponent = components.get(DataComponentTypes.CUSTOM_DATA);

        if (nbtComponent == null) {
            return Optional.empty();  // Cannot find custom data component
        }

        NbtCompound nbtCompound = nbtComponent.copyNbt();

        if (!nbtCompound.contains("id")) {
            return Optional.empty();  // Cannot find the key "id" in the custom data component
        }

        return nbtCompound.getString("id");
    }

}
