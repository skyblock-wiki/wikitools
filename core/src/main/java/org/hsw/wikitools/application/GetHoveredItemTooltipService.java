package org.hsw.wikitools.application;

import java.util.Optional;

import org.hsw.wikitools.application.port.in_port.GetHoveredItemTooltip;
import org.hsw.wikitools.application.port.out_port.FindHoveredInvslot;
import org.hsw.wikitools.domain.value.Invslot;
import org.hsw.wikitools.domain.value.TooltipModuleDataItem;
import org.hsw.wikitools.domain.value.InventorySlotTemplateCall;

public class GetHoveredItemTooltipService implements GetHoveredItemTooltip {
    private final FindHoveredInvslot findHoveredInvslot;
    private final InventoryContentFormattingService inventoryContentFormattingService;

    public GetHoveredItemTooltipService(FindHoveredInvslot findHoveredInvslot) {
        this.findHoveredInvslot = findHoveredInvslot;
        this.inventoryContentFormattingService = new InventoryContentFormattingService();
    }

    @Override
    public Optional<InventorySlotTemplateCall> getHoveredItemAsTemplateCall() {
        Optional<Invslot> invslot = findHoveredInvslot.findHoveredInvslot();
        if (!invslot.isPresent()) {
            return Optional.empty();
        }
        InventorySlotTemplateCall inventorySlotTemplateCall =
                inventoryContentFormattingService.inventorySlotTemplateCallOf(invslot.get());
        return Optional.of(inventorySlotTemplateCall);
    }

    @Override
    public Optional<TooltipModuleDataItem> getHoveredItemAsModuleData() {
        Optional<Invslot> invslot = findHoveredInvslot.findHoveredInvslot();
        if (!invslot.isPresent()) {
            return Optional.empty();
        }
        TooltipModuleDataItem tooltipModuleDataItem =
                inventoryContentFormattingService.tooltipModuleDataItemOf(invslot.get());
        return Optional.of(tooltipModuleDataItem);
    }

}
