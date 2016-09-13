package levina.web.service.logic;

import levina.web.dao.RoomDao;
import levina.web.dao.impl.InMemoryRoomDao;
import levina.web.model.Room;
import levina.web.model.enums.RoomType;

import java.sql.Date;
import java.util.Collection;

/**
 * Created by MY on 18.08.2016.
 */
public class RoomService {
    private RoomDao roomDao;

    public RoomService() {
        roomDao = InMemoryRoomDao.instance;
    }

    public Collection<Room> getAvailableRooms(Date start, Date end, int personsCount, RoomType roomType){
        return roomDao.getRoomsByParameters(start,end,personsCount,roomType);
    }

    public Room getById(Long roomId){
        return roomDao.getById(roomId);
    }

    public Room getByType(RoomType roomType){
        return roomDao.getByType(roomType);
    }

    public Collection<Room> getAllAvailableRooms(){
        return roomDao.getAllAvailableRooms();
    }

}
