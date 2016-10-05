package levina.web.service.commands.user;

import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements ActionCommand {
    /**
     * invalidate session, after finishing seance
     * @param request  {HttpServletRequest}
     * @param response {HttpServletResponse}
     * @return String - target page after execution
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManager.getProperty("path.page.index");
        HttpSession session = request.getSession(false);
        session.invalidate();
        return page;
    }
}
