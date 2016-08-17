package levina.web.service.commands;

import levina.web.service.commands.interfaces.ActionCommand;

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
        Long id = null;
        boolean role = (boolean)request.getSession().getAttribute("role");
        if (!request.getParameter("id").equals("")) {
            id = Long.parseLong(request.getParameter("id"));
        }
        ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute("id", id);
        if(role){
            page = "admin-booking.jsp";
        }else{
            page = "booking-edit-form.jsp";
        }

        return page;
    }
}
