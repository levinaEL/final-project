package levina.web.service.commands;

import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by MY on 11.08.2016.
 */
public class EmptyCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        return ConfigurationManager.getProperty("path.page.login");

    }
}
