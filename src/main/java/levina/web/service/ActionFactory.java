package levina.web.service;

import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.commands.EmptyCommand;
import levina.web.service.commands.enums.CommandEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by MY on 11.08.2016.
 */
public class ActionFactory {

    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            //TODO
        }
        return current;
    }

}
