package org.hsw.wikitools;

import org.hsw.wikitools.adapter.data_access.FindHoveredItemFromMC;
import org.hsw.wikitools.adapter.presentation.CopyHoveredItemTooltipListener;
import org.hsw.wikitools.application.GetHoveredItemTooltipService;

/**
 * Manages the lifecycle and instantiation of listeners.
 */
class ListenerManager {
    private final CopyHoveredItemTooltipListener copyHoveredItemTooltipListener;

	public ListenerManager() {
		copyHoveredItemTooltipListener = createCopyHoveredItemTooltipListener();
	}

	private CopyHoveredItemTooltipListener createCopyHoveredItemTooltipListener() {
		FindHoveredItemFromMC findHoveredInvslot = new FindHoveredItemFromMC();
		GetHoveredItemTooltipService getHoveredItemTooltip = new GetHoveredItemTooltipService(findHoveredInvslot);
		return new CopyHoveredItemTooltipListener(getHoveredItemTooltip);
	}

}
