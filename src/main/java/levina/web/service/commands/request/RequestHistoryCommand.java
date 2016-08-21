package levina.web.service.commands.request;

import levina.web.model.Client;
import levina.web.model.Request;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;
import levina.web.service.logic.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by MY on 20.08.2016.
 */
public class RequestHistoryCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page ;
        RequestService requestService = new RequestService();
        request.setAttribute("clientsName", getClientsName());
        request.setAttribute("requests", requestService.getAll());
        page = "history-requests.jsp";
        return page;
    }

    private Collection<String> getClientsName(){
        Collection<String> namesCollection = new ArrayList<>();
        RequestService requestService = new RequestService();
        ClientService clientService = new ClientService();
        Collection<Request> requests = requestService.getAll();
        for(Request request : requests){
            Long id = request.getClientID();
            Client client = clientService.getById(id);
            namesCollection.add(client.getLastName());
        }

        return namesCollection;
    }
}
