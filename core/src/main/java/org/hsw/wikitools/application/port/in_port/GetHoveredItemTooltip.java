package org.hsw.wikitools.application.port.in_port;

import org.hsw.wikitools.domain.value.TooltipModuleDataItem;
import org.hsw.wikitools.domain.value.InventorySlotTemplateCall;

import java.util.Optional;

public interface GetHoveredItemTooltip {
    Optional<InventorySlotTemplateCall> getHoveredItemAsTemplateCall();

    Optional<TooltipModuleDataItem> getHoveredItemAsModuleData();
}
