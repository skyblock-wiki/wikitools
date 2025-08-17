package org.hsw.wikitools.common;

import java.util.ArrayList;
import java.util.List;

public class InventoryItemFormattingService {

    public static String formatName(String name, boolean toTemplateFormatting) {
        boolean removeFormattingCode = true;
        return toTemplateFormatting ?
                formatTextForTemplate(name, removeFormattingCode) :
                formatTextForModule(name, removeFormattingCode);
    }

    public static String formatTitle(String name, boolean toTemplateFormatting) {
        boolean removeFormattingCode = false;
        return toTemplateFormatting ?
                formatTextForTemplate(name, removeFormattingCode) :
                formatTextForModule(name, removeFormattingCode);
    }

    public static String formatLore(List<String> lore, boolean toTemplateFormatting) {
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

    private static String formatTextForTemplate(String text, boolean removeFormattingCode) {
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

    private static String formatTextForModule(String text, boolean removeFormattingCode) {
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

    private static String formatFormattingCode(String text, boolean removeFormattingCode) {
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
