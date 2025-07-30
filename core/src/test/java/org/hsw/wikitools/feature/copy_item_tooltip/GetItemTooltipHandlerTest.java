package org.hsw.wikitools.feature.copy_item_tooltip;

import org.hsw.wikitools.feature.copy_item_tooltip.app.GetItemTooltipHandler;
import org.hsw.wikitools.feature.copy_item_tooltip.app.FindHoveredInvslot;
import org.hsw.wikitools.feature.copy_item_tooltip.app.InventorySlotTemplateCall;
import org.hsw.wikitools.feature.copy_item_tooltip.app.Invslot;
import org.hsw.wikitools.feature.copy_item_tooltip.app.TooltipModuleDataItem;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GetItemTooltipHandlerTest {
    private void runTestExpectEmpty(Optional<Invslot> invslot) {
        FindHoveredInvslot invslotMock = new HoveredInvslotFinderStub(invslot);
        GetItemTooltipHandler classUnderTest = new GetItemTooltipHandler(invslotMock);

        Optional<InventorySlotTemplateCall> inventorySlotTemplateCall = classUnderTest.getInventorySlotTemplateCall();
        assertFalse(inventorySlotTemplateCall.isPresent());

        Optional<TooltipModuleDataItem> tooltipModuleDataItem = classUnderTest.getTooltipModuleDataItem();
        assertFalse(tooltipModuleDataItem.isPresent());
    }

    private void runTest(Optional<Invslot> invslot, String expectedTemplateString, String expectedModuleString) {
        FindHoveredInvslot invslotMock = new HoveredInvslotFinderStub(invslot);
        GetItemTooltipHandler classUnderTest = new GetItemTooltipHandler(invslotMock);

        Optional<InventorySlotTemplateCall> inventorySlotTemplateCall = classUnderTest.getInventorySlotTemplateCall();
        assertTrue(inventorySlotTemplateCall.isPresent());
        String templateString = inventorySlotTemplateCall.get().string;
        assertEquals(expectedTemplateString, templateString);

        Optional<TooltipModuleDataItem> tooltipModuleDataItem = classUnderTest.getTooltipModuleDataItem();
        assertTrue(tooltipModuleDataItem.isPresent());
        String moduleString = tooltipModuleDataItem.get().string;
        assertEquals(expectedModuleString, moduleString);
    }

    @Test
    void shouldReturnEmpty() {
        Optional<Invslot> invslot = Optional.empty();
        runTestExpectEmpty(invslot);
    }

    @Test
    void shouldReturnTooltipWithEmptyLore() {
        Optional<Invslot> invslot = Optional.of(new Invslot("Empty Lore Item", Arrays.asList()));
        String expectedTemplateString = "{{Slot|Empty Lore Item|title=Empty Lore Item}}";
        String expectedModuleString = "['Empty Lore Item'] = { name = 'Empty Lore Item', title = 'Empty Lore Item', },";
        runTest(invslot, expectedTemplateString, expectedModuleString);
    }

    @Test
    void shouldReturnMultiLineTooltip() {
        Optional<Invslot> invslot = Optional.of(new Invslot("Test Item", Arrays.asList("Lore line 1", "Lore line 2")));
        String expectedTemplateString = "{{Slot|Test Item|title=Test Item|text=Lore line 1\\nLore line 2}}";
        String expectedModuleString = "['Test Item'] = { name = 'Test Item', title = 'Test Item', text = 'Lore line 1\\nLore line 2', },";
        runTest(invslot, expectedTemplateString, expectedModuleString);
    }

    @Test
    void shouldDealWithFormattingCode() {
        Optional<Invslot> invslot = Optional.of(new Invslot("§5Formatted Item", Arrays.asList("Line with §a green text", "Line with §b blue text")));
        String expectedTemplateString = "{{Slot|Formatted Item|title=&5Formatted Item|text=Line with &a green text\\nLine with &b blue text}}";
        String expectedModuleString = "['Formatted Item'] = { name = 'Formatted Item', title = '&5Formatted Item', text = 'Line with &a green text\\nLine with &b blue text', },";
        runTest(invslot, expectedTemplateString, expectedModuleString);
    }

    /* Test escape common special characters */

    @Test
    void shouldEscapeBackslash() {
        Optional<Invslot> invslot = Optional.of(new Invslot("Special Item", Arrays.asList("Line with \\ backslash")));
        String expectedTemplateString = "{{Slot|Special Item|title=Special Item|text=Line with \\\\ backslash}}";
        String expectedModuleString = "['Special Item'] = { name = 'Special Item', title = 'Special Item', text = 'Line with \\\\\\\\ backslash', },";
        runTest(invslot, expectedTemplateString, expectedModuleString);
    }

    @Test
    void shouldEscapeAmpersand() {
        Optional<Invslot> invslot = Optional.of(new Invslot("Special Item", Arrays.asList("Line with & ampersand")));
        String expectedTemplateString = "{{Slot|Special Item|title=Special Item|text=Line with \\& ampersand}}";
        String expectedModuleString = "['Special Item'] = { name = 'Special Item', title = 'Special Item', text = 'Line with \\\\& ampersand', },";
        runTest(invslot, expectedTemplateString, expectedModuleString);
    }

    @Test
    void shouldEscapeForwardSlash() {
        Optional<Invslot> invslot = Optional.of(new Invslot("Special Item", Arrays.asList("Line with / forward slash")));
        String expectedTemplateString = "{{Slot|Special Item|title=Special Item|text=Line with \\/ forward slash}}";
        String expectedModuleString = "['Special Item'] = { name = 'Special Item', title = 'Special Item', text = 'Line with \\\\/ forward slash', },";
        runTest(invslot, expectedTemplateString, expectedModuleString);
    }

    /* Test escape template special characters */

    @Test
    void shouldEscapePipe() {
        Optional<Invslot> invslot = Optional.of(new Invslot("Special Item", Arrays.asList("Line with | pipe")));
        String expectedTemplateString = "{{Slot|Special Item|title=Special Item|text=Line with {{!}} pipe}}";
        String expectedModuleString = "['Special Item'] = { name = 'Special Item', title = 'Special Item', text = 'Line with | pipe', },";
        runTest(invslot, expectedTemplateString, expectedModuleString);
    }

    @Test
    void shouldEscapeEquals() {
        Optional<Invslot> invslot = Optional.of(new Invslot("Special Item", Arrays.asList("Line with = equals")));
        String expectedTemplateString = "{{Slot|Special Item|title=Special Item|text=Line with {{=}} equals}}";
        String expectedModuleString = "['Special Item'] = { name = 'Special Item', title = 'Special Item', text = 'Line with = equals', },";
        runTest(invslot, expectedTemplateString, expectedModuleString);
    }

    /* Test escape module special characters */

    @Test
    void shouldEscapeSingleQuote() {
        Optional<Invslot> invslot = Optional.of(new Invslot("Special Item", Arrays.asList("Line with ' single quote")));
        String expectedTemplateString = "{{Slot|Special Item|title=Special Item|text=Line with ' single quote}}";
        String expectedModuleString = "['Special Item'] = { name = 'Special Item', title = 'Special Item', text = 'Line with \\' single quote', },";
        runTest(invslot, expectedTemplateString, expectedModuleString);
    }

}
