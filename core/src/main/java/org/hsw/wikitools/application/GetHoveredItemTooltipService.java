package org.hsw.wikitools.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hsw.wikitools.application.port.in_port.GetHoveredItemTooltip;
import org.hsw.wikitools.application.port.out_port.FindHoveredInvslot;
import org.hsw.wikitools.domain.value.Invslot;
import org.hsw.wikitools.domain.value.WikiTooltip;

public class GetHoveredItemTooltipService implements GetHoveredItemTooltip {
    private final FindHoveredInvslot findHoveredInvslot;
    private final WikiStringFormattingService wikiStringFormattingService;

    public GetHoveredItemTooltipService(FindHoveredInvslot findHoveredInvslot) {
        this.findHoveredInvslot = findHoveredInvslot;
        this.wikiStringFormattingService = new WikiStringFormattingService();
    }

    @Override
    public Optional<WikiTooltip> getHoveredItemTooltip(boolean toTemplateFormatting) {
        Optional<Invslot> invslot = findHoveredInvslot.findHoveredInvslot();
        if (!invslot.isPresent()) {
            return Optional.empty();
        }
        WikiTooltip wikiTooltip = getWikiTooltipFromInvslot(invslot.get(), toTemplateFormatting);
        return Optional.of(wikiTooltip);
    }

    private WikiTooltip getWikiTooltipFromInvslot(Invslot invslot, boolean toTemplateFormatting) {
        String name = sanitizeName(invslot.name, toTemplateFormatting);
        String title = sanitizeTitle(invslot.name, toTemplateFormatting);
        String loreString = loreToString(invslot.lore, toTemplateFormatting);

        return new WikiTooltip(name, title, loreString);
    }

    private String sanitizeName(String name, boolean toTemplateFormatting) {
        boolean removeFormattingCode = true;
        return toTemplateFormatting ?
            wikiStringFormattingService.formatTextForTemplate(name, removeFormattingCode) :
            wikiStringFormattingService.formatTextForModule(name, removeFormattingCode);
    }

    private String sanitizeTitle(String name, boolean toTemplateFormatting) {
        boolean removeFormattingCode = false;
        return toTemplateFormatting ?
            wikiStringFormattingService.formatTextForTemplate(name, removeFormattingCode) :
            wikiStringFormattingService.formatTextForModule(name, removeFormattingCode);
    }

    private String loreToString(List<String> lore, boolean toTemplateFormatting) {
        List<String> loreList = new ArrayList<>();
        String delimiter = "\\n";
        boolean removeFormattingCode = false;

        for (String line : lore) {
            String formattedLine = toTemplateFormatting ?
                wikiStringFormattingService.formatTextForTemplate(line, removeFormattingCode) :
                wikiStringFormattingService.formatTextForModule(line, removeFormattingCode);
            loreList.add(formattedLine);
        }

        return String.join(delimiter, loreList);
    }
}
