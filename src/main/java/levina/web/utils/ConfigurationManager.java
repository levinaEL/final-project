package levina.web.utils;

import java.util.ResourceBundle;

/**
 * Created by MY on 02.09.2016.
 */
public class ConfigurationManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

    private ConfigurationManager() { }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
