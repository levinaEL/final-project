package levina.web.service.commands.request;

import levina.web.contants.IUserConstants;
import levina.web.model.Client;
import levina.web.model.Request;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;
import levina.web.service.logic.RequestService;
import levina.web.utils.ClientsUtils;
import levina.web.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * Created by MY on 16.08.2016.
 */
public class BookingListCommand implements ActionCommand {
    public static final int RECORDS_PER_PAGE = 5;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        int noPage = 1;
        int noOfRecords;
        int noOfPages;

        boolean role = (boolean) request.getSession().getAttribute("role");
        RequestService requestService = new RequestService();
        ClientService clientService = new ClientService();
        Collection<Request> requests = null;

        if (request.getParameter("page") != null) {
            noPage = Integer.parseInt(request.getParameter("page"));
        }

        if (role) {
            requests = requestService.getAdminRequests((noPage - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
            request.setAttribute("clientsName", ClientsUtils.getClientsName(requests));

            noOfRecords = requestService.getNoOfRecords();
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);

            page = ConfigurationManager.getProperty("path.page.admin-home");
        } else {
            Long userID = (Long) request.getSession().getAttribute(IUserConstants.USER_ID);

            Client client = clientService.getByUserId(userID);
            if (client != null) {
                Long clientId = client.getId();
                requests = requestService.getAllClientsRequests(clientId, (noPage - 1) * RECORDS_PER_PAGE,
                        RECORDS_PER_PAGE);
                request.setAttribute("clientId", clientId);
                request.setAttribute("cost", ClientsUtils.getClientsCost(requests));
            }

            noOfRecords = requestService.getNoOfRecords();
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
            //page = request.getServletPath();

            page = ConfigurationManager.getProperty("path.page.client-home");
        }

        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", noPage);
        request.setAttribute("recordsPerPage", RECORDS_PER_PAGE);
        request.setAttribute("requests", requests);
        return page;
    }


}
