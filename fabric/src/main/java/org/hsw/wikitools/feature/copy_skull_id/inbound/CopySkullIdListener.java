package org.hsw.wikitools.feature.copy_skull_id.inbound;

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
import org.hsw.wikitools.feature.copy_skull_id.app.GetSkullIdHandler;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

public class CopySkullIdListener {
    private final GetSkullIdHandler getSkullIdHandler;
    private KeyBinding copyTooltipKeyBinding;

    public CopySkullIdListener(GetSkullIdHandler getSkullIdHandler) {
        this.getSkullIdHandler = getSkullIdHandler;

        registerKeyBinding();

        registerEvent();
    }

     private void registerKeyBinding() {
         copyTooltipKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
             "key.wikitools.copy-skull-id",
             InputUtil.Type.KEYSYM,
             GLFW.GLFW_KEY_Z,
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
            copySkullId(client);
        }
    }

    private void onKeyPress(MinecraftClient client, int key, int scanCode) {
        if (copyTooltipKeyBinding.matchesKey(key, scanCode)) {
            copySkullId(client);
        }
    }

    private void copySkullId(MinecraftClient client) {
        Optional<GetSkullIdHandler.GetSkullIdResponse> response = getSkullIdHandler.getSkullId(new GetSkullIdHandler.GetSkullIdRequest());
        if (response.isEmpty()) {
            return;
        }
        String stringToCopy = response.get().textureId;
        ClipboardHelper.setClipboard(stringToCopy);
        client.getMessageHandler().onGameMessage(Text.of("Copied skull ID"), false);
    }
}
