package levina.web.dao;

import levina.web.model.Room;
import levina.web.model.enums.RoomType;

import java.sql.Date;
import java.util.List;

/**
 * RoomDao defined methods which will be used during connection to DB, table room
 */
public interface RoomDao {
    Room getById(Long roomId);

    List<Room> getRoomsByParameters(Date start, int personsCount, RoomType roomType);

    List<Room> getAllAvailableRooms();
}
