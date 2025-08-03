package org.hsw.wikitools.feature.copy_data_tags.app;

import java.util.Optional;

public class GetDataTagsHandler {

    private final FindHoveredItemDataTags findHoveredItemDataTags;
    private final FindFacingEntityDataTags findFacingEntityDataTags;

    public GetDataTagsHandler(FindHoveredItemDataTags findHoveredItemDataTags, FindFacingEntityDataTags findFacingEntityDataTags) {
        this.findHoveredItemDataTags = findHoveredItemDataTags;
        this.findFacingEntityDataTags = findFacingEntityDataTags;
    }

    public Optional<GetDataTagsResponse> getDataTags(GetDataTagsRequest getDataTagsRequest) {
        Optional<ItemDataTags> itemDataTags = findHoveredItemDataTags.findHoveredItemDataTags();
        Optional<EntityDataTags> entityDataTags = findFacingEntityDataTags.findFacingEntityDataTags();

        if (itemDataTags.isPresent()) {
            return Optional.of(new GetDataTagsResponse(itemDataTags.get().serialized));
        }

        if (entityDataTags.isPresent()) {
            return Optional.of(new GetDataTagsResponse(entityDataTags.get().serialized));
        }

        return Optional.empty();
    }

    public static class GetDataTagsRequest {}

    public static class GetDataTagsResponse {
        public String dataTags;

        public GetDataTagsResponse(String dataTags) {
            this.dataTags = dataTags;
        }
    }
}
