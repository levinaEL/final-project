package levina.web.service.commands.request;

import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by MY on 19.08.2016.
 */
public class ApproveRequestCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page ;
        RequestService requestService = new RequestService();
        Long requestId = (Long)request.getServletContext().getAttribute("requestId");
        Long roomId = Long.parseLong(request.getParameter("roomId"));

        request.setAttribute("Room", roomId);
        if(request.getServletContext().getAttribute("roomNotFound") != null) {
            requestService.approve(requestId, roomId);
        }else{
            requestService.delete(requestId);
        }
        page = "controller?command=booking_list";
        return page;
    }
}
