package org.hsw.wikitools.feature.copy_skull_id;

import org.hsw.wikitools.feature.copy_skull_id.app.FindFacingEntitySkull;
import org.hsw.wikitools.feature.copy_skull_id.app.Skull;

import java.util.Optional;

public class FacingEntitySkullFinderStub implements FindFacingEntitySkull {
    Optional<Skull> skull;

    public FacingEntitySkullFinderStub(Optional<Skull> skull) {
        this.skull = skull;
    }

    @Override
    public Optional<Skull> findFacingSkull() {
        return skull;
    }
}
