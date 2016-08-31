package levina.web.dao.impl;

import levina.web.dao.DBConnectionPool;
import levina.web.dao.RoomDao;
import levina.web.model.Room;
import levina.web.model.enums.RoomType;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by MY on 18.08.2016.
 */
public class InMemoryRoomDao implements RoomDao {
    public static Logger logger = Logger.getLogger(InMemoryClientDao.class);
    public static volatile InMemoryRoomDao instance = new InMemoryRoomDao();
    public DBConnectionPool dbConnectionPool;


    private InMemoryRoomDao() {
    }

    @Override
    public Room getById(Long roomId) {
        String selectTableSQL = "SELECT r.*, t.cost FROM room r JOIN type_room t USING (room_type)"
                + "WHERE r.room_id = ?";
        ResultSet rs;
        Room room = null;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);
            preparedStatement.setLong(1, roomId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("room_id");
                int numberSeats = rs.getInt("numb_seats");
                double cost = rs.getDouble("cost");
                RoomType roomType = RoomType.valueOf(rs.getString("room_type").toUpperCase());

                room = new Room();

                room.setRoomID(id);
                room.setNumberSeats(numberSeats);
                room.setCost(cost);
                room.setRoomType(roomType);
            }
            preparedStatement.close();
            rs.close();
            dbConnectionPool.freeConnection(connection);
        } catch (SQLException e) {
            logger.error("SQL exception in getting room by id", e);
        } catch (Exception e) {
            logger.error("Exception in getting room by id", e);
        }
        return room;
    }

    public void getAvailableRoomsTable(Date start, Date end) {

        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            String createView =
                    "CREATE  OR REPLACE VIEW AVAILABLE_ROOMS " +
            "AS (SELECT r.room_id, r.numb_seats, r.room_type, t.cost, req.room_id AS req_room, req.end_date" +
                            " FROM room r JOIN type_room t USING (room_type) " +
                    "LEFT JOIN requests req ON (req.room_id = r.room_id) " +
                    "GROUP BY r.room_id HAVING (req.room_id IS NULL)" +
                    " OR (MAX(req.end_date) < ?)) ";

            PreparedStatement preparedStatement = connection.prepareStatement(createView);
            preparedStatement.setDate(1, start);

            preparedStatement.execute();
            dbConnectionPool.freeConnection(connection);
        } catch (SQLException e) {
            logger.error("SQL exception in getting available rooms", e);
        } catch (Exception e) {
            logger.error("Exception in getting available rooms", e);
        }
    }

    public Collection getRoomsByParameters(Date start, Date end, int personsCount, RoomType roomType) {
        getAvailableRoomsTable(start, end);
        Collection<Room> rooms = new ArrayList<>();

        String selectTableSQL = "SELECT * FROM available_rooms  WHERE  " +
                " numb_seats>=? AND room_type=?";
        ResultSet rs;
        try {
            dbConnectionPool = DBConnectionPool.getInstance();
            Connection connection = dbConnectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectTableSQL);

            preparedStatement.setInt(1, personsCount);
            preparedStatement.setString(2, String.valueOf(roomType));

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long roomID = rs.getLong("room_id");
                double cost = rs.getDouble("cost");
                int numberSeats = rs.getInt("numb_seats");
                RoomType type = RoomType.valueOf(rs.getString("room_type").toUpperCase());

                Room room = new Room();
                room.setRoomID(roomID);
                room.setCost(cost);
                room.setRoomType(type);
                room.setNumberSeats(numberSeats);

                rooms.add(room);
            }

            preparedStatement.close();
            rs.close();
            dbConnectionPool.freeConnection(connection);

        } catch (SQLException e) {
            logger.error("SQL exception in getting available rooms", e);
        } catch (Exception e) {
            logger.error("Exception in getting available rooms", e);
        }
        return rooms;
    }
}
