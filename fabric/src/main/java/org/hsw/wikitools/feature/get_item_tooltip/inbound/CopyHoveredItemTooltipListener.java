package org.hsw.wikitools.feature.get_item_tooltip.inbound;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.hsw.wikitools.common.ClipboardHelper;
import org.hsw.wikitools.feature.get_item_tooltip.app.GetItemTooltipHandler;
import org.hsw.wikitools.feature.get_item_tooltip.app.InventorySlotTemplateCall;
import org.hsw.wikitools.feature.get_item_tooltip.app.TooltipModuleDataItem;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

public class CopyHoveredItemTooltipListener {
    private final GetItemTooltipHandler getItemTooltipHandler;
     private KeyBinding copyTooltipKeyBinding;

    public CopyHoveredItemTooltipListener(GetItemTooltipHandler getItemTooltipHandler) {
        this.getItemTooltipHandler = getItemTooltipHandler;

        registerKeyBinding();

        registerEvent();
    }

     private void registerKeyBinding() {
         copyTooltipKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
             "key.wikitools.copy-tooltip",
             InputUtil.Type.KEYSYM,
             GLFW.GLFW_KEY_X,
             "key.categories.wikitools"
         ));
     }

    private void registerEvent() {
        // Add a listener for screen events (when a screen is opened)
        // to add a keyboard event listener for the HandledScreen

        ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            // Add a listener for keyboard events if the screen is a HandledScreen

            if (!(screen instanceof HandledScreen<?>)) {
                return; // Only register for InventoryScreen
            }

            ScreenKeyboardEvents.afterKeyPress(screen).register((screen1, key, scanCode, modifiers) -> handleEvent(client, key, modifiers));
        });
    }

    private void handleEvent(MinecraftClient client, int key, int modifiers) {
        boolean xIsPressed = copyTooltipKeyBinding.matchesKey(key, 0);
        boolean xIsPressedWithShift = xIsPressed && modifiers == GLFW.GLFW_MOD_SHIFT;

        if (xIsPressed && xIsPressedWithShift) {
            copyTooltipAsModuleData(client);
        }
        else if (xIsPressed) {
            copyTooltipAsTemplateCall(client);
        }
    }

    private void copyTooltipAsTemplateCall(MinecraftClient client) {
        Optional<InventorySlotTemplateCall> tooltip = getItemTooltipHandler.getInventorySlotTemplateCall();
        if (tooltip.isEmpty()) {
            return; // No tooltip to copy
        }
        String stringToCopy = tooltip.get().string;
        ClipboardHelper.setClipboard(stringToCopy);
        client.getMessageHandler().onGameMessage(Text.of("Copied tooltip (template formatting)"), false);
    }

    private void copyTooltipAsModuleData(MinecraftClient client) {
        Optional<TooltipModuleDataItem> tooltip = getItemTooltipHandler.getTooltipModuleDataItem();
        if (tooltip.isEmpty()) {
            return; // No tooltip to copy
        }
        String stringToCopy = tooltip.get().string;
        ClipboardHelper.setClipboard(stringToCopy);
        client.getMessageHandler().onGameMessage(Text.of("Copied tooltip (module formatting)"), false);
    }

}
