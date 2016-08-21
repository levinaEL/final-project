package levina.web.service.commands.client;

import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;
import levina.web.service.logic.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by MY on 12.08.2016.
 */

public class ClientsListCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        ClientService clientService = new ClientService();
        RequestService requestService = new RequestService();
        Map<Long, Integer> clientCountMap = requestService.countClientRequest();
        request.setAttribute("clients", clientService.getAll());
        request.setAttribute("clientCountMap", clientCountMap);
        page = "users-list.jsp";
        return page;
    }
}
