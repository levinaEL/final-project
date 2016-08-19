package levina.web.service.commands.client;

import levina.web.model.Client;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by MY on 11.08.2016.
 */
public class CreateClientCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page ;
        ClientService clientService = new ClientService();

        HttpSession session = request.getSession();
        Long userID = (Long) session.getAttribute("userID");

        Client client = null;
        Long id = null;
        boolean role = (boolean) session.getAttribute("role");

        if (!request.getParameter("id").equals("")) {
            id = Long.parseLong(request.getParameter("id"));
            client = clientService.getById(id);
        }

        String firstName = request.getParameter("fname");
        String patronName = request.getParameter("pname");
        String lastName = request.getParameter("lname");
        String pSeries = request.getParameter("pSeries");
        String email = request.getParameter("email");
        String pNumber = request.getParameter("pNumber");
        String personalNumb = request.getParameter("prslNumber");
        String address = request.getParameter("address");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        boolean ban = Boolean.parseBoolean(request.getParameter("ban"));
        if (client != null) {
            client.setId(id);
            if (role) {
                client.setUserID(null);
            } else {
                client.setUserID(userID);
            }
            client.setFirstName(firstName);
            client.setPatronymicName(patronName);
            client.setLastName(lastName);
            client.setPassportSeries(pSeries);
            client.setEmail(email);
            client.setPassportNumber(Integer.parseInt(pNumber));
            client.setPersonalNumber(personalNumb);
            client.setAddress(address);
            client.setBirthday(birthday);
            client.setPhoneNumber(phone);
            client.setBan(ban);

            clientService.update(client);
        } else {
            if (role) {
                clientService.createNew(null, email, firstName, patronName, lastName, address, phone, pSeries, Integer.parseInt(pNumber), personalNumb,
                        birthday);
            } else {
                clientService.createNew(userID, email, firstName, patronName, lastName, address, phone, pSeries, Integer.parseInt(pNumber), personalNumb,
                        birthday);
            }
        }
        if (role) {
            page = "controller?command=clients_list";
        } else {
            page = "controller?command=booking_list";
        }

        return page;
    }
}
