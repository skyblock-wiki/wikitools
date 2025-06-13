package org.hsw.wikitools.domain.value;

public class WikiTooltip {
    public final String name;
    public final String title;
    public final String text;

    public WikiTooltip(String name, String title, String text) {
        this.name = name;
        this.title = title;
        this.text = text;
    }

    public String toTemplateString() {
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

    public String toModuleString() {
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
