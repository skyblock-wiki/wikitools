package org.hsw.wikitools.common;

import com.mojang.logging.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigProperties.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("config.properties not found!");
            }
            else {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        String property = properties.getProperty(key);
        if (property == null) {
            LogUtils.getLogger().error("Missing property (config.properties): {}", key);
        }
        return property;
    }
}
