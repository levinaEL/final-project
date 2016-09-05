package levina.web.utils;

import java.util.ResourceBundle;

/**
 * Created by MY on 04.09.2016.
 */
public class MessageManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

    private MessageManager() { }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
