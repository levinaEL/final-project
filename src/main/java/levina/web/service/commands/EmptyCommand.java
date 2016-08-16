package levina.web.service.commands;

import levina.web.service.commands.interfaces.ActionCommand;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by MY on 11.08.2016.
 */
public class EmptyCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {

        String page = "login.jsp";
        return page;
    }
}
