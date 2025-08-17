package org.hsw.wikitools.feature.copy_data_tags;

import org.hsw.wikitools.feature.copy_data_tags.app.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GetDataTagsHandlerTest {
    private static @NotNull GetDataTagsHandler getDataTagsHandler(Optional<ItemDataTags> itemDataTags, Optional<EntityDataTags> entityDataTags) {
        FindHoveredItemDataTags findHoveredItemDataTags = new HoveredItemDataTagsFinderStub(itemDataTags);
        FindFacingEntityDataTags findFacingEntityDataTags = new FacingEntityDataTagsFinderStub(entityDataTags);

        return new GetDataTagsHandler(findHoveredItemDataTags, findFacingEntityDataTags);
    }

    @Nested
    class shouldReturnEmpty {
        @Test
        public void whenCannotFindAny() {
            GetDataTagsHandler classUnderTest = getDataTagsHandler(Optional.empty(), Optional.empty());

            Optional<GetDataTagsHandler.GetDataTagsResponse> response = classUnderTest.getDataTags(new GetDataTagsHandler.GetDataTagsRequest());
            assertFalse(response.isPresent());
        }
    }

    @Nested
    class shouldReturnHoveredItemDataTags {
        @Test
        public void whenFoundItemDataTags_withoutFindingOthers() {
            Optional<ItemDataTags> itemDataTags = Optional.of(new ItemDataTags("{id:\"minecraft:nether_star\"}"));
            GetDataTagsHandler classUnderTest = getDataTagsHandler(itemDataTags, Optional.empty());

            Optional<GetDataTagsHandler.GetDataTagsResponse> response = classUnderTest.getDataTags(new GetDataTagsHandler.GetDataTagsRequest());

            assertTrue(response.isPresent());

            assertEquals("{id:\"minecraft:nether_star\"}", response.get().dataTags);
        }

        @Test
        public void whenFoundItemDataTags_withEntityDataTags() {
            Optional<ItemDataTags> itemDataTags = Optional.of(new ItemDataTags("{id:\"minecraft:nether_star\"}"));
            Optional<EntityDataTags> entityDataTags = Optional.of(new EntityDataTags("{Air:300s}", Optional.empty()));
            GetDataTagsHandler classUnderTest = getDataTagsHandler(itemDataTags, entityDataTags);

            Optional<GetDataTagsHandler.GetDataTagsResponse> response = classUnderTest.getDataTags(new GetDataTagsHandler.GetDataTagsRequest());

            assertTrue(response.isPresent());

            assertEquals("{id:\"minecraft:nether_star\"}", response.get().dataTags);
        }
    }

    @Nested
    class shouldReturnFacingEntityDataTags {
        @Test
        public void whenFoundEntityDataTags_withoutFindingOthers() {
            String gamePropertyString = "textures=[Property[name=textures, value=ewogIC, signature=yGPTZD]]";
            Optional<String> gameProperty = Optional.of(gamePropertyString);
            Optional<EntityDataTags> entityDataTags = Optional.of(new EntityDataTags("{Air:300s}", gameProperty));
            GetDataTagsHandler classUnderTest = getDataTagsHandler(Optional.empty(), entityDataTags);

            Optional<GetDataTagsHandler.GetDataTagsResponse> response = classUnderTest.getDataTags(new GetDataTagsHandler.GetDataTagsRequest());

            assertTrue(response.isPresent());

            assertEquals("{Air:300s,__gameProfile:" + gamePropertyString + "}", response.get().dataTags);
        }
    }
}
