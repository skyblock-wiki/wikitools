package org.hsw.wikitools.feature.copy_item_tooltip.app;

public class InventorySlotTemplateCall {
    public final String string;

    public InventorySlotTemplateCall(String name, String title, String text) {
        this.string = toTemplateString(name, title, text);
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
