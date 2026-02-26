package org.hsw.wikitools;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.hsw.wikitools.common.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiToolsIdentity {
    public static final String MOD_ID = ConfigProperties.getProperty("modId");
    public static final String VERSION = ConfigProperties.getProperty("modVersion");

    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "main"));

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
}
