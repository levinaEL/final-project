package levina.web.factories;

import levina.web.service.commands.enums.CommandEnum;
import levina.web.service.commands.interfaces.ActionCommand;
import org.apache.log4j.Logger;

/**
 * ActionFactory used for creating instance-commands
 */
public class ActionFactory {
    public static Logger logger = Logger.getLogger(ActionFactory.class);

    /**
     * Define ActionCommand by action parameter
     * @param action - command param from jsp
     * @return ActionCommand
     */
    public ActionCommand defineCommand(String action) {
        ActionCommand current = null;

        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();

        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument exception in getting command ", e);
        }
        return current;
    }

}
