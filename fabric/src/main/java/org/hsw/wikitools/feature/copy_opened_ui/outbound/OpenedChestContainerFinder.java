package org.hsw.wikitools.feature.copy_opened_ui.outbound;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.hsw.wikitools.feature.copy_opened_ui.app.ChestContainer;
import org.hsw.wikitools.feature.copy_opened_ui.app.FindOpenedChestContainer;
import org.hsw.wikitools.feature.copy_opened_ui.app.Invslot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OpenedChestContainerFinder implements FindOpenedChestContainer {

    @Override
    public Optional<ChestContainer> findCurrentChestContainer() {
        Screen currentScreen = MinecraftClient.getInstance().currentScreen;

        if (currentScreen == null) {
            return Optional.empty();  // Cannot find screen
        }

        if (!(currentScreen instanceof GenericContainerScreen genericContainerScreen)) {
            return Optional.empty();  // Not a container screen
        }

        ChestContainer chestContainer = getChestContainerFromContainerScreen(genericContainerScreen);

        return Optional.of(chestContainer);
    }

    private @NotNull ChestContainer getChestContainerFromContainerScreen(GenericContainerScreen genericContainerScreen) {
        GenericContainerScreenHandler screenHandler = genericContainerScreen.getScreenHandler();
        Inventory inventory = screenHandler.getInventory();

        String containerName = genericContainerScreen.getTitle().getString();
        ChestContainer chestContainer = new ChestContainer(containerName, screenHandler.getRows());
        chestContainer.populateGrid(cellPosition -> {
            ItemStack itemStack = inventory.getStack(cellPosition.i);

            if (itemStack == ItemStack.EMPTY) {
                return Optional.empty();  // Empty item stack
            }

            return Optional.of(getInvslotFromItemStack(itemStack));
        });
        return chestContainer;
    }

    private @NotNull Invslot getInvslotFromItemStack(ItemStack itemStack) {
        String displayedName = toFormattedText(itemStack.getName());

        String minecraftItemNameInEnglish = EnglishTranslationStorage.get()
                .get(itemStack.getItem().getTranslationKey());

        ComponentMap components = itemStack.getComponents();

        LoreComponent loreComponent = components.get(DataComponentTypes.LORE);

        List<Text> loreTexts = new ArrayList<>();
        if (loreComponent != null) {
            loreTexts = loreComponent.styledLines();
        }

        List<String> loreLines = loreTexts.stream().map(OpenedChestContainerFinder::toFormattedText).toList();

        ProfileComponent profileComponent = components.get(DataComponentTypes.PROFILE);
        boolean isCustomSkull = profileComponent != null;

        return new Invslot(
                displayedName,
                minecraftItemNameInEnglish,
                loreLines,
                itemStack.getCount(),
                isCustomSkull,
                itemStack.hasGlint()
        );
    }

    private static @NotNull String toFormattedText(Text text) {
        StringBuilder sb = new StringBuilder();

        toFormattedText(text, sb);

        return sb.toString();
    }

    private static void toFormattedText(Text text, StringBuilder sb) {
        // Assumption: Only leaf nodes contain literal text

        Style style = text.getStyle();
        String string = text.getString();
        boolean hasSiblings = !text.getSiblings().isEmpty();

        toFormattedStyle(style, sb);

        if (!hasSiblings) {
            // It is a leaf node
            sb.append(string);
        }

        for (Text siblingText : text.getSiblings()) {
            toFormattedText(siblingText, sb);
        }
    }

    private static void toFormattedStyle(Style style, StringBuilder sb) {
        // Assumption: All colors are identified by color names (not rgb values)

        TextColor color = style.getColor();
        if (color != null) {
            String colorName = style.getColor().getName();
            Formatting formatting = Formatting.byName(colorName);
            if (formatting != null) {
                sb.append(formatting);
            }
        }

        if (style.isObfuscated()) {
            sb.append(Formatting.OBFUSCATED);
        }

        if (style.isBold()) {
            sb.append(Formatting.BOLD);
        }

        if (style.isStrikethrough()) {
            sb.append(Formatting.STRIKETHROUGH);
        }

        if (style.isUnderlined()) {
            sb.append(Formatting.UNDERLINE);
        }

        if (style.isItalic()) {
            sb.append(Formatting.ITALIC);
        }
    }

}
