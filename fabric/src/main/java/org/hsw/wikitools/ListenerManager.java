package org.hsw.wikitools;

import org.hsw.wikitools.feature.copy_data_tags.GetDataTagsHandler;
import org.hsw.wikitools.feature.copy_data_tags.CopyDataTagsListener;
import org.hsw.wikitools.feature.copy_data_tags.FacingEntityDataTagsFinder;
import org.hsw.wikitools.feature.copy_data_tags.HoveredItemDataTagsFinder;
import org.hsw.wikitools.feature.copy_item_tooltip.HoveredInvslotFinder;
import org.hsw.wikitools.feature.copy_item_tooltip.CopyHoveredItemTooltipListener;
import org.hsw.wikitools.feature.copy_item_tooltip.GetItemTooltipHandler;
import org.hsw.wikitools.feature.copy_opened_ui.GetOpenedUiHandler;
import org.hsw.wikitools.feature.copy_opened_ui.CopyOpenedUiListener;
import org.hsw.wikitools.feature.copy_opened_ui.OpenedChestContainerFinder;
import org.hsw.wikitools.feature.copy_skull_id.GetSkullIdHandler;
import org.hsw.wikitools.feature.copy_skull_id.CopySkullIdListener;
import org.hsw.wikitools.feature.copy_skull_id.FacingBlockSkullFinder;
import org.hsw.wikitools.feature.copy_skull_id.FacingEntitySkullFinder;
import org.hsw.wikitools.feature.copy_skull_id.HoveredSkullItemFinder;

/**
 * Manages the lifecycle and instantiation of listeners.
 */
class ListenerManager {
    private final CopyHoveredItemTooltipListener copyHoveredItemTooltipListener;
	private final CopySkullIdListener copySkullIdListener;
	private final CopyDataTagsListener copyDataTagsListener;
	private final CopyOpenedUiListener copyOpenedUiListener;

	public ListenerManager() {
		copyHoveredItemTooltipListener = createCopyHoveredItemTooltipListener();
		copySkullIdListener = createCopySkullIdListener();
		copyDataTagsListener = createCopyDataTagsListener();
		copyOpenedUiListener = createOpenedUiListener();
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

	private CopyOpenedUiListener createOpenedUiListener() {
		OpenedChestContainerFinder openedChestContainerFinder = new OpenedChestContainerFinder();
		GetOpenedUiHandler getOpenedUiHandler = new GetOpenedUiHandler(openedChestContainerFinder);
		return new CopyOpenedUiListener(getOpenedUiHandler);
	}

}
