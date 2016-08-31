package levina.web.service.commands.client;

import levina.web.model.Client;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;
import levina.web.service.logic.RequestService;
import levina.web.service.utils.ClientsUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by MY on 12.08.2016.
 */

public class ClientsListCommand implements ActionCommand {
    public static final int RECORDS_PER_PAGE = 5;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int page = 1;
        ClientService clientService = new ClientService();
        RequestService requestService = new RequestService();

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        Collection<Client> clients = clientService.getAll((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        int noOfRecords = clientService.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);

        Map<Long, Integer> clientCountMap = requestService.countClientRequest((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        Set<Long> clientsForSale = ClientsUtils.clientsNeededSale(clientCountMap);

        request.setAttribute("clients", clients);
        request.setAttribute("clientCountMap", clientCountMap);
        request.getServletContext().setAttribute("clientsForSale", clientsForSale);

        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);

        return "jsp/admin/users-list.jsp";
    }
}
