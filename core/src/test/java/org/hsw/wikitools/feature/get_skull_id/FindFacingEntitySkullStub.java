package org.hsw.wikitools.feature.get_skull_id;

import org.hsw.wikitools.feature.get_skull_id.app.FindFacingEntitySkull;
import org.hsw.wikitools.feature.get_skull_id.app.Skull;

import java.util.Optional;

public class FindFacingEntitySkullStub implements FindFacingEntitySkull {
    Optional<Skull> skull;

    public FindFacingEntitySkullStub(Optional<Skull> skull) {
        this.skull = skull;
    }

    @Override
    public Optional<Skull> findFacingSkull() {
        return skull;
    }
}
