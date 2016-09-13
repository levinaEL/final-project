package levina.web.dao;

import levina.web.model.Room;
import levina.web.model.enums.RoomType;

import java.sql.Date;
import java.util.Collection;

/**
 * Created by MY on 18.08.2016.
 */
public interface RoomDao {
    Room getById(Long roomId);

    Room getByType(RoomType roomType);

    Collection<Room> getRoomsByParameters(Date start, Date end, int personsCount, RoomType roomType);

    Collection<Room> getAllAvailableRooms();
}
