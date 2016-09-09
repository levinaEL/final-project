package levina.web.service.commands.client;

import levina.web.contants.IClientConstants;
import levina.web.contants.IUserConstants;
import levina.web.model.Client;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;
import levina.web.utils.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by MY on 11.08.2016.
 */
public class CreateClientCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        HttpSession session = request.getSession();
        ClientService clientService = new ClientService();

        Long userID = (Long) session.getAttribute(IUserConstants.USER_ID);
        boolean role = (boolean) session.getAttribute(IUserConstants.ROLE);

        Client client = null;
        Long id = null;

        if (!request.getParameter(IClientConstants.CLIENT_ID).equals("")) {
            id = Long.parseLong(request.getParameter(IClientConstants.CLIENT_ID));
            client = clientService.getById(id);
        }

        String firstName = request.getParameter(IClientConstants.FIRST_NAME);
        String patronName = request.getParameter(IClientConstants.PATRONYMIC);
        String lastName = request.getParameter(IClientConstants.LAST_NAME);
        String pSeries = request.getParameter(IClientConstants.PASSPORT_SERIES);
        String email = request.getParameter(IClientConstants.EMAIL);
        String pNumber = request.getParameter(IClientConstants.PASSPORT_NUMBER);
        String personalNumb = request.getParameter(IClientConstants.PERSONAL_NUMBER);
        String address = request.getParameter(IClientConstants.ADDRESS);
        String birthday = request.getParameter(IClientConstants.BIRTHDAY);
        String phone = request.getParameter(IClientConstants.PHONE);
        boolean ban = Boolean.parseBoolean(request.getParameter(IClientConstants.BAN));
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
            page = ConfigurationManager.getProperty("path.action.clients-list");
        } else {
            page = ConfigurationManager.getProperty("path.action.booking-list");
        }

        return page;
    }
}
