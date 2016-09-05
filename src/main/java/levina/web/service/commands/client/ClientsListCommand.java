package levina.web.service.commands.client;

import levina.web.model.Client;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;
import levina.web.service.logic.RequestService;
import levina.web.utils.ClientsUtils;
import levina.web.utils.ConfigurationManager;

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
        String page = ConfigurationManager.getProperty("path.page.clients-list");
        int noPage = 1;
        ClientService clientService = new ClientService();
        RequestService requestService = new RequestService();

        if (request.getParameter("page") != null) {
            noPage = Integer.parseInt(request.getParameter("page"));
        }

        Collection<Client> clients = clientService.getAll((noPage - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        int noOfRecords = clientService.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);

        Map<Long, Integer> clientCountMap = requestService.countClientRequest((noPage - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        Set<Long> clientsForSale = ClientsUtils.clientsNeededSale(clientCountMap);

        request.setAttribute("clients", clients);
        request.setAttribute("clientCountMap", clientCountMap);
        request.getServletContext().setAttribute("clientsForSale", clientsForSale);

        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", noPage);

        return page;
    }
}
