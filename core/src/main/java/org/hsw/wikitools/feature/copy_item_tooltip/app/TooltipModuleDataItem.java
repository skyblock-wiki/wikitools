package org.hsw.wikitools.feature.copy_item_tooltip.app;

import org.hsw.wikitools.common.InventoryItemFormattingService;

class TooltipModuleDataItem {
    public final String tooltip;

    private TooltipModuleDataItem(String name, String title, String text) {
        this.tooltip = toModuleString(name, title, text);
    }

    public static TooltipModuleDataItem of(Invslot invslot) {
        boolean toTemplateFormatting = false;

        String name = InventoryItemFormattingService.formatName(invslot.name, toTemplateFormatting);
        String title = InventoryItemFormattingService.formatTitle(invslot.name, toTemplateFormatting);
        String loreString = InventoryItemFormattingService.formatLore(invslot.lore, toTemplateFormatting);

        return new TooltipModuleDataItem(name, title, loreString);
    }

    private String toModuleString(String name, String title, String text) {
        String id = name;
        StringBuilder sb = new StringBuilder();
        sb.append("['").append(id).append("'] = { ")
            .append("name = '").append(name).append("', ")
            .append("title = '").append(title).append("', ");
        if (text != null && !text.isEmpty()) {
            sb.append("text = '").append(text).append("', ");
        }
        sb.append("},");
        return sb.toString();
    }

}
