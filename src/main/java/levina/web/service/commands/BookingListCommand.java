package levina.web.service.commands;

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
 * Created by MY on 16.08.2016.
 */
public class BookingListCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        boolean role = (boolean)request.getSession().getAttribute("role");
        RequestService requestService = new RequestService();
        ClientService clientService = new ClientService();
        if(role){
            request.setAttribute("clientsName", getClientsName());
            request.setAttribute("requests", requestService.getAll());
            page = "admin-home-prot.jsp";
        }else{
            Long userID = (Long)request.getSession().getAttribute("userID");

            Client client = clientService.getByUserId(userID);
            Long clientID = client.getId();

           // request.setAttribute("requests", requestService.getAll());
            request.setAttribute("requests", requestService.getAllClientsRequests(clientID));
            page = "client-home-prot.jsp";
        }

        return page;
    }

    private Collection getClientsName(){
        Collection namesCollection = new ArrayList<>();
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
