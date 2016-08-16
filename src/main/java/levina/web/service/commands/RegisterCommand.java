package levina.web.service.commands;

import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by MY on 11.08.2016.
 */
public class RegisterCommand implements ActionCommand{
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        UserService userService = new UserService();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if(!userService.createNew(login, password)) {
            request.setAttribute("userAlreadyExist", true);
            page = "registration.jsp";
        }else{
            page = "login.jsp";
        }

        return page;
    }
}
