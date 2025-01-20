package com.bbl.ekychelper.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigReader {
	

	private static final Properties properties = new Properties();
    private static final String CONFIG_FILE_PATH = "config/limoClient.cfg";
	
    static {
        try (FileInputStream input = new FileInputStream(Paths.get(CONFIG_FILE_PATH).toFile())) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file from " + CONFIG_FILE_PATH, e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
