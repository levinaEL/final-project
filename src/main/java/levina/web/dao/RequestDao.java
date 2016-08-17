package levina.web.dao;

import levina.web.model.Request;

import java.util.Collection;

/**
 * Created by MY on 15.08.2016.
 */
public interface RequestDao {
    Request getById(Long id);

    Request getByClientId(Long id);

    void save(Request request);

    void cancel(Long id);

    Collection<Request> getAll();

    Collection<Request> getAllClientsRequests(Long id);

    void updateBookingStatus(Long id);
}
