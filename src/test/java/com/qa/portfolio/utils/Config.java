package com.qa.portfolio.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

/**
 * Reads settings from config.properties. A value passed on the command line
 * (for example -Dbrowser=firefox) always wins over the file.
 */
public final class Config {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                PROPS.load(in);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read config.properties", e);
        }
    }

    private Config() {
    }

    public static String get(String key) {
        return System.getProperty(key, PROPS.getProperty(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
