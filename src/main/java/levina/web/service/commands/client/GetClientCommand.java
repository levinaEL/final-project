package levina.web.service.commands.client;

import levina.web.constants.IClientConstants;
import levina.web.constants.IUserConstants;
import levina.web.model.Client;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;
import levina.web.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by MY on 17.08.2016.
 */
public class GetClientCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManager.getProperty("path.page.personal-info");
        Client client;
        ClientService clientService = new ClientService();
        HttpSession session = request.getSession();

        boolean role = (boolean) session.getAttribute(IUserConstants.ROLE);
        Long userID = (Long) session.getAttribute(IUserConstants.USER_ID);

        if (role && !request.getParameter(IClientConstants.CLIENT_ID).equals("")) {
            Long id = Long.parseLong(request.getParameter(IClientConstants.CLIENT_ID));
            client = clientService.getById(id);
        } else {
            client = clientService.getByUserId(userID);
        }

        if (client == null) {
            client = new Client();
        }

        request.setAttribute("client", client);

        return page;
    }
}
