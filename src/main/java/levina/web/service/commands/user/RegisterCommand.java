package levina.web.service.commands.user;

import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by MY on 11.08.2016.
 */
public class RegisterCommand implements ActionCommand{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        UserService userService = new UserService();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if(!userService.createNew(login, password)) {
            request.setAttribute("userAlreadyExist", true);
            page = "jsp/common/registration.jsp";
        }else{
            page = "jsp/common/login.jsp";
        }

        return page;
    }
}
