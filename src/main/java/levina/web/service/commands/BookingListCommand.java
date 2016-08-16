package levina.web.service.commands;

import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.RequestService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by MY on 16.08.2016.
 */
public class BookingListCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        RequestService requestService = new RequestService();

        request.setAttribute("requests", requestService.getAll());
        page = "client-home-prot.jsp";
        return page;
    }
}
