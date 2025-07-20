package org.hsw.wikitools;

import org.hsw.wikitools.adapter.data_access.HoveredInvslotFinder;
import org.hsw.wikitools.adapter.presentation.CopyHoveredItemTooltipListener;
import org.hsw.wikitools.application.HoveredItemTooltipAccessor;

/**
 * Manages the lifecycle and instantiation of listeners.
 */
class ListenerManager {
    private final CopyHoveredItemTooltipListener copyHoveredItemTooltipListener;

	public ListenerManager() {
		copyHoveredItemTooltipListener = createCopyHoveredItemTooltipListener();
	}

	private CopyHoveredItemTooltipListener createCopyHoveredItemTooltipListener() {
		HoveredInvslotFinder findHoveredInvslot = new HoveredInvslotFinder();
		HoveredItemTooltipAccessor hoveredItemTooltipAccessor = new HoveredItemTooltipAccessor(findHoveredInvslot);
		return new CopyHoveredItemTooltipListener(hoveredItemTooltipAccessor);
	}

}
