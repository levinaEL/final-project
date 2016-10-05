package levina.web.service.commands.request;

import levina.web.constants.IServiceConstants;
import levina.web.constants.IUserConstants;
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

public class BookingListCommand implements ActionCommand {

    /**
     * Forms list of requests for client and for admin
     * @param request  {HttpServletRequest}
     * @param response {HttpServletResponse}
     * @return String - target page after execution
     */
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
            requests = requestService.getAdminRequests((noPage - 1) * IServiceConstants.RECORDS_PER_PAGE,
                    IServiceConstants.RECORDS_PER_PAGE); // params for pagination
            request.setAttribute("clientsName", ClientsUtils.getClientsName(requests));

            noOfRecords = requestService.getNoOfRecords();
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / IServiceConstants.RECORDS_PER_PAGE);

            page = ConfigurationManager.getProperty("path.page.admin-home");
        } else {
            Long userID = (Long) request.getSession().getAttribute(IUserConstants.USER_ID);

            Client client = clientService.getByUserId(userID);
            if (client != null) {
                Long clientId = client.getId();
                requests = requestService.getAllClientsRequests(clientId,
                        (noPage - 1) * IServiceConstants.RECORDS_PER_PAGE, IServiceConstants.RECORDS_PER_PAGE);
                request.setAttribute("clientId", clientId);
                request.setAttribute("cost", ClientsUtils.getClientsCost(requests));
            }

            noOfRecords = requestService.getNoOfRecords();
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / IServiceConstants.RECORDS_PER_PAGE);

            page = ConfigurationManager.getProperty("path.page.client-home");
        }

        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", noPage);
        request.setAttribute("recordsPerPage", IServiceConstants.RECORDS_PER_PAGE);
        request.setAttribute("requests", requests);
        return page;
    }


}
