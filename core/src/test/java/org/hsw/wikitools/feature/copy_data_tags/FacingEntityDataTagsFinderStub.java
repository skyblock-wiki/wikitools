package org.hsw.wikitools.feature.copy_data_tags;

import org.hsw.wikitools.feature.copy_data_tags.app.EntityDataTags;
import org.hsw.wikitools.feature.copy_data_tags.app.FindFacingEntityDataTags;

import java.util.Optional;

public class FacingEntityDataTagsFinderStub implements FindFacingEntityDataTags {
    private final Optional<EntityDataTags> entityDataTags;

    public FacingEntityDataTagsFinderStub(Optional<EntityDataTags> entityDataTags) {
        this.entityDataTags = entityDataTags;
    }

    @Override
    public Optional<EntityDataTags> findFacingEntityDataTags() {
        return entityDataTags;
    }
}
