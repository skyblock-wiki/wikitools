package org.hsw.wikitools.feature.view_item_id.app;

import java.util.Optional;

public class GetItemIdHandler {
    private FindHoveredItemId findHoveredItemId;

    public GetItemIdHandler(FindHoveredItemId findHoveredItemId) {
        this.findHoveredItemId = findHoveredItemId;
    }

    public Optional<GetItemIdResponse> getItemId(GetItemIdRequest request) {
        Optional<String> itemId = findHoveredItemId.findHoveredItemId();

        return itemId.map(GetItemIdResponse::new);
    }

    public static class GetItemIdRequest {}

    public static class GetItemIdResponse {
        public final String itemId;

        public GetItemIdResponse(String itemId) {
            this.itemId = itemId;
        }
    }
}
