package org.hsw.wikitools.feature.mod_update_checker;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.CommonColors;
import org.hsw.wikitools.WikiToolsIdentity;
import org.hsw.wikitools.common.ConfigProperties;

import java.net.URI;


public class ModUpdateChecker {

    private final GetNewVersionHandler getNewVersionHandler;

    public ModUpdateChecker(GetNewVersionHandler getNewVersionHandler) {
        this.getNewVersionHandler = getNewVersionHandler;

        registerEvent();
    }

    private void registerEvent() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            var isSelfJoin = client.player == Minecraft.getInstance().player;

            if (!isSelfJoin) {
                return;
            }

            handleModUpdateCheck();
        });
    }

    private void handleModUpdateCheck() {
        String currentVersionName = WikiToolsIdentity.VERSION;
        getNewVersionHandler.getNewVersion(
                new GetNewVersionHandler.GetNewVersionRequest(currentVersionName))
                .thenAccept((response) -> {
                    if (!response.success || !response.result.isPresent()) {
                        warnFailure(response.message.orElse("Unknown error"));
                        return;
                    }

                    if (response.result.get().hasNewRelease) {
                        remindUserToUpdateMod(response.result.get().latestVersion);
                    }
                });
    }

    private static void remindUserToUpdateMod(String newVersionName) {
        Minecraft client = Minecraft.getInstance();

        Component frontComponent = Component.translatable("message.wikitools.mod-update-checker.new-update", newVersionName)
                .setStyle(Style.EMPTY.withColor(CommonColors.GREEN));

        String latestReleaseDownloadUrl = ConfigProperties.getProperty("latestReleaseDownloadUrl");
        Style linkStyle = Style.EMPTY
                .withColor(CommonColors.GRAY)
                .withUnderlined(true)
                .withClickEvent(new ClickEvent.OpenUrl(URI.create(latestReleaseDownloadUrl)));

        Component linkComponent = Component.translatable("message.wikitools.mod-update-checker.update-link-text")
                .setStyle(linkStyle);

        Component messageComponent = Component.literal("")
                .append(frontComponent)
                .append(" ")
                .append(linkComponent);

        client.getChatListener().handleSystemMessage(messageComponent, false);
    }

    private static void warnFailure(String problemName) {
        String warningText = Component.translatable("message.wikitools.mod-update-checker.error", problemName).getString();
        WikiToolsIdentity.LOGGER.warn(warningText, false);
    }

}
