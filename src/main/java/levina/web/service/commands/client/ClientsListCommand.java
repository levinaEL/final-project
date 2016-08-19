package levina.web.service.commands.client;

import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by MY on 12.08.2016.
 */

public class ClientsListCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        ClientService clientService = new ClientService();

        request.setAttribute("clients", clientService.getAll());
        page = "users-list.jsp";
        return page;
    }
}
