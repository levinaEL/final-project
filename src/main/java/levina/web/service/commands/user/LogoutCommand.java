package levina.web.service.commands.user;

import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by MY on 13.08.2016.
 */
public class LogoutCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManager.getProperty("path.page.index");
        HttpSession session = request.getSession(false);
        session.invalidate();
        return page;
    }
}
