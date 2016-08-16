package levina.web.service.logic;

import levina.web.dao.ClientDao;
import levina.web.dao.impl.InMemoryClientDao;
import levina.web.model.Client;

import java.util.Collection;

/**
 * Created by MY on 08.08.2016.
 */
public class ClientService {
    private ClientDao clientDao;

    public ClientService() {
        clientDao = InMemoryClientDao.instance;
    }

    public Collection<Client> getAll() {
        return clientDao.getAll();
    }

    public void createNew(Long userID, String email, String firstName, String patronymicName, String lastName, String address,
                             String phoneNumber, String passportSeries, int passportNumber, String personalNumber,
                             String birthday) {
        Client client = new Client();
        client.setUserID(userID);
        client.setEmail(email);
        client.setFirstName(firstName);
        client.setPatronymicName(patronymicName);
        client.setLastName(lastName);
        client.setAddress(address);
        client.setPhoneNumber(phoneNumber);
        client.setPassportSeries(passportSeries);
        client.setPassportNumber(passportNumber);
        client.setPersonalNumber(personalNumber);
        client.setBirthday(birthday);

        if (clientDao.getClientByEmail(email) == null) {
            clientDao.save(client);
        }
    }

    public Client getByUserId(Long id) {
        return clientDao.getByUserId(id);
    }


    public Client getById(Long id) {
        return clientDao.getById(id);
    }

    public void update(Client client) {
        clientDao.update(client);
    }


    public void banClient(Client client){
       clientDao.banClient(client);
    }

    public Client getClientByEmail(String email) {
        return clientDao.getClientByEmail(email);
    }
}
