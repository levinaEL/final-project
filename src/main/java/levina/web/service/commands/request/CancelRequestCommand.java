package levina.web.service.commands.request;

import levina.web.constants.IRequestConstants;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.RequestService;
import levina.web.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelRequestCommand implements ActionCommand {
    /**
     * Update request, set field status is cancel
     * @param request  {HttpServletRequest}
     * @param response {HttpServletResponse}
     * @return String target page after execution
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = ConfigurationManager.getProperty("path.action.booking-list");
        RequestService requestService = new RequestService();
        Long id = Long.parseLong(request.getParameter(IRequestConstants.REQUEST_ID));
        requestService.cancel(id);

        return page;
    }
}
