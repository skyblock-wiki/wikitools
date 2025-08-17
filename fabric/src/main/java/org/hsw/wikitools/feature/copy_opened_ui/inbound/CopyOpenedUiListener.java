package org.hsw.wikitools.feature.copy_opened_ui.inbound;

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
import org.hsw.wikitools.feature.copy_opened_ui.app.GetOpenedUiHandler;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

public class CopyOpenedUiListener {
    private final GetOpenedUiHandler getOpenedUiHandler;
    private final KeyBinding copyOpenedUiKeybinding;

    public CopyOpenedUiListener(GetOpenedUiHandler getOpenedUiHandler) {
        this.getOpenedUiHandler = getOpenedUiHandler;

        this.copyOpenedUiKeybinding = registerKeyBinding();

        registerEvent();
    }

    private KeyBinding registerKeyBinding() {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.wikitools.copy-opened-ui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
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

            ScreenKeyboardEvents.afterKeyPress(screen).register((screen1, key, scanCode, modifiers) -> onKeyPress(client, key, scanCode, modifiers));
        });
    }

    private void onKeyPress(MinecraftClient client, int key, int scanCode, int modifiers) {
        boolean cIsPressed = copyOpenedUiKeybinding.matchesKey(key, 0);
        boolean cIsPressedWithShift = cIsPressed && (modifiers & GLFW.GLFW_MOD_SHIFT) > 0;
        boolean cIsPressedWithControl = cIsPressed && (modifiers & GLFW.GLFW_MOD_CONTROL) > 0;

        if (cIsPressed) {
            copyOpenedUiTemplateCall(client, cIsPressedWithShift, cIsPressedWithControl);
        }
    }

    private void copyOpenedUiTemplateCall(MinecraftClient client, boolean fillWithBlankByDefault, boolean alwaysUseMcItemNameForNonSkullItems) {
        GetOpenedUiHandler.GetOpenedUiRequest request = new GetOpenedUiHandler.GetOpenedUiRequest(
                fillWithBlankByDefault,
                alwaysUseMcItemNameForNonSkullItems
        );
        Optional<GetOpenedUiHandler.GetOpenedUiResponse> response = getOpenedUiHandler.getOpenedUiTemplateCall(request);

        if (response.isEmpty()) {
            return;  // No ui to copy
        }
        String stringToCopy = response.get().templateCall;
        ClipboardHelper.setClipboard(stringToCopy);

        Text tick = Text.literal("(✔)");
        Text cross = Text.literal("(✘)");
        MutableText fwbbdOptionTips = Text.literal("(◕‿◕)").setStyle(Style.EMPTY.withHoverEvent(
                new HoverEvent.ShowText(Text.literal("This mode is activated by Shift+Keybind."))));
        MutableText auminfnsiOptionTips = Text.literal("(◕‿◕)").setStyle(Style.EMPTY.withHoverEvent(
                new HoverEvent.ShowText(Text.literal("This mode is activated by Control+Keybind."))));
        MutableText outputText = Text.literal("Copied UI").append("\n")
                .append("├ ").append(fillWithBlankByDefault ? tick : cross).append(" ")
                    .append("fill with blank by default").append(" ").append(fwbbdOptionTips).append("\n")
                .append("└ ").append(alwaysUseMcItemNameForNonSkullItems ? tick : cross).append(" ")
                    .append("always use Minecraft item name for non skull items").append(" ").append(auminfnsiOptionTips);
        client.getMessageHandler().onGameMessage(outputText, false);
    }
}
