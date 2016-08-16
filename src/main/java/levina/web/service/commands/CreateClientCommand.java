package levina.web.service.commands;

import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by MY on 11.08.2016.
 */
public class CreateClientCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page =  null;
        ClientService clientService = new ClientService();
        HttpSession session = request.getSession();
        Long userID = (Long)session.getAttribute("userID");
        String firstName = request.getParameter("fname");
        String patronName = request.getParameter("pname");
        String lastName = request.getParameter("lname");
        String pSeries = request.getParameter("pSeries");
        String email = request.getParameter("email");
        int pNumber = Integer.parseInt(request.getParameter("pNumber"));
        String personalNumb = request.getParameter("prslNumber");
        String address = request.getParameter("address");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");



        return page;
    }
}
