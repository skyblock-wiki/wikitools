package org.hsw.wikitools.adapter.presentation;

import org.hsw.wikitools.application.port.in_port.GetHoveredItemTooltip;
import org.hsw.wikitools.domain.value.WikiTooltip;
import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.text.Text;

import java.util.Optional;

// import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
// import net.minecraft.client.MinecraftClient;
// import net.minecraft.client.option.KeyBinding;
// import net.minecraft.client.util.InputUtil;

public class CopyHoveredItemTooltipListener {
    private final GetHoveredItemTooltip getHoveredItemTooltip;
    // private KeyBinding copyTooltipKeyBinding;

    public CopyHoveredItemTooltipListener(GetHoveredItemTooltip getHoveredItemTooltip) {
        this.getHoveredItemTooltip = getHoveredItemTooltip;

        // registerKeyBinding();

        registerEvent();
    }

    // private void registerKeyBinding() {
    //     copyTooltipKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
    //         "key.wikitools.copy_tooltip",
    //         InputUtil.Type.KEYSYM,
    //         GLFW.GLFW_KEY_X,
    //         "key.categories.wikitools"
    //     ));
    // }

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
        boolean xIsPressed = key == GLFW.GLFW_KEY_X;
        boolean xIsPressedWithShift = key == GLFW.GLFW_KEY_X && modifiers == GLFW.GLFW_MOD_SHIFT;

        if (xIsPressed && xIsPressedWithShift) {
            copyTooltip(client, false);
        }
        else if (xIsPressed) {
            copyTooltip(client, true);
        }
    }

    private void copyTooltip(MinecraftClient client, boolean toTemplateFormatting) {
        Optional<WikiTooltip> tooltip = getHoveredItemTooltip.getHoveredItemTooltip(toTemplateFormatting);
        if (tooltip.isEmpty()) {
            return; // No tooltip to copy
        }
        WikiTooltip wikiTooltip = tooltip.get();
        ClipboardHelper.setClipboard(toTemplateFormatting ? wikiTooltip.toTemplateString() : wikiTooltip.toModuleString());
        client.getMessageHandler().onGameMessage(Text.of("Copied tooltip (" + (toTemplateFormatting ? "template" : "module") +" formatting)"), false);
    }

}
