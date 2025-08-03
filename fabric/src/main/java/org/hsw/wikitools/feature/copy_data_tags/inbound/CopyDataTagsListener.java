package org.hsw.wikitools.feature.copy_data_tags.inbound;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.hsw.wikitools.common.inbound.ClipboardHelper;
import org.hsw.wikitools.feature.copy_data_tags.app.GetDataTagsHandler;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

public class CopyDataTagsListener {
    private final GetDataTagsHandler getDataTagsHandler;
    private KeyBinding copyTooltipKeyBinding;

    public CopyDataTagsListener(GetDataTagsHandler getDataTagsHandler) {
        this.getDataTagsHandler = getDataTagsHandler;

        registerKeyBinding();

        registerEvent();
    }

     private void registerKeyBinding() {
         copyTooltipKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
             "key.wikitools.copy-data-tags",
             InputUtil.Type.KEYSYM,
             GLFW.GLFW_KEY_N,
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

            ScreenKeyboardEvents.afterKeyPress(screen).register((screen1, key, scanCode, modifiers) -> onKeyPress(client, key, scanCode));
        });

        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
    }

    private void onClientTick(MinecraftClient client) {
        while (client.currentScreen == null && copyTooltipKeyBinding.wasPressed()) {
            copyDataTags(client);
        }
    }

    private void onKeyPress(MinecraftClient client, int key, int scanCode) {
        if (copyTooltipKeyBinding.matchesKey(key, scanCode)) {
            copyDataTags(client);
        }
    }

    private void copyDataTags(MinecraftClient client) {
        Optional<GetDataTagsHandler.GetDataTagsResponse> response = getDataTagsHandler.getDataTags(new GetDataTagsHandler.GetDataTagsRequest());
        if (response.isEmpty()) {
            return;
        }
        String stringToCopy = response.get().dataTags;
        ClipboardHelper.setClipboard(stringToCopy);
        client.getMessageHandler().onGameMessage(Text.of("Copied data tags"), false);
    }
}
