package org.hsw.wikitools.common;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryItemFormattingServiceTest {
    @Nested
    class TemplateFormattingRuleTest {
        private String formatForTemplate(String input) {
            return InventoryItemFormattingService.formatTitle(input, true);
        }

        @Test
        void should_escapeBackslash_withBackslash() {
            String input = "Line with \\ backslash";

            String result = formatForTemplate(input);

            String expected = "Line with \\\\ backslash";

            assertEquals(expected, result);
        }

        @Test
        void should_escapeAmpersand_withBackslash() {
            String input = "Line with & ampersand";

            String result = formatForTemplate(input);

            String expected = "Line with \\& ampersand";

            assertEquals(expected, result);
        }

        @Test
        void should_escapeForwardSlash_withBackslash() {
            String input = "Line with / forward slash";

            String result = formatForTemplate(input);

            String expected = "Line with \\/ forward slash";

            assertEquals(expected, result);
        }

        @Test
        void should_escapeComma_withBackslash() {
            String input = "Line with , comma";

            String result = formatForTemplate(input);

            String expected = "Line with \\, comma";

            assertEquals(expected, result);
        }

        @Test
        void should_escapePipe_asTemplateLiteral() {
            String input = "Line with | pipe";

            String result = formatForTemplate(input);

            String expected = "Line with {{!}} pipe";

            assertEquals(expected, result);
        }

        @Test
        void should_escapeEqualSign_asTemplateLiteral() {
            String input = "Line with = equal sign";

            String result = formatForTemplate(input);

            String expected = "Line with {{=}} equal sign";

            assertEquals(expected, result);
        }
    }

    @Nested
    class ModuleFormattingRuleTest {
        private String formatForModule(String input) {
            return InventoryItemFormattingService.formatTitle(input, false);
        }

        @Test
        void should_escapeBackslash_asFourBackslashes() {
            String input = "Line with \\ backslash";

            String result = formatForModule(input);

            String expected = "Line with \\\\\\\\ backslash";

            assertEquals(expected, result);
        }

        @Test
        void should_escapeAmpersand_withTwoBackslashes() {
            String input = "Line with & ampersand";

            String result = formatForModule(input);

            String expected = "Line with \\\\& ampersand";

            assertEquals(expected, result);
        }

        @Test
        void should_escapeForwardSlash_withTwoBackslashes() {
            String input = "Line with / forward slash";

            String result = formatForModule(input);

            String expected = "Line with \\\\/ forward slash";

            assertEquals(expected, result);
        }

        @Test
        void should_escapeSingleQuotationMark_withBackslash() {
            String input = "Line with ' single quotation mark";

            String result = formatForModule(input);

            String expected = "Line with \\' single quotation mark";

            assertEquals(expected, result);
        }
    }

    @Nested
    class TestsForFormatName {
        @ParameterizedTest
        @CsvSource({
                "§aGreen text,Green text",
                "§a§lBold green text,Bold green text",
                "§aGreen text then §bBlue text,Green text then Blue text"
        })
        void shouldRemoveFormattingCodeAndItsPrefix(String input, String expected) {
            String result = InventoryItemFormattingService.formatName(input, true);

            assertEquals(expected, result);
        }

        @Test
        void shouldFollowTemplateFormattingRule() {
            String input = "Line with \\ backslash";

            String result = InventoryItemFormattingService.formatName(input, true);

            String expected = "Line with \\\\ backslash";

            assertEquals(expected, result);
        }

        @Test
        void shouldFollowModuleFormattingRule() {
            String input = "Line with \\ backslash";

            String result = InventoryItemFormattingService.formatName(input, false);

            String expected = "Line with \\\\\\\\ backslash";

            assertEquals(expected, result);
        }
    }

    @Nested
    class TestsForFormatTitle {
        @ParameterizedTest
        @CsvSource({
                "§aGreen text,&aGreen text",
                "§a§lBold green text,&a&lBold green text",
                "§aGreen text then §bBlue text,&aGreen text then &bBlue text"
        })
        void shouldReplaceFormattingCodePrefixWithAmpersand(String input, String expected) {
            String result = InventoryItemFormattingService.formatTitle(input, true);

            assertEquals(expected, result);
        }

        @Test
        void shouldFollowTemplateFormattingRule() {
            String input = "Line with \\ backslash";

            String result = InventoryItemFormattingService.formatTitle(input, true);

            String expected = "Line with \\\\ backslash";

            assertEquals(expected, result);
        }

        @Test
        void shouldFollowModuleFormattingRule() {
            String input = "Line with \\ backslash";

            String result = InventoryItemFormattingService.formatTitle(input, false);

            String expected = "Line with \\\\\\\\ backslash";

            assertEquals(expected, result);
        }
    }

    @Nested
    class TestsForFormatLore {
        @Test
        void emptyLoreShouldResultInEmptyString() {
            String result = InventoryItemFormattingService.formatLore(Collections.emptyList(), true);

            assertEquals("", result);
        }

        @Test
        void shouldJoinLinesUsingNewlineCharacter() {
            List<String> lore = Arrays.asList(
                    "Line 1",
                    "Line 2",
                    "Line 3"
            );

            String result = InventoryItemFormattingService.formatLore(lore, true);

            String expected = "Line 1\\nLine 2\\nLine 3";

            assertEquals(expected, result);
        }

        @ParameterizedTest
        @CsvSource({
                "§aGreen text,&aGreen text",
                "§a§lBold green text,&a&lBold green text",
                "§aGreen text then §bBlue text,&aGreen text then &bBlue text"
        })
        void shouldReplaceFormattingCodePrefixWithAmpersand(String input, String expected) {
            String result = InventoryItemFormattingService.formatLore(
                    Collections.singletonList(input), true);

            assertEquals(expected, result);
        }

        @Test
        void shouldFollowTemplateFormattingRule() {
            List<String> input = Collections.singletonList("Line with \\ backslash");

            String result = InventoryItemFormattingService.formatLore(input, true);

            String expected = "Line with \\\\ backslash";

            assertEquals(expected, result);
        }

        @Test
        void shouldFollowModuleFormattingRule() {
            List<String> input = Collections.singletonList("Line with \\ backslash");

            String result = InventoryItemFormattingService.formatLore(input, false);

            String expected = "Line with \\\\\\\\ backslash";

            assertEquals(expected, result);
        }
    }
}
