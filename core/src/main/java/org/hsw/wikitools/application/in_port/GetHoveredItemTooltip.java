package org.hsw.wikitools.application.in_port;

import org.hsw.wikitools.domain.value.TooltipModuleDataItem;
import org.hsw.wikitools.domain.value.InventorySlotTemplateCall;

import java.util.Optional;

public interface GetHoveredItemTooltip {
    Optional<InventorySlotTemplateCall> getInventorySlotTemplateCall();

    Optional<TooltipModuleDataItem> getTooltipModuleDataItem();
}
