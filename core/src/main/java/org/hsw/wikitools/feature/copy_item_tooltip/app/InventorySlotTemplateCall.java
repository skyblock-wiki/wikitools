package org.hsw.wikitools.feature.copy_item_tooltip.app;

import org.hsw.wikitools.common.InventoryItemFormattingService;

class InventorySlotTemplateCall {
    public final String tooltip;

    private InventorySlotTemplateCall(String name, String title, String text) {
        this.tooltip = toTemplateString(name, title, text);
    }

    public static InventorySlotTemplateCall of(Invslot invslot) {
        boolean toTemplateFormatting = true;

        String name = InventoryItemFormattingService.formatName(invslot.name, toTemplateFormatting);
        String title = InventoryItemFormattingService.formatTitle(invslot.name, toTemplateFormatting);
        String loreString = InventoryItemFormattingService.formatLore(invslot.lore, toTemplateFormatting);

        return new InventorySlotTemplateCall(name, title, loreString);
    }

    private String toTemplateString(String name, String title, String text) {
        StringBuilder sb = new StringBuilder();
        sb.append("{{Slot")
            .append("|").append(name)
            .append("|title=").append(title);
        if (text != null && !text.isEmpty()) {
            sb.append("|text=").append(text);
        }
        sb.append("}}");
        return sb.toString();
    }

}
