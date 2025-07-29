package org.hsw.wikitools;

import org.hsw.wikitools.feature.get_item_tooltip.outbound.HoveredInvslotFinder;
import org.hsw.wikitools.feature.get_item_tooltip.inbound.CopyHoveredItemTooltipListener;
import org.hsw.wikitools.feature.get_item_tooltip.app.GetItemTooltipHandler;
import org.hsw.wikitools.feature.get_skull_id.app.GetSkullIdHandler;
import org.hsw.wikitools.feature.get_skull_id.inbound.CopySkullIdListener;
import org.hsw.wikitools.feature.get_skull_id.outbound.FacingBlockSkullFinder;
import org.hsw.wikitools.feature.get_skull_id.outbound.FacingEntitySkullFinder;
import org.hsw.wikitools.feature.get_skull_id.outbound.HoveredSkullItemFinder;

/**
 * Manages the lifecycle and instantiation of listeners.
 */
class ListenerManager {
    private final CopyHoveredItemTooltipListener copyHoveredItemTooltipListener;
	private final CopySkullIdListener copySkullIdListener;

	public ListenerManager() {
		copyHoveredItemTooltipListener = createCopyHoveredItemTooltipListener();
		copySkullIdListener = createCopySkullIdListener();
	}

	private CopyHoveredItemTooltipListener createCopyHoveredItemTooltipListener() {
		HoveredInvslotFinder hoveredInvslotFinder = new HoveredInvslotFinder();
		GetItemTooltipHandler getItemTooltipHandler = new GetItemTooltipHandler(hoveredInvslotFinder);
		return new CopyHoveredItemTooltipListener(getItemTooltipHandler);
	}

	private CopySkullIdListener createCopySkullIdListener() {
		HoveredSkullItemFinder hoveredSkullItemFinder = new HoveredSkullItemFinder();
		FacingEntitySkullFinder facingEntitySkullFinder = new FacingEntitySkullFinder();
		FacingBlockSkullFinder facingBlockSkullFinder = new FacingBlockSkullFinder();
		GetSkullIdHandler getSkullIdHandler = new GetSkullIdHandler(hoveredSkullItemFinder, facingEntitySkullFinder, facingBlockSkullFinder);
		return new CopySkullIdListener(getSkullIdHandler);
	}

}
