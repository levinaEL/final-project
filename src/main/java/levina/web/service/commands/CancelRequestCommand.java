package levina.web.service.commands;

import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by MY on 17.08.2016.
 */
public class CancelRequestCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        RequestService requestService = new RequestService();
        Long id = Long.parseLong(request.getParameter("id"));
        requestService.delete(id);
        page = "controller?command=booking_list";
        return page;
    }
}
