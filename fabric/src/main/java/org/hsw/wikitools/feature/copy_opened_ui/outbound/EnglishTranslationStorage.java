package org.hsw.wikitools.feature.copy_opened_ui.outbound;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.resource.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

class EnglishTranslationStorage {
    private static @Nullable TranslationStorage translationStorage = null;

    public static @NotNull TranslationStorage get() {
        if (EnglishTranslationStorage.translationStorage != null) {
            return EnglishTranslationStorage.translationStorage;
        }

        TranslationStorage translationStorage = createEnglishTranslationStorage();
        EnglishTranslationStorage.translationStorage = translationStorage;
        return translationStorage;
    }

    private static TranslationStorage createEnglishTranslationStorage() {
        ResourceManager resourceManager = MinecraftClient.getInstance().getResourceManager();

        final LanguageDefinition ENGLISH_US = new LanguageDefinition("US", "English", false);

        List<String> list = Collections.singletonList("en_us");

        boolean bl = ENGLISH_US.rightToLeft();

        return TranslationStorage.load(resourceManager, list, bl);
    }

}
