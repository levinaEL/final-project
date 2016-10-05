package levina.web.dao;

import levina.web.model.Client;

import java.util.Collection;

/**
 * ClientDao defined methods which will be used during connection to DB, table clients
 */
public interface ClientDao {

    Client getById(Long id);

    Client getByUserId(Long id);

    void save(Client client);

    void update(Client client) ;

    Client getClientByEmail(String email);

    void banClient(Client client);

    Collection<Client> getAll(int offset, int noOfRecords);

    Collection<Client> getSortedAll(int offset, int noOfRecords);

    int getNoOfRecords();
}
