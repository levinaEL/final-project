package levina.web.dao;

import levina.web.model.Request;

import java.util.Collection;
import java.util.Map;

/**
 * RequestDao defined methods which will be used during connection to DB, table requests
 */
public interface RequestDao {
    Request getById(Long id);

    Request getByClientId(Long id);

    void save(Request request);

    void cancel(Long id);

    void approve(Long id, Long roomId);

    Collection<Request> getAdminRequests(int offset, int noOfRecords);

    Collection<Request> getAllClientsRequests(Long id, int offset, int noOfRecords);

    Collection<Request> getAll(int offset, int noOfRecords);

    int getNoOfRecords();

    Map<Long, Integer> countClientRequest(int offset, int noOfPages);

}
