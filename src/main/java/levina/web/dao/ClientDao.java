package levina.web.dao;

import levina.web.model.Client;

import java.util.Collection;

/**
 * Created by MY on 15.08.2016.
 */
public interface ClientDao {

    Client getById(Long id);

    Client getByUserId(Long id);

    void save(Client client);

    void update(Client client) ;

    Client getClientByEmail(String email);

    void banClient(Client client);

    Collection<Client> getAll();
}
