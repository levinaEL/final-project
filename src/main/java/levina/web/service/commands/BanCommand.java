package levina.web.service.commands;

import levina.web.model.Client;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by MY on 15.08.2016.
 */
public class BanCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        ClientService clientService = new ClientService();
        Long id = Long.parseLong(request.getParameter("id"));

        Client client = clientService.getById(id);

        clientService.banClient(client);
        page = "admin-home-prot.jsp";

        return page;
    }
}
