package org.hsw.wikitools.domain.value;

import java.util.List;

public class Invslot {
    public final String name;
    public final List<String> lore;

    public Invslot(String name, List<String> lore) {
        this.name = name;
        this.lore = lore;
    }
}
