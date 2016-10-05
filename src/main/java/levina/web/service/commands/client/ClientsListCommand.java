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


public class ClientsListCommand implements ActionCommand {

    private static final String SORT_BY_NAME = "Sort By Name";
    private static final String SORT_PARAM = "sort";
    private static final String REVERT = "Revert";

    /**
     * Forms lists of clients including sorted list by name
     * @param request  {HttpServletRequest}
     * @param response {HttpServletResponse}
     * @return String - target page after execution
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManager.getProperty("path.page.clients-list");
        int noPage = 1;
        Collection<Client> clients = null;
        ClientService clientService = new ClientService();
        RequestService requestService = new RequestService();

        if(request.getSession().getAttribute(SORT_PARAM) == null) {
            request.getSession().setAttribute(SORT_PARAM, false);
        }

        if (request.getParameter("page") != null) {
            noPage = Integer.parseInt(request.getParameter("page"));
        }

        if(SORT_BY_NAME.equals(request.getParameter(SORT_PARAM)) || (boolean)request.getSession().getAttribute("sort")){
            clients = clientService.getSortedAll((noPage - 1) * IServiceConstants.RECORDS_PER_PAGE,
                    IServiceConstants.RECORDS_PER_PAGE);
            request.getSession().setAttribute(SORT_PARAM, true);

        }
        if(REVERT.equals(request.getParameter(SORT_PARAM)) || !(boolean)request.getSession().getAttribute("sort")){
            clients = clientService.getAll((noPage - 1) * IServiceConstants.RECORDS_PER_PAGE,
                    IServiceConstants.RECORDS_PER_PAGE);
            request.getSession().setAttribute(SORT_PARAM, false);

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
