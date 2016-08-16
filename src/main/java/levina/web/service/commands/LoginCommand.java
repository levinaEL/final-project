package levina.web.service.commands;

import levina.web.model.User;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by MY on 11.08.2016.
 */
public class LoginCommand implements ActionCommand {
    private final boolean ADMIN = true;
    private final boolean USER = false;

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        UserService userService = new UserService();
        String login = request.getParameter("u_login");
        String password = request.getParameter("u_password");
        User user = userService.getUserByLogin(login);
        boolean role = USER;
        Long id;
        if (user == null) {
            request.setAttribute("userNotFound", true);
            page = "login.jsp";
        } else {
            role = user.isAdmin();
            id = user.getId();
            if (userService.checkPassword(login, password)) {
                request.getSession().setAttribute("userID", id);
                request.getSession().setAttribute("role", role);
                if (role) {
                    page = "admin-home-prot.jsp";
                } else {
                    page = "client-home-prot.jsp";
                }
            } else {
                request.setAttribute("passwordIsNotCorrect", true);
                page = "login.jsp";
            }
        }

        return page;
    }
}
