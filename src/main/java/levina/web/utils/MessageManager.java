package levina.web.utils;

import java.util.ResourceBundle;

/**
 * MessageManager is service class for extracting information from properties files
 */
public class MessageManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    private MessageManager() { }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
