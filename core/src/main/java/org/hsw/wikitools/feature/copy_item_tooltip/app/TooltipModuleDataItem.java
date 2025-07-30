package org.hsw.wikitools.feature.copy_item_tooltip.app;

public class TooltipModuleDataItem {
    public final String string;

    public TooltipModuleDataItem(String name, String title, String text) {
        this.string = toModuleString(name, title, text);
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
