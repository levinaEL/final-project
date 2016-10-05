package levina.web.service.commands.user;

import levina.web.constants.IUserConstants;
import levina.web.model.User;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.UserService;
import levina.web.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LoginCommand execute login command
 */
public class LoginCommand implements ActionCommand {

    /**
     *
     * @param request  {HttpServletRequest}
     * @param response {HttpServletResponse}
     * @return String - target page after execution
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        HttpSession session = request.getSession(true);
        UserService userService = new UserService();
        String login = request.getParameter(IUserConstants.LOGIN);
        String password = request.getParameter(IUserConstants.PASSWORD);
        User user = userService.getUserByLogin(login);
        boolean role;
        Long id;
        if (user == null) {
            request.setAttribute("userNotFound", true);
            page = ConfigurationManager.getProperty("path.page.login");
        } else {
            role = user.isAdmin();
            id = user.getId();
            if (userService.checkPassword(login, password)) {
                session.setAttribute("user_id", id);
                session.setAttribute("role", role);
               page =  ConfigurationManager.getProperty("path.action.booking-list");

            } else {
                request.setAttribute("passwordIsNotCorrect", true);
                page = ConfigurationManager.getProperty("path.page.login");
            }
        }

        return page;
    }
}
