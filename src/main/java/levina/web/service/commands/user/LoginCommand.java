package levina.web.service.commands.user;

import levina.web.model.User;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by MY on 11.08.2016.
 */
public class LoginCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        UserService userService = new UserService();
        String login = request.getParameter("u_login");
        String password = request.getParameter("u_password");
        User user = userService.getUserByLogin(login);
        boolean role;
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

                page = "controller?command=booking_list";

            } else {
                request.setAttribute("passwordIsNotCorrect", true);
                page = "login.jsp";
            }
        }

        return page;
    }
}
