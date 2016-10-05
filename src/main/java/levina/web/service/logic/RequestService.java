package levina.web.service.logic;

import levina.web.dao.RequestDao;
import levina.web.dao.impl.InMemoryRequestDao;
import levina.web.model.Request;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;

/**
 * RequestService connect dao level with logic
 */
public class RequestService {
    public static Logger logger = Logger.getLogger(RequestService.class);
    private RequestDao requestDao;

    public RequestService() {
        try {
            requestDao = InMemoryRequestDao.getInstance();
        } catch (Exception e) {
            logger.error("Exception in getting requestDao instance", e);
        }
    }

    public Request getById(Long id){
        return requestDao.getById(id);
    }

    public Request getByClientId(Long id){
        return requestDao.getByClientId(id);
    }

    /**
     *
     * @param id - client id
     * @param offset      - use for pagination, number of start record on the page
     * @param noOfRecords - use for pagination, number of records per page
     * @return Collection
     */
    public Collection<Request> getAllClientsRequests(Long id, int offset, int noOfRecords){
        return requestDao.getAllClientsRequests(id, offset, noOfRecords);
    }

    /**
     *Get all records that should be process by admin
     * @param offset      - use for pagination, number of start record on the page
     * @param noOfRecords - use for pagination, number of records per page
     * @return Collection
     */
    public Collection<Request> getAdminRequests(int offset, int noOfRecords) {
        return requestDao.getAdminRequests(offset, noOfRecords);
    }

    /**
     * Create new request
     * @param request
     */
    public void createNew(Request request) {
        request.setRequestDate(new Timestamp(System.currentTimeMillis()));
        requestDao.save(request);
    }

    /**
     * Cancel request
     * @param id - client id
     */
    public void cancel(Long id){
         requestDao.cancel(id);
    }

    /**
     * Approve request
     * @param reqId - request id
     * @param roomId - number of the room, that need to be set
     */
    public void approve(Long reqId, Long roomId){
        requestDao.approve(reqId, roomId);
    }

    public Collection<Request> getAll(int offset, int noOfRecords){
        return requestDao.getAll(offset, noOfRecords);
    }

    /**
     *
     * @param offset      - use for pagination, number of start record on the page
     * @param noOfPages - use for pagination, number of records per page
     * @return Map: key - client id, value -count requests
     */
    public Map<Long, Integer> countClientRequest(int offset, int noOfPages){
        return requestDao.countClientRequest(offset, noOfPages);
    }

    public int getNoOfRecords(){
        return requestDao.getNoOfRecords();
    }
}
