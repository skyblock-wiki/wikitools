package org.hsw.wikitools.common;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Clipboard;

public class ClipboardHelper {
    public static void setClipboard(String text) {
        long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
        Clipboard clipboard = new Clipboard();
        clipboard.setClipboard(windowHandle, text);
    }

    public static String getClipboard() {
        long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();
        Clipboard clipboard = new Clipboard();

        return clipboard.getClipboard(windowHandle, (ret, args) -> {});
    }
}
