package levina.web.service.commands.client;

import levina.web.model.Client;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by MY on 15.08.2016.
 */
public class BanCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = "controller?command=clients_list";
        ClientService clientService = new ClientService();
        Long clientId = Long.parseLong(request.getParameter("id"));
        Client client = clientService.getById(clientId);
        clientService.banClient(client);

        return page;
    }
}
