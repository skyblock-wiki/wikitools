package org.hsw.wikitools.application.out_port;

import org.hsw.wikitools.domain.value.Invslot;

import java.util.Optional;

public interface FindHoveredInvslot {
    Optional<Invslot> findHoveredInvslot();
}
