package org.hsw.wikitools;

import org.hsw.wikitools.feature.copy_data_tags.app.GetDataTagsHandler;
import org.hsw.wikitools.feature.copy_data_tags.inbound.CopyDataTagsListener;
import org.hsw.wikitools.feature.copy_data_tags.outbound.FacingEntityDataTagsFinder;
import org.hsw.wikitools.feature.copy_data_tags.outbound.HoveredItemDataTagsFinder;
import org.hsw.wikitools.feature.copy_item_tooltip.outbound.HoveredInvslotFinder;
import org.hsw.wikitools.feature.copy_item_tooltip.inbound.CopyHoveredItemTooltipListener;
import org.hsw.wikitools.feature.copy_item_tooltip.app.GetItemTooltipHandler;
import org.hsw.wikitools.feature.copy_skull_id.app.GetSkullIdHandler;
import org.hsw.wikitools.feature.copy_skull_id.inbound.CopySkullIdListener;
import org.hsw.wikitools.feature.copy_skull_id.outbound.FacingBlockSkullFinder;
import org.hsw.wikitools.feature.copy_skull_id.outbound.FacingEntitySkullFinder;
import org.hsw.wikitools.feature.copy_skull_id.outbound.HoveredSkullItemFinder;

/**
 * Manages the lifecycle and instantiation of listeners.
 */
class ListenerManager {
    private final CopyHoveredItemTooltipListener copyHoveredItemTooltipListener;
	private final CopySkullIdListener copySkullIdListener;
	private final CopyDataTagsListener copyDataTagsListener;

	public ListenerManager() {
		copyHoveredItemTooltipListener = createCopyHoveredItemTooltipListener();
		copySkullIdListener = createCopySkullIdListener();
		copyDataTagsListener = createCopyDataTagsListener();
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

	private CopyDataTagsListener createCopyDataTagsListener() {
		HoveredItemDataTagsFinder hoveredItemDataTagsFinder = new HoveredItemDataTagsFinder();
		FacingEntityDataTagsFinder facingEntityDataTagsFinder = new FacingEntityDataTagsFinder();
		GetDataTagsHandler getDataTagsHandler = new GetDataTagsHandler(hoveredItemDataTagsFinder, facingEntityDataTagsFinder);
		return new CopyDataTagsListener(getDataTagsHandler);
	}

}
