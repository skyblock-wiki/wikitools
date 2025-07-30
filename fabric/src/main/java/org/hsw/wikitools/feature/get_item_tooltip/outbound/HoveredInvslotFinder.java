package org.hsw.wikitools.feature.get_item_tooltip.outbound;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.hsw.wikitools.feature.get_item_tooltip.app.FindHoveredInvslot;
import org.hsw.wikitools.feature.get_item_tooltip.app.Invslot;
import org.hsw.wikitools.mixin.HandledScreenAccessor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HoveredInvslotFinder implements FindHoveredInvslot {

    @Override
    public Optional<Invslot> findHoveredInvslot() {
        // Implementation to find the hovered inventory slot in Minecraft
        // This method should interact with Minecraft's API to retrieve the hovered item
        // and return it as an Invslot object.

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

        Invslot focusInvslot = getInvslotFromItemStack(focusedItemStack);

        return Optional.of(focusInvslot);
    }

    private @NotNull Invslot getInvslotFromItemStack(ItemStack itemStack) {
        Text name = itemStack.getCustomName();

        if (name == null) {
            name = itemStack.getName();
        }

        String itemName = toFormattedText(name);

        ComponentMap components = itemStack.getComponents();

        LoreComponent loreComponent = components.get(DataComponentTypes.LORE);

        List<Text> loreTexts = new ArrayList<>();
        if (loreComponent != null) {
            loreTexts = loreComponent.styledLines();
        }

        List<String> loreLines = loreTexts.stream().map(HoveredInvslotFinder::toFormattedText).toList();

        return new Invslot(itemName, loreLines);
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
