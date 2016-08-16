package levina.web.service.logic;

import levina.web.dao.RequestDao;
import levina.web.dao.impl.InMemoryRequestDao;
import levina.web.model.Request;
import levina.web.model.enums.StatusRequest;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by MY on 14.08.2016.
 */
public class RequestService {
    private RequestDao requestDao;

    public RequestService() {
        requestDao = InMemoryRequestDao.instance;
    }

    public Collection<Request> getAll() {
        return requestDao.getAll();
    }

    public void createNew(Long clientID, Long roomID, Date startDate, Date endDate,
                          int personsCount, StatusRequest status) {
        Request request = new Request();
        Timestamp reqDate = new Timestamp(Calendar.getInstance().getTime().getTime());
        request.setClientID(clientID);
        request.setRoomID(roomID);
        request.setRequestDate(reqDate);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setPersonsCount(personsCount);
        request.setStatusRequest(status);

        requestDao.save(request);

    }
}
