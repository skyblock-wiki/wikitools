package org.hsw.wikitools.feature.get_skull_id.outbound;

import com.mojang.authlib.properties.Property;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PlayerHeadItem;
import net.minecraft.screen.slot.Slot;
import org.hsw.wikitools.feature.get_skull_id.app.FindHoveredSkullItem;
import org.hsw.wikitools.feature.get_skull_id.app.Skull;
import org.hsw.wikitools.mixin.HandledScreenAccessor;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class HoveredSkullItemFinder implements FindHoveredSkullItem {
    @Override
    public Optional<Skull> getHoveredSkull() {
        Optional<ItemStack> focusedItemStack = findFocusedItemStack();

        if (focusedItemStack.isEmpty()) {
            return Optional.empty(); // Cannot find focused item stack
        }

        return findSkull(focusedItemStack.get());
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

    private static @NotNull Optional<Skull> findSkull(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof PlayerHeadItem)) {
            return Optional.empty(); // Not a player head
        }

        ComponentMap components = itemStack.getComponents();

        ProfileComponent profileComponent = components.get(DataComponentTypes.PROFILE);

        if (profileComponent == null) {
            return Optional.empty(); // Cannot find profile component
        }

        Optional<Property> textureProperty = profileComponent.properties().get("textures").stream().findFirst();

        if (textureProperty.isEmpty()) {
            return Optional.empty(); // Cannot get texture property
        }

        String textureValue = textureProperty.get().value();

        return Optional.of(Skull.ofTextureValue(textureValue));
    }
}
