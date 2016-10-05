package levina.web.service.commands.client;

import levina.web.constants.IClientConstants;
import levina.web.constants.IUserConstants;
import levina.web.model.Client;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.logic.ClientService;
import levina.web.utils.ConfigurationManager;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by MY on 11.08.2016.
 */
public class CreateClientCommand implements ActionCommand {

    /**
     * Parsing request parameters and set to client
     * @param paramMap - Map of the request parameters
     * @return Client
     */
    private Client parseClientFromParam(Map<String, String[]> paramMap) {
        Client client = new Client();

        client.setFirstName(paramMap.get(IClientConstants.FIRST_NAME)[0]);
        client.setPatronymicName(paramMap.get(IClientConstants.PATRONYMIC)[0]);
        client.setLastName(paramMap.get(IClientConstants.LAST_NAME)[0]);
        client.setPassportSeries(paramMap.get(IClientConstants.PASSPORT_SERIES)[0]);
        client.setEmail(paramMap.get(IClientConstants.EMAIL)[0]);
        client.setPassportNumber(Integer.parseInt(paramMap.get(IClientConstants.PASSPORT_NUMBER)[0]));
        client.setPersonalNumber(paramMap.get(IClientConstants.PERSONAL_NUMBER)[0]);
        client.setAddress(paramMap.get(IClientConstants.ADDRESS)[0]);
        client.setBirthday(paramMap.get(IClientConstants.BIRTHDAY)[0]);
        client.setPhoneNumber(paramMap.get(IClientConstants.PHONE)[0]);
        client.setBan(Boolean.parseBoolean(paramMap.get(IClientConstants.BAN)[0]));

        return client;
    }

    /**
     * Create OR Update client
     * @param request  {HttpServletRequest}
     * @param response {HttpServletResponse}
     * @return String - target page after execution
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        HttpSession session = request.getSession();
        ClientService clientService = new ClientService();

        boolean role = (boolean) session.getAttribute(IUserConstants.ROLE);

        Client storedClient = null;
        Client requestClient = parseClientFromParam(request.getParameterMap());

        if (!StringUtils.isEmpty(request.getParameter(IClientConstants.CLIENT_ID))) {
            Long id = Long.parseLong(request.getParameter(IClientConstants.CLIENT_ID));
            storedClient = clientService.getById(id);
            requestClient.setId(id);
            requestClient.setUserID(storedClient.getUserID());
        }

        if (storedClient != null) {
            clientService.update(requestClient);
        }else{
            if(role){
                requestClient.setUserID(null);
            }
            clientService.createNew(requestClient);
        }

        if (role) {
            page = ConfigurationManager.getProperty("path.action.clients-list");
        } else {
            page = ConfigurationManager.getProperty("path.action.booking-list");
        }

        return page;
    }
}
