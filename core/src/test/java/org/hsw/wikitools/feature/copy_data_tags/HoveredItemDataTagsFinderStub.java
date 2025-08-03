package org.hsw.wikitools.feature.copy_data_tags;

import org.hsw.wikitools.feature.copy_data_tags.app.FindHoveredItemDataTags;
import org.hsw.wikitools.feature.copy_data_tags.app.ItemDataTags;

import java.util.Optional;

public class HoveredItemDataTagsFinderStub implements FindHoveredItemDataTags {
    private final Optional<ItemDataTags> itemDataTags;

    public HoveredItemDataTagsFinderStub(Optional<ItemDataTags> itemDataTags) {
        this.itemDataTags = itemDataTags;
    }

    @Override
    public Optional<ItemDataTags> findHoveredItemDataTags() {
        return itemDataTags;
    }
}
