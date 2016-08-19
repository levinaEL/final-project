package levina.web.service.commands.client;

import levina.web.model.Client;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by MY on 17.08.2016.
 */
public class GetClientCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        Client client;
        Long id;
        ClientService clientService = new ClientService();
        HttpSession session = request.getSession();
        boolean role = (boolean) session.getAttribute("role");
        Long userID = (Long) session.getAttribute("userID");

        if (role && !request.getParameter("id").equals("")) {
            id = Long.parseLong(request.getParameter("id"));
            client = clientService.getById(id);
        } else {
            client = clientService.getByUserId(userID);
        }

        if (client == null) {
            client = new Client();
        }

        request.setAttribute("client", client);

        page = "personal-info.jsp";
        return page;
    }
}
