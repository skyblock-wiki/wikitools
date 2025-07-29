package org.hsw.wikitools.feature.get_item_tooltip.app;

import java.util.ArrayList;
import java.util.List;

public class InventoryContentFormattingService {

    public InventorySlotTemplateCall inventorySlotTemplateCallOf(Invslot invslot) {
        boolean toTemplateFormatting = true;
        String name = sanitizeName(invslot.name, toTemplateFormatting);
        String title = sanitizeTitle(invslot.name, toTemplateFormatting);
        String loreString = loreToString(invslot.lore, toTemplateFormatting);

        return new InventorySlotTemplateCall(name, title, loreString);
    }

    public TooltipModuleDataItem tooltipModuleDataItemOf(Invslot invslot) {
        boolean toTemplateFormatting = false;
        String name = sanitizeName(invslot.name, toTemplateFormatting);
        String title = sanitizeTitle(invslot.name, toTemplateFormatting);
        String loreString = loreToString(invslot.lore, toTemplateFormatting);

        return new TooltipModuleDataItem(name, title, loreString);
    }

    private String sanitizeName(String name, boolean toTemplateFormatting) {
        boolean removeFormattingCode = true;
        return toTemplateFormatting ?
                formatTextForTemplate(name, removeFormattingCode) :
                formatTextForModule(name, removeFormattingCode);
    }

    private String sanitizeTitle(String name, boolean toTemplateFormatting) {
        boolean removeFormattingCode = false;
        return toTemplateFormatting ?
                formatTextForTemplate(name, removeFormattingCode) :
                formatTextForModule(name, removeFormattingCode);
    }

    private String loreToString(List<String> lore, boolean toTemplateFormatting) {
        List<String> loreList = new ArrayList<>();
        String delimiter = "\\n";
        boolean removeFormattingCode = false;

        for (String line : lore) {
            String formattedLine = toTemplateFormatting ?
                    formatTextForTemplate(line, removeFormattingCode) :
                    formatTextForModule(line, removeFormattingCode);
            loreList.add(formattedLine);
        }

        return String.join(delimiter, loreList);
    }

    private String formatTextForTemplate(String text, boolean removeFormattingCode) {
        // Common handlers

        text = text.replaceAll("\\\\", "\\\\\\\\") // Replace \ with \\
                .replaceAll("&", "\\\\&") // Replace & with \&
                .replaceAll("/", "\\\\/"); // Replace / with \/

        text = formatFormattingCode(text, removeFormattingCode);

        // Extra handlers when used in a template context, e.g. {{UI}}

        text = text.replaceAll(",", "\\\\,") // Replace , with \,
                .replaceAll("\\|", "{{!}}") // Replace | with {{!}}
                .replaceAll("=", "{{=}}"); // Replace = with {{=}}

        return text;
    }

    private String formatTextForModule(String text, boolean removeFormattingCode) {
        // Common handlers

        text = text.replaceAll("\\\\", "\\\\\\\\") // Replace \ with \\
                .replaceAll("&", "\\\\&") // Replace & with \&
                .replaceAll("/", "\\\\/"); // Replace / with \/

        text = formatFormattingCode(text, removeFormattingCode);

        // Extra handlers when used in module context, e.g. tooltip string

        // Backslash handler: The backslash is an escape character in lua
        // So we need to escape all backslashes
        text = text.replaceAll("\\\\", "\\\\\\\\"); // Replace \ with \\
        // Then, handle single quotation marks
        text = text.replaceAll("'", "\\\\'"); // Replace ' with \'

        return text;
    }

    private String formatFormattingCode(String text, boolean removeFormattingCode) {
        if (removeFormattingCode) {
            // Remove color formatting
            text = text.replaceAll("§.", "");
        }
        else {
            // Replace all colour codes (§) with &
            text = text.replaceAll("§", "&");
        }
        return text;
    }

}
