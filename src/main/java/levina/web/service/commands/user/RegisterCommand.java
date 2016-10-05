package levina.web.service.commands.user;

import levina.web.constants.IUserConstants;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.UserService;
import levina.web.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegisterCommand implements ActionCommand {

    /**
     * Creates a new user, if not exest
     * @param request  {HttpServletRequest}
     * @param response {HttpServletResponse}
     * @return String - target page after execution
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        UserService userService = new UserService();

        String login = request.getParameter(IUserConstants.LOGIN);
        String password = request.getParameter(IUserConstants.PASSWORD);

        if(!userService.createNew(login, password)) {
            request.setAttribute("userAlreadyExist", true);
            page = ConfigurationManager.getProperty("path.page.registration");
        }else{
            page = ConfigurationManager.getProperty("path.page.login");
        }

        return page;
    }
}
