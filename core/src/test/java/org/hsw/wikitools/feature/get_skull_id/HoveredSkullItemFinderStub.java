package org.hsw.wikitools.feature.get_skull_id;

import org.hsw.wikitools.feature.get_skull_id.app.FindHoveredSkullItem;
import org.hsw.wikitools.feature.get_skull_id.app.Skull;

import java.util.Optional;

public class HoveredSkullItemFinderStub implements FindHoveredSkullItem {
    Optional<Skull> skull;

    public HoveredSkullItemFinderStub(Optional<Skull> skull) {
        this.skull = skull;
    }

    @Override
    public Optional<Skull> getHoveredSkull() {
        return skull;
    }
}
