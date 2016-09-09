package levina.web.service.commands.request;

import levina.web.contants.IRequestConstants;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.RequestService;
import levina.web.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by MY on 17.08.2016.
 */
public class CancelRequestCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManager.getProperty("path.action.booking-list");
        RequestService requestService = new RequestService();
        Long id = Long.parseLong(request.getParameter(IRequestConstants.REQUEST_ID));
        requestService.delete(id);

        return page;
    }
}
