package levina.web.service.commands.request;

import levina.web.model.Request;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.RequestService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by MY on 17.08.2016.
 */
public class GetRequestCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        Long clientId = null;
        Long requestId = null;
        boolean role = (boolean) request.getSession().getAttribute("role");
        RequestService requestService = new RequestService();
        ServletContext servletContext = request.getServletContext();
        if (request.getParameter("book").equals("create_book")) {
            if (!request.getParameter("clientId").equals("")) {
                clientId = Long.parseLong(request.getParameter("clientId"));
            }

            servletContext.setAttribute("clientId", clientId);
        }
        if(request.getParameter("book").equals("approve_book")){
            if (!request.getParameter("requestId").equals("")) {
                requestId = Long.parseLong(request.getParameter("requestId"));
                Request book = requestService.getById(requestId);
                servletContext.setAttribute("request", book);
            }
            servletContext.setAttribute("requestId", requestId);
        }

        if (role) {
            page = "admin-booking.jsp";
        } else {
            page = "booking-edit-form.jsp";
        }

        return page;
    }
}
