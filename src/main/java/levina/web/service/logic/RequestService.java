package levina.web.service.logic;

import levina.web.dao.RequestDao;
import levina.web.dao.impl.InMemoryRequestDao;
import levina.web.model.Request;
import levina.web.model.enums.RoomType;
import levina.web.model.enums.StatusRequest;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by MY on 14.08.2016.
 */
public class RequestService {
    public static Logger logger = Logger.getLogger(RequestService.class);
    private RequestDao requestDao;

    public RequestService() {
        requestDao = InMemoryRequestDao.instance;
    }

    public Request getById(Long id){
        return requestDao.getById(id);
    }

    public Request getByClientId(Long id){
        return requestDao.getByClientId(id);
    }

    public Collection<Request> getAllClientsRequests(Long id, int offset, int noOfRecords){
        return requestDao.getAllClientsRequests(id, offset, noOfRecords);
    }

    public Collection<Request> getAdminRequests(int offset, int noOfRecords) {
        return requestDao.getAdminRequests(offset, noOfRecords);
    }

    public void createNew(Long clientID, Long roomID, RoomType type, Date startDate, Date endDate,
                          int personsCount, StatusRequest status) {
        Request request = new Request();
        Timestamp reqDate = null;
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);
        try {
            reqDate = new Timestamp(sdf.parse(currentTime).getTime());
        } catch (ParseException e) {
            logger.error("SQL exception in creating request", e);
        }
        request.setClientID(clientID);
        request.setRoomID(roomID);
        request.setRoomType(type);
        request.setRequestDate(reqDate);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setPersonsCount(personsCount);
        request.setStatusRequest(status);

        requestDao.save(request);

    }

    public void cancel(Long id){
         requestDao.cancel(id);
    }

    public void approve(Long reqId, Long roomId){
        requestDao.approve(reqId, roomId);
    }

    public Collection<Request> getAll(int offset, int noOfRecords){
        return requestDao.getAll(offset, noOfRecords);
    }

    public Map<Long, Integer> countClientRequest(int offset, int noOfPages){
        return requestDao.countClientRequest(offset, noOfPages);
    }

    public int getNoOfRecords(){
        return requestDao.getNoOfRecords();
    }
}
