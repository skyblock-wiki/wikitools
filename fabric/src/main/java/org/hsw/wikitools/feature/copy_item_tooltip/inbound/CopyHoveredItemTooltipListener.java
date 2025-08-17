package org.hsw.wikitools.feature.copy_item_tooltip.inbound;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.hsw.wikitools.common.inbound.ClipboardHelper;
import org.hsw.wikitools.feature.copy_item_tooltip.app.GetItemTooltipHandler;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

public class CopyHoveredItemTooltipListener {
    private final GetItemTooltipHandler getItemTooltipHandler;
    private final KeyBinding copyTooltipKeyBinding;

    public CopyHoveredItemTooltipListener(GetItemTooltipHandler getItemTooltipHandler) {
        this.getItemTooltipHandler = getItemTooltipHandler;

        this.copyTooltipKeyBinding = registerKeyBinding();

        registerEvent();
    }

     private KeyBinding registerKeyBinding() {
         return KeyBindingHelper.registerKeyBinding(new KeyBinding(
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

            ScreenKeyboardEvents.afterKeyPress(screen).register((screen1, key, scanCode, modifiers) -> onKeyPress(client, key, modifiers));
        });
    }

    private void onKeyPress(MinecraftClient client, int key, int modifiers) {
        boolean xIsPressed = copyTooltipKeyBinding.matchesKey(key, 0);
        boolean xIsPressedWithShift = xIsPressed && (modifiers & GLFW.GLFW_MOD_SHIFT) > 0;

        if (xIsPressed && xIsPressedWithShift) {
            copyTooltipAsModuleData(client);
        }
        else if (xIsPressed) {
            copyTooltipAsTemplateCall(client);
        }
    }

    private void copyTooltipAsTemplateCall(MinecraftClient client) {
        Optional<GetItemTooltipHandler.GetItemTooltipResponse> tooltip =
                getItemTooltipHandler.getInventorySlotTemplateCall(new GetItemTooltipHandler.GetItemTooltipRequest());
        if (tooltip.isEmpty()) {
            return; // No tooltip to copy
        }
        String stringToCopy = tooltip.get().tooltip;
        ClipboardHelper.setClipboard(stringToCopy);

        MutableText formattingModeTip = Text.literal("(◕‿◕)").setStyle(
                Style.EMPTY.withHoverEvent(new HoverEvent.ShowText(Text.literal(
                        "Template formatting is used by default.\nModule formatting is activated by Shift+Keybind."))));
        MutableText outputText = Text.literal("Copied tooltip").append("\n")
                .append("└ ").append("with template formatting").append(" ").append(formattingModeTip);
        client.getMessageHandler().onGameMessage(Text.of(outputText), false);
    }

    private void copyTooltipAsModuleData(MinecraftClient client) {
        Optional<GetItemTooltipHandler.GetItemTooltipResponse> tooltip =
                getItemTooltipHandler.getTooltipModuleDataItem(new GetItemTooltipHandler.GetItemTooltipRequest());

        if (tooltip.isEmpty()) {
            return; // No tooltip to copy
        }
        String stringToCopy = tooltip.get().tooltip;
        ClipboardHelper.setClipboard(stringToCopy);

        MutableText formattingModeTip = Text.literal("(◕‿◕)").setStyle(
                Style.EMPTY.withHoverEvent(new HoverEvent.ShowText(Text.literal(
                        "Template formatting is used by default.\nModule formatting is activated by Shift+Keybind."))));
        MutableText outputText = Text.literal("Copied tooltip").append("\n")
                .append("└ ").append("with module formatting").append(" ").append(formattingModeTip);
        client.getMessageHandler().onGameMessage(Text.of(outputText), false);
    }

}
