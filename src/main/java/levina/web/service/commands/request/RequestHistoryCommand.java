package levina.web.service.commands.request;

import levina.web.constants.IServiceConstants;
import levina.web.model.Request;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.RequestService;
import levina.web.utils.ClientsUtils;
import levina.web.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * Created by MY on 20.08.2016.
 */
public class RequestHistoryCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int noPage = 1;
        String page = ConfigurationManager.getProperty("path.page.booking-history");

        RequestService requestService = new RequestService();

        if(request.getParameter("page") != null) {
            noPage = Integer.parseInt(request.getParameter("page"));
        }
        Collection<Request> requests = requestService.getAll((noPage - 1)* IServiceConstants.RECORDS_PER_PAGE,
                IServiceConstants.RECORDS_PER_PAGE);
        Collection names = ClientsUtils.getClientsName(requests);

        int noOfRecords = requestService.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / IServiceConstants.RECORDS_PER_PAGE);

        request.setAttribute("clientsName", names);
        request.setAttribute("requests", requests);
        request.setAttribute("cost", ClientsUtils.getClientsCost(requests));
        request.setAttribute("recordsPerPage", IServiceConstants.RECORDS_PER_PAGE);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", noPage);

        return page;
    }
}
