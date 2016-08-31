package levina.web.service.commands.request;

import levina.web.model.Client;
import levina.web.model.Request;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;
import levina.web.service.logic.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by MY on 17.08.2016.
 */
public class GetRequestCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String page;
        Request book;
        Long clientId = null;
        Long requestId = null;

        RequestService requestService = new RequestService();
        ClientService clientService = new ClientService();
        boolean role = (boolean) request.getSession().getAttribute("role");

        if (role) {
            if (request.getParameter("book").equals("create_book")) {
                if (!request.getParameter("clientId").equals("")) {
                    clientId = Long.parseLong(request.getParameter("clientId"));
                }
                session.setAttribute("requestId", "");
                session.setAttribute("clientId", clientId);
            }
            if (request.getParameter("book").equals("approve_book")) {
                if (!request.getParameter("requestId").equals("")) {
                    requestId = Long.parseLong(request.getParameter("requestId"));
                    book = requestService.getById(requestId);
                    clientId = book.getClientID();
                    request.setAttribute("request", book);
                }
                session.setAttribute("clientId", clientId);
                session.setAttribute("requestId", requestId);
            }
            page = "jsp/admin/admin-booking.jsp";
        } else {
            Long userId = (Long)request.getSession().getAttribute("userID");
            Client client = clientService.getByUserId(userId);
            if(client != null) {
                request.setAttribute("clientId", client.getId());
                if (client.isBan()) {
                    request.setAttribute("ban", true);
                }
            }
            page = "jsp/client/booking-edit-form.jsp";
        }
        return page;
    }
}
