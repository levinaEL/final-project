package levina.web.service;

import levina.web.service.commands.EmptyCommand;
import levina.web.service.commands.enums.CommandEnum;
import levina.web.service.commands.interfaces.ActionCommand;
import org.apache.log4j.Logger;

/**
 * Created by MY on 11.08.2016.
 */
public class ActionFactory {
    public static Logger logger = Logger.getLogger(ActionFactory.class);

    public ActionCommand defineCommand(String action) {
        ActionCommand current = new EmptyCommand();
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument exception in getting command ", e);
        }
        return current;
    }

}
