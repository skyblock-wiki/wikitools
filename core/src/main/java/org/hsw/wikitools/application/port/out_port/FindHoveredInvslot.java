package org.hsw.wikitools.application.port.out_port;

import org.hsw.wikitools.domain.value.Invslot;

import java.util.Optional;

public interface FindHoveredInvslot {
    Optional<Invslot> findHoveredInvslot();
}
