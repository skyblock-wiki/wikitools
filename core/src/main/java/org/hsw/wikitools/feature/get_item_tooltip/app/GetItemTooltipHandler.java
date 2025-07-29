package org.hsw.wikitools.feature.get_item_tooltip.app;

import java.util.Optional;

public class GetItemTooltipHandler {
    private final FindHoveredInvslot findHoveredInvslot;
    private final InventoryContentFormattingService inventoryContentFormattingService;

    public GetItemTooltipHandler(FindHoveredInvslot findHoveredInvslot) {
        this.findHoveredInvslot = findHoveredInvslot;
        this.inventoryContentFormattingService = new InventoryContentFormattingService();
    }

    public Optional<InventorySlotTemplateCall> getInventorySlotTemplateCall() {
        Optional<Invslot> invslot = findHoveredInvslot.findHoveredInvslot();
        if (!invslot.isPresent()) {
            return Optional.empty();
        }
        InventorySlotTemplateCall inventorySlotTemplateCall =
                inventoryContentFormattingService.inventorySlotTemplateCallOf(invslot.get());
        return Optional.of(inventorySlotTemplateCall);
    }

    public Optional<TooltipModuleDataItem> getTooltipModuleDataItem() {
        Optional<Invslot> invslot = findHoveredInvslot.findHoveredInvslot();
        if (!invslot.isPresent()) {
            return Optional.empty();
        }
        TooltipModuleDataItem tooltipModuleDataItem =
                inventoryContentFormattingService.tooltipModuleDataItemOf(invslot.get());
        return Optional.of(tooltipModuleDataItem);
    }

}
