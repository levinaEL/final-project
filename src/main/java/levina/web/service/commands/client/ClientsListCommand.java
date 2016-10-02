package levina.web.service.commands.client;

import levina.web.constants.IServiceConstants;
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

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManager.getProperty("path.page.clients-list");
        int noPage = 1;
        Collection<Client> clients = null;
        ClientService clientService = new ClientService();
        RequestService requestService = new RequestService();
        if(request.getSession().getAttribute("sort")==null) {
            request.getSession().setAttribute("sort", false);
        }

        if (request.getParameter("page") != null) {
            noPage = Integer.parseInt(request.getParameter("page"));
        }

        if("Sort By Name".equals(request.getParameter("sort")) || (boolean)request.getSession().getAttribute("sort")){
            clients = clientService.getSortedAll((noPage - 1) * IServiceConstants.RECORDS_PER_PAGE,
                    IServiceConstants.RECORDS_PER_PAGE);
            request.getSession().setAttribute("sort", true);

        }
        if("Revert".equals(request.getParameter("sort")) || !(boolean)request.getSession().getAttribute("sort")){
            clients = clientService.getAll((noPage - 1) * IServiceConstants.RECORDS_PER_PAGE,
                    IServiceConstants.RECORDS_PER_PAGE);
            request.getSession().setAttribute("sort", false);

        }
        int noOfRecords = clientService.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / IServiceConstants.RECORDS_PER_PAGE);

        Map<Long, Integer> clientCountMap = requestService.countClientRequest((noPage - 1) * IServiceConstants.RECORDS_PER_PAGE,
                IServiceConstants.RECORDS_PER_PAGE);
        Set<Long> clientsForSale = ClientsUtils.clientsNeededSale(clientCountMap);

        request.setAttribute("clients", clients);
        request.setAttribute("clientCountMap", clientCountMap);
        request.getServletContext().setAttribute("clientsForSale", clientsForSale);

        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", noPage);

        return page;
    }
}
