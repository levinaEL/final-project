package levina.web.utils;

import java.util.ResourceBundle;

/**
 *ConfigurationManager is service class for extracting information from properties files
 */
public class ConfigurationManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

    private ConfigurationManager() { }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
