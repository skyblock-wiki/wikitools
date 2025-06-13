package org.hsw.wikitools;

import org.hsw.wikitools.application.port.out_port.FindHoveredInvslot;
import org.hsw.wikitools.domain.value.Invslot;

import java.util.Optional;

class FindHoveredInvslotMock implements FindHoveredInvslot {
    private final Optional<Invslot> invslot;

    public FindHoveredInvslotMock(Optional<Invslot> invslot) {
        this.invslot = invslot;
    }

    @Override
    public Optional<Invslot> findHoveredInvslot() {
        return invslot;
    }
}
