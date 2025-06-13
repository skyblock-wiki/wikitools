package org.hsw.wikitools.application;

public class WikiStringFormattingService {

    public String formatTextForTemplate(String text, boolean removeFormattingCode) {
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

    public String formatTextForModule(String text, boolean removeFormattingCode) {
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
