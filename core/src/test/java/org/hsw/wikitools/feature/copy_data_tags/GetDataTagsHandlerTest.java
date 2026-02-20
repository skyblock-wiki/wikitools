package org.hsw.wikitools.feature.copy_data_tags;

import org.hsw.wikitools.feature.copy_data_tags.app.EntityDataTags;
import org.hsw.wikitools.feature.copy_data_tags.app.GetDataTagsHandler;
import org.hsw.wikitools.feature.copy_data_tags.app.ItemDataTags;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GetDataTagsHandlerTest {
    @Nested
    class shouldReturnEmpty {
        @Test
        public void whenCannotFindAny() {
            HoveredItemDataTagsFinderStub findHoveredItemDataTags = new HoveredItemDataTagsFinderStub(Optional.empty());
            FacingEntityDataTagsFinderStub findFacingEntityDataTags = new FacingEntityDataTagsFinderStub(Optional.empty());
            GetDataTagsHandler classUnderTest = new GetDataTagsHandler(findHoveredItemDataTags, findFacingEntityDataTags);

            Optional<GetDataTagsHandler.GetDataTagsResponse> response = classUnderTest.getDataTags(new GetDataTagsHandler.GetDataTagsRequest());
            assertFalse(response.isPresent());
        }
    }

    @Nested
    class shouldReturnHoveredItemDataTags {
        @Test
        public void whenFirstFoundItemDataTags() {
            Optional<ItemDataTags> itemDataTags = Optional.of(new ItemDataTags("{id:\"minecraft:nether_star\"}"));
            Optional<EntityDataTags> entityDataTags = Optional.of(new EntityDataTags("{Air:300s}", Optional.empty()));

            HoveredItemDataTagsFinderStub findHoveredItemDataTags = new HoveredItemDataTagsFinderStub(itemDataTags);
            FacingEntityDataTagsFinderStub findFacingEntityDataTags = new FacingEntityDataTagsFinderStub(entityDataTags);
            GetDataTagsHandler classUnderTest = new GetDataTagsHandler(findHoveredItemDataTags, findFacingEntityDataTags);

            Optional<GetDataTagsHandler.GetDataTagsResponse> response = classUnderTest.getDataTags(new GetDataTagsHandler.GetDataTagsRequest());

            assertTrue(response.isPresent());
            assertEquals("{id:\"minecraft:nether_star\"}", response.get().dataTags);

            assertEquals(1, findHoveredItemDataTags.callCount);
            assertEquals(0, findFacingEntityDataTags.callCount);
        }
    }

    @Nested
    class shouldReturnFacingEntityDataTags {
        @Test
        public void whenFirstFoundEntityDataTags() {
            String gamePropertyString = "textures=[Property[name=textures, value=ewogIC, signature=yGPTZD]]";
            Optional<String> gameProperty = Optional.of(gamePropertyString);
            Optional<EntityDataTags> entityDataTags = Optional.of(new EntityDataTags("{Air:300s}", gameProperty));

            HoveredItemDataTagsFinderStub findHoveredItemDataTags = new HoveredItemDataTagsFinderStub(Optional.empty());
            FacingEntityDataTagsFinderStub findFacingEntityDataTags = new FacingEntityDataTagsFinderStub(entityDataTags);
            GetDataTagsHandler classUnderTest = new GetDataTagsHandler(findHoveredItemDataTags, findFacingEntityDataTags);

            Optional<GetDataTagsHandler.GetDataTagsResponse> response = classUnderTest.getDataTags(new GetDataTagsHandler.GetDataTagsRequest());

            assertTrue(response.isPresent());
            assertEquals("{Air:300s,__gameProfile:" + gamePropertyString + "}", response.get().dataTags);

            assertEquals(1, findHoveredItemDataTags.callCount);
            assertEquals(1, findFacingEntityDataTags.callCount);
        }
    }
}
