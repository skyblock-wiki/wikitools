package org.hsw.wikitools.feature.copy_skull_id;

import org.hsw.wikitools.feature.copy_skull_id.app.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GetSkullIdTest {
    private static @NotNull GetSkullIdHandler getSkullIdHandler(Optional<Skull> hoveredSkullItem, Optional<Skull> facingEntitySkull, Optional<Skull> facingBlockSkull) {
        FindHoveredSkullItem findHoveredSkullItem = new HoveredSkullItemFinderStub(hoveredSkullItem);
        FindFacingEntitySkull findFacingEntitySkull = new FacingEntitySkullFinderStub(facingEntitySkull);
        FindFacingBlockSkull findFacingBlockSkull = new FacingBlockSkullFinderStub(facingBlockSkull);
        return new GetSkullIdHandler(findHoveredSkullItem, findFacingEntitySkull, findFacingBlockSkull);
    }

    private Optional<Skull> mockSkullItem() {
        return Optional.of(Skull.ofTextureValue("ewogICJ0aW1lc3RhbXAiIDogMTYyNDYyMzE3NjM1MywKICAicHJvZmlsZUlkIiA6ICIxYWZhZjc2NWI1ZGY0NjA3YmY3ZjY1ZGYzYWIwODhhOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJMb3lfQmxvb2RBbmdlbCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kODZjMmEyMGYwN2ZkMWM0NzI2Zjg1YzQ5MzY5Y2QzNzNhNDMyY2Y1MGIwODM0ZTU5ODljMmJmMWQxOThmMGU4IgogICAgfQogIH0KfQ"));
    }

    private String mockSkullItemTextureId() {
        return "d86c2a20f07fd1c4726f85c49369cd373a432cf50b0834e5989c2bf1d198f0e8";
    }

    private Optional<Skull> mockEntitySkull() {
        return Optional.of(Skull.ofTextureValue("ewogICJ0aW1lc3RhbXAiIDogMTcyMDA0MjMxNDE2MCwKICAicHJvZmlsZUlkIiA6ICJiNzRlYjViMTc5OTc0YzZjODk3ZTgwNTM4Y2M1NmYwMSIsCiAgInByb2ZpbGVOYW1lIiA6ICJQYW5kYUNoYW4yOCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mZjdiYjk3NDgwYzEwZmRmZWI4ZmFlOTM5MjllNWVlYjRmMDQ5MWQ4Y2NhNzBlZDAwYjQ1ZTNmYjJiNzRmNmM1IgogICAgfQogIH0KfQ"));
    }

    private String mockEntitySkullTextureId() {
        return "ff7bb97480c10fdfeb8fae93929e5eeb4f0491d8cca70ed00b45e3fb2b74f6c5";
    }

    private Optional<Skull> mockBlockSkull() {
        return Optional.of(Skull.ofTextureValue("ewogICJ0aW1lc3RhbXAiIDogMTYxOTg1NTE0OTQ4NCwKICAicHJvZmlsZUlkIiA6ICJjZGM5MzQ0NDAzODM0ZDdkYmRmOWUyMmVjZmM5MzBiZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJSYXdMb2JzdGVycyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83ZmQ1ZjZiNmRiOTNkNTMwMjRmZWYyNzk4ZWI3ZThjYTQxMWEyYzZhZTEzMjA5YWQxM2UyOWZiOTgwZjE3ZjUiCiAgICB9CiAgfQp9"));
    }

    private String mockBlockSkullTextureId() {
        return "7fd5f6b6db93d53024fef2798eb7e8ca411a2c6ae13209ad13e29fb980f17f5";
    }

    @Nested
    class shouldReturnEmpty {
        @Test
        public void whenCannotFindAny() {
            GetSkullIdHandler classUnderTest = getSkullIdHandler(Optional.empty(), Optional.empty(), Optional.empty());

            Optional<GetSkullIdHandler.GetSkullIdResponse> response = classUnderTest.getSkullId(new GetSkullIdHandler.GetSkullIdRequest());
            assertFalse(response.isPresent());
        }
    }

    @Nested
    class shouldReturnHoveredSkullItem {
        @Test
        public void whenFoundSkullItem_withoutFindingOthers() {
            GetSkullIdHandler classUnderTest = getSkullIdHandler(mockSkullItem(), Optional.empty(), Optional.empty());

            Optional<GetSkullIdHandler.GetSkullIdResponse> response = classUnderTest.getSkullId(new GetSkullIdHandler.GetSkullIdRequest());
            assertTrue(response.isPresent());
            String textureId = response.get().textureId;
            assertEquals(mockSkullItemTextureId(), textureId);
        }

        @Test
        public void whenFoundSkullItem_withEntitySkullOrBlockSkull() {
            GetSkullIdHandler classUnderTest = getSkullIdHandler(mockSkullItem(), mockEntitySkull(), mockBlockSkull());

            Optional<GetSkullIdHandler.GetSkullIdResponse> response = classUnderTest.getSkullId(new GetSkullIdHandler.GetSkullIdRequest());
            assertTrue(response.isPresent());
            String textureId = response.get().textureId;
            assertEquals(mockSkullItemTextureId(), textureId);
        }
    }

    @Nested
    class shouldReturnFacingEntitySkull {
        @Test
        public void whenFoundEntitySkull_withoutFindingOthers() {
            GetSkullIdHandler classUnderTest = getSkullIdHandler(Optional.empty(), mockEntitySkull(), Optional.empty());

            Optional<GetSkullIdHandler.GetSkullIdResponse> response = classUnderTest.getSkullId(new GetSkullIdHandler.GetSkullIdRequest());
            assertTrue(response.isPresent());
            String textureId = response.get().textureId;
            assertEquals(mockEntitySkullTextureId(), textureId);
        }

        @Test
        public void whenFoundEntitySkull_withBlockSkull() {
            GetSkullIdHandler classUnderTest = getSkullIdHandler(Optional.empty(), mockEntitySkull(), mockBlockSkull());

            Optional<GetSkullIdHandler.GetSkullIdResponse> response = classUnderTest.getSkullId(new GetSkullIdHandler.GetSkullIdRequest());
            assertTrue(response.isPresent());
            String textureId = response.get().textureId;
            assertEquals(mockEntitySkullTextureId(), textureId);
        }
    }

    @Nested
    class shouldReturnFacingBlockSkull {
        @Test
        public void whenFoundBlockSkull_withoutFindingOthers() {
            GetSkullIdHandler classUnderTest = getSkullIdHandler(Optional.empty(), Optional.empty(), mockBlockSkull());

            Optional<GetSkullIdHandler.GetSkullIdResponse> response = classUnderTest.getSkullId(new GetSkullIdHandler.GetSkullIdRequest());
            assertTrue(response.isPresent());
            String textureId = response.get().textureId;
            assertEquals(mockBlockSkullTextureId(), textureId);
        }
    }
}
