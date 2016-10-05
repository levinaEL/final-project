package levina.web.service.logic;

import levina.web.dao.ClientDao;
import levina.web.dao.impl.InMemoryClientDao;
import levina.web.model.Client;
import org.apache.log4j.Logger;

import java.util.Collection;

/**
 * ClientService connect dao level with logic
 */
public class ClientService {
    public static Logger logger = Logger.getLogger(ClientService.class);

    private ClientDao clientDao;

    public ClientService() {
        try {
            clientDao = InMemoryClientDao.getInstance();
        } catch (Exception e) {
            logger.error("Exception in getting clientDao instance", e);
        }
    }

    public int getNoOfRecords() {
        return clientDao.getNoOfRecords();
    }

    /**
     *
     * @param offset      - use for pagination, number of start record on the page
     * @param noOfRecords - use for pagination, number of records per page
     * @return Collection
     */
    public Collection<Client> getAll(int offset, int noOfRecords) {
        return clientDao.getAll(offset, noOfRecords);
    }
    /**
     *List of client sorted by name
     * @param offset      - use for pagination, number of start record on the page
     * @param noOfRecords - use for pagination, number of records per page
     * @return Collection
     */
    public Collection<Client> getSortedAll(int offset, int noOfRecords) {
        return clientDao.getSortedAll(offset, noOfRecords);
    }

    /**
     * Create new client
     * @param client
     */
    public void createNew(Client client) {
        if (clientDao.getClientByEmail(client.getEmail()) == null) {
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


    public void banClient(Client client) {
        clientDao.banClient(client);
    }

}
