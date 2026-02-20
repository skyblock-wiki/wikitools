package org.hsw.wikitools.feature.copy_skull_id;

import org.hsw.wikitools.feature.copy_skull_id.app.FindHoveredSkullItem;
import org.hsw.wikitools.feature.copy_skull_id.app.Skull;

import java.util.Optional;

public class HoveredSkullItemFinderStub implements FindHoveredSkullItem {
    public int callCount = 0;
    Optional<Skull> skull;

    public HoveredSkullItemFinderStub(Optional<Skull> skull) {
        this.skull = skull;
    }

    @Override
    public Optional<Skull> findHoveredSkull() {
        callCount += 1;
        return skull;
    }
}
