package levina.web.service.logic;

import levina.web.dao.RoomDao;
import levina.web.dao.impl.InMemoryRoomDao;
import levina.web.model.Request;
import levina.web.model.Room;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.List;

/**
 * RoomService connect dao level with logic
 */
public class RoomService {
    public static Logger logger = Logger.getLogger(RoomService.class);
    private RoomDao roomDao;

    public RoomService() {
        try {
            roomDao = InMemoryRoomDao.getInstance();
        } catch (Exception e) {
            logger.error("Exception in getting roomDao instance", e);
        }
    }

    /**
     * List of available rooms, that satisfied by params
     * @param request
     * @return List
     */
    public List<Room> getAvailableRooms(Request request) {
        return roomDao.getRoomsByParameters(
                request.getStartDate(),
                request.getPersonsCount(),
                request.getRoomType()
        );
    }

    public Room getById(Long roomId) {
        return roomDao.getById(roomId);
    }

    /**
     * List of all available rooms
     * @return Collection
     */
    public Collection<Room> getAllAvailableRooms() {
        return roomDao.getAllAvailableRooms();
    }

}

