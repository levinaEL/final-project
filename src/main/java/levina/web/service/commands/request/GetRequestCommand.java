package levina.web.service.commands.request;

import levina.web.constants.IRequestConstants;
import levina.web.constants.IUserConstants;
import levina.web.model.Client;
import levina.web.model.Request;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;
import levina.web.service.logic.RequestService;
import levina.web.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by MY on 17.08.2016.
 */
public class GetRequestCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        Request book;
        Long requestId = null;

        RequestService requestService = new RequestService();
        ClientService clientService = new ClientService();
        boolean role = (boolean) request.getSession().getAttribute(IUserConstants.ROLE);

        if (role) {
            if (!request.getParameter(IRequestConstants.REQUEST_ID).equals("")) {
                requestId = Long.parseLong(request.getParameter(IRequestConstants.REQUEST_ID));
            }
            book = requestService.getById(requestId);
            request.setAttribute("request", book);
            request.setAttribute("requestId", requestId);
            page = ConfigurationManager.getProperty("path.page.admin-booking-form");
        } else {
            Long userId = (Long) request.getSession().getAttribute(IUserConstants.USER_ID);
            Client client = clientService.getByUserId(userId);
            if (client != null) {
                request.setAttribute("clientId", client.getId());
                if (client.isBan()) {
                    request.setAttribute("ban", true);
                }
            }
            page = ConfigurationManager.getProperty("path.page.client-booking-form");
        }
        return page;
    }
}
