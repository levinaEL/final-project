package levina.web.service.commands;

import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by MY on 12.08.2016.
 */

public class ClientsListCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        ClientService clientService = new ClientService();

        request.setAttribute("clients", clientService.getAll());
        page = "users-list.jsp";
        return page;
    }
}